/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : guli

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 18/05/2021 17:38:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crm_banner
-- ----------------------------
DROP TABLE IF EXISTS `crm_banner`;
CREATE TABLE `crm_banner`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '链接地址',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`title`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '首页banner表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_banner
-- ----------------------------
INSERT INTO `crm_banner` VALUES ('1194556896025845762', 'test1', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/9dc9910b8b1142feb463dda30ed1e2f52048641.jpg', '/course', 1, 0, '2019-11-13 18:05:32', '2019-11-18 10:28:22');
INSERT INTO `crm_banner` VALUES ('1194607458461216769', 'test2', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/0bd393194a0746e2b8b3a1c8dc8f56cf2030137.jpg', '/teacher', 2, 0, '2019-11-13 21:26:27', '2019-11-14 09:12:15');

-- ----------------------------
-- Table structure for edu_chapter
-- ----------------------------
DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节ID',
  `course_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节名称',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '显示排序',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of edu_chapter
-- ----------------------------
INSERT INTO `edu_chapter` VALUES ('1391014347756556289', '1391014267272056834', '第一章 Java的认识', 1, '2021-05-08 20:57:19', '2021-05-08 20:57:19');
INSERT INTO `edu_chapter` VALUES ('1391014515805540354', '1391014267272056834', '第二章 安装配置jdk，实现第一个代码', 2, '2021-05-08 20:57:59', '2021-05-08 20:57:59');
INSERT INTO `edu_chapter` VALUES ('1391014610131243010', '1391014267272056834', '第三章 学习使用eclipse IDE开发', 3, '2021-05-08 20:58:22', '2021-05-08 20:58:22');
INSERT INTO `edu_chapter` VALUES ('1391018405087170562', '1391018332022394882', '第一章 认识C++', 1, '2021-05-08 21:13:26', '2021-05-08 21:13:26');
INSERT INTO `edu_chapter` VALUES ('1391018484871221250', '1391018332022394882', '第二章 学习指针', 2, '2021-05-08 21:13:45', '2021-05-08 21:13:45');
INSERT INTO `edu_chapter` VALUES ('1391025944780623873', '1391025874517643266', '第一章 对集合的认识学习', 1, '2021-05-08 21:43:24', '2021-05-08 21:43:24');
INSERT INTO `edu_chapter` VALUES ('1391026678439890946', '1391026643442618369', 'vue', 1, '2021-05-08 21:46:19', '2021-05-08 21:46:19');
INSERT INTO `edu_chapter` VALUES ('1391027121920430081', '1391027093344636930', 'MySQL', 1, '2021-05-08 21:48:05', '2021-05-08 21:48:05');
INSERT INTO `edu_chapter` VALUES ('1391027533582979074', '1391027497570684930', 'JavaScript', 1, '2021-05-08 21:49:43', '2021-05-08 21:49:43');
INSERT INTO `edu_chapter` VALUES ('1391028205170741249', '1391028181045104642', '阿萨大大', 1, '2021-05-08 21:52:23', '2021-05-08 21:52:23');
INSERT INTO `edu_chapter` VALUES ('1391033889098584065', '1391033862431199233', '胜多负少', 1, '2021-05-08 22:14:58', '2021-05-08 22:14:58');
INSERT INTO `edu_chapter` VALUES ('1391038898498457602', '1391038879091412994', 'asda', 1, '2021-05-08 22:34:52', '2021-05-08 22:34:52');

-- ----------------------------
-- Table structure for edu_comment
-- ----------------------------
DROP TABLE IF EXISTS `edu_comment`;
CREATE TABLE `edu_comment`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '讲师ID',
  `course_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程id',
  `teacher_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '讲师id',
  `member_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE,
  INDEX `idx_member_id`(`member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_comment
-- ----------------------------

-- ----------------------------
-- Table structure for edu_course
-- ----------------------------
DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程标题',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '销售数量',
  `view_count` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览数量',
  `version` bigint(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint(3) NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_title`(`title`) USING BTREE,
  INDEX `idx_subject_id`(`subject_id`) USING BTREE,
  INDEX `idx_teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of edu_course
-- ----------------------------
INSERT INTO `edu_course` VALUES ('1391014267272056834', '1391013543310020609', '1380503481047097346', '1380503481013542913', 'Java从入门到精通', 19.00, 99, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/9dc9910b8b1142feb463dda30ed1e2f52048641.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 20:57:00', '2021-05-08 20:57:00');
INSERT INTO `edu_course` VALUES ('1391018332022394882', '1391012493115019266', '1380503481089040386', '1380503481013542913', '两小时带你学会指针', 0.00, 12, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/0bd393194a0746e2b8b3a1c8dc8f56cf2030137.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:13:09', '2021-05-08 21:28:17');
INSERT INTO `edu_course` VALUES ('1391025874517643266', '1391013042510123010', '1380503481047097346', '1380503481013542913', 'Java集合讲解', 0.00, 8, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/140db9fd11344ee09d147b66d8d2c3a3ynni (8).jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:43:07', '2021-05-08 21:43:47');
INSERT INTO `edu_course` VALUES ('1391026643442618369', '1391012778386411521', '1380503480875130881', '1380503480535392258', '跟随尤大神一起感受VUE', 3.00, 1, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/280e7b5e66614ddb8d78b50a378086b42048387.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:46:10', '2021-05-08 21:46:50');
INSERT INTO `edu_course` VALUES ('1391027093344636930', '1391013302317895681', '1380503481147760641', '1380503481114206209', 'MySQL从入门到精通', 0.01, 32, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:47:58', '2021-05-08 21:48:26');
INSERT INTO `edu_course` VALUES ('1391027497570684930', '1391012778386411521', '1380503480946434049', '1380503480535392258', 'JavaScript从入门到精通', 0.02, 12, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/0bd393194a0746e2b8b3a1c8dc8f56cf2030137.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:49:34', '2021-05-08 21:50:12');
INSERT INTO `edu_course` VALUES ('1391028181045104642', '1391013302317895681', '1380503480984182786', '1380503480535392258', 'jQuery之路', 1.00, 24, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/0bd393194a0746e2b8b3a1c8dc8f56cf2030137.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 21:52:17', '2021-05-08 21:52:37');
INSERT INTO `edu_course` VALUES ('1391033862431199233', '1391013543310020609', '1380503481047097346', '1380503481013542913', 'Java', 0.00, 3, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/e59399ed5c6847219687373e4b90cd66ynni (8).jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 22:14:52', '2021-05-08 22:23:10');
INSERT INTO `edu_course` VALUES ('1391038879091412994', '1391013042510123010', '1380503480875130881', '1380503480535392258', 'asd', 1.00, 3, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/1e11caf617744dde9baa47ff087ad0cc2030137.jpg', 0, 0, 1, 'Normal', 0, '2021-05-08 22:34:48', '2021-05-08 22:35:26');

-- ----------------------------
-- Table structure for edu_course_collect
-- ----------------------------
DROP TABLE IF EXISTS `edu_course_collect`;
CREATE TABLE `edu_course_collect`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏ID',
  `course_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程收藏' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of edu_course_collect
-- ----------------------------

-- ----------------------------
-- Table structure for edu_course_description
-- ----------------------------
DROP TABLE IF EXISTS `edu_course_description`;
CREATE TABLE `edu_course_description`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '课程简介',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程简介' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_course_description
-- ----------------------------
INSERT INTO `edu_course_description` VALUES ('1391014267272056834', '<p>给学员们介绍Java只是，从入门到精通，非常详细，<strong>全网最详细</strong>，没有之一，极力推荐学习。对初学者非常友好，手把手教学。</p>', '2021-05-08 20:57:00', '2021-05-08 20:57:00');
INSERT INTO `edu_course_description` VALUES ('1391018332022394882', '<p>张老师带你两小时玩转指针，了解指针的底层含义，得到你没见识过的指针知识。</p>', '2021-05-08 21:13:09', '2021-05-08 21:13:09');
INSERT INTO `edu_course_description` VALUES ('1391025874517643266', '<p>给学生讲解集合的内容</p>', '2021-05-08 21:43:07', '2021-05-08 21:43:07');
INSERT INTO `edu_course_description` VALUES ('1391026643442618369', '<p>八八八八八八八八八八八八八</p>', '2021-05-08 21:46:10', '2021-05-08 21:46:10');
INSERT INTO `edu_course_description` VALUES ('1391027093344636930', '<p>学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。学习MySQL。</p>', '2021-05-08 21:47:58', '2021-05-08 21:47:58');
INSERT INTO `edu_course_description` VALUES ('1391027497570684930', '<p>便可还是贷记卡收费道路喀什那里快速降低栏目的绿卡那里是可降低哦啦世界那么大拉克丝的风景</p>', '2021-05-08 21:49:34', '2021-05-08 21:49:34');
INSERT INTO `edu_course_description` VALUES ('1391028181045104642', '<p>就立刻时代覅老人家发i哦的时间菲尼克斯i分类监管思路，开发板v建瓯市客人通过科举滴哦飞机过来上课都二是</p>', '2021-05-08 21:52:17', '2021-05-08 21:52:17');
INSERT INTO `edu_course_description` VALUES ('1391033862431199233', '<p>AS阿萨十大发生法发大撒旦发</p>', '2021-05-08 22:14:52', '2021-05-08 22:14:52');
INSERT INTO `edu_course_description` VALUES ('1391038879091412994', '<p>asdadsasdada</p>', '2021-05-08 22:34:48', '2021-05-08 22:34:48');

-- ----------------------------
-- Table structure for edu_subject
-- ----------------------------
DROP TABLE IF EXISTS `edu_subject`;
CREATE TABLE `edu_subject`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类别名称',
  `parent_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序字段',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程科目' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of edu_subject
-- ----------------------------
INSERT INTO `edu_subject` VALUES ('1380503480535392258', '前端开发', '0', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503480875130881', 'vue', '1380503480535392258', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503480946434049', 'JavaScript', '1380503480535392258', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503480984182786', 'jQuery', '1380503480535392258', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503481013542913', '后端开发', '0', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503481047097346', 'Java', '1380503481013542913', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503481089040386', 'C++', '1380503481013542913', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503481114206209', '数据库开发', '0', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');
INSERT INTO `edu_subject` VALUES ('1380503481147760641', 'MySQL', '1380503481114206209', 0, '2021-04-09 20:50:53', '2021-04-09 20:50:53');

-- ----------------------------
-- Table structure for edu_teacher
-- ----------------------------
DROP TABLE IF EXISTS `edu_teacher`;
CREATE TABLE `edu_teacher`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '讲师ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int(10) UNSIGNED NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '讲师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_teacher
-- ----------------------------
INSERT INTO `edu_teacher` VALUES ('1391012493115019266', '张三', '非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。非常好，选他就是了。', '八八八八', 1, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/ec35d2f5cce64beba37cc9314ae99612file.png', 1, 0, '2021-05-08 20:49:57', '2021-05-08 20:49:57');
INSERT INTO `edu_teacher` VALUES ('1391012778386411521', '里斯', '建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。建议选他。', '九九九九', 2, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/b8c1a2a244de4558bc883f715a0e7ea6file.png', 1, 0, '2021-05-08 20:51:05', '2021-05-08 20:51:05');
INSERT INTO `edu_teacher` VALUES ('1391013042510123010', '晴天', '他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。他是非常厉害的。', '非常棒', 1, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/4913d190c4254db79203394ef29d846afile.png', 1, 0, '2021-05-08 20:52:08', '2021-05-08 20:52:08');
INSERT INTO `edu_teacher` VALUES ('1391013302317895681', '菊丝', '大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技大厂出来，Java特技', '大厂出来，Java特技', 2, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/f82bd0c2aa0545d8b944b1136435add9file.png', 1, 0, '2021-05-08 20:53:10', '2021-05-08 20:53:10');
INSERT INTO `edu_teacher` VALUES ('1391013543310020609', '杰克', '创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。创始人特级教师。', '创始人特级教师', 1, 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/d18b96826569462e933d2bd0b768c71afile.png', 1, 0, '2021-05-08 20:54:07', '2021-05-08 20:54:07');

-- ----------------------------
-- Table structure for edu_video
-- ----------------------------
DROP TABLE IF EXISTS `edu_video`;
CREATE TABLE `edu_video`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频ID',
  `course_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原始文件名称',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序字段',
  `play_count` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '播放次数',
  `is_free` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT 0 COMMENT '视频时长（秒）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '视频源文件大小（字节）',
  `version` bigint(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_chapter_id`(`chapter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程视频' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of edu_video
-- ----------------------------
INSERT INTO `edu_video` VALUES ('1391014747498893313', '1391014267272056834', '1391014347756556289', '第一节 Java历史', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 20:58:54', '2021-05-08 20:58:54');
INSERT INTO `edu_video` VALUES ('1391014905980669953', '1391014267272056834', '1391014347756556289', '第二节 Java的发展前景', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 2, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 20:59:32', '2021-05-08 20:59:32');
INSERT INTO `edu_video` VALUES ('1391015049094516737', '1391014267272056834', '1391014347756556289', '第三节 Java之父', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 3, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:00:06', '2021-05-08 21:00:06');
INSERT INTO `edu_video` VALUES ('1391015127502835714', '1391014267272056834', '1391014515805540354', '第一节 下载jdk', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:00:25', '2021-05-08 21:00:25');
INSERT INTO `edu_video` VALUES ('1391015267672281089', '1391014267272056834', '1391014515805540354', '第二节 windows上安装配置jdk', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 2, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:00:58', '2021-05-08 21:00:58');
INSERT INTO `edu_video` VALUES ('1391015395451752449', '1391014267272056834', '1391014515805540354', '第三节 测试安装是否成功', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 3, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:01:29', '2021-05-08 21:01:29');
INSERT INTO `edu_video` VALUES ('1391015516511948801', '1391014267272056834', '1391014515805540354', '第四节 编写第一个Java代码', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 4, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:01:58', '2021-05-08 21:01:58');
INSERT INTO `edu_video` VALUES ('1391015620195143681', '1391014267272056834', '1391014610131243010', '第一节 使用ide开发', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:02:22', '2021-05-08 21:02:22');
INSERT INTO `edu_video` VALUES ('1391015698897063937', '1391014267272056834', '1391014610131243010', '第二节 下载eclipse', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 2, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:02:41', '2021-05-08 21:02:41');
INSERT INTO `edu_video` VALUES ('1391015816102694914', '1391014267272056834', '1391014610131243010', '第三节 使用eclipse开发程序', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 3, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:03:09', '2021-05-08 21:03:09');
INSERT INTO `edu_video` VALUES ('1391018591947608065', '1391018332022394882', '1391018405087170562', '第一节 c++背景', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:14:11', '2021-05-08 21:14:11');
INSERT INTO `edu_video` VALUES ('1391018737636757505', '1391018332022394882', '1391018405087170562', '第二节 c的发展，c++和C的关系', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 2, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:14:46', '2021-05-08 21:14:46');
INSERT INTO `edu_video` VALUES ('1391018812312145922', '1391018332022394882', '1391018484871221250', '第一节 指针学习', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:15:03', '2021-05-08 21:15:03');
INSERT INTO `edu_video` VALUES ('1391026016821989378', '1391025874517643266', '1391025944780623873', '第一节 学习集合', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:43:41', '2021-05-08 21:43:41');
INSERT INTO `edu_video` VALUES ('1391026775307341825', '1391026643442618369', '1391026678439890946', '01vue', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:46:42', '2021-05-08 21:46:42');
INSERT INTO `edu_video` VALUES ('1391027187750031362', '1391027093344636930', '1391027121920430081', '01MySQL', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:48:20', '2021-05-08 21:48:20');
INSERT INTO `edu_video` VALUES ('1391027628995006466', '1391027497570684930', '1391027533582979074', '01 JavaScript学习', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:50:05', '2021-05-08 21:50:05');
INSERT INTO `edu_video` VALUES ('1391028246690156545', '1391028181045104642', '1391028205170741249', '阿萨大大', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 21:52:33', '2021-05-08 21:52:33');
INSERT INTO `edu_video` VALUES ('1391033985961840641', '1391033862431199233', '1391033889098584065', '士大夫', 'd81acc123c16441db4878326eaccab0c', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 22:15:21', '2021-05-08 22:15:21');
INSERT INTO `edu_video` VALUES ('1391039015502761986', '1391038879091412994', '1391038898498457602', 'asda', '0e04846a4f21439c999e68573d8b0347', '6 - What If I Want to Move Faster.mp4', 1, 0, 0, 0, 'Empty', 0, 1, '2021-05-08 22:35:20', '2021-05-08 22:35:20');

-- ----------------------------
-- Table structure for statistics_daily
-- ----------------------------
DROP TABLE IF EXISTS `statistics_daily`;
CREATE TABLE `statistics_daily`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `date_calculated` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统计日期',
  `register_num` int(11) NOT NULL DEFAULT 0 COMMENT '注册人数',
  `login_num` int(11) NOT NULL DEFAULT 0 COMMENT '登录人数',
  `video_view_num` int(11) NOT NULL DEFAULT 0 COMMENT '每日播放视频数',
  `course_num` int(11) NOT NULL DEFAULT 0 COMMENT '每日新增课程数',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `statistics_day`(`date_calculated`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '网站统计日数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistics_daily
-- ----------------------------
INSERT INTO `statistics_daily` VALUES ('1393863475671490561', '2021-04-28', 234, 453, 786, 34, '2021-05-11 22:01:26', '2021-05-20 22:01:30');
INSERT INTO `statistics_daily` VALUES ('1393869533461490561', '2021-05-07', 23, 421, 23, 346, '2021-05-17 22:00:37', '2021-05-17 22:00:39');
INSERT INTO `statistics_daily` VALUES ('1393869534871490561', '2021-04-21', 1, 130, 127, 100, '2021-05-16 18:02:49', '2021-05-16 18:02:49');
INSERT INTO `statistics_daily` VALUES ('1394292887100882945', '2021-05-16', 0, 109, 129, 101, '2021-05-17 22:05:04', '2021-05-17 22:05:04');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `course_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程id',
  `course_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `course_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程封面',
  `teacher_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '讲师名称',
  `member_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员手机',
  `total_fee` decimal(10, 2) NULL DEFAULT 0.01 COMMENT '订单金额（分）',
  `pay_type` tinyint(3) NULL DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint(3) NULL DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_order_no`(`order_no`) USING BTREE,
  INDEX `idx_course_id`(`course_id`) USING BTREE,
  INDEX `idx_member_id`(`member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1393053194719879169', '20210514115857500', '1391027093344636930', 'MySQL从入门到精通', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', '菊丝', '1384889600664707073', '哈哈', '18787462706', 0.01, 1, 0, 0, '2021-05-14 11:58:58', '2021-05-14 11:58:58');
INSERT INTO `t_order` VALUES ('1393580116344340482', '20210515225245742', '1391027093344636930', 'MySQL从入门到精通', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', '菊丝', '1384889600664707073', '哈哈', '18787462706', 0.01, 1, 0, 0, '2021-05-15 22:52:46', '2021-05-15 22:52:46');
INSERT INTO `t_order` VALUES ('1393581374325161985', '20210515225745081', '1391027093344636930', 'MySQL从入门到精通', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', '菊丝', '1384889600664707073', '哈哈', '18787462706', 0.01, 1, 0, 0, '2021-05-15 22:57:46', '2021-05-15 22:57:46');
INSERT INTO `t_order` VALUES ('1393582317695442946', '20210515230130778', '1391027093344636930', 'MySQL从入门到精通', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', '菊丝', '1384889600664707073', '哈哈', '18787462706', 0.01, 1, 0, 0, '2021-05-15 23:01:31', '2021-05-15 23:01:31');
INSERT INTO `t_order` VALUES ('1393583137581211650', '20210515230446429', '1391027093344636930', 'MySQL从入门到精通', 'https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/05/08/43e67518f8924fb3a4c9256b58c171b52048967.jpg', '菊丝', '1384889600664707073', '哈哈', '18787462706', 0.01, 1, 0, 0, '2021-05-15 23:04:46', '2021-05-15 23:04:46');

-- ----------------------------
-- Table structure for t_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_log`;
CREATE TABLE `t_pay_log`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `order_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10, 2) NULL DEFAULT 0.01 COMMENT '支付金额（分）',
  `transaction_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '其他属性',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_pay_log
-- ----------------------------
INSERT INTO `t_pay_log` VALUES ('1194498446013001730', '1194498300579704832', '2019-11-13 14:13:17', 1.00, '4200000469201911130676624386', 'SUCCESS', 1, '{\"transaction_id\":\"4200000469201911130676624386\",\"nonce_str\":\"2Lc23ILl231It53M\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"5404850AA3ED0E844DE104651477F07A\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1194498300579704832\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191113141314\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2019-11-13 14:13:17', '2019-11-13 14:13:17');
INSERT INTO `t_pay_log` VALUES ('1195253787449430017', '1195253049260314624', '2019-11-15 16:14:44', 1.00, '4200000454201911150981874895', 'SUCCESS', 1, '{\"transaction_id\":\"4200000454201911150981874895\",\"nonce_str\":\"MAM5UM4Xhv1lItvO\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"7DBDCAF4A078B30BB3EF073E6A238C20\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1195253049260314624\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191115161440\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2019-11-15 16:14:44', '2019-11-15 16:14:44');
INSERT INTO `t_pay_log` VALUES ('1196264321397342210', '1196264005255872512', '2019-11-18 11:10:14', 1.00, '4200000453201911184025781554', 'SUCCESS', 1, '{\"transaction_id\":\"4200000453201911184025781554\",\"nonce_str\":\"D1dHexCLIFIxAAg2\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"CFT\",\"openid\":\"oNpSGwR-QGG5DaZtDkh2UZlsFDQE\",\"sign\":\"C9F5CA1EE49EA7891736D73BEB423962\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1473426802\",\"cash_fee\":\"1\",\"out_trade_no\":\"1196264005255872512\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx8397f8696b538317\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20191118111011\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}', 0, '2019-11-18 11:10:14', '2019-11-18 11:10:14');

-- ----------------------------
-- Table structure for ucenter_member
-- ----------------------------
DROP TABLE IF EXISTS `ucenter_member`;
CREATE TABLE `ucenter_member`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员id',
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(2) UNSIGNED NULL DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ucenter_member
-- ----------------------------
INSERT INTO `ucenter_member` VALUES ('1384889600664707073', NULL, '18787462706', 'e10adc3949ba59abbe56e057f20f883e', '哈哈', NULL, NULL, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/07/91871e25-fd83-4af6-845f-ea8d471d825d.png', NULL, 0, 0, '2021-04-21 23:19:45', '2021-04-21 23:19:45');

SET FOREIGN_KEY_CHECKS = 1;
