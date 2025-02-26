package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.History;

/**
 * 历史数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface HistoryDao extends BaseMapper<History> {
    
    /**
     * 插入历史记录
     * 
     * @param history 历史记录对象
     * @return 影响行数
     */
    @Insert("INSERT INTO history(user_id, keyword, operate_type) VALUES(#{userId}, #{keyword}, #{operateType})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertHistory(History history);
}
