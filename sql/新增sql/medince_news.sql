-- 设置统一字符集
SET NAMES utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- 1. 删除所有表（按依赖逆序）
DROP TABLE IF EXISTS `article_illness_relation`;
DROP TABLE IF EXISTS `article_tag_relation`;
DROP TABLE IF EXISTS `article_favorite`;
DROP TABLE IF EXISTS `medical_news`;
DROP TABLE IF EXISTS `article_tag`;
DROP TABLE IF EXISTS `news_category`;

-- 2. 创建新闻分类表（保留原始user表）
CREATE TABLE `news_category` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `name` varchar(50) NOT NULL,
                                 `description` varchar(255) DEFAULT NULL,
                                 `sort_order` int DEFAULT 0,
                                 `status` tinyint DEFAULT 1,
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. 创建医疗新闻表（带外键约束）
CREATE TABLE `medical_news` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `title` varchar(100) NOT NULL,
                                `content` text NOT NULL,
                                `author` varchar(50) DEFAULT NULL,
                                `category_id` int NOT NULL,
                                `cover_image` varchar(255) DEFAULT NULL,
                                `view_count` int DEFAULT 0,
                                `status` tinyint DEFAULT 0,
                                `publish_time` datetime DEFAULT NULL,
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`),
                                KEY `category_id` (`category_id`),
                                CONSTRAINT `fk_news_category`
                                    FOREIGN KEY (`category_id`)
                                        REFERENCES `news_category` (`id`)
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 4. 创建文章标签表
CREATE TABLE `article_tag` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `name` varchar(30) NOT NULL,
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 5. 创建文章收藏表（关联原始user表）
CREATE TABLE `article_favorite` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `user_id` int NOT NULL,
                                    `article_id` int NOT NULL,
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    CONSTRAINT `fk_fav_user`
                                        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                    CONSTRAINT `fk_fav_article`
                                        FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 6. 创建标签关联表
CREATE TABLE `article_tag_relation` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `article_id` int NOT NULL,
                                        `tag_id` int NOT NULL,
                                        PRIMARY KEY (`id`),
                                        CONSTRAINT `fk_tag_article`
                                            FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`),
                                        CONSTRAINT `fk_tag_rel`
                                            FOREIGN KEY (`tag_id`) REFERENCES `article_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 7. 创建疾病关联表
CREATE TABLE `article_illness_relation` (
                                            `id` int NOT NULL AUTO_INCREMENT,
                                            `article_id` int NOT NULL,
                                            `illness_id` int NOT NULL,
                                            PRIMARY KEY (`id`),
                                            CONSTRAINT `fk_illness_article`
                                                FOREIGN KEY (`article_id`) REFERENCES `medical_news` (`id`),
                                            CONSTRAINT `fk_illness_rel`
                                                FOREIGN KEY (`illness_id`) REFERENCES `illness` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 8. 创建索引
CREATE INDEX idx_category_id ON medical_news(category_id);
CREATE INDEX idx_title ON medical_news(title);
CREATE INDEX idx_publish_time ON medical_news(publish_time);
CREATE INDEX idx_user_id ON article_favorite(user_id);
CREATE INDEX idx_user_article ON article_favorite(user_id, article_id);
CREATE INDEX idx_article_tag ON article_tag_relation(article_id);
CREATE INDEX idx_tag_id ON article_tag_relation(tag_id);