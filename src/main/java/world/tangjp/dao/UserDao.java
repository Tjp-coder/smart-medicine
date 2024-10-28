package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.User;

/**
 * 用户数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}
