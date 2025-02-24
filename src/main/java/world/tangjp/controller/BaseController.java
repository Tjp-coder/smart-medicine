package world.tangjp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import world.tangjp.component.EmailClient;
import world.tangjp.result.RespResult;
import world.tangjp.entity.IllnessKind;
import world.tangjp.entity.User;
import world.tangjp.service.*;
import world.tangjp.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 基础控制器
 * @author Tangjp
 */
@Api(tags = "基础接口")
public class BaseController<T> {

    @Autowired
    protected ApiService apiService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected IllnessKindService illnessKindService;
    @Autowired
    protected IllnessMedicineService illnessMedicineService;
    @Autowired
    protected IllnessService illnessService;
    @Autowired
    protected MedicalNewsService medicalNewsService;
    @Autowired
    protected MedicineService medicineService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected FeedbackService feedbackService;

    @Autowired
    protected BaseService<T> service;

    @Autowired
    protected EmailClient emailClient;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected User loginUser;
    protected List<IllnessKind> kindList;

    @ApiOperation("保存数据")
    @ApiImplicitParam(name = "obj", value = "保存的对象", required = true)
    @ResponseBody
    @PostMapping("save")
    public RespResult save(T obj) {
        if (Assert.isEmpty(obj)) {
            return RespResult.fail("保存对象不能为空");
        }
        obj = service.save(obj);
        return RespResult.success("保存成功", obj);
    }

    @ApiOperation("删除数据") 
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, example = "1")
    @ResponseBody
    @PostMapping("/delete")
    public RespResult delete(Integer id) {
        if (Assert.isEmpty(id)) {
            return RespResult.fail("删除ID不能为空");
        }
        if (service.delete(id) == 0) {
            T t = service.get(id);
            if (Assert.isEmpty(t)) {
                return RespResult.notFound("数据不存在");
            }
            return RespResult.fail("删除失败");
        }
        return RespResult.success("删除成功");
    }

    /**
     * 在每个子类方法调用之前先调用
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
        loginUser = (User) session.getAttribute("loginUser");
        session.setAttribute("kindList", illnessKindService.findList());
    }
}
