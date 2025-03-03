package world.tangjp.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.tangjp.dao.IllnessDao;
import world.tangjp.entity.*;
import world.tangjp.utils.Assert;
import world.tangjp.utils.BeanUtil;
import world.tangjp.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 疾病服务类
 *
 * @author Tangjp
 */
@Service
public class IllnessService extends BaseService<Illness> {

    @Autowired
    protected IllnessDao illnessDao;

    /**
     * 动态条件查询疾病信息
     * @param o 查询条件对象（非空时生效）
     * @return 符合条件的疾病列表（无结果时返回空列表）
     * 
     * 实现逻辑：
     * 1. 创建MyBatis Plus查询包装器
     * 2. 当传入对象非空时：
     *    - 将对象属性转换为Map结构
     *    - 遍历Map中的每个属性：
     *      * 跳过空值属性
     *      * 将驼峰属性名转换为下划线字段名
     *      * 添加等值查询条件（WHERE field = value）
     * 3. 执行查询并返回结果
     */
    @Override
    public List<Illness> query(Illness o) {
        QueryWrapper<Illness> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return illnessDao.selectList(wrapper);
    }

    /**
     * 查询全部疾病记录
     * @return 所有疾病数据列表（按默认排序）
     * 
     * 实现方式：
     * 通过调用query方法并传入null参数，触发无条件查询
     * 等效于执行 SQL: SELECT * FROM illness
     */
    @Override
    public List<Illness> all() {
        return query(null);
    }

    /**
     * 保存/更新疾病信息
     * @param o 疾病实体对象
     * @return 操作后的完整疾病对象
     * 
     * 实现逻辑：
     * 1. ID为空时执行插入操作
     * 2. ID非空时执行更新操作
     * 3. 返回最新数据库记录
     */
    @Override
    public Illness save(Illness o) {
        if (Assert.isEmpty(o.getId())) {
            illnessDao.insert(o);
        } else {
            illnessDao.updateById(o);
        }
        return illnessDao.selectById(o.getId());
    }

    /**
     * 根据ID获取单个疾病
     * @param id 疾病记录主键
     * @return 对应ID的疾病对象（不存在时返回null）
     */
    @Override
    public Illness get(Serializable id) {
        return illnessDao.selectById(id);
    }

    /**
     * 根据ID删除疾病记录
     * @param id 要删除的记录主键
     * @return 受影响的行数（通常1表示成功，0表示失败）
     */
    @Override
    public int delete(Serializable id) {
        return illnessDao.deleteById(id);
    }

    /**
     * 分页查询疾病信息（带搜索条件）
     * @param kind 疾病分类ID（null表示不限制）
     * @param illnessName 疾病名称关键字（支持模糊搜索）
     * @param page 当前页码（从1开始）
     * @return 包含两个属性的map：
     *         illness: 当前页数据列表（带分类名称和浏览量）
     *         size: 总页数
     * 
     * 特别说明：
     * 1. 每页固定显示9条记录
     * 2. 当同时存在分类和搜索词时，使用SQL拼接条件
     * 3. 结果集包含分类名称转换和浏览量统计
     */
    public Map<String, Object> findIllness(Integer kind, String illnessName, Integer page) {
        Map<String, Object> map = new HashMap<>(4);
        QueryWrapper<Illness> illnessQueryWrapper = new QueryWrapper<>();
        if (Assert.notEmpty(illnessName)) {
            illnessQueryWrapper
                    .like("illness_name", illnessName)
                    .or()
                    .like("include_reason", illnessName)
                    .or()
                    .like("illness_symptom", illnessName)
                    .or()
                    .like("special_symptom", illnessName);
        }
        if (kind != null) {
            if (Assert.notEmpty(illnessName)) {
                illnessQueryWrapper.last("and (kind_id = " + kind + ") ORDER BY create_time DESC limit " + (page - 1) * 9 + "," + page * 9);
            } else {
                illnessQueryWrapper.eq("kind_id", kind);
                illnessQueryWrapper.orderByDesc("create_time");
                illnessQueryWrapper.last("limit " + (page - 1) * 9 + "," + page * 9);
            }
        } else {
            illnessQueryWrapper.orderByDesc("create_time");
            illnessQueryWrapper.last("limit " + (page - 1) * 9 + "," + page * 9);

        }
        int size = illnessDao.selectMaps(illnessQueryWrapper).size();
        List<Map<String, Object>> list = illnessDao.selectMaps(illnessQueryWrapper);
        list.forEach(l -> {
            Integer id = MapUtil.getInt(l, "id");
            Pageview pageInfo = pageviewDao.selectOne(new QueryWrapper<Pageview>().eq("illness_id", id));
            l.put("kindName", "暂无归属类");
            l.put("create_time", MapUtil.getDate(l, "create_time"));
            l.put("pageview", pageInfo == null ? 0 : pageInfo.getPageviews());
            Integer kindId = MapUtil.getInt(l, "kind_id");
            if (Assert.notEmpty(kindId)) {
                IllnessKind illnessKind = illnessKindDao.selectById(kindId);
                if (Assert.notEmpty(illnessKind)) {
                    l.put("kindName", illnessKind.getName());
                }
            }
        });
        map.put("illness", list);
        map.put("size", size < 9 ? 1 : size / 9 + 1);
        return map;
    }

    /**
     * 获取疾病详细信息（含关联药品）
     * @param id 疾病ID
     * @return 包含两个属性的map：
     *         illness: 疾病详细信息
     *         medicine: 关联药品列表
     * 
     * 附带功能：
     * 1. 每次访问自动增加该疾病的浏览量
     * 2. 自动关联查询对应药品信息
     */
    public Map<String, Object> findIllnessOne(Integer id) {
        Illness illness = illnessDao.selectOne(new QueryWrapper<Illness>().eq("id", id));
        List<IllnessMedicine> illnessMedicines = illnessMedicineDao.selectList(new QueryWrapper<IllnessMedicine>().eq("illness_id", id));
        List<Medicine> list = new ArrayList<>(4);
        Map<String, Object> map = new HashMap<>(4);
        Pageview illness_id = pageviewDao.selectOne(new QueryWrapper<Pageview>().eq("illness_id", id));
        if (Assert.isEmpty(illness_id)) {
            illness_id = new Pageview();
            illness_id.setIllnessId(id);
            illness_id.setPageviews(1);
            pageviewDao.insert(illness_id);
        } else {
            illness_id.setPageviews(illness_id.getPageviews() + 1);
            pageviewDao.updateById(illness_id);
        }
        map.put("illness", illness);

        if (CollUtil.isNotEmpty(illnessMedicines)) {
            illnessMedicines.forEach(illnessMedicine -> {
                Medicine medicine = medicineDao.selectOne(new QueryWrapper<Medicine>().eq("id", illnessMedicine.getMedicineId()));
                if (ObjectUtil.isNotNull(medicine)) {
                    list.add(medicine);
                }
            });
            map.put("medicine", list);
        }
        return map;
    }

    /**
     * 自定义条件查询单个疾病
     * @param queryWrapper 自定义查询条件
     * @return 符合条件的第一条记录（无结果返回null）
     */
    public Illness getOne(QueryWrapper<Illness> queryWrapper) {
        return illnessDao.selectOne(queryWrapper);
    }
}