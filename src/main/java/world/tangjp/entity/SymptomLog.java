package world.tangjp.entity;


import java.time.LocalDateTime;

public class SymptomLog {
    private Integer id;
    private Integer userId;       // 关联user.id
    private String keyword;
    private String firstIllness;  // 存储第一个匹配疾病名称
    private String illnessIds;    // 逗号分隔的疾病ID
    private LocalDateTime createTime;
}