package world.tangjp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.tangjp.dao.SymptomDao;
import world.tangjp.entity.Illness;
import world.tangjp.entity.SymptomLog;
import world.tangjp.utils.Assert;
import world.tangjp.utils.BeanUtil;
import world.tangjp.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 症状记录服务类
 * 
 * @author Tangjp
 */
@Service
public class SymptomService extends BaseService<SymptomLog> {

    @Autowired
    private SymptomDao symptomDao;

    /**
     * 根据症状关键词查询匹配的疾病
     */
    public List<Illness> searchBySymptom(String keyword) {
        // 1. 分割并清理关键词
        List<String> keywords = Arrays.asList(keyword.trim().split("\\s+"));
        
        // 2. 调用DAO层执行查询
        return symptomDao.queryByKeywords(keywords);
    }

    /**
     * 查询用户的症状记录
     */
    @Override
    public List<SymptomLog> query(SymptomLog symptomLog) {
        QueryWrapper<SymptomLog> wrapper = new QueryWrapper<>();
        if (Assert.notEmpty(symptomLog.getUserId())) {
            wrapper.eq("user_id", symptomLog.getUserId());
        }
        wrapper.orderByDesc("create_time");
        List<SymptomLog> result = symptomDao.selectList(wrapper);
        return result;
    }

    /**
     * 根据当前登录用户id
     * 去查询相应用户匹配的症状
     * */
    public List<Illness> queryByUserId(Integer userId) {
        return symptomDao.queryByUserId(userId); 
    }

    /**
     * 保存症状记录
     */
    @Override
    public SymptomLog save(SymptomLog symptomLog) {
        if (Assert.isEmpty(symptomLog)) {
            throw new IllegalArgumentException("症状记录不能为空");
        }
        symptomDao.insert(symptomLog);
        return symptomLog;
    }

    @Override
    public SymptomLog get(Serializable id) {
        return symptomDao.selectById(id);
    }

    @Override
    public List<SymptomLog> all() {
        return symptomDao.selectList(null);
    }

    @Override
    public int delete(Serializable id) {
        return symptomDao.deleteById(id); 
    }
}