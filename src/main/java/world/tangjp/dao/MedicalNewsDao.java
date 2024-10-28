package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.MedicalNews;

/**
 * 咨询数据库访问
 *
 * @author Tangjp
 */
@Repository
public interface MedicalNewsDao extends BaseMapper<MedicalNews> {

}
