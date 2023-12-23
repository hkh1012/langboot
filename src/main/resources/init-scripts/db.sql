
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_session
-- ----------------------------
DROP TABLE IF EXISTS `chat_session`;
CREATE TABLE `chat_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(50) NOT NULL COMMENT '会话ID',
  `title` varchar(50) NOT NULL COMMENT '会后标题',
  `model_id` int(11) DEFAULT NULL COMMENT '模型ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='用户会话';

-- ----------------------------
-- Table structure for conversation
-- ----------------------------
DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(50) NOT NULL COMMENT '会话ID',
  `type` char(1) NOT NULL COMMENT '会话类型：Q问，A答',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `qa_time` datetime DEFAULT NULL COMMENT '问答时间',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COMMENT='对话';

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `kid` varchar(10) NOT NULL COMMENT '知识库ID',
     `uid` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
     `kname` varchar(50) NOT NULL COMMENT '知识库名称',
     `description` varchar(1000) NULL COMMENT '描述',
     `create_time` datetime DEFAULT NULL,
     `create_by` varchar(50) DEFAULT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `idx_kid` (`kid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='知识库';

-- ----------------------------
-- Table structure for knowledge_attach
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_attach`;
CREATE TABLE `knowledge_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kid` varchar(10) NOT NULL COMMENT '知识库ID',
  `doc_id` varchar(10) NOT NULL COMMENT '文档ID',
  `doc_name` varchar(50) NOT NULL COMMENT '文档名称',
  `doc_type` varchar(10) NOT NULL COMMENT '文档类型',
  `content` longtext COMMENT '文档内容',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_kname` (`kid`,`doc_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COMMENT='知识库附件';

-- ----------------------------
-- Table structure for knowledge_share
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_share`;
CREATE TABLE `knowledge_share` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `kid` varchar(10) NOT NULL COMMENT '知识库ID',
    `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
    `kname` varchar(50) DEFAULT NULL COMMENT '知识库名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='知识库分享表';

DROP TABLE IF EXISTS `knowledge_fragment`;
CREATE TABLE `knowledge_fragment` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `kid` varchar(10) NOT NULL COMMENT '知识库ID',
                                      `doc_id` varchar(10) DEFAULT NULL COMMENT '文档ID',
                                      `fid` varchar(16) NOT NULL COMMENT '知识片段ID',
                                      `idx` int(11) NOT NULL COMMENT '片段索引下标',
                                      `content` text NOT NULL COMMENT '文档内容',
                                      `create_time` datetime DEFAULT NULL,
                                      `create_by` varchar(50) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COMMENT='知识片段';

-- ----------------------------
-- Table structure for sys_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_model`;
CREATE TABLE `sys_model` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '模型名称',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `local` bit(1) DEFAULT b'0' COMMENT '是否本地模型',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `free` bit(1) DEFAULT b'0' COMMENT '是否免费',
  `std_rate` decimal(10,8) DEFAULT NULL COMMENT '标准token比',
  `role_text` varchar(255) DEFAULT NULL COMMENT '角色设定',
  `temperature` decimal(10,2) DEFAULT NULL COMMENT '模型默认温度',
  `top_p` int(11) DEFAULT NULL COMMENT '默认结果',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统模型';

-- ----------------------------
-- Table structure for demand
-- ----------------------------
DROP TABLE IF EXISTS `demand`;
CREATE TABLE `demand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `did` varchar(50) NOT NULL COMMENT '需求ID',
  `fid` varchar(50) NOT NULL COMMENT '所属领域ID',
  `content` text COMMENT '需求内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `unambiguous` bit(1) DEFAULT b'0' COMMENT '明确的',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='需求';

-- ----------------------------
-- Table structure for demand_step
-- ----------------------------
DROP TABLE IF EXISTS `demand_step`;
CREATE TABLE `demand_step` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `did` varchar(50) NOT NULL COMMENT '需求ID',
   `fid` varchar(50) NOT NULL COMMENT '所属领域ID',
   `step_name` varchar(100) DEFAULT NULL COMMENT '步骤名称',
   `description` text COMMENT '步骤描述',
   `role` varchar(100) DEFAULT NULL COMMENT '负责人角色',
   `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
   `create_time` datetime DEFAULT NULL,
   `create_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='需求步骤';

-- ----------------------------
-- Table structure for agent_field
-- ----------------------------
DROP TABLE IF EXISTS `agent_field`;
CREATE TABLE `agent_field` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `fid` varchar(50) NOT NULL COMMENT '领域ID',
   `field_name` varchar(50) NOT NULL COMMENT '领域名称',
   `description` text COMMENT '领域描述',
   `status` bit(1) DEFAULT b'0' COMMENT '是否生效：0无效1有效',
   `create_time` datetime DEFAULT NULL,
   `create_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='代理领域';

-- ----------------------------
-- Table structure for access_token
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `app` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '应用',
                                `token` varchar(255) NOT NULL COMMENT 'token值',
                                `expired_time` datetime NOT NULL COMMENT '过期时间',
                                `create_time` datetime DEFAULT NULL,
                                `create_by` varchar(50) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COMMENT='第三方接口访问token';

DROP TABLE IF EXISTS `chat_request_log`;
CREATE TABLE `chat_request_log` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `user_id` int(11) NOT NULL COMMENT '用户ID',
                                    `kid` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '知识库ID',
                                    `request_time` datetime NOT NULL COMMENT '请求时间',
                                    `content` text COMMENT '内容',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8mb4 COMMENT='对话请求日志';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `admin_flag` bit DEFAULT false NULL COMMENT '管理员角色',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `std_tokens` bigint(20) DEFAULT NULL COMMENT '标准token余量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `sys_user` (`id`, `user_name`, `email`, `password`, `nick_name`, `mobile`, `avatar_url`, admin_flag,`register_time`, `last_login_time`, `std_tokens`, `create_time`, `create_by`) VALUES (1, 'demo', 'xxx@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '心远', '18000000000', NULL, false,'2023-6-15 13:53:42', '2023-6-15 13:53:45', 10000, '2023-6-15 13:53:53', '18000000000');