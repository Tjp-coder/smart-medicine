/*
 Navicat Premium Dump SQL

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : smart-medicine

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 27/02/2025 21:28:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_favorite
-- ----------------------------
DROP TABLE IF EXISTS `article_favorite`;
CREATE TABLE `article_favorite`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_fav_article`(`article_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_user_article`(`user_id` ASC, `article_id` ASC) USING BTREE,
  CONSTRAINT `fk_fav_article` FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_fav_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_favorite
-- ----------------------------
INSERT INTO `article_favorite` VALUES (1, 4, 1, '2025-02-27 14:45:15');
INSERT INTO `article_favorite` VALUES (2, 4, 2, '2025-02-27 14:45:15');

-- ----------------------------
-- Table structure for article_illness_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_illness_relation`;
CREATE TABLE `article_illness_relation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `illness_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_illness_article`(`article_id` ASC) USING BTREE,
  INDEX `fk_illness_rel`(`illness_id` ASC) USING BTREE,
  CONSTRAINT `fk_illness_article` FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_illness_rel` FOREIGN KEY (`illness_id`) REFERENCES `illness` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_illness_relation
-- ----------------------------
INSERT INTO `article_illness_relation` VALUES (1, 1, 2);
INSERT INTO `article_illness_relation` VALUES (2, 2, 13);

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (1, '预防', '2025-02-27 14:45:15');
INSERT INTO `article_tag` VALUES (2, '治疗', '2025-02-27 14:45:15');
INSERT INTO `article_tag` VALUES (3, '营养', '2025-02-27 14:45:15');
INSERT INTO `article_tag` VALUES (4, '中医', '2025-02-27 14:45:15');
INSERT INTO `article_tag` VALUES (5, '儿科', '2025-02-27 14:45:15');

-- ----------------------------
-- Table structure for article_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_relation`;
CREATE TABLE `article_tag_relation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_tag`(`article_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE,
  CONSTRAINT `fk_tag_article` FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tag_rel` FOREIGN KEY (`tag_id`) REFERENCES `article_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag_relation
-- ----------------------------
INSERT INTO `article_tag_relation` VALUES (1, 1, 1);
INSERT INTO `article_tag_relation` VALUES (2, 1, 3);
INSERT INTO `article_tag_relation` VALUES (3, 2, 2);
INSERT INTO `article_tag_relation` VALUES (4, 2, 3);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反馈用户',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反馈标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '反馈内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (6, '路人甲', '31952874@qq.com', '测试一号', '测试这个系统有问题吗？', '2022-05-03 16:13:59', '2022-05-03 16:13:59');
INSERT INTO `feedback` VALUES (7, '路人乙', '31952874@qq.com', '测试二号', '惆怅长岑长错错错错错错', '2022-05-03 16:14:20', '2022-05-03 16:14:20');
INSERT INTO `feedback` VALUES (8, 'tjp', '838219014@qq.com', '有bug咯', 'bug描述', '2024-10-28 11:03:57', '2024-10-28 11:03:57');
INSERT INTO `feedback` VALUES (9, '游客', '333', '测试游客反馈功能标题', '测试游客反馈功能内容', '2024-10-28 11:04:44', '2024-10-28 11:04:44');
INSERT INTO `feedback` VALUES (10, 'tjp ', '838219014@qq.com', '22', '2', '2024-12-27 00:11:34', '2024-12-27 00:11:34');
INSERT INTO `feedback` VALUES (11, '123', '123', '123', '123', '2025-02-26 11:14:42', '2025-02-26 11:14:42');
INSERT INTO `feedback` VALUES (12, '张三', 'zhangsan@example.com', '系统使用问题', '在使用系统时，发现保存历史记录功能无法正常工作，请尽快修复。', '2025-02-26 11:26:14', '2025-02-26 11:26:14');
INSERT INTO `feedback` VALUES (13, '张三', 'zhangsan@example.com', '系统使用问题', '在使用系统时，发现保存历史记录功能无法正常工作，请尽快修复。', '2025-02-26 11:56:17', '2025-02-26 11:56:17');
INSERT INTO `feedback` VALUES (14, 'DDD', 'DDD', 'DD', 'DDD', '2025-02-26 14:26:36', '2025-02-26 14:26:36');

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户搜索历史主键id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '搜索关键字',
  `operate_type` int NULL DEFAULT NULL COMMENT '类型：1搜索，2科目，3药品',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_history_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_history_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 188 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES (126, 4, '10,无', 1, '2022-05-03 16:09:34', '2022-05-03 16:09:34');
INSERT INTO `history` VALUES (127, 4, '10,无', 1, '2022-05-03 16:09:40', '2022-05-03 16:09:40');
INSERT INTO `history` VALUES (128, 4, '病毒性感冒', 2, '2022-05-03 16:09:48', '2022-05-03 16:09:48');
INSERT INTO `history` VALUES (129, 4, '10,无', 1, '2022-05-03 16:09:52', '2022-05-03 16:09:52');
INSERT INTO `history` VALUES (130, 4, '湿疹', 2, '2022-05-03 16:12:15', '2022-05-03 16:12:15');
INSERT INTO `history` VALUES (131, 4, '偏头痛', 2, '2022-05-03 16:12:49', '2022-05-03 16:12:49');
INSERT INTO `history` VALUES (132, 5, '7,无', 1, '2022-05-03 16:17:41', '2022-05-03 16:17:41');
INSERT INTO `history` VALUES (133, 5, '湿疹', 2, '2022-05-03 16:17:53', '2022-05-03 16:17:53');
INSERT INTO `history` VALUES (134, 5, '感冒', 2, '2022-05-03 16:18:08', '2022-05-03 16:18:08');
INSERT INTO `history` VALUES (135, 5, '17,无', 1, '2022-05-03 16:18:22', '2022-05-03 16:18:22');
INSERT INTO `history` VALUES (136, 5, '17,溃疡', 1, '2022-05-03 16:18:28', '2022-05-03 16:18:28');
INSERT INTO `history` VALUES (137, 5, '溃疡', 2, '2022-05-03 16:18:28', '2022-05-03 16:18:28');
INSERT INTO `history` VALUES (138, 5, '17,溃疡', 1, '2022-05-03 16:26:48', '2022-05-03 16:26:48');
INSERT INTO `history` VALUES (139, 5, '溃疡', 2, '2022-05-03 16:26:48', '2022-05-03 16:26:48');
INSERT INTO `history` VALUES (140, 5, '17,溃疡', 1, '2022-05-03 16:28:20', '2022-05-03 16:28:20');
INSERT INTO `history` VALUES (141, 5, '溃疡', 2, '2022-05-03 16:28:20', '2022-05-03 16:28:20');
INSERT INTO `history` VALUES (142, 5, '17,溃疡', 1, '2022-05-03 16:33:52', '2022-05-03 16:33:52');
INSERT INTO `history` VALUES (143, 5, '溃疡', 2, '2022-05-03 16:33:52', '2022-05-03 16:33:52');
INSERT INTO `history` VALUES (144, 5, '溃疡', 2, '2022-05-03 16:34:08', '2022-05-03 16:34:08');
INSERT INTO `history` VALUES (145, 5, '7,无', 1, '2022-05-03 16:37:57', '2022-05-03 16:37:57');
INSERT INTO `history` VALUES (146, 5, '9,无', 1, '2022-05-03 16:38:34', '2022-05-03 16:38:34');
INSERT INTO `history` VALUES (147, 5, '9,无', 1, '2022-05-03 16:41:59', '2022-05-03 16:41:59');
INSERT INTO `history` VALUES (148, 5, '9,无', 1, '2022-05-03 16:42:14', '2022-05-03 16:42:14');
INSERT INTO `history` VALUES (149, 5, '9,无', 1, '2022-05-03 16:42:45', '2022-05-03 16:42:45');
INSERT INTO `history` VALUES (150, 5, '9,无', 1, '2022-05-03 16:43:54', '2022-05-03 16:43:54');
INSERT INTO `history` VALUES (151, 5, '9,无', 1, '2022-05-03 16:44:26', '2022-05-03 16:44:26');
INSERT INTO `history` VALUES (152, 5, '9,无', 1, '2022-05-03 16:44:45', '2022-05-03 16:44:45');
INSERT INTO `history` VALUES (153, 5, '2,无', 1, '2022-05-03 16:44:51', '2022-05-03 16:44:51');
INSERT INTO `history` VALUES (154, 5, '2,无', 1, '2022-05-03 16:45:46', '2022-05-03 16:45:46');
INSERT INTO `history` VALUES (155, 5, '1', 3, '2022-05-07 15:34:34', '2022-05-07 15:34:34');
INSERT INTO `history` VALUES (156, 4, '牙周炎', 2, '2022-07-14 19:32:05', '2022-07-14 19:32:05');
INSERT INTO `history` VALUES (157, 4, '9,无', 1, '2022-07-14 19:32:52', '2022-07-14 19:32:52');
INSERT INTO `history` VALUES (158, 4, '1,无', 1, '2022-07-14 19:32:56', '2022-07-14 19:32:56');
INSERT INTO `history` VALUES (159, 4, '17,无', 1, '2022-07-14 19:32:59', '2022-07-14 19:32:59');
INSERT INTO `history` VALUES (160, 4, '湿疹', 2, '2024-10-28 16:53:20', '2024-10-28 16:53:20');
INSERT INTO `history` VALUES (161, 4, '湿疹', 2, '2024-10-28 16:54:56', '2024-10-28 16:54:56');
INSERT INTO `history` VALUES (162, 4, '湿疹', 2, '2024-10-28 16:55:04', '2024-10-28 16:55:04');
INSERT INTO `history` VALUES (163, 4, '胃溃疡', 2, '2024-10-28 16:55:46', '2024-10-28 16:55:46');
INSERT INTO `history` VALUES (164, 4, '口腔溃疡', 2, '2024-10-28 16:55:54', '2024-10-28 16:55:54');
INSERT INTO `history` VALUES (165, 4, '口腔溃疡', 2, '2024-10-28 20:18:23', '2024-10-28 20:18:23');
INSERT INTO `history` VALUES (166, 4, '病毒性感冒', 2, '2024-10-28 20:18:29', '2024-10-28 20:18:29');
INSERT INTO `history` VALUES (167, 4, '牙周炎', 2, '2024-10-28 20:25:11', '2024-10-28 20:25:11');
INSERT INTO `history` VALUES (168, 4, '风寒感冒', 2, '2024-12-28 00:59:08', '2024-12-28 00:59:08');
INSERT INTO `history` VALUES (169, 4, '风寒感冒', 2, '2024-12-28 01:01:31', '2024-12-28 01:01:31');
INSERT INTO `history` VALUES (170, 4, '风寒感冒', 2, '2025-02-14 11:13:13', '2025-02-14 11:13:13');
INSERT INTO `history` VALUES (171, 4, '口腔溃疡', 2, '2025-02-20 23:04:14', '2025-02-20 23:04:14');
INSERT INTO `history` VALUES (172, 4, '口腔溃疡', 2, '2025-02-22 18:33:07', '2025-02-22 18:33:07');
INSERT INTO `history` VALUES (174, 4, '3,无', 1, '2025-02-25 21:09:38', '2025-02-25 21:09:38');
INSERT INTO `history` VALUES (175, 4, '骨折', 2, '2025-02-25 21:09:41', '2025-02-25 21:09:41');
INSERT INTO `history` VALUES (177, 6, '口腔溃疡', 2, '2025-02-26 11:53:24', '2025-02-26 11:53:24');
INSERT INTO `history` VALUES (178, 6, '口腔溃疡', 2, '2025-02-26 11:53:29', '2025-02-26 11:53:29');
INSERT INTO `history` VALUES (179, 0, '高血压', 1, '2025-02-26 13:45:28', '2025-02-27 14:39:27');
INSERT INTO `history` VALUES (180, 6, '999', 2, '2025-02-26 14:27:59', '2025-02-26 14:27:59');
INSERT INTO `history` VALUES (181, 6, '999感冒灵颗粒', 2, '2025-02-26 14:28:18', '2025-02-26 14:28:18');
INSERT INTO `history` VALUES (182, 6, '感冒药', 2, '2025-02-26 14:28:43', '2025-02-26 14:28:43');
INSERT INTO `history` VALUES (183, 6, '999感冒灵颗粒', 2, '2025-02-26 14:28:48', '2025-02-26 14:28:48');
INSERT INTO `history` VALUES (184, 6, '开塞露', 2, '2025-02-26 14:29:07', '2025-02-26 14:29:07');
INSERT INTO `history` VALUES (185, 6, '风寒感冒', 2, '2025-02-26 14:29:23', '2025-02-26 14:29:23');
INSERT INTO `history` VALUES (186, 4, '口腔溃疡', 2, '2025-02-26 14:44:42', '2025-02-26 14:44:42');

-- ----------------------------
-- Table structure for illness
-- ----------------------------
DROP TABLE IF EXISTS `illness`;
CREATE TABLE `illness`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '疾病id',
  `kind_id` int NULL DEFAULT NULL COMMENT '疾病分类ID',
  `illness_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '疾病名字',
  `include_reason` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '诱发因素',
  `illness_symptom` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '疾病症状',
  `special_symptom` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '特殊症状',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_illness_kind`(`kind_id` ASC) USING BTREE,
  CONSTRAINT `fk_illness_kind` FOREIGN KEY (`kind_id`) REFERENCES `illness_kind` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of illness
-- ----------------------------
INSERT INTO `illness` VALUES (2, 10, '风寒感冒', '身体免疫力低下的情况下，体内有寒气入侵，导致感冒', '恶寒重、发热轻、无汗、头痛身痛、鼻塞流清涕、咳嗽吐稀白痰、口不渴或渴喜热饮、苔薄白', '恶寒重、发热轻、无汗、头痛身痛、畏寒', '2022-05-01 11:31:10', '2022-05-03 16:01:51');
INSERT INTO `illness` VALUES (3, 2, '扁桃体发炎', '扁桃体炎形成的原因与多种因素有关，包括感染因素、免疫因素、邻近器官的急性炎症等，细菌和病毒积存\n于扁桃体窝引起该病。扁桃体炎还可继发于某些急性传染病，如猩红热、麻疹、流感等。', '发热、咽部不适、咽部疼痛，甚至吞咽、呼吸困难、局部可有咽痛，吞咽时尤为明显，甚至因畏惧疼痛不敢吞咽，疼痛可放射至耳部，幼儿常因不能吞咽而拒食哭闹。', '咽痛、咽部不适', '2022-05-01 11:31:10', '2022-05-01 17:49:05');
INSERT INTO `illness` VALUES (4, 3, '偏头痛', '偏头痛的病因尚不明确，可能与遗传、内分泌代谢、环境因素、精神因素有关。', '偏头痛的常见症状包括：头痛，开始常为隐约疼痛，逐渐变为搏动性疼痛，活动时加重，还可从头的一侧转\n移至另一侧，累及头前部或整个头部；对光线、噪音和气味敏感；伴有恶心、呕吐，胃部不适，腹部疼痛；\n食欲差；感觉非常的暖或冷；肤色苍白；疲劳；头晕；视野模糊；腹泻。比较军见的症状包括发烧、影响正\n常的肢体活动。', '左侧疼痛、右侧疼痛、单侧疼痛、一阵一阵的疼痛、像针扎一样', '2022-05-01 11:31:10', '2022-05-01 17:49:06');
INSERT INTO `illness` VALUES (5, 2, '便秘', '便秘通常是由于美便在消化道中移动太慢，或无法从直肠中有效清除时，导致类便脱水、变硬和干燥，从而引发的便秘。', '排便次数减少、一周内小于3次、粪便干燥或结块、如羊粪、排便因难，如排便时间长、排便时感觉有阻碍、排便后仍有粪便未排尽的感觉、需手按腹部帮助排便等', '大便困难、拉不出来', '2022-05-01 11:31:10', '2022-05-03 16:03:44');
INSERT INTO `illness` VALUES (6, 3, '骨折', '骨折是由创伤或骨骼痪病所导致，大部分骨折都是由于直接或间接暴力引起。跌倒、撞击、交通意外等暴力因素是导致骨折的常见原因。积累性劳损及骨骼痪病也会增加骨折发生几率，骨骼痪病（如骨髓炎、骨肿瘤）导致骨质破坏，患者受到轻微外力就可能发生骨折。', '骨折特有特征为畸形、异常活动和骨擦音（感）。大部分骨折一般只引起局部症状，最常见的症状就是局部\n疼痛、肿胀及功能障碍。严重骨折和多发性骨折可伴随全身症状（如休克、发热）。', '骨折的一般表现为局部疼痛、肿胀及功能障碍', '2022-05-01 11:31:10', '2022-05-03 16:04:23');
INSERT INTO `illness` VALUES (7, 17, '牙周炎', '牙周炎是一种破坏性庆病，与微生物、宿主反应有关，是导致我国成人牙齿丧失的主要原因，严重影响患者的口腔健康。在局部致病因素中，牙菌斑是最主要的致病因素，而在全身因素中吸烟是高危因素。', '健康的牙龈应该呈粉红色，边缘薄且紧贴牙面，质坚韧，探诊不出血。牙周災的主要症状是牙龈红肿、质地\n松软、探诊出血、牙周袋溢脓和牙齿松动。', '牙龈出血、牙齿松动、牙龈肿', '2022-05-01 11:31:10', '2022-05-03 16:04:43');
INSERT INTO `illness` VALUES (8, 2, '胃溃疡', '胃溃疡是一种常见的消化痪病，任何年龄的人都可能患病。在全球范围内，约占10%的人群一生中都会患有消化性渍疡。在患病人群中，40-60岁的中老年患者最为多见，而且男性多于女性。', '胃溃疡的症状较多，包括胃部疼痛、食欲不振、餐后腹胀或胃部不适、体重减轻等等。这些症状的严重程度\n取决于溃疡的严重程度。有些患者可能没有任何症状（如“无症状性溃疡\"），或者是以胃出血、胃穿孔等并\n发症为首发症状。', '餐后腹胀、体重减轻、食欲不振', '2022-05-01 11:31:10', '2022-05-03 16:05:16');
INSERT INTO `illness` VALUES (9, 17, '口腔溃疡', '口腔渍疡的致病原因尚不明确，多种因素可诱发，包括遗传因素、饮食因素、免疫因素等，且具有明显的个体差异。口腔渍疡经常、反复发作时，严重影响患者的日常生活和工作。', '口腔溃疡常见于口腔的唇、脸颊、软腭或牙龈等处的黏膜上，溃疡面一般呈圆形或椭圆形，溃疡面凹陷、有\n白色或黄色的中心、周围充血微红肿，有明显疼痛感。', '口腔溃疡常见于口腔的唇、脸颊、软腭或牙龈等处的黏膜上，溃疡面一般呈圆形或椭圆形，溃疡面凹陷、有\n白色或黄色的中心、周围充血微红肿，有明显疼痛感。', '2022-05-01 11:31:10', '2022-05-03 16:05:51');
INSERT INTO `illness` VALUES (13, 7, '湿疹', '湿疹的病因目前尚不明确，与机体内因、外因、社会心理因素等都有关。机体内因包括免疫功能异常和系统性痪病（如内分泌痪病、营养障碍、慢性感染等）以及遗传性或获得性 皮肤屏障功能障碍。', '急性期表现为红斑、水肿、粟粒大小的丘疹、丘疱疹、水疱，糜烂及渗出；亚急性期表现为红肿和渗出减\n轻，糜烂面结痂、脱屑；慢性期主要表现为粗糙肥厚、苔藓样变。湿疹容易复发，严重影响患者的生活质\n量。', '起病较急、发病较快，瘙痒剧烈。', '2022-05-03 16:08:58', '2022-05-03 16:09:11');
INSERT INTO `illness` VALUES (15, 17, '口腔溃疡', '上火', '口腔疼', '巨疼', '2024-12-29 11:44:41', '2024-12-29 11:44:41');
INSERT INTO `illness` VALUES (17, 2, '123', '123', '123', '123', '2025-02-27 20:18:21', '2025-02-27 20:18:21');

-- ----------------------------
-- Table structure for illness_kind
-- ----------------------------
DROP TABLE IF EXISTS `illness_kind`;
CREATE TABLE `illness_kind`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of illness_kind
-- ----------------------------
INSERT INTO `illness_kind` VALUES (1, '急诊科', '急诊科疾病', '2022-05-01 11:57:39', '2022-05-01 12:01:00');
INSERT INTO `illness_kind` VALUES (2, '内科', '内科疾病', '2022-05-01 11:57:57', '2022-05-01 12:00:59');
INSERT INTO `illness_kind` VALUES (3, '外科', '外科疾病', '2022-05-01 11:58:26', '2022-05-01 12:00:57');
INSERT INTO `illness_kind` VALUES (4, '妇产科', '妇产科疾病', '2022-05-01 11:58:36', '2022-05-01 12:00:56');
INSERT INTO `illness_kind` VALUES (5, '儿科', '儿科疾病', '2022-05-01 11:58:49', '2022-05-01 12:00:54');
INSERT INTO `illness_kind` VALUES (6, '男科', '男科疾病', '2022-05-01 11:58:59', '2022-05-01 12:00:53');
INSERT INTO `illness_kind` VALUES (7, '皮肤科', '皮肤科疾病', '2022-05-03 16:07:12', '2022-05-03 16:07:12');
INSERT INTO `illness_kind` VALUES (9, '肝病', '肝病疾病', '2022-05-01 11:59:27', '2022-05-01 12:00:49');
INSERT INTO `illness_kind` VALUES (10, '传染科', '传染科疾病', '2022-05-01 11:59:35', '2022-05-01 12:00:48');
INSERT INTO `illness_kind` VALUES (16, '耳鼻喉科', '耳鼻喉科疾病', '2022-05-01 12:00:23', '2022-05-01 12:00:41');
INSERT INTO `illness_kind` VALUES (17, '口腔科', '口腔科疾病', '2022-05-01 12:00:31', '2022-05-01 12:00:39');

-- ----------------------------
-- Table structure for illness_medicine
-- ----------------------------
DROP TABLE IF EXISTS `illness_medicine`;
CREATE TABLE `illness_medicine`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '病和药品关联id',
  `illness_id` int NULL DEFAULT NULL COMMENT '病id',
  `medicine_id` int NULL DEFAULT NULL COMMENT '药品id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_im_illness`(`illness_id` ASC) USING BTREE,
  INDEX `fk_im_medicine`(`medicine_id` ASC) USING BTREE,
  CONSTRAINT `fk_im_illness` FOREIGN KEY (`illness_id`) REFERENCES `illness` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_im_medicine` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of illness_medicine
-- ----------------------------
INSERT INTO `illness_medicine` VALUES (6, 3, 1, '2022-05-03 16:10:35', '2022-05-03 16:10:35');
INSERT INTO `illness_medicine` VALUES (7, 2, 1, '2022-05-03 16:10:37', '2022-05-03 16:10:37');
INSERT INTO `illness_medicine` VALUES (9, 4, 1, '2022-05-03 16:10:42', '2022-05-03 16:10:42');
INSERT INTO `illness_medicine` VALUES (10, 7, 1, '2022-05-03 16:10:44', '2022-05-03 16:10:44');
INSERT INTO `illness_medicine` VALUES (12, 2, 2, '2022-05-03 16:11:01', '2022-05-03 16:11:01');
INSERT INTO `illness_medicine` VALUES (13, 5, 3, '2022-05-03 16:11:16', '2022-05-03 16:11:16');
INSERT INTO `illness_medicine` VALUES (14, 13, 5, '2022-05-03 16:11:29', '2022-05-03 16:11:29');
INSERT INTO `illness_medicine` VALUES (15, 8, 4, '2022-05-03 16:11:39', '2022-05-03 16:11:39');
INSERT INTO `illness_medicine` VALUES (16, 7, 6, '2022-05-03 16:11:50', '2022-05-03 16:11:50');
INSERT INTO `illness_medicine` VALUES (17, 4, 7, '2022-05-03 16:12:01', '2022-05-03 16:12:01');
INSERT INTO `illness_medicine` VALUES (18, 2, 7, '2022-05-03 16:12:03', '2022-05-03 16:12:03');
INSERT INTO `illness_medicine` VALUES (20, 3, 7, '2022-05-03 16:12:05', '2022-05-03 16:12:05');
INSERT INTO `illness_medicine` VALUES (21, 5, 1, '2025-02-14 10:59:26', '2025-02-14 10:59:26');
INSERT INTO `illness_medicine` VALUES (22, 5, 1, '2025-02-14 10:59:27', '2025-02-14 10:59:27');

-- ----------------------------
-- Table structure for medical_news
-- ----------------------------
DROP TABLE IF EXISTS `medical_news`;
CREATE TABLE `medical_news`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_id` int NOT NULL,
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `view_count` int NULL DEFAULT 0,
  `status` tinyint NULL DEFAULT 0,
  `publish_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_title`(`title` ASC) USING BTREE,
  INDEX `idx_publish_time`(`publish_time` ASC) USING BTREE,
  CONSTRAINT `fk_news_category` FOREIGN KEY (`category_id`) REFERENCES `news_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medical_news
-- ----------------------------
INSERT INTO `medical_news` VALUES (1, '春季流感预防指南', '随着天气转暖，流感病毒活动增强。专家建议...（此处省略详细内容）', '健康时报', 1, 'https://example.com/images/flu.jpg', 0, 1, '2024-03-15 09:00:00', '2025-02-27 14:45:15', '2025-02-27 14:45:15');
INSERT INTO `medical_news` VALUES (2, '糖尿病患者饮食管理新发现', '最新研究表明...（此处省略详细内容）', '医学研究月刊', 2, 'https://example.com/images/diabetes.jpg', 0, 1, '2024-03-20 14:30:00', '2025-02-27 14:45:15', '2025-02-27 14:45:15');

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '药品主键ID',
  `medicine_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '药的名字',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键字搜索',
  `medicine_effect` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '药的功效',
  `medicine_brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '药的品牌',
  `interaction` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '药的相互作用',
  `taboo` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '禁忌',
  `us_age` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用法用量',
  `medicine_type` int NULL DEFAULT NULL COMMENT '药的类型，0西药，1中药，2中成药',
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关图片路径',
  `medicine_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '药的价格',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (1, '阿莫西林胶囊', '消炎药、感冒药', '本品尚可用于治疗伤寒、伤寒带菌者及钩端螺旋体病；阿莫西林亦可与克拉霉素、兰索拉唑三联用药根除胃、十二指肠幽门螺杆菌，降低消化道溃疡复发率。', '仁和', '1．丙磺舒竞争性地减少本品的肾小管分泌，两者同时应用可引起阿莫西林血浓度升高、半衰期延长。\n2．氯霉素、大环内酯类、磺胺类和四环素类药物在体外干扰阿莫西林的抗菌作用，但其临床意义不明。', '1. 青霉素类口服药可引起过敏性休克，有多见于青霉素或头孢菌素过敏史的患者。用药前必须详细询问药物过敏史并做青霉素皮肤试验。如发生过敏性休克，应就地抢救，予以保持气道畅通、吸氧及应用肾上腺素、糖皮质激素等治疗措施。\n2.传染性单核细胞增多症患者应用本品易发生皮疹，应避免使用。\n3.疗程较长患者应检查肝、肾功能和血常规。\n4.阿莫西林可导致采用Benedict或Fehling试剂的尿糖试验出现假阳性。\n5.下列情况应慎用：\n(1)有哮喘、枯草热等过敏性疾病史者。\n(2)老年人和肾功能严重损害时可能须调整剂量。', '成人的具体使用剂量为0.5g/次，每6-8小时重复用药，24小时内服用剂量不能超过4g。儿童一日用药剂量按照患者实际体重为20-40mg/kg，重复用药间隔时长为8h/次。', 1, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/1%E9%98%BF%E8%8E%AB%E8%A5%BF%E6%9E%97.png', 14.00, '2022-05-02 11:46:00', '2024-10-28 20:17:02');
INSERT INTO `medicine` VALUES (2, '999感冒灵颗粒', '感冒药、流鼻涕、发烧', '解热镇痛功效，用于因感冒引起的头痛，发热，鼻塞，流涕，咽痛等症状。', '999', '三九感冒灵颗粒是复方药，里面含有中西药成分，不宜和西药感冒药同服。如果两种药中含有同一种成分，就只能选择服用其中一种，以免使摄入药量加倍，增加毒性，成为重复用药 [3]  。比如，三九感冒灵颗粒和西药泰诺，都含有解热镇痛效果的“扑热息痛”成分，若是两种药一起吃，过量的“扑热息痛”会对人体肝脏造成损害。', '1.忌烟，酒及辛辣，生冷，油腻食物。\n2.不宜在服药期间同时服用滋补性中成药\n3. 高血压、心脏病、肝病、肾病等慢性病严重者应在医师指导下服用。\n4.本品含对乙氨基酚，马来酸氨苯那敏，咖啡因。服用本品期间不得饮酒或含有酒精的饮料；不能同时服用与本品成分相似的其它抗感冒药；肝，肾功能不全者慎用；膀胱颈梗阻，甲状腺功能亢进，青光眼，高血压和前列腺肥大者慎用；孕妇及哺乳期妇女慎用；服药期间不得驾驶机，车，船，从事高空作业，机械作业及操作精密仪器。\n5.脾胃虚寒，症见腹痛，喜暖，泄泻者慎用。\n6.糖尿病患者、消化道溃疡患者、膀胱颈梗阻、幽门十二指肠梗阻、甲状腺机能亢进、青光眼以及前列腺肥大等患者慎用。\n7.儿童，年老体弱者应在医师指导下使用。\n8.服药3天后症状无改善，或症状加重，或出现新的严重症状如胸闷，心悸等应立即停药，并去医院就诊。\n9.对本药过敏者禁用，过敏体质者慎用。\n10. 本品性状发生改变时禁止使用。\n11.儿童必须在成人监护下使用。\n12.请将本品放在儿童不能接触的地方。\n13.如正在使用其他药品，使用本品前请咨询医师或药师.', '开水冲服，一次1袋，一日3次。小儿减量或遵医嘱。', 2, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/999%E6%84%9F%E5%86%92%E7%81%B5.png', 39.80, '2022-05-02 11:50:13', '2024-10-28 20:17:09');
INSERT INTO `medicine` VALUES (3, '开塞露', '便秘', '都是利用甘油或山梨醇的高浓度，即高渗作用，软化大便，刺激肠壁，反射性地引起排便反应，再加上其具有润滑作用，能使大便容易排出', '信龙', NULL, '1.刺破或剪开后的注药导管的开口应光滑，以免擦伤肛门或直肠。\n2.对本品过敏者禁用，过敏体质者慎用。\n3.本品性状发生改变时禁止使用。\n4.请将本品放在儿童不能接触的地方。\n5.儿童必须在成人监护下使用。\n6.如正在使用其他药品，使用本品前请咨询医师或药师。', '将容器顶端刺破或剪开，涂以油脂少许，缓慢插入肛门，然后将药液挤入直肠内，成人一次1支，儿童一次\n0.5支。', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/%E5%BC%80%E5%A1%9E%E9%9C%B2.png', 18.00, '2022-05-02 12:52:13', '2024-10-28 20:17:54');
INSERT INTO `medicine` VALUES (4, '三九胃泰颗粒', '胃胀、胃痛、胃不舒服', '清热祛湿，消炎止痛，理气除胀，养胃益肠。', '999', NULL, '1.服药期间，忌食辛辣，油炸，过酸食物及酒类等刺激性食品。\n2． 十五天为一疗程，初显疗效后不宜立即停药，建议再服3—4个疗程以巩固疗效。\n3．胃寒患者慎用。', '开水冲服。一次1袋，一日2次。', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/%E4%B8%89%E8%83%83%E6%B3%B0%E9%A2%97%E7%B2%92.png', 15.00, '2022-05-02 12:58:32', '2024-10-28 20:19:09');
INSERT INTO `medicine` VALUES (5, '999皮炎平', '皮肤瘙痒', '用于局限性瘙痒症、神经性皮炎、接触性皮炎、脂溢性皮炎以及慢性湿疹。', '999', NULL, '1.患处已破溃、化脓或有明显渗出者禁用。\n2.病毒感染者（如有疱疹、水痘）禁用。\n3.对本品成分过敏者禁用。', '皮肤外用。取少量涂于患处，并轻揉片刻；一日1~2次，病情较重或慢性炎症患者，每日5-8次或遵医嘱。', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/999%E7%9A%AE%E7%82%8E%E5%B9%B3.png', 15.21, '2022-05-02 13:01:34', '2024-10-28 20:17:33');
INSERT INTO `medicine` VALUES (6, '甲硝唑', '牙痛', '适应症为用于治疗肠道和肠外阿米巴病（如阿米巴肝脓肿、胸膜阿米巴病等）。还可用于治疗阴道滴虫病、小袋虫病和皮肤利什曼病、麦地那龙线虫感染等。目前还广泛用于厌氧菌感染的治疗', '奥可安', '本品能增强华法林等抗凝药物的作用。与土霉素合用可干扰甲硝唑清除阴道滴虫的作用。', '有活动性中枢神经系统疾患和血液病者禁用。', '成人一次两片，一日三次', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/%E7%94%B2%E7%A1%9D%E5%94%91%E7%89%87.png', 28.50, '2022-05-02 13:03:27', '2024-10-28 20:17:52');
INSERT INTO `medicine` VALUES (7, '布洛芬缓释胶囊', '头疼、缓解痛', '用于缓解轻至中度疼痛如头痛、偏头痛、牙痛、痛经、关节痛、肌肉痛、神经痛，也用于普通感冒或流行性感冒引起的发热', '芬必得', '.本品与其他解热、镇痛、抗炎药物同用时可增加胃肠道不良反应，并可能导致溃疡。 2.本品与肝素、双香豆素类(如华法林)等抗凝药 同用时，可导致凝血酶原时间延长，增加出血倾向。 3.本品与地高辛、甲氨蝶呤、口服降血糖药物同用 时，能使这些药物的血药浓度增高，不宜同用。 ', '1.对其他非甾休抗炎药过敏者禁用。 2.孕妇及晡乳期妇女禁用。 3.对阿司匹林过敏的哮喘患者禁用。 4.严重肝肾功能不全者或严重心力衰竭者禁用。 5.正在服用其他含有布洛芬或其他非甾休抗炎药， 包括服用已知是特异性环氧化酶-2抑制剂药物的患者禁用。除非医生建议使用。 6.既往有与使用非甾体类抗炎药治疗相关的上消化道出血或穿孔史者禁用。 7.活动性或既往有消化性溃疡史，胃肠道出血或穿孔的患者禁用。', '口服。成人，一次1片，一日2次（早晚各一次）。', 1, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/%E5%9B%BE%E7%89%87%E8%B5%84%E6%BA%90/%E5%B8%83%E6%B4%9B%E8%8A%AC.png', 1.00, '2022-05-02 13:10:47', '2024-10-28 20:17:44');

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort_order` int NULL DEFAULT 0,
  `status` tinyint NULL DEFAULT 1,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_category
-- ----------------------------
INSERT INTO `news_category` VALUES (1, '健康资讯', '最新健康生活方式与养生知识', 0, 1, '2025-02-27 14:45:15', '2025-02-27 14:45:15');
INSERT INTO `news_category` VALUES (2, '疾病知识', '常见疾病预防与治疗指南', 0, 1, '2025-02-27 14:45:15', '2025-02-27 14:45:15');
INSERT INTO `news_category` VALUES (3, '医学前沿', '全球最新医学研究成果', 0, 1, '2025-02-27 14:45:15', '2025-02-27 14:45:15');

-- ----------------------------
-- Table structure for pageview
-- ----------------------------
DROP TABLE IF EXISTS `pageview`;
CREATE TABLE `pageview`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pageviews` int NULL DEFAULT NULL COMMENT '浏览量',
  `illness_id` int NULL DEFAULT NULL COMMENT '病的id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pageview_illness`(`illness_id` ASC) USING BTREE,
  CONSTRAINT `fk_pageview_illness` FOREIGN KEY (`illness_id`) REFERENCES `illness` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pageview
-- ----------------------------
INSERT INTO `pageview` VALUES (6, 13, 13);
INSERT INTO `pageview` VALUES (7, 2, 4);
INSERT INTO `pageview` VALUES (8, 6, 2);
INSERT INTO `pageview` VALUES (9, 2, 3);
INSERT INTO `pageview` VALUES (10, 1, 5);
INSERT INTO `pageview` VALUES (11, 2, 6);
INSERT INTO `pageview` VALUES (12, 4, 7);
INSERT INTO `pageview` VALUES (13, 4, 8);
INSERT INTO `pageview` VALUES (14, 4, 9);
INSERT INTO `pageview` VALUES (15, 6, 15);

-- ----------------------------
-- Table structure for symptom_log
-- ----------------------------
DROP TABLE IF EXISTS `symptom_log`;
CREATE TABLE `symptom_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '游客无user_id',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '症状关键词',
  `matched_illness_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '逗号分隔的疾病ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of symptom_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的真实名字',
  `user_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_age` int NULL DEFAULT NULL COMMENT '用户年龄',
  `user_sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_status` int NULL DEFAULT NULL COMMENT '角色状态，1管理员，0普通用户',
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `blood_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '血型',
  `allergy_history` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '过敏史',
  `height` decimal(5, 2) NULL DEFAULT NULL COMMENT '身高(cm)',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT '体重(kg)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'admin', '管理员', '123456', 23, '男', '2678788262@qq.com', '17746678954', 1, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/4/af63959401034cecb93496b733c3f2ad.png', '2022-05-03 15:55:41', '2024-10-28 16:33:48', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, 'zhangsan', '张三', '123456', 23, '女', 'isxuewei@qq.com', '17879544343', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/5/a0d6d6dd713b4216b0b37439d5e38144.png', '2022-05-03 16:15:53', '2024-12-28 10:53:53', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, 'user1', 'tjp', '123456', 22, '男', '838219014@qq.com', '15372952160', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/6/3072365424324cf098e291393c398b5e.png', '2024-10-27 11:08:04', '2024-10-28 11:01:29', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, 'user5', 'tjp', '123456', 1, '男', '838219014@qq.com', '15372952160', 0, 'https://smart-medicine-sys.oss-cn-guangzhou.aliyuncs.com/7/c1dfa01c4070425ca49fa1112a53cc5f.jpg', '2024-12-28 11:14:35', '2024-12-28 11:15:44', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (8, 'system_user', '系统用户', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2025-02-27 14:39:27', '2025-02-27 14:39:27', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;