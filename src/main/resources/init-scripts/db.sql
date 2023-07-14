/*
Navicat MySQL Data Transfer

Source Server         : myaliyun
Source Server Version : 50728
Source Host           : 47.103.117.181:3306
Source Database       : openai

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2023-07-14 21:30:27
*/

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
  `kname` varchar(50) NOT NULL COMMENT '知识库名称',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_kname` (`kname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='知识库';

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
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `std_tokens` bigint(20) DEFAULT NULL COMMENT '标准token余量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `sys_user` (`id`, `user_name`, `email`, `password`, `nick_name`, `mobile`, `avatar_url`, `register_time`, `last_login_time`, `std_tokens`, `create_time`, `create_by`) VALUES (1, 'hkh', 'xxx@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '心远', '18000000000', NULL, '2023-6-15 13:53:42', '2023-6-15 13:53:45', 10000, '2023-6-15 13:53:53', '18000000000');