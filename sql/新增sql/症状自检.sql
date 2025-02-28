-- 新增症状搜索记录表（与现有表兼容）
CREATE TABLE `symptom_log` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `user_id` int DEFAULT NULL COMMENT '游客无user_id',
                               `keyword` varchar(255) NOT NULL COMMENT '症状关键词',
                               `matched_illness_ids` varchar(255) DEFAULT NULL COMMENT '逗号分隔的疾病ID',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `fk_user_id` (`user_id`),
                               CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `symptom_log` VALUES
                              (1, 4, '头痛 发热', '2,4', '2025-02-27 15:00:00'),
                              (2, NULL, '口腔溃疡', '9,15', '2025-02-27 15:05:00'),
                              (3, 6, '胃痛 恶心', '8', '2025-02-27 15:10:00'),
                              (4, 5, '皮肤瘙痒', '13', '2025-02-27 15:15:00');



INSERT INTO `pageview` VALUES
                           (16, 10, 2),   -- 风寒感冒浏览量+10
                           (17, 5, 3),    -- 扁桃体发炎浏览量+5
                           (18, 8, 9),    -- 口腔溃疡浏览量+8
                           (19, 3, 13);   -- 湿疹浏览量+3