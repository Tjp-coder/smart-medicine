package world.tangjp.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SymptomSaveDTO {
    @NotBlank(message = "症状关键词不能为空")
    private String keyword;
    
    // 不需要前端传入illnessIds，而是在后端根据keyword查询后设置
}
