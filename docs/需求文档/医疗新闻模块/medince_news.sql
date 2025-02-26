-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `sort_order` int DEFAULT 0 COMMENT '排序顺序',
  `status` tinyint DEFAULT 1 COMMENT '状态(0禁用,1启用)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for medical_news
-- ----------------------------
DROP TABLE IF EXISTS `medical_news`;
CREATE TABLE `medical_news` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '新闻标题',
  `content` text NOT NULL COMMENT '新闻内容',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图片URL',
  `view_count` int DEFAULT 0 COMMENT '浏览量',
  `status` tinyint DEFAULT 0 COMMENT '状态(0草稿,1发布,2下架)',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `news_category`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_favorite
-- ----------------------------
DROP TABLE IF EXISTS `article_favorite`;
CREATE TABLE `article_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`article_id`) REFERENCES `medical_news`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) NOT NULL COMMENT '标签名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_relation`;
CREATE TABLE `article_tag_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`article_id`) REFERENCES `medical_news`(`id`),
  FOREIGN KEY (`tag_id`) REFERENCES `article_tag`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_illness_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_illness_relation`;
CREATE TABLE `article_illness_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `illness_id` bigint NOT NULL COMMENT '疾病ID',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`article_id`) REFERENCES `medical_news`(`id`),
  FOREIGN KEY (`illness_id`) REFERENCES `illness`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Index creation
-- ----------------------------

-- 医疗新闻表索引
CREATE INDEX idx_category_id ON medical_news(category_id);
CREATE INDEX idx_title ON medical_news(title);
CREATE INDEX idx_publish_time ON medical_news(publish_time);

-- 文章收藏表索引
CREATE INDEX idx_user_id ON article_favorite(user_id);
CREATE INDEX idx_user_article ON article_favorite(user_id, article_id);

-- 文章标签关联表索引
CREATE INDEX idx_article_id ON article_tag_relation(article_id);
CREATE INDEX idx_tag_id ON article_tag_relation(tag_id); 