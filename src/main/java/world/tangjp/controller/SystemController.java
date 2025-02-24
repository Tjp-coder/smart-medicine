package world.tangjp.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import world.tangjp.constant.MedicalConstants;
import world.tangjp.entity.*;
import world.tangjp.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.*;

/**
 * 系统跳转控制器

 * @author Tangjp
 */
@Api(tags = "系统管理接口")
@Controller
public class SystemController extends BaseController<User> {

    /**
     * 首页
     */
    @ApiOperation("首页")
    @GetMapping("/index.html")
    public String index(Map<String, Object> map) {
        return "index";
    }

    /**
     * 智能医生
     */
    @ApiOperation("智能医生")
    @GetMapping("/doctor")
    public String doctor(Map<String, Object> map) {
        return "doctor";
    }

    /**
     * 退出登录
     */
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/index.html";
    }

    /**
     * 所有反馈
     */
    @ApiOperation("查看反馈")
    @GetMapping("/all-feedback")
    public String feedback(Map<String, Object> map) {
        List<Feedback> feedbackList = feedbackService.all();

        map.put("feedbackList", feedbackList);
        return "all-feedback";
    }

    /**
     * 我的资料
     */
    @ApiOperation("个人资料")
    @GetMapping("/profile")
    public String profile(Map<String, Object> map) {
        return "profile";
    }

    /**
     * 查询相关疾病，传入kind分类参数，查找某分类的疾病
     *
     * @param map 用于存储数据并传递给前端视图的参数容器
     * @param kind 疾病分类的ID
     * @param illnessName 疾病名称的搜索关键字（可选）
     * @param page 当前分页页码，默认为1
     * @return 返回前端页面名称"search-illness"
     */
    @ApiOperation("查询相关疾病")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "kind", value = "疾病分类ID"),
        @ApiImplicitParam(name = "illnessName", value = "疾病名称关键字"),
        @ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1")
    })
    @GetMapping("findIllness")
    public String findIllness(Map<String, Object> map, Integer kind, String illnessName, Integer page) {
        // 处理分页参数，如果未传入页码，则默认为第一页
        page = ObjectUtils.isEmpty(page) ? 1 : page;

        // 调用服务层方法查询疾病信息，结果存储到 illness Map 中
        Map<String, Object> illness = illnessService.findIllness(kind, illnessName, page);

        // 根据传入的分类参数和疾病名称设置页面的标题信息
        if (Assert.notEmpty(kind)) {
            // 当kind存在时，设置标题为分类名称 + 搜索结果
            map.put("title", illnessKindService.get(kind).getName() +
                    (illnessName == null ? "" : ('"' + illnessName + '"' + "的搜索结果")));
        } else {
            // 当kind为空时，标题为 "全部" 或 疾病名称的搜索结果
            map.put("title", illnessName == null ? "全部" : ('"' + illnessName + '"' + "的搜索结果"));
        }

        // 如果用户已登录且kind不为空，记录操作历史
        if (loginUser != null && kind != null) {
            historyService.insetOne(
                    loginUser.getId(),
                    MedicalConstants.TYPE_OPERATE,
                    illnessKindService.get(kind).getId() + "," +
                            (Assert.isEmpty(illnessName) ? "无" : illnessName)
            );
        }

        // 如果用户已登录且传入了疾病名称，记录疾病搜索历史
        if (loginUser != null && Assert.notEmpty(illnessName)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illnessName);
        }

        // 将疾病信息和其他参数加入到 map 中，传递给前端页面
        map.putAll(illness); // 疾病数据
        map.put("page", page); // 当前页码
        map.put("kind", kind); // 分类ID
        map.put("illnessName", illnessName); // 搜索关键字
        map.put("kindList", illnessKindService.findList()); // 疾病分类列表
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId())); // 历史记录

        // 返回前端页面的名称
        return "search-illness";
    }


    /**
     * 查询疾病详情
     */
    @GetMapping("findIllnessOne")
    public String findIllnessOne(Map<String, Object> map, Integer id) {
        Map<String, Object> illnessOne = illnessService.findIllnessOne(id);
        Illness illness = illnessService.get(id);
        if (loginUser != null) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illness.getIllnessName());
        }
        map.putAll(illnessOne);
        return "illness-reviews";
    }

    /**
     * 查询药品详情
     */
    @GetMapping("findMedicineOne")
    public String findMedicineOne(Map<String, Object> map, Integer id) {
        Medicine medicine = medicineService.get(id);
//        historyService.insetOne(loginUser.getId(),MedicalConstants.TYPE_MEDICINE,medicine.getMedicineName());
        map.put("medicine", medicine);
        return "medicine";
    }

    /**
     * 查找药品
     */
    @GetMapping("findMedicines")
    public String findMedicines(Map<String, Object> map, String nameValue, Integer page) {
        // 处理page
        page = ObjectUtils.isEmpty(page) ? 1 : page;
        if (loginUser != null && Assert.notEmpty(nameValue)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_MEDICINE, nameValue);
        }
        map.putAll(medicineService.getMedicineList(nameValue, page));
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId()));
        map.put("title", nameValue);
        return "illness";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("globalSelect")
    public String globalSelect(Map<String, Object> map, String nameValue) {
        nameValue = nameValue.replace("，", ",");
        List<String> idArr = Arrays.asList(nameValue.split(","));
        //首先根据关键字去查询
        Set<Illness> illnessSet = new HashSet<>();
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_name", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("special_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        map.put("illnessSet", illnessSet);
        return "index";
    }

    /**
     * 添加疾病页面
     */
    @ApiOperation("添加/编辑疾病")
    @ApiImplicitParam(name = "id", value = "疾病ID(编辑时传入)")
    @GetMapping("add-illness")
    public String addIllness(Integer id, Map<String, Object> map) {
        Illness illness = new Illness();
        if (Assert.notEmpty(id)) {
            illness = illnessService.get(id);
        }
        List<IllnessKind> illnessKinds = illnessKindService.all();
        map.put("illness", illness);
        map.put("kinds", illnessKinds);
        return "add-illness";
    }

    @ApiOperation("添加/编辑药品")
    @ApiImplicitParam(name = "id", value = "药品ID(编辑时传入)")
    @GetMapping("add-medical")
    public String addMedical(Integer id, Map<String, Object> map) {
        // 获取所有的疾病信息
        List<Illness> illnesses = illnessService.all();

        // 创建一个空的药品对象
        Medicine medicine = new Medicine();

        // 如果有传入药品ID，则加载该药品信息并关联相关疾病
        if (Assert.notEmpty(id)) {
            medicine = medicineService.get(id);
            for (Illness illness : illnesses) {
                List<IllnessMedicine> query = illnessMedicineService.query(IllnessMedicine.builder().medicineId(id).illnessId(illness.getId()).build());
                if (Assert.notEmpty(query)) {
                    illness.setIllnessMedicine(query.get(0));
                }
            }
        }

        // 将疾病集合和药品对象放入模型中
        map.put("illnesses", illnesses);
        map.put("medicine", medicine);

        // 返回视图名称
        return "add-medical";
    }


    /**
     * 疾病管理页面
     */
    @ApiOperation("疾病管理列表")
    @GetMapping("all-illness")
    public String allIllness(Map<String, Object> map) {
        List<Illness> illnesses = illnessService.all();
        for (Illness illness : illnesses) {
            illness.setKind(illnessKindService.get(illness.getKindId()));
        }
        map.put("illnesses", illnesses);
        return "all-illness";
    }

    /**
     * 药品管理页面
     */
    @ApiOperation("药品管理列表")
    @GetMapping("all-medical")
    public String allMedical(Map<String, Object> map) {
        List<Medicine> medicines = medicineService.all();
        map.put("medicines", medicines);
        return "all-medical";
    }
}
