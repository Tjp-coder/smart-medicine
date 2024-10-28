package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.History;

/**
 * 历史数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface HistoryDao extends BaseMapper<History> {

}
