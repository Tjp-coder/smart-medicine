package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.IllnessMedicine;

/**
 * 疾病药品数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface IllnessMedicineDao extends BaseMapper<IllnessMedicine> {

}
