package world.tangjp.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SymptomSearchDTO {
    @NotBlank(message = "症状关键词不能为空")
    private String keyword;
}
