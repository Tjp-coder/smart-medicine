package world.tangjp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * 症状实体
 *
 * @author Tangjp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("symptom_log")  // 确认这里的表名是否与数据库一致
public class SymptomLog {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @TableField("user_id")
    private Integer userId;
    
    private String keyword;
    
    @TableField("matched_illness_ids")  // 修正字段名映射
    private String matchedIllnessIds;
    
    @TableField("create_time")
    private LocalDateTime createTime;
}