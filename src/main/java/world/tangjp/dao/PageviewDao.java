package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.Pageview;

/**
 * 分页数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface PageviewDao extends BaseMapper<Pageview> {

}
