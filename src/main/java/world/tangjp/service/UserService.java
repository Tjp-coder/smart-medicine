package world.tangjp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.tangjp.dao.UserDao;
import world.tangjp.entity.User;
import world.tangjp.utils.Assert;
import world.tangjp.utils.BeanUtil;
import world.tangjp.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户服务类
 *
 * @author Tangjp
 */
@Service
public class UserService extends BaseService<User> {

    @Autowired
    protected UserDao userDao;

    @Override
    public List<User> query(User o) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return userDao.selectList(wrapper);
    }

    @Override
    public List<User> all() {
        return query(null);
    }

    @Override
    public User save(User o) {
        if (Assert.isEmpty(o.getId())) {
            userDao.insert(o);
        } else {
            userDao.updateById(o);
        }
        return userDao.selectById(o.getId());
    }

    @Override
    public User get(Serializable id) {
        return userDao.selectById(id);
    }

    @Override
    public int delete(Serializable id) {
        return userDao.deleteById(id);
    }
}