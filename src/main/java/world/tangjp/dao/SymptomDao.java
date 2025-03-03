package world.tangjp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import world.tangjp.entity.Illness;
import world.tangjp.entity.SymptomLog;

import java.util.List;

/**
 * 症状记录数据库访问
 * 
 * @author Tangjp
 */
@Repository
public interface SymptomDao extends BaseMapper<SymptomLog> {
    
    @Select("<script>" +
            "SELECT * FROM illness WHERE 1=1 " +
            "<if test='keywords != null and keywords.size > 0'>" +
            " AND (" +
            "<foreach item='keyword' collection='keywords' separator=' OR '>" +
            "(" +
            "illness_symptom LIKE CONCAT('%', #{keyword}, '%') " +
            "OR illness_name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR include_reason LIKE CONCAT('%', #{keyword}, '%') " +
            "OR special_symptom LIKE CONCAT('%', #{keyword}, '%')" +
            ")" +
            "</foreach>" +
            ")" +
            "</if>" +
            "</script>")
List<Illness> queryByKeywords(@Param("keywords") List<String> keywords);

    @Select("SELECT i.* " +
            "FROM illness i " +
            "WHERE i.illness_name LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR i.include_reason LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR i.illness_symptom LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR i.special_symptom LIKE CONCAT('%', #{keyword}, '%')")
    List<Illness> queryBySymptom(String keyword);

    @Select("SELECT i.* FROM illness i " +
            "JOIN symptom_log s ON FIND_IN_SET(i.id, s.matched_illness_ids) " +
            "WHERE s.user_id = #{userId}")
    List<Illness> queryByUserId(Integer userId);
}