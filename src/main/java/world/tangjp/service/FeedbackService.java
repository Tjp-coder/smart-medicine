package world.tangjp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.tangjp.dao.FeedbackDao;
import world.tangjp.entity.Feedback;
import world.tangjp.utils.Assert;
import world.tangjp.utils.BeanUtil;
import world.tangjp.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 反馈服务类
 *
 * @author Tangjp
 */
@Service
public class FeedbackService extends BaseService<Feedback> {

    @Autowired
    protected FeedbackDao feedbackDao;

    @Override
    public List<Feedback> query(Feedback o) {
        QueryWrapper<Feedback> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return feedbackDao.selectList(wrapper);
    }

    @Override
    public List<Feedback> all() {
        return query(null);
    }

    @Override
    public Feedback save(Feedback o) {
        // 检查必要字段是否为空
        if (o.getName() == null || o.getEmail() == null || o.getTitle() == null || o.getContent() == null) {
            throw new IllegalArgumentException("name, email, title, content不能为空");
        }
        
        if (Assert.isEmpty(o.getId())) {
            feedbackDao.insertFeedback(o);
        } else {
            feedbackDao.updateById(o);
        }
        return feedbackDao.selectById(o.getId());
    }

    @Override
    public Feedback get(Serializable id) {
        return feedbackDao.selectById(id);
    }

    @Override
    public int delete(Serializable id) {
        return feedbackDao.deleteById(id);
    }
}