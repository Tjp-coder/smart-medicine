package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.Feedback;

/**
 * 反馈数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface FeedbackDao extends BaseMapper<Feedback> {

}
