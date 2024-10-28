package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.Illness;

/**
 * 疾病数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface IllnessDao extends BaseMapper<Illness> {

}
