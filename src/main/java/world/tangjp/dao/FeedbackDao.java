package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.Feedback;

/**
 * 反馈数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface FeedbackDao extends BaseMapper<Feedback> {
    
    /**
     * 插入反馈记录
     * 
     * @param feedback 反馈对象
     * @return 影响行数
     */
    @Insert("INSERT INTO feedback(name, email, title, content) VALUES(#{name}, #{email}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertFeedback(Feedback feedback);
}
