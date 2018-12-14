/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.100-mysql
Source Server Version : 50713
Source Host           : 192.168.1.108:3306
Source Database       : cloudoa

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2018-12-13 16:40:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jbpm4_deployment
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
  `DBID_` bigint(20) NOT NULL,
  `NAME_` longtext,
  `TIMESTAMP_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_deployment
-- ----------------------------
INSERT INTO `jbpm4_deployment` VALUES ('1', null, '0', 'active');
INSERT INTO `jbpm4_deployment` VALUES ('7', null, '0', 'active');

-- ----------------------------
-- Table structure for jbpm4_deployprop
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
  `DBID_` bigint(20) NOT NULL,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `OBJNAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `STRINGVAL_` varchar(255) DEFAULT NULL,
  `LONGVAL_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  KEY `FK_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  CONSTRAINT `FK_DEPLPROP_DEPL` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_deployprop
-- ----------------------------
INSERT INTO `jbpm4_deployprop` VALUES ('3', '1', 'gather', 'langid', 'jpdl-4.4', null);
INSERT INTO `jbpm4_deployprop` VALUES ('4', '1', 'gather', 'pdid', 'gather-1', null);
INSERT INTO `jbpm4_deployprop` VALUES ('5', '1', 'gather', 'pdkey', 'gather', null);
INSERT INTO `jbpm4_deployprop` VALUES ('6', '1', 'gather', 'pdversion', null, '1');
INSERT INTO `jbpm4_deployprop` VALUES ('9', '7', 'dispatch', 'langid', 'jpdl-4.4', null);
INSERT INTO `jbpm4_deployprop` VALUES ('10', '7', 'dispatch', 'pdid', 'dispatch-1', null);
INSERT INTO `jbpm4_deployprop` VALUES ('11', '7', 'dispatch', 'pdkey', 'dispatch', null);
INSERT INTO `jbpm4_deployprop` VALUES ('12', '7', 'dispatch', 'pdversion', null, '1');

-- ----------------------------
-- Table structure for jbpm4_execution
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ACTIVITYNAME_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `HISACTINST_` bigint(20) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `INSTANCE_` bigint(20) DEFAULT NULL,
  `SUPEREXEC_` bigint(20) DEFAULT NULL,
  `SUBPROCINST_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  UNIQUE KEY `ID_` (`ID_`),
  KEY `IDX_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `IDX_EXEC_PARENT` (`PARENT_`),
  KEY `IDX_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `IDX_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `FK_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `FK_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `FK_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `FK_EXEC_PARENT` (`PARENT_`),
  CONSTRAINT `FK_EXEC_INSTANCE` FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUBPI` FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUPEREXEC` FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_execution
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_actinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TRANSITION_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HTI_HTASK` (`HTASK_`),
  KEY `IDX_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HTI_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HACTI_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HTI_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_detail
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HPROCIIDX_` int(11) DEFAULT NULL,
  `HACTI_` bigint(20) DEFAULT NULL,
  `HACTIIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  `HTASKIDX_` int(11) DEFAULT NULL,
  `HVAR_` bigint(20) DEFAULT NULL,
  `HVARIDX_` int(11) DEFAULT NULL,
  `MESSAGE_` longtext,
  `OLD_STR_` varchar(255) DEFAULT NULL,
  `NEW_STR_` varchar(255) DEFAULT NULL,
  `OLD_INT_` int(11) DEFAULT NULL,
  `NEW_INT_` int(11) DEFAULT NULL,
  `OLD_TIME_` datetime DEFAULT NULL,
  `NEW_TIME_` datetime DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HDET_HVAR` (`HVAR_`),
  KEY `IDX_HDET_HACTI` (`HACTI_`),
  KEY `IDX_HDET_HTASK` (`HTASK_`),
  KEY `IDX_HDET_HPROCI` (`HPROCI_`),
  KEY `FK_HDETAIL_HVAR` (`HVAR_`),
  KEY `FK_HDETAIL_HPROCI` (`HPROCI_`),
  KEY `FK_HDETAIL_HTASK` (`HTASK_`),
  KEY `FK_HDETAIL_HACTI` (`HACTI_`),
  CONSTRAINT `FK_HDETAIL_HACTI` FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HVAR` FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_detail
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_procinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ENDACTIVITY_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `OUTCOME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HSUPERT_SUB` (`SUPERTASK_`),
  KEY `FK_HSUPERT_SUB` (`SUPERTASK_`),
  CONSTRAINT `FK_HSUPERT_SUB` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_task
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_var
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `PROCINSTID_` varchar(255) DEFAULT NULL,
  `EXECUTIONID_` varchar(255) DEFAULT NULL,
  `VARNAME_` varchar(255) DEFAULT NULL,
  `VALUE_` varchar(5000) DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HVAR_HTASK` (`HTASK_`),
  KEY `IDX_HVAR_HPROCI` (`HPROCI_`),
  KEY `FK_HVAR_HPROCI` (`HPROCI_`),
  KEY `FK_HVAR_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HVAR_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HVAR_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_var
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_job
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ISEXCLUSIVE_` bit(1) DEFAULT NULL,
  `LOCKOWNER_` varchar(255) DEFAULT NULL,
  `LOCKEXPTIME_` datetime DEFAULT NULL,
  `EXCEPTION_` longtext,
  `RETRIES_` int(11) DEFAULT NULL,
  `PROCESSINSTANCE_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `CFG_` bigint(20) DEFAULT NULL,
  `SIGNAL_` varchar(255) DEFAULT NULL,
  `EVENT_` varchar(255) DEFAULT NULL,
  `REPEAT_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_JOBRETRIES` (`RETRIES_`),
  KEY `IDX_JOBDUEDATE` (`DUEDATE_`),
  KEY `IDX_JOBLOCKEXP` (`LOCKEXPTIME_`),
  KEY `IDX_JOB_CFG` (`CFG_`),
  KEY `IDX_JOB_EXE` (`EXECUTION_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_JOB_CFG` (`CFG_`),
  CONSTRAINT `FK_JOB_CFG` FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_job
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_lob
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `BLOB_VALUE_` longblob,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `NAME_` longtext,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  KEY `FK_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  CONSTRAINT `FK_LOB_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_lob
-- ----------------------------
INSERT INTO `jbpm4_lob` VALUES ('2', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A0A3C70726F63657373206E616D653D226761746865722220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E342F6A70646C223E0D0A2020203C737461727420673D2234312C33312C34382C343822206E616D653D22737461727431223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE694B6E69687E799BBE8AEB02220746F3D22E694B6E69687E799BBE8AEB0222F3E0D0A2020203C2F73746172743E0D0A2020203C7461736B2061737369676E65653D22237B637265617465727D2220673D223235372C32382C39322C353222206E616D653D22E694B6E69687E799BBE8AEB0223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E9A286E5AFBCE689B9E99885222F3E0D0A2020202020203C7472616E736974696F6E20673D222D34332C2D323122206E616D653D22E8BDACE694B6E69687E58886E58F912220746F3D22E694B6E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D222D33302C2D323122206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223235382C3138392C39322C353222206E616D653D22E9A286E5AFBCE689B9E99885223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E8BDACE9A286E5AFBCE689B9E99885222F3E0D0A2020202020203C7472616E736974696F6E20673D222D33372C2D323222206E616D653D22E8BDACE694B6E69687E58886E58F912220746F3D22E694B6E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D222D34322C313022206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C73637269707420657870723D226C6F6F702220673D2233352C3138332C39322C353222206E616D653D22E8BDACE9A286E5AFBCE689B9E99885223E0D0A2020202020203C7472616E736974696F6E20673D223139392C3331373A2D34362C2D313522206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E9A286E5AFBCE689B9E99885222F3E0D0A2020203C2F7363726970743E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223435332C3138362C39322C353222206E616D653D22E694B6E69687E58886E58F91223E0D0A2020202020203C7472616E736974696F6E20673D222D33362C2D323122206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B20673D223434362C33352C39322C353222206E616D653D22E694B6E69687E99885E8AFBB223E0D0A2020203C61737369676E6D656E742D68616E646C657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E696D706C2E4D756C746941737369676E5461736B223E200D0A20202020202020203C2F61737369676E6D656E742D68616E646C65723E0D0A2020202020203C7472616E736974696F6E20673D222D32312C2D323022206E616D653D22E5B7B2E998852220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D227A697065722220673D223637382C34302C38302C343022206E616D653D22E5BD92E6A1A3223E0A202020203C6F6E206576656E743D2274696D656F7574223E0A2020202020203C74696D657220647565646174653D2231207365636F6E6473222F3E0A2020202020203C6576656E742D6C697374656E657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E5A69704265686176696F724C697374656E657222202F3E0A202020203C2F6F6E3E0A20202009093C7472616E736974696F6E20673D222D32352C2D313522206E616D653D22E5BD92E6A1A32220746F3D22656E6431222F3E0D0A2020203C2F7461736B3E0D0A2020203C656E6420673D223831372C33342C34382C343822206E616D653D22656E6431222F3E2020200D0A3C2F70726F636573733E, '1', 'cn/com/qytx/cbb/publicDom/service/gather-file.jpdl.xml');
INSERT INTO `jbpm4_lob` VALUES ('8', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A0A3C70726F63657373206E616D653D2264697370617463682220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E342F6A70646C223E0D0A2020203C737461727420673D2234392C3136322C34382C343822206E616D653D22737461727431223E0D0A2020202020203C7472616E736974696F6E20673D222D34302C2D323122206E616D653D22E8BDACE58F91E69687E68B9FE7A8BF2220746F3D22E58F91E69687E68B9FE7A8BF222F3E0D0A2020203C2F73746172743E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223435382C3135342C39322C353222206E616D653D22E58F91E69687E6A0B8E7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D222D33352C2D323622206E616D653D22E8BDACE79B96E7ABA02220746F3D22E5A597E7BAA2E79B96E7ABA0222F3E0D0A2020202020203C7472616E736974696F6E20673D223439382C3433303A2D35342C2D313822206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E7BBA7E7BBADE6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D223734322C3332323A2D34372C2D3922206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223733372C3336313A2D31382C2D3522206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223636322C3134392C39322C353222206E616D653D22E5A597E7BAA2E79B96E7ABA0223E0D0A2020202020203C7472616E736974696F6E20673D222D33312C2D313422206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223734302C3238323A2D33372C2D333222206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D22313131342C3238363A2D31362C2D3322206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B637265617465727D2220673D223231322C3135352C39322C353222206E616D653D22E58F91E69687E68B9FE7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D223338372C3138303A2D34312C2D3922206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D223639332C3131383A2D32382C2D3122206E616D653D22E8BDACE79B96E7ABA02220746F3D22E5A597E7BAA2E79B96E7ABA0222F3E0D0A2020202020203C7472616E736974696F6E20673D223638392C37323A2D34312C2D323322206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223637362C32353A2D33312C2D313622206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223932312C3135362C39322C353222206E616D653D22E58F91E69687E58886E58F91223E0D0A2020202020203C7472616E736974696F6E20673D222D32382C2D3522206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A0D0A2020203C656E6420673D22313332362C3136312C34382C343822206E616D653D22656E6431222F3E0D0A2020203C73637269707420657870723D226C6F6F702220673D223230392C3430332C39322C353222206E616D653D22E7BBA7E7BBADE6A0B8E7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D222D33382C2D323222206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020203C2F7363726970743E0D0A2020203C7461736B2061737369676E65653D227A697065722220673D22313134322C3135342C39322C353222206E616D653D22E5BD92E6A1A3223E0A20202020202020203C6F6E206576656E743D2274696D656F7574223E0A2020202020203C74696D657220647565646174653D2231207365636F6E6473222F3E0A2020202020203C6576656E742D6C697374656E657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E5A69704265686176696F724C697374656E657222202F3E0A202020203C2F6F6E3E0D0A2020202020203C7472616E736974696F6E20673D222D32302C2D323122206E616D653D22E5BD92E6A1A32220746F3D22656E6431222F3E0D0A2020203C2F7461736B3E0D0A3C2F70726F636573733E, '7', 'cn/com/qytx/cbb/publicDom/service/dispatch-file.jpdl.xml');

-- ----------------------------
-- Table structure for jbpm4_participation
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `GROUPID_` varchar(255) DEFAULT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_PART_TASK` (`TASK_`),
  KEY `FK_PART_SWIMLANE` (`SWIMLANE_`),
  KEY `FK_PART_TASK` (`TASK_`),
  CONSTRAINT `FK_PART_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `FK_PART_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_participation
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_property
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_property`;
CREATE TABLE `jbpm4_property` (
  `KEY_` varchar(255) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_property
-- ----------------------------
INSERT INTO `jbpm4_property` VALUES ('next.dbid', '1', '10001');

-- ----------------------------
-- Table structure for jbpm4_swimlane
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_SWIMLANE_EXEC` (`EXECUTION_`),
  KEY `FK_SWIMLANE_EXEC` (`EXECUTION_`),
  CONSTRAINT `FK_SWIMLANE_EXEC` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_swimlane
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` char(1) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DESCR_` longtext,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `FORM_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `PROGRESS_` int(11) DEFAULT NULL,
  `SIGNALLING_` bit(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `PROCINST_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  `TASKDEFNAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SWIML` (`SWIMLANE_`),
  KEY `FK_TASK_SUPERTASK` (`SUPERTASK_`),
  CONSTRAINT `FK_TASK_SUPERTASK` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`),
  CONSTRAINT `FK_TASK_SWIML` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_task
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_variable
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CONVERTER_` varchar(255) DEFAULT NULL,
  `HIST_` bit(1) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `LOB_` bigint(20) DEFAULT NULL,
  `DATE_VALUE_` datetime DEFAULT NULL,
  `DOUBLE_VALUE_` double DEFAULT NULL,
  `CLASSNAME_` varchar(255) DEFAULT NULL,
  `LONG_VALUE_` bigint(20) DEFAULT NULL,
  `STRING_VALUE_` varchar(5000) DEFAULT NULL,
  `TEXT_VALUE_` longtext,
  `EXESYS_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_VAR_EXESYS` (`EXESYS_`),
  KEY `IDX_VAR_TASK` (`TASK_`),
  KEY `IDX_VAR_EXECUTION` (`EXECUTION_`),
  KEY `IDX_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_EXESYS` (`EXESYS_`),
  KEY `FK_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_TASK` (`TASK_`),
  KEY `FK_VAR_EXECUTION` (`EXECUTION_`),
  CONSTRAINT `FK_VAR_EXECUTION` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_EXESYS` FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_LOB` FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`),
  CONSTRAINT `FK_VAR_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_variable
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `action` varchar(64) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `log_content` varchar(255) DEFAULT NULL,
  `log_type` int(11) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `ref_id` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sys_name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for pcompanyinfo_openedservices
-- ----------------------------
DROP TABLE IF EXISTS `pcompanyinfo_openedservices`;
CREATE TABLE `pcompanyinfo_openedservices` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `companyid` int(11) DEFAULT NULL,
  `feecode` varchar(50) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `lastupdatetime` datetime DEFAULT NULL,
  `openflag` int(11) DEFAULT NULL,
  `serviceinfo` varchar(50) DEFAULT NULL,
  `servicetype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pcompanyinfo_openedservices
-- ----------------------------

-- ----------------------------
-- Table structure for publicphonebook
-- ----------------------------
DROP TABLE IF EXISTS `publicphonebook`;
CREATE TABLE `publicphonebook` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `categoryid` int(11) DEFAULT NULL,
  `categoryname` varchar(200) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `picture` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publicphonebook
-- ----------------------------

-- ----------------------------
-- Table structure for publicphonebookcategory
-- ----------------------------
DROP TABLE IF EXISTS `publicphonebookcategory`;
CREATE TABLE `publicphonebookcategory` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `categoryname` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publicphonebookcategory
-- ----------------------------

-- ----------------------------
-- Table structure for tb_attendance
-- ----------------------------
DROP TABLE IF EXISTS `tb_attendance`;
CREATE TABLE `tb_attendance` (
  `att_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `att_Source` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`att_id`),
  UNIQUE KEY `att_id` (`att_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_attendance
-- ----------------------------

-- ----------------------------
-- Table structure for tb_attendance_days
-- ----------------------------
DROP TABLE IF EXISTS `tb_attendance_days`;
CREATE TABLE `tb_attendance_days` (
  `att_day_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `day` datetime DEFAULT NULL,
  `week` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`att_day_id`),
  UNIQUE KEY `att_day_id` (`att_day_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_attendance_days
-- ----------------------------

-- ----------------------------
-- Table structure for tb_attendance_ipset
-- ----------------------------
DROP TABLE IF EXISTS `tb_attendance_ipset`;
CREATE TABLE `tb_attendance_ipset` (
  `ipset_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `begin_ip` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_ip` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ipset_id`),
  UNIQUE KEY `ipset_id` (`ipset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_attendance_ipset
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_baseworkflow
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_baseworkflow`;
CREATE TABLE `tb_cbb_baseworkflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `form_data` varchar(255) DEFAULT NULL,
  `instanceId` varchar(255) DEFAULT NULL,
  `userIds` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_baseworkflow
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_comment`;
CREATE TABLE `tb_cbb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `approve_date` datetime DEFAULT NULL,
  `attach_ids` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  `is_anonymity` int(11) DEFAULT NULL,
  `statue` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `approve_user_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK218AB9B29B9F8D7C` (`create_user_id`),
  KEY `FK218AB9B2B41A0ECD` (`approve_user_id`),
  KEY `FK218AB9B21E4181D1` (`parent_id`),
  CONSTRAINT `FK218AB9B21E4181D1` FOREIGN KEY (`parent_id`) REFERENCES `tb_cbb_comment` (`id`),
  CONSTRAINT `FK218AB9B29B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK218AB9B2B41A0ECD` FOREIGN KEY (`approve_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_data_priv
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_data_priv`;
CREATE TABLE `tb_cbb_data_priv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `group_ids` varchar(2000) DEFAULT NULL,
  `group_names` varchar(2000) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `module_name` varchar(200) DEFAULT NULL,
  `ref_id` int(11) DEFAULT NULL,
  `ref_name` varchar(50) DEFAULT NULL,
  `role_ids` varchar(2000) DEFAULT NULL,
  `role_names` varchar(2000) DEFAULT NULL,
  `sub_module_name` varchar(100) DEFAULT NULL,
  `user_ids` varchar(2000) DEFAULT NULL,
  `user_names` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_data_priv
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_dict`;
CREATE TABLE `tb_cbb_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `info_type` varchar(100) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `record_user_id` int(11) DEFAULT NULL,
  `sys_tag` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `info_order` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1374 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_dict
-- ----------------------------
INSERT INTO `tb_cbb_dict` VALUES ('1', '2014-06-28 11:23:57', 'notifyType35', '0', '2014-06-28 11:23:57', '通知公告', '4', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('2', '2014-06-30 17:53:15', 'notifyType35', '1', '2014-10-08 15:12:57', '内网公告', '1', '1', '33', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('3', '2014-06-30 17:53:25', 'notifyType35', '1', '2014-06-30 17:53:25', '外网公告', '1', '1', '35', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('4', '2014-06-30 17:53:38', 'notifyType35', '1', '2014-10-13 14:59:42', '系统公告', '11612886', '1', '36', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('5', null, 'dom_catery', '0', null, '公文一级分类', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('6', '2014-07-04 11:27:12', 'dom_catery', '1', '2015-08-06 14:38:30', '收文类型', '1', '1', '1', '5', '1');
INSERT INTO `tb_cbb_dict` VALUES ('7', '2014-07-04 11:27:21', 'dom_catery', '1', '2015-08-06 14:38:34', '发文类型', '1', '1', '2', '5', '1');
INSERT INTO `tb_cbb_dict` VALUES ('8', '2014-07-07 09:51:17', 'huanji', '0', '2014-07-07 09:51:17', '缓急', '1', '-1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('9', '2014-07-07 09:56:43', 'huanji', '0', '2014-07-07 09:56:43', '紧急', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('10', '2014-07-07 09:56:50', 'huanji', '0', '2014-07-13 14:11:26', '一般', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('11', '2014-07-13 13:40:08', 'miji', '0', '2014-07-13 13:40:08', '密级', '11612407', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('12', '2014-07-13 13:40:19', 'miji', '0', '2014-07-13 13:40:19', '一般', '11612407', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('13', '2014-07-13 13:40:30', 'miji', '0', '2015-08-05 18:50:10', '保密', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('14', '2014-07-16 15:45:40', 'zhongyaodu', '0', '2014-07-16 16:02:54', '重要度', '1', '-1', '10', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('15', '2014-07-16 15:45:59', 'zhongyaodu', '0', '2014-07-16 15:45:59', '一般', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('16', '2014-07-16 15:46:08', 'zhongyaodu', '0', '2014-07-16 15:46:08', '重要', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('17', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 18:02:49', '0', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('18', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:13:47', '部门新闻', '1', '1', '41', '3', '1');
INSERT INTO `tb_cbb_dict` VALUES ('19', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:11:57', '部门公告', '1', '1', '42', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('20', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:13:21', '学习资料', '1', '1', '43', '3', '1');
INSERT INTO `tb_cbb_dict` VALUES ('21', '2014-08-06 11:18:03', 'dom_catery', '1', '2015-08-06 14:38:42', '国资委公文', '1', '1', '3', '5', '1');
INSERT INTO `tb_cbb_dict` VALUES ('22', '2014-08-27 15:03:28', 'user_type', '0', '2014-08-27 15:03:28', '员工类型', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('23', '2014-08-27 15:04:03', 'marriage_status', '0', '2014-08-27 15:04:03', '婚姻状况', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('24', '2014-08-27 15:04:03', 'politics_face', '0', '2014-08-27 15:04:03', '政治面貌', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('25', '2014-08-27 15:04:03', 'registered_type', '0', '2014-08-27 15:04:03', '户口类型', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('26', '2014-08-27 15:04:03', 'job_title', '0', '2014-08-27 15:04:03', '职称', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('27', '2014-08-27 15:04:03', 'job_title_level', '0', '2014-08-27 15:04:03', '职称级别', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('28', '2014-08-27 15:04:03', 'station', '0', '2014-08-27 15:04:03', '岗位', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('29', '2014-08-27 15:04:03', 'work_status', '0', '2014-08-27 15:04:03', '在职状态', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('30', '2014-08-27 15:04:03', 'edu_qualifications', '0', '2014-08-27 15:04:03', '学历', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('31', '2014-08-27 15:04:03', 'edu_level', '0', '2014-08-27 15:04:03', '学位', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('32', '2014-08-27 15:08:03', 'marriage_status', '0', '2014-08-27 15:08:03', '已婚', '1', '1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('33', '2014-08-27 15:08:11', 'marriage_status', '0', '2014-08-27 15:08:11', '未婚', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('34', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '团员', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('35', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '党员', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('36', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '务农', '1', '1', '3', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('37', '2014-08-27 15:11:20', 'registered_type', '0', '2014-09-09 19:14:04', '城镇', '1', '1', '1', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('38', '2014-08-27 15:11:20', 'registered_type', '0', '2014-08-27 15:11:20', '农村', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('39', '2014-08-27 15:13:02', 'job_title', '0', '2014-08-27 15:13:02', '正科级', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('40', '2014-08-27 15:13:02', 'job_title', '0', '2014-08-27 15:13:02', '副科级', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('41', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '正高级', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('42', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '副高级', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('43', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '中级', '1', '1', '3', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('44', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '助理级', '1', '1', '4', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('45', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '员级', '1', '1', '5', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('46', '2014-08-27 15:19:14', 'station', '0', '2014-08-27 15:19:14', '行政', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('47', '2014-08-27 15:19:14', 'station', '0', '2014-08-27 15:19:14', '人事', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('48', '2014-08-27 15:19:52', 'work_status', '0', '2014-08-27 15:19:52', '在职', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('49', '2014-08-27 15:19:52', 'work_status', '0', '2014-08-27 15:19:52', '离职', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('50', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '学士', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('51', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '硕士', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('52', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '博士', '1', '1', '3', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('53', '2014-08-27 15:22:33', 'user_type', '0', '2014-08-27 15:22:33', '类型1', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('54', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '高中', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('55', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '大专', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('56', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '本科', '1', '1', '3', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('57', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '硕士', '1', '1', '4', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('58', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '博士', '1', '1', '5', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('59', null, 'redTemplate', '0', null, '套红模板类型', '1', '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('60', '2014-12-01 11:20:06', 'redTemplate', '0', '2014-12-01 11:20:06', '套红模板1', '1', '1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('61', '2014-12-01 11:20:14', 'redTemplate', '0', '2014-12-01 11:20:14', '套红模板2', '1', '1', '1', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('62', '2014-06-28 11:23:57', 'notifyType35', '0', '2014-06-28 11:23:57', '通知公告', '4', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('63', '2014-06-30 17:53:15', 'notifyType35', '0', '2014-10-08 15:12:57', '内网公告', '1', '1', '33', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('64', '2014-06-30 17:53:25', 'notifyType35', '0', '2014-06-30 17:53:25', '外网公告', '1', '1', '35', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('65', '2014-06-30 17:53:38', 'notifyType35', '0', '2014-10-13 14:59:42', '系统公告', '11612886', '1', '36', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('66', null, 'dom_catery', '0', null, '公文一级分类', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('67', '2014-07-04 11:27:12', 'dom_catery', '0', '2014-07-04 11:27:12', '收文类型', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('68', '2014-07-04 11:27:21', 'dom_catery', '0', '2014-07-04 11:27:21', '发文类型', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('69', '2014-07-07 09:51:17', 'huanji', '0', '2014-07-07 09:51:17', '缓急', '1', '-1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('70', '2014-07-07 09:56:43', 'huanji', '0', '2014-07-07 09:56:43', '紧急', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('71', '2014-07-07 09:56:50', 'huanji', '0', '2014-07-13 14:11:26', '一般', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('72', '2014-07-13 13:40:08', 'miji', '0', '2014-07-13 13:40:08', '密级', '11612407', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('73', '2014-07-13 13:40:19', 'miji', '0', '2014-07-13 13:40:19', '一般', '11612407', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('74', '2014-07-13 13:40:30', 'miji', '0', '2014-07-13 13:40:30', '保密', '11612407', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('75', '2014-07-16 15:45:40', 'zhongyaodu', '0', '2014-07-16 16:02:54', '重要度', '1', '-1', '10', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('76', '2014-07-16 15:45:59', 'zhongyaodu', '0', '2014-07-16 15:45:59', '一般', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('77', '2014-07-16 15:46:08', 'zhongyaodu', '0', '2014-07-16 15:46:08', '重要', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('78', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:13:47', '部门新闻', '1', '1', '41', '3', '2');
INSERT INTO `tb_cbb_dict` VALUES ('79', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:11:57', '部门公告', '1', '1', '42', '2', '2');
INSERT INTO `tb_cbb_dict` VALUES ('80', '2014-06-28 11:23:57', 'notifyType163', '0', '2014-09-09 19:13:21', '学习资料', '1', '1', '43', '3', '2');
INSERT INTO `tb_cbb_dict` VALUES ('81', '2014-08-06 11:18:03', 'dom_catery', '0', '2014-08-06 11:18:03', '国资委公文', '1', '1', '3', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('82', '2014-08-27 15:03:28', 'user_type', '0', '2014-08-27 15:03:28', '员工类型', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('83', '2014-08-27 15:04:03', 'marriage_status', '0', '2014-08-27 15:04:03', '婚姻状况', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('84', '2014-08-27 15:04:03', 'politics_face', '0', '2014-08-27 15:04:03', '政治面貌', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('85', '2014-08-27 15:04:03', 'registered_type', '0', '2014-08-27 15:04:03', '户口类型', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('86', '2014-08-27 15:04:03', 'job_title', '0', '2014-08-27 15:04:03', '职称', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('87', '2014-08-27 15:04:03', 'job_title_level', '0', '2014-08-27 15:04:03', '职称级别', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('88', '2014-08-27 15:04:03', 'station', '0', '2014-08-27 15:04:03', '岗位', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('89', '2014-08-27 15:04:03', 'work_status', '0', '2014-08-27 15:04:03', '在职状态', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('90', '2014-08-27 15:04:03', 'edu_qualifications', '0', '2014-08-27 15:04:03', '学历', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('91', '2014-08-27 15:04:03', 'edu_level', '0', '2014-08-27 15:04:03', '学位', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('92', '2014-08-27 15:08:03', 'marriage_status', '0', '2014-08-27 15:08:03', '已婚', '1', '1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('93', '2014-08-27 15:08:11', 'marriage_status', '0', '2014-08-27 15:08:11', '未婚', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('94', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '团员', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('95', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '党员', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('96', '2014-08-27 15:08:46', 'politics_face', '0', '2014-08-27 15:08:46', '务农', '1', '1', '3', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('97', '2014-08-27 15:11:20', 'registered_type', '0', '2014-09-09 19:14:04', '城镇', '1', '1', '1', '2', '2');
INSERT INTO `tb_cbb_dict` VALUES ('98', '2014-08-27 15:11:20', 'registered_type', '0', '2014-08-27 15:11:20', '农村', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('99', '2014-08-27 15:13:02', 'job_title', '0', '2014-08-27 15:13:02', '正科级', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('100', '2014-08-27 15:13:02', 'job_title', '0', '2014-08-27 15:13:02', '副科级', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('101', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '正高级', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('102', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '副高级', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('103', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '中级', '1', '1', '3', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('104', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '助理级', '1', '1', '4', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('105', '2014-08-27 15:16:37', 'job_title_level', '0', '2014-08-27 15:16:37', '员级', '1', '1', '5', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('106', '2014-08-27 15:19:14', 'station', '0', '2014-08-27 15:19:14', '行政', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('107', '2014-08-27 15:19:14', 'station', '0', '2014-08-27 15:19:14', '人事', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('108', '2014-08-27 15:19:52', 'work_status', '0', '2014-08-27 15:19:52', '在职', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('109', '2014-08-27 15:19:52', 'work_status', '0', '2014-08-27 15:19:52', '离职', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('110', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '学士', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('111', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '硕士', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('112', '2014-08-27 15:21:28', 'edu_level', '0', '2014-08-27 15:21:28', '博士', '1', '1', '3', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('113', '2014-08-27 15:22:33', 'user_type', '0', '2014-08-27 15:22:33', '类型1', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('114', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '高中', '1', '1', '1', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('115', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '大专', '1', '1', '2', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('116', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '本科', '1', '1', '3', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('117', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '硕士', '1', '1', '4', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('118', '2014-08-27 15:24:16', 'edu_qualifications', '0', '2014-08-27 15:24:16', '博士', '1', '1', '5', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('119', null, 'redTemplate', '0', null, '套红模板类型', '1', '-1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('120', '2014-12-01 11:20:06', 'redTemplate', '0', '2014-12-01 11:20:06', '套红模板1', '1', '1', '0', '1', '2');
INSERT INTO `tb_cbb_dict` VALUES ('121', '2014-12-01 11:20:14', 'redTemplate', '0', '2014-12-01 11:20:14', '套红模板2', '1', '1', '1', '2', '2');
INSERT INTO `tb_cbb_dict` VALUES ('122', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('123', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('124', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('125', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('126', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('127', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('128', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('129', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('130', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('131', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('132', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('133', null, 'miji', '0', null, '一般', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('134', null, 'miji', '0', null, '保密', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('135', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('136', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('137', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('138', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('139', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '0');
INSERT INTO `tb_cbb_dict` VALUES ('140', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '0');
INSERT INTO `tb_cbb_dict` VALUES ('141', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '0');
INSERT INTO `tb_cbb_dict` VALUES ('142', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('143', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('144', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('145', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('146', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('147', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('148', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('149', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('150', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('151', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('152', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('153', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('154', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('155', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('156', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('157', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('158', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '0');
INSERT INTO `tb_cbb_dict` VALUES ('159', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('160', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('161', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('162', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('163', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('164', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('165', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('166', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('167', null, 'station', '0', null, '行政', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('168', null, 'station', '0', null, '人事', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('169', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('170', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('171', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('172', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('173', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('174', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('175', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('176', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('177', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('178', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('179', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('180', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '0');
INSERT INTO `tb_cbb_dict` VALUES ('181', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('182', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('183', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('184', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('185', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('186', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('187', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('188', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('189', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('190', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('191', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('192', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('193', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('194', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('195', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('196', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('197', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '0');
INSERT INTO `tb_cbb_dict` VALUES ('198', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('199', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('200', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('201', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('202', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('203', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('204', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('205', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('206', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('207', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('208', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('209', null, 'miji', '0', null, '一般', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('210', null, 'miji', '0', null, '保密', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('211', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('212', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('213', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('214', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '9');
INSERT INTO `tb_cbb_dict` VALUES ('215', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('216', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '9');
INSERT INTO `tb_cbb_dict` VALUES ('217', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('218', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('219', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('220', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('221', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('222', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('223', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('224', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('225', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('226', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('227', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('228', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('229', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('230', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('231', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('232', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('233', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('234', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('235', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('236', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('237', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('238', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('239', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('240', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('241', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('242', null, 'station', '0', null, '行政', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('243', null, 'station', '0', null, '人事', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('244', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('245', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('246', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('247', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('248', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('249', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('250', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('251', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('252', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('253', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('254', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('255', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('256', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('257', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('258', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('259', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('260', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('261', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('262', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('263', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('264', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('265', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('266', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('267', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('268', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('269', null, 'miji', '0', null, '一般', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('270', null, 'miji', '0', null, '保密', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('271', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('272', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('273', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('274', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '9');
INSERT INTO `tb_cbb_dict` VALUES ('275', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('276', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '9');
INSERT INTO `tb_cbb_dict` VALUES ('277', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('278', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('279', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('280', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('281', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('282', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('283', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('284', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('285', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('286', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('287', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('288', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('289', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('290', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('291', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('292', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('293', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('294', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('295', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('296', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('297', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('298', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('299', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('300', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('301', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('302', null, 'station', '0', null, '行政', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('303', null, 'station', '0', null, '人事', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('304', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('305', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('306', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('307', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('308', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('309', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('310', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('311', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('312', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('313', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('314', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('315', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('316', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '9');
INSERT INTO `tb_cbb_dict` VALUES ('317', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '9');
INSERT INTO `tb_cbb_dict` VALUES ('318', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('319', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('320', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('321', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('322', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('323', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('324', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('325', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('326', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('327', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('328', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('329', null, 'miji', '0', null, '一般', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('330', null, 'miji', '0', null, '保密', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('331', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('332', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('333', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('334', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '10');
INSERT INTO `tb_cbb_dict` VALUES ('335', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('336', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '10');
INSERT INTO `tb_cbb_dict` VALUES ('337', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('338', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('339', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('340', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('341', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('342', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('343', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('344', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('345', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('346', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('347', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('348', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('349', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('350', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('351', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('352', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('353', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('354', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('355', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('356', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('357', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('358', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('359', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('360', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('361', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('362', null, 'station', '0', null, '行政', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('363', null, 'station', '0', null, '人事', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('364', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('365', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('366', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('367', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('368', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('369', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('370', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('371', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('372', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('373', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('374', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('375', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('376', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('377', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('378', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('379', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('380', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('381', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('382', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('383', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('384', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('385', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('386', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('387', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('388', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('389', null, 'miji', '0', null, '一般', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('390', null, 'miji', '0', null, '保密', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('391', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('392', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('393', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('394', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '10');
INSERT INTO `tb_cbb_dict` VALUES ('395', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('396', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '10');
INSERT INTO `tb_cbb_dict` VALUES ('397', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('398', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('399', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('400', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('401', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('402', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('403', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('404', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('405', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('406', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('407', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('408', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('409', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('410', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('411', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('412', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('413', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('414', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('415', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('416', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('417', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('418', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('419', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('420', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('421', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('422', null, 'station', '0', null, '行政', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('423', null, 'station', '0', null, '人事', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('424', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('425', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('426', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('427', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('428', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('429', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('430', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('431', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('432', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('433', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('434', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('435', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('436', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '10');
INSERT INTO `tb_cbb_dict` VALUES ('437', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '10');
INSERT INTO `tb_cbb_dict` VALUES ('438', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('439', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('440', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('441', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('442', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('443', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '11');
INSERT INTO `tb_cbb_dict` VALUES ('444', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '11');
INSERT INTO `tb_cbb_dict` VALUES ('445', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '11');
INSERT INTO `tb_cbb_dict` VALUES ('446', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('447', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('448', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('449', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('450', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('451', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('452', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('453', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('454', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('455', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('456', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('457', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('458', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('459', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('460', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('461', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '11');
INSERT INTO `tb_cbb_dict` VALUES ('462', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('463', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('464', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('465', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('466', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('467', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('468', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('469', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('470', null, 'station', '0', null, '行政', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('471', null, 'station', '0', null, '人事', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('472', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('473', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('474', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('475', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('476', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('477', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('478', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('479', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('480', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('481', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('482', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('483', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('484', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '11');
INSERT INTO `tb_cbb_dict` VALUES ('485', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '11');
INSERT INTO `tb_cbb_dict` VALUES ('486', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('487', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('488', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('489', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('490', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('491', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('492', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('493', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('494', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('495', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('496', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('497', null, 'miji', '0', null, '一般', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('498', null, 'miji', '0', null, '保密', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('499', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('500', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('501', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('502', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '12');
INSERT INTO `tb_cbb_dict` VALUES ('503', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('504', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '12');
INSERT INTO `tb_cbb_dict` VALUES ('505', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('506', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('507', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('508', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('509', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('510', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('511', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('512', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('513', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('514', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('515', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('516', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('517', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('518', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('519', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('520', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('521', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('522', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('523', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('524', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('525', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('526', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('527', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('528', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('529', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('530', null, 'station', '0', null, '行政', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('531', null, 'station', '0', null, '人事', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('532', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('533', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('534', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('535', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('536', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('537', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('538', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('539', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('540', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('541', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('542', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('543', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('544', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('545', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('546', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('547', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('548', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('549', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('550', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('551', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('552', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('553', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('554', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('555', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('556', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('557', null, 'miji', '0', null, '一般', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('558', null, 'miji', '0', null, '保密', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('559', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('560', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('561', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('562', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '12');
INSERT INTO `tb_cbb_dict` VALUES ('563', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('564', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '12');
INSERT INTO `tb_cbb_dict` VALUES ('565', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('566', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('567', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('568', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('569', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('570', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('571', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('572', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('573', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('574', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('575', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('576', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('577', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('578', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('579', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('580', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('581', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('582', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('583', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('584', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('585', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('586', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('587', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('588', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('589', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('590', null, 'station', '0', null, '行政', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('591', null, 'station', '0', null, '人事', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('592', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('593', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('594', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('595', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('596', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('597', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('598', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('599', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('600', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('601', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('602', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('603', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('604', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '12');
INSERT INTO `tb_cbb_dict` VALUES ('605', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '12');
INSERT INTO `tb_cbb_dict` VALUES ('606', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('607', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('608', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('609', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('610', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('611', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('612', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('613', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('614', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('615', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('616', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('617', null, 'miji', '0', null, '一般', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('618', null, 'miji', '0', null, '保密', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('619', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('620', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('621', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('622', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '13');
INSERT INTO `tb_cbb_dict` VALUES ('623', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('624', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '13');
INSERT INTO `tb_cbb_dict` VALUES ('625', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('626', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('627', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('628', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('629', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('630', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('631', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('632', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('633', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('634', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('635', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('636', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('637', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('638', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('639', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('640', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('641', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('642', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('643', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('644', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('645', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('646', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('647', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('648', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('649', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('650', null, 'station', '0', null, '行政', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('651', null, 'station', '0', null, '人事', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('652', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('653', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('654', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('655', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('656', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('657', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('658', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('659', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('660', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('661', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('662', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('663', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('664', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('665', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('666', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('667', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('668', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('669', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('670', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('671', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('672', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('673', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('674', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('675', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('676', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('677', null, 'miji', '0', null, '一般', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('678', null, 'miji', '0', null, '保密', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('679', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('680', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('681', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('682', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '13');
INSERT INTO `tb_cbb_dict` VALUES ('683', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('684', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '13');
INSERT INTO `tb_cbb_dict` VALUES ('685', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('686', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('687', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('688', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('689', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('690', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('691', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('692', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('693', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('694', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('695', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('696', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('697', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('698', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('699', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('700', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('701', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('702', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('703', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('704', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('705', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('706', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('707', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('708', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('709', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('710', null, 'station', '0', null, '行政', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('711', null, 'station', '0', null, '人事', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('712', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('713', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('714', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('715', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('716', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('717', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('718', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('719', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('720', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('721', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('722', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('723', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('724', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '13');
INSERT INTO `tb_cbb_dict` VALUES ('725', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '13');
INSERT INTO `tb_cbb_dict` VALUES ('726', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('727', null, 'miji', '0', null, '一般', null, '1', '1', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('728', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('729', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('730', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('731', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('732', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('733', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('734', null, 'miji', '0', null, '一般', null, '1', '1', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('735', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('736', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('737', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('738', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('739', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '14');
INSERT INTO `tb_cbb_dict` VALUES ('740', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('741', null, 'miji', '0', null, '一般', null, '1', '1', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('742', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('743', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('744', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('745', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('746', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('747', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('748', null, 'miji', '0', null, '一般', null, '1', '1', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('749', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('750', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('751', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('752', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('753', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '15');
INSERT INTO `tb_cbb_dict` VALUES ('754', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('755', null, 'miji', '0', null, '一般', null, '1', '1', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('756', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('757', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('758', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('759', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('760', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('761', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('762', null, 'miji', '0', null, '一般', null, '1', '1', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('763', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '16');
INSERT INTO `tb_cbb_dict` VALUES ('764', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '17');
INSERT INTO `tb_cbb_dict` VALUES ('765', null, 'miji', '0', null, '一般', null, '1', '1', '1', '17');
INSERT INTO `tb_cbb_dict` VALUES ('766', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('767', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('768', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('769', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('770', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('771', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('772', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('773', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('774', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('775', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('776', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('777', null, 'miji', '0', null, '一般', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('778', null, 'miji', '0', null, '保密', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('779', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('780', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('781', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('782', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('783', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '19');
INSERT INTO `tb_cbb_dict` VALUES ('784', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('785', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '19');
INSERT INTO `tb_cbb_dict` VALUES ('786', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('787', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('788', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('789', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('790', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('791', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('792', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('793', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('794', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('795', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('796', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('797', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('798', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('799', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('800', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('801', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('802', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('803', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('804', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('805', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('806', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('807', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('808', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('809', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('810', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('811', null, 'station', '0', null, '行政', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('812', null, 'station', '0', null, '人事', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('813', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('814', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('815', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('816', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('817', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('818', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('819', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('820', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('821', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('822', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('823', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('824', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('825', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('826', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('827', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('828', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('829', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('830', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('831', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('832', null, 'huanji', '1', null, '紧急', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('833', null, 'huanji', '1', null, '一般', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('834', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('835', null, 'miji', '0', null, '一般', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('836', null, 'miji', '0', null, '保密', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('837', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('838', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('839', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('840', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '19');
INSERT INTO `tb_cbb_dict` VALUES ('841', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('842', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '19');
INSERT INTO `tb_cbb_dict` VALUES ('843', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('844', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('845', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('846', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('847', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('848', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('849', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('850', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('851', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('852', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('853', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('854', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('855', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('856', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('857', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('858', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('859', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('860', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('861', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('862', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('863', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('864', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('865', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('866', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('867', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('868', null, 'station', '0', null, '行政', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('869', null, 'station', '0', null, '人事', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('870', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('871', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('872', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('873', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('874', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('875', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('876', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('877', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('878', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('879', null, 'edu_qualifications', '0', null, '硕士', null, '1', '4', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('880', null, 'edu_qualifications', '0', null, '博士', null, '1', '5', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('881', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('882', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('883', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '19');
INSERT INTO `tb_cbb_dict` VALUES ('884', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('885', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('886', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('887', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('888', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('889', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('890', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('891', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('892', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('893', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('894', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('895', null, 'miji', '0', null, '一般', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('896', null, 'miji', '0', null, '保密', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('897', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('898', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('899', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('900', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('901', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '20');
INSERT INTO `tb_cbb_dict` VALUES ('902', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '20');
INSERT INTO `tb_cbb_dict` VALUES ('903', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '20');
INSERT INTO `tb_cbb_dict` VALUES ('904', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('905', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('906', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('907', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('908', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('909', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('910', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('911', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('912', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('913', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('914', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('915', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('916', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('917', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('918', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('919', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('920', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '20');
INSERT INTO `tb_cbb_dict` VALUES ('921', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('922', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('923', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('924', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('925', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('926', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('927', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('928', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('929', null, 'station', '0', null, '行政', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('930', null, 'station', '0', null, '人事', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('931', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('932', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('933', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('934', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('935', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('936', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('937', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('938', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('939', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('940', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('941', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('942', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '20');
INSERT INTO `tb_cbb_dict` VALUES ('943', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('944', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('945', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('946', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('947', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('948', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('949', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('950', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('951', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('952', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('953', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('954', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('955', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('956', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('957', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('958', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('959', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('960', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '20');
INSERT INTO `tb_cbb_dict` VALUES ('961', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('962', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('963', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('964', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('965', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('966', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('967', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('968', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('969', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('970', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('971', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('972', null, 'miji', '0', null, '一般', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('973', null, 'miji', '0', null, '保密', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('974', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('975', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('976', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('977', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('978', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '21');
INSERT INTO `tb_cbb_dict` VALUES ('979', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '21');
INSERT INTO `tb_cbb_dict` VALUES ('980', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '21');
INSERT INTO `tb_cbb_dict` VALUES ('981', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('982', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('983', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('984', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('985', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('986', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('987', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('988', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('989', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('990', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('991', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('992', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('993', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('994', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('995', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('996', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('997', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '21');
INSERT INTO `tb_cbb_dict` VALUES ('998', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('999', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1000', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1001', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1002', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1003', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1004', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1005', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1006', null, 'station', '0', null, '行政', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1007', null, 'station', '0', null, '人事', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1008', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1009', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1010', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1011', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1012', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1013', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1014', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1015', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1016', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1017', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1018', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1019', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1020', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1021', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1022', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1023', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1024', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1025', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1026', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1027', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1028', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1029', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1030', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1031', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1032', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1033', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1034', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1035', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1036', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1037', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '21');
INSERT INTO `tb_cbb_dict` VALUES ('1038', '2015-03-09 15:03:05', 'notifyType35', '1', '2015-03-09 15:03:05', 'rrrr', '4', '1', '37', '222', '2');
INSERT INTO `tb_cbb_dict` VALUES ('1039', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1040', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1041', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1042', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1043', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1044', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1045', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1046', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1047', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1048', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1049', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1050', null, 'miji', '0', null, '一般', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1051', null, 'miji', '0', null, '保密', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1052', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1053', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1054', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1055', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1056', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1057', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1058', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1059', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1060', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1061', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1062', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1063', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1064', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1065', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1066', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1067', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1068', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1069', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1070', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1071', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1072', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1073', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1074', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1075', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1076', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1077', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1078', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1079', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1080', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1081', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1082', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1083', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1084', null, 'station', '0', null, '行政', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1085', null, 'station', '0', null, '人事', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1086', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1087', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1088', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1089', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1090', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1091', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1092', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1093', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1094', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1095', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1096', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1097', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1098', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1099', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1100', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1101', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1102', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1103', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1104', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1105', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1106', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1107', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1108', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1109', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1110', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1111', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1112', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1113', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1114', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1115', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '22');
INSERT INTO `tb_cbb_dict` VALUES ('1116', '2015-03-09 17:44:56', 'politics_face', '0', '2015-03-09 17:44:56', '其他', '11613100', '1', '4', '1', '19');
INSERT INTO `tb_cbb_dict` VALUES ('1117', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1118', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1119', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1120', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1121', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1122', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1123', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1124', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1125', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1126', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1127', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1128', null, 'miji', '0', null, '一般', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1129', null, 'miji', '0', null, '保密', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1130', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1131', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1132', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1133', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1134', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1135', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1136', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1137', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1138', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1139', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1140', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1141', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1142', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1143', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1144', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1145', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1146', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1147', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1148', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1149', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1150', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1151', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1152', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1153', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1154', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1155', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1156', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1157', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1158', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1159', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1160', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1161', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1162', null, 'station', '0', null, '行政', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1163', null, 'station', '0', null, '人事', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1164', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1165', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1166', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1167', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1168', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1169', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1170', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1171', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1172', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1173', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1174', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1175', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1176', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1177', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1178', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1179', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1180', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1181', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1182', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1183', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1184', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1185', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1186', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1187', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1188', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1189', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1190', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1191', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1192', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '23');
INSERT INTO `tb_cbb_dict` VALUES ('1193', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1194', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1195', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1196', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1197', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1198', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1199', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1200', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1201', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1202', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1203', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1204', null, 'miji', '0', null, '一般', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1205', null, 'miji', '0', null, '保密', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1206', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1207', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1208', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1209', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1210', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1211', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1212', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1213', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1214', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1215', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1216', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1217', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1218', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1219', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1220', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1221', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1222', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1223', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1224', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1225', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1226', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1227', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1228', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1229', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1230', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1231', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1232', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1233', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1234', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1235', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1236', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1237', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1238', null, 'station', '0', null, '行政', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1239', null, 'station', '0', null, '人事', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1240', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1241', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1242', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1243', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1244', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1245', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1246', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1247', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1248', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1249', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1250', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1251', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1252', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1253', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1254', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1255', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1256', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1257', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1258', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1259', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1260', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1261', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1262', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1263', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1264', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1265', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1266', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1267', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1268', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '24');
INSERT INTO `tb_cbb_dict` VALUES ('1269', null, 'notifyType35', '0', null, '通知公告', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1270', null, 'notifyType35', '0', null, '内网公告', null, '1', '33', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1271', null, 'notifyType35', '0', null, '外网公告', null, '1', '35', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1272', null, 'notifyType35', '0', null, '系统公告', null, '1', '36', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1273', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1274', null, 'dom_catery', '0', null, '收文类型', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1275', null, 'dom_catery', '0', null, '发文类型', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1276', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1277', null, 'huanji', '0', null, '紧急', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1278', null, 'huanji', '0', null, '一般', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1279', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1280', null, 'miji', '0', null, '一般', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1281', null, 'miji', '0', null, '保密', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1282', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1283', null, 'zhongyaodu', '0', null, '一般', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1284', null, 'zhongyaodu', '0', null, '重要', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1285', null, 'notifyType163', '0', null, '0', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1286', null, 'notifyType163', '0', null, '部门新闻', null, '1', '41', '3', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1287', null, 'notifyType163', '0', null, '部门公告', null, '1', '42', '2', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1288', null, 'notifyType163', '0', null, '学习资料', null, '1', '43', '3', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1289', null, 'dom_catery', '0', null, '国资委公文', null, '1', '3', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1290', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1291', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1292', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1293', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1294', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1295', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1296', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1297', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1298', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1299', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1300', null, 'marriage_status', '0', null, '已婚', null, '1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1301', null, 'marriage_status', '0', null, '未婚', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1302', null, 'politics_face', '0', null, '团员', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1303', null, 'politics_face', '0', null, '党员', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1304', null, 'politics_face', '0', null, '务农', null, '1', '3', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1305', null, 'registered_type', '0', null, '城镇', null, '1', '1', '2', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1306', null, 'registered_type', '0', null, '农村', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1307', null, 'job_title', '0', null, '正科级', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1308', null, 'job_title', '0', null, '副科级', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1309', null, 'job_title_level', '0', null, '正高级', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1310', null, 'job_title_level', '0', null, '副高级', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1311', null, 'job_title_level', '0', null, '中级', null, '1', '3', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1312', null, 'job_title_level', '0', null, '助理级', null, '1', '4', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1313', null, 'job_title_level', '0', null, '员级', null, '1', '5', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1314', null, 'station', '0', null, '行政', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1315', null, 'station', '0', null, '人事', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1316', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1317', null, 'work_status', '0', null, '离职', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1318', null, 'edu_level', '0', null, '学士', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1319', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1320', null, 'edu_level', '0', null, '博士', null, '1', '3', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1321', null, 'user_type', '0', null, '类型1', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1322', null, 'edu_qualifications', '0', null, '高中', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1323', null, 'edu_qualifications', '0', null, '大专', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1324', null, 'edu_qualifications', '0', null, '本科', null, '1', '3', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1325', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1326', null, 'redTemplate', '0', null, '套红模板1', null, '1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1327', null, 'redTemplate', '0', null, '套红模板2', null, '1', '1', '2', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1328', null, 'dom_catery', '0', null, '公文一级分类', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1329', null, 'huanji', '0', null, '缓急', null, '-1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1330', null, 'miji', '0', null, '密级', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1331', null, 'zhongyaodu', '0', null, '重要度', null, '-1', '10', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1332', null, 'user_type', '0', null, '员工类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1333', null, 'marriage_status', '0', null, '婚姻状况', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1334', null, 'politics_face', '0', null, '政治面貌', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1335', null, 'registered_type', '0', null, '户口类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1336', null, 'job_title', '0', null, '职称', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1337', null, 'job_title_level', '0', null, '职称级别', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1338', null, 'station', '0', null, '岗位', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1339', null, 'work_status', '0', null, '在职状态', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1340', null, 'edu_qualifications', '0', null, '学历', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1341', null, 'edu_level', '0', null, '学位', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1342', null, 'work_status', '0', null, '在职', null, '1', '1', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1343', null, 'edu_level', '0', null, '硕士', null, '1', '2', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1344', null, 'redTemplate', '0', null, '套红模板类型', null, '-1', '0', '1', '25');
INSERT INTO `tb_cbb_dict` VALUES ('1345', '2015-04-01 17:12:12', 'notifyType35', '1', '2015-04-01 17:12:12', '你好公告1', '1', '1', '37', '0', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1346', '2015-04-01 17:25:09', 'dom_catery', '1', '2015-04-01 17:25:09', '公文1', '1', '1', '4', '0', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1347', '2015-04-14 09:36:13', 'politics_face', '1', '2015-04-14 09:36:13', '111', '1', '1', '4', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1348', '2015-04-30 22:15:54', 'notifyType35', '1', '2015-04-30 22:15:54', '公文1', '11614175', '1', '37', '0', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1349', '2015-04-30 22:24:37', 'notifyType35', '1', '2015-04-30 22:24:37', '一般', '11614175', '1', '37', '0', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1350', '2015-08-06 11:20:06', 'notifyType35', '1', '2015-08-06 11:20:41', '通知公告-修改', '1', '1', '38', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1351', '2015-08-06 11:22:17', 'dom_catery', '1', '2015-08-06 14:38:53', '0806收文', '1', '1', '5', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1352', '2015-08-06 11:22:40', 'huanji', '0', '2015-08-06 11:22:40', '非常紧急', '1', '1', '3', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1353', '2015-08-06 11:22:58', 'miji', '0', '2015-08-06 11:22:58', '不保密', '1', '1', '3', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1354', '2015-08-06 11:23:10', 'zhongyaodu', '0', '2015-08-06 11:23:10', '很重要', '1', '1', '3', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1355', '2015-08-06 11:24:36', 'notifyType35', '0', '2015-08-21 14:23:33', '内网通知', '1', '1', '39', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1356', '2015-08-06 11:24:43', 'notifyType35', '0', '2015-08-06 11:24:43', '外网公告', '1', '1', '40', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1357', '2015-08-06 11:24:59', 'notifyType35', '0', '2015-08-06 11:24:59', '系统公告', '1', '1', '41', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1358', '2015-08-06 14:36:44', 'dom_catery', '1', '2015-08-21 10:21:28', '测试公文类型', '1', '1', '6', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1359', '2015-08-06 15:10:02', 'redTemplate', '0', '2015-08-06 15:10:02', '测试模板', '1', '1', '2', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1360', '2015-08-21 10:21:54', 'dom_catery', '1', '2015-08-21 10:22:27', 'kki', '1', '1', '7', '522', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1361', '2015-09-16 10:09:38', 'dom_catery', '1', '2015-09-16 10:10:15', '公文分类0916', '1', '1', '8', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1362', '2015-09-16 11:39:22', 'dom_catery', '1', '2015-09-16 11:39:22', '测试公文', '1', '1', '9', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1363', '2015-09-16 11:40:48', 'dom_catery', '0', '2015-09-16 11:40:48', '测试公文', '1', '1', '10', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1364', '2015-09-16 11:41:06', 'dom_catery', '0', '2015-09-16 11:41:06', '我的公文', '1', '1', '11', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1365', '2016-02-20 10:52:19', 'notifyType759', '0', null, '信息资讯', null, '-1', '0', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1366', '2016-02-20 10:52:40', 'notifyType759', '0', '2016-02-20 10:52:40', '分类1', '1', '1', '1', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1367', '2016-02-20 10:52:48', 'notifyType759', '0', '2016-02-20 10:52:48', '分类2', '1', '1', '2', '2', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1368', '2016-02-20 10:52:54', 'notifyType759', '0', '2016-02-20 10:52:54', '分类3', '1', '1', '3', '3', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1369', '2016-03-15 18:04:29', 'notifyType35', '1', '2016-03-15 18:04:40', '32235', '1', '1', '42', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1370', '2016-03-15 18:21:07', 'dom_catery', '0', '2016-03-15 18:21:07', '收文', '1', '1', '12', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1371', '2016-03-15 18:21:12', 'dom_catery', '0', '2016-03-15 18:21:12', '发文', '1', '1', '13', '1', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1372', '2016-03-17 14:36:06', 'notifyType35', '1', '2016-03-17 14:36:09', '563753', '1', '1', '43', '357', '1');
INSERT INTO `tb_cbb_dict` VALUES ('1373', '2016-05-30 17:30:05', 'notifyType35', '1', '2016-05-30 17:30:05', '测试公告', '1', '1', '44', '4', '1');

-- ----------------------------
-- Table structure for tb_cbb_form_attribute
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_form_attribute`;
CREATE TABLE `tb_cbb_form_attribute` (
  `form_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `dept_id` varchar(100) DEFAULT NULL,
  `form_name` varchar(100) DEFAULT NULL,
  `form_source` varchar(5000) DEFAULT NULL,
  `is_new_version` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_form_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_form_authority
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_form_authority`;
CREATE TABLE `tb_cbb_form_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `candidate` varchar(500) DEFAULT NULL,
  `formid` int(11) DEFAULT NULL,
  `form_property_id` int(11) DEFAULT NULL,
  `groups` varchar(500) DEFAULT NULL,
  `propertyname` varchar(255) DEFAULT NULL,
  `roles` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_form_authority
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_form_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_form_category`;
CREATE TABLE `tb_cbb_form_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `category_name` varchar(100) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `domtype` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `orderindex` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_form_category
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_form_properties
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_form_properties`;
CREATE TABLE `tb_cbb_form_properties` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `form_id` int(11) DEFAULT NULL,
  `html_type` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `property_name` varchar(100) DEFAULT NULL,
  `property_name_ch` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_form_properties
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_form_property_value
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_form_property_value`;
CREATE TABLE `tb_cbb_form_property_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `bean_id` varchar(255) DEFAULT NULL,
  `bean_property_id` int(11) NOT NULL,
  `bean_value` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_form_property_value
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_jpush
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_jpush`;
CREATE TABLE `tb_cbb_jpush` (
  `push_id` int(11) NOT NULL AUTO_INCREMENT,
  `Insert_Time` datetime DEFAULT NULL,
  `Is_Delete` int(11) DEFAULT NULL,
  `Push_Content` varchar(5000) DEFAULT NULL,
  `Push_Time` datetime DEFAULT NULL,
  `Show_Content` varchar(5000) DEFAULT NULL,
  `Subject` varchar(200) NOT NULL,
  `User_Id` int(11) DEFAULT NULL,
  `User_Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`push_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_jpush
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_jpush_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_jpush_user`;
CREATE TABLE `tb_cbb_jpush_user` (
  `send_no` int(11) NOT NULL AUTO_INCREMENT,
  `is_read` int(11) DEFAULT NULL,
  `Push_Id` int(11) NOT NULL,
  `User_Id` int(11) NOT NULL,
  `User_name` varchar(255) DEFAULT NULL,
  `User_type` int(11) NOT NULL,
  PRIMARY KEY (`send_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_jpush_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_my_processed
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_my_processed`;
CREATE TABLE `tb_cbb_my_processed` (
  `my_started_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `advice` varchar(200) DEFAULT NULL,
  `approveResult` varchar(255) DEFAULT NULL,
  `category_name` varchar(50) DEFAULT NULL,
  `creater_name` varchar(50) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `instance_id` varchar(50) DEFAULT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `processer_id` int(11) DEFAULT NULL,
  `processer_name` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`my_started_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_my_processed
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_my_started
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_my_started`;
CREATE TABLE `tb_cbb_my_started` (
  `my_started_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `category_name` varchar(50) DEFAULT NULL,
  `creater_id` int(11) DEFAULT NULL,
  `creater_name` varchar(50) DEFAULT NULL,
  `creater_time` datetime DEFAULT NULL,
  `instance_id` varchar(50) DEFAULT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`my_started_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_my_started
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_my_wait_process
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_my_wait_process`;
CREATE TABLE `tb_cbb_my_wait_process` (
  `my_started_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `category_name` varchar(50) DEFAULT NULL,
  `creater_name` varchar(50) DEFAULT NULL,
  `instance_id` varchar(50) DEFAULT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `processer_id` int(11) DEFAULT NULL,
  `processer_name` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`my_started_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_my_wait_process
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_column
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_column`;
CREATE TABLE `tb_cbb_news_column` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `approve` varchar(1) DEFAULT NULL,
  `approver` varchar(20) DEFAULT NULL,
  `approverid` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT '1',
  `company_id` int(11) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `deleteid` int(11) DEFAULT NULL,
  `distribution` text,
  `distribution_name` text,
  `issuer` text,
  `issuer_name` text,
  `order_index` int(11) DEFAULT '0',
  `range_type` int(11) DEFAULT NULL,
  `title` varchar(25) DEFAULT NULL,
  `title_icon` varchar(100) DEFAULT NULL,
  `unit_type` int(11) DEFAULT '0',
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_column
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_column_range
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_column_range`;
CREATE TABLE `tb_cbb_news_column_range` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` int(11) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `read_flag` varchar(1) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `user_phone` varchar(12) DEFAULT NULL,
  `user_unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_column_range
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_column_summary
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_column_summary`;
CREATE TABLE `tb_cbb_news_column_summary` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `category_name` varchar(25) DEFAULT NULL,
  `columnid` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `issuer_count` int(11) DEFAULT NULL,
  `title` varchar(25) DEFAULT NULL,
  `title_icon` varchar(100) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_column_summary
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_issue
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_issue`;
CREATE TABLE `tb_cbb_news_issue` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `approve` varchar(1) DEFAULT NULL,
  `approver` varchar(20) DEFAULT NULL,
  `approverid` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `columnid` int(11) DEFAULT NULL,
  `comments` varchar(1000) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `content` varchar(3000) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `deleteid` int(11) DEFAULT NULL,
  `distribution` varchar(255) DEFAULT NULL,
  `distribution_name` varchar(255) DEFAULT NULL,
  `issuer` varchar(255) DEFAULT NULL,
  `issuerid` varchar(255) DEFAULT NULL,
  `materialid` int(11) DEFAULT NULL,
  `rangetype` int(11) DEFAULT NULL,
  `status_flag` varchar(1) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `title_icon` varchar(100) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_issue
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_issue_range
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_issue_range`;
CREATE TABLE `tb_cbb_news_issue_range` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `deleteid` int(11) DEFAULT NULL,
  `issues_id` int(11) NOT NULL,
  `read_flag` varchar(1) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `user_phone` varchar(12) DEFAULT NULL,
  `user_unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_issue_range
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_material
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_material`;
CREATE TABLE `tb_cbb_news_material` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(25) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `deleteid` int(11) DEFAULT NULL,
  `distribution` varchar(255) DEFAULT NULL,
  `distribution_name` varchar(255) DEFAULT NULL,
  `issue_datetime` datetime DEFAULT NULL,
  `issuer` text,
  `issuer_name` text,
  `show_flag` varchar(1) DEFAULT NULL,
  `status_flag` varchar(1) DEFAULT NULL,
  `title` varchar(65) DEFAULT NULL,
  `title_icon` varchar(100) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_material
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_news_material_icons
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_news_material_icons`;
CREATE TABLE `tb_cbb_news_material_icons` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(25) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `delete_datetime` datetime DEFAULT NULL,
  `deltetid` int(11) DEFAULT NULL,
  `distribution` varchar(255) DEFAULT NULL,
  `distribution_name` varchar(255) DEFAULT NULL,
  `iconurl` varchar(100) DEFAULT NULL,
  `issue_datetime` datetime DEFAULT NULL,
  `materialid` int(11) NOT NULL,
  `show_flag` varchar(255) DEFAULT NULL,
  `status_flag` varchar(1) DEFAULT NULL,
  `title` varchar(65) DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_news_material_icons
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_node_form_attribute
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_node_form_attribute`;
CREATE TABLE `tb_cbb_node_form_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `candidate` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `decison_into` varchar(255) DEFAULT NULL,
  `decison_out` varchar(255) DEFAULT NULL,
  `depts` varchar(255) DEFAULT NULL,
  `descri` varchar(255) DEFAULT NULL,
  `editdoc` int(11) DEFAULT NULL,
  `el` varchar(255) DEFAULT NULL,
  `is_mydep_can_accept` int(11) DEFAULT NULL,
  `node_name` varchar(255) DEFAULT NULL,
  `node_order` int(11) DEFAULT NULL,
  `node_type` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `secret_properties` varchar(255) DEFAULT NULL,
  `time_set` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `writeable_properties` varchar(255) DEFAULT NULL,
  `process_attribute_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4504AF912D02BD3` (`process_attribute_id`),
  CONSTRAINT `FK4504AF912D02BD3` FOREIGN KEY (`process_attribute_id`) REFERENCES `tb_cbb_process_attribute` (`process_attribute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_node_form_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_notify
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_notify`;
CREATE TABLE `tb_cbb_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `approve_date` datetime DEFAULT NULL,
  `attment` varchar(500) DEFAULT NULL,
  `auditer` int(11) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `columnid` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `content` text,
  `create_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `images` varchar(1000) DEFAULT NULL,
  `is_delete` int(11) NOT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  `is_top` int(11) DEFAULT NULL,
  `notify_type` int(11) DEFAULT NULL,
  `publish_group_id` int(11) DEFAULT NULL,
  `publish_user_ids` text,
  `publish_user_names` text,
  `reason` varchar(1000) DEFAULT NULL,
  `sendtype` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subject` varchar(500) DEFAULT NULL,
  `summary` varchar(60) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA8829596A27BA512` (`last_update_user_id`),
  KEY `FKA88295969B9F8D7C` (`create_user_id`),
  CONSTRAINT `FKA88295969B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKA8829596A27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_notify
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_notify_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_notify_comment`;
CREATE TABLE `tb_cbb_notify_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(1000) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `notify_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK806C34B69B9F8D7C` (`create_user_id`),
  KEY `FK806C34B655AADD7C` (`notify_id`),
  CONSTRAINT `FK806C34B655AADD7C` FOREIGN KEY (`notify_id`) REFERENCES `tb_cbb_notify` (`id`),
  CONSTRAINT `FK806C34B69B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_notify_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_notify_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_notify_view`;
CREATE TABLE `tb_cbb_notify_view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `notify_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA03B6BAE9B9F8D7C` (`create_user_id`),
  KEY `FKA03B6BAE55AADD7C` (`notify_id`),
  CONSTRAINT `FKA03B6BAE55AADD7C` FOREIGN KEY (`notify_id`) REFERENCES `tb_cbb_notify` (`id`),
  CONSTRAINT `FKA03B6BAE9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_notify_view
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_process_attribute
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_process_attribute`;
CREATE TABLE `tb_cbb_process_attribute` (
  `process_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `dept` int(11) DEFAULT NULL,
  `directions` varchar(255) DEFAULT NULL,
  `form_id` int(11) DEFAULT NULL,
  `is_attach` int(11) DEFAULT NULL,
  `process_order` int(11) DEFAULT NULL,
  `printcode` varchar(255) DEFAULT NULL,
  `printtemplatecode` varchar(255) DEFAULT NULL,
  `process_define_byxml` varchar(5000) DEFAULT NULL,
  `process_define_id` varchar(255) DEFAULT NULL,
  `process_name_num_length` int(11) DEFAULT NULL,
  `process_name` varchar(50) DEFAULT NULL,
  `process_name_beginnum` int(11) DEFAULT NULL,
  `process_name_can_update` int(11) DEFAULT NULL,
  `process_name_expr` varchar(255) DEFAULT NULL,
  `process_state` int(11) DEFAULT NULL,
  `process_define_byjson` varchar(5000) DEFAULT NULL,
  `redtemplate` int(11) DEFAULT NULL,
  `selectusermode` int(11) DEFAULT NULL,
  `taoda` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`process_attribute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_process_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_salary_bill
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_salary_bill`;
CREATE TABLE `tb_cbb_salary_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_publish` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `salary_month` int(11) DEFAULT NULL,
  `salary_name` varchar(255) DEFAULT NULL,
  `salary_time` datetime DEFAULT NULL,
  `salary_year` int(11) DEFAULT NULL,
  `send_type` int(11) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_salary_bill
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_salary_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_salary_info`;
CREATE TABLE `tb_cbb_salary_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `base1` varchar(255) DEFAULT NULL,
  `base10` varchar(255) DEFAULT NULL,
  `base2` varchar(255) DEFAULT NULL,
  `base3` varchar(255) DEFAULT NULL,
  `base4` varchar(255) DEFAULT NULL,
  `base5` varchar(255) DEFAULT NULL,
  `base6` varchar(255) DEFAULT NULL,
  `base7` varchar(255) DEFAULT NULL,
  `base8` varchar(255) DEFAULT NULL,
  `base9` varchar(255) DEFAULT NULL,
  `bill_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_publish` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `salary1` decimal(19,2) DEFAULT NULL,
  `salary10` decimal(19,2) DEFAULT NULL,
  `salary11` decimal(19,2) DEFAULT NULL,
  `salary12` decimal(19,2) DEFAULT NULL,
  `salary13` decimal(19,2) DEFAULT NULL,
  `salary14` decimal(19,2) DEFAULT NULL,
  `salary15` decimal(19,2) DEFAULT NULL,
  `salary16` decimal(19,2) DEFAULT NULL,
  `salary17` decimal(19,2) DEFAULT NULL,
  `salary18` decimal(19,2) DEFAULT NULL,
  `salary19` decimal(19,2) DEFAULT NULL,
  `salary2` decimal(19,2) DEFAULT NULL,
  `salary20` decimal(19,2) DEFAULT NULL,
  `salary3` decimal(19,2) DEFAULT NULL,
  `salary4` decimal(19,2) DEFAULT NULL,
  `salary5` decimal(19,2) DEFAULT NULL,
  `salary6` decimal(19,2) DEFAULT NULL,
  `salary7` decimal(19,2) DEFAULT NULL,
  `salary8` decimal(19,2) DEFAULT NULL,
  `salary9` decimal(19,2) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_salary_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_salary_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_salary_template`;
CREATE TABLE `tb_cbb_salary_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_default` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_salary_template
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_salary_templatecontent
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_salary_templatecontent`;
CREATE TABLE `tb_cbb_salary_templatecontent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `column_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `filed_type` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `template_filed` varchar(255) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9E9CF277373783DD` (`template_id`),
  CONSTRAINT `FK9E9CF277373783DD` FOREIGN KEY (`template_id`) REFERENCES `tb_cbb_salary_template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_salary_templatecontent
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_secret_property
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_secret_property`;
CREATE TABLE `tb_cbb_secret_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  `attribute_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_secret_property
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_secret_settings
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_secret_settings`;
CREATE TABLE `tb_cbb_secret_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `apply_user_ids` text,
  `attribute` varchar(255) DEFAULT NULL,
  `attribute_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `invisible_user_ids` text,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_secret_settings
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_workflow_var
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_workflow_var`;
CREATE TABLE `tb_cbb_workflow_var` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `breforetaskname` varchar(255) DEFAULT NULL,
  `beforeuser` varchar(255) DEFAULT NULL,
  `advice` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  `currenttaskname` varchar(255) DEFAULT NULL,
  `currentuser` varchar(255) DEFAULT NULL,
  `instanceid` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `processattributeid` int(11) DEFAULT NULL,
  `refprocess` varchar(255) DEFAULT NULL,
  `suspendtime` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_workflow_var
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog`;
CREATE TABLE `tb_cbb_worklog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `last_check_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `create_address` varchar(1000) DEFAULT NULL,
  `success_content` varchar(2000) DEFAULT NULL,
  `fail_content` varchar(2000) DEFAULT NULL,
  `tie_content` varchar(2000) DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_comment`;
CREATE TABLE `tb_cbb_worklog_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `worklog_id` int(11) DEFAULT NULL,
  `comment_user_id` int(11) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_content
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_content`;
CREATE TABLE `tb_cbb_worklog_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `worklog_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_content
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_file`;
CREATE TABLE `tb_cbb_worklog_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `worklog_id` int(11) DEFAULT NULL,
  `file_id` varchar(200) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_file
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_lastshare
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_lastshare`;
CREATE TABLE `tb_cbb_worklog_lastshare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user_id` int(11) DEFAULT NULL,
  `user_ids` varchar(1000) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_lastshare
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_praise
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_praise`;
CREATE TABLE `tb_cbb_worklog_praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `worklog_id` int(11) DEFAULT NULL,
  `praise_user_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_praise
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cbb_worklog_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_cbb_worklog_user`;
CREATE TABLE `tb_cbb_worklog_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `worklog_id` int(11) DEFAULT NULL,
  `publish_user_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cbb_worklog_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_company_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_info`;
CREATE TABLE `tb_company_info` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(150) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `bank_account` varchar(50) DEFAULT NULL,
  `company_code` varchar(50) DEFAULT NULL,
  `company_name` varchar(150) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `introduction` varchar(500) DEFAULT NULL,
  `link_man` varchar(150) DEFAULT NULL,
  `log_url` varchar(100) DEFAULT NULL,
  `short_name` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `web_site` varchar(100) DEFAULT NULL,
  `zip_code` varchar(50) DEFAULT NULL,
  `sys_name` varchar(50) DEFAULT NULL,
  `extension` varchar(500) DEFAULT NULL,
  `philosophy` varchar(4000) DEFAULT NULL,
  `company_state` int(11) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `cancel_time` datetime DEFAULT NULL,
  `expiration_time` datetime DEFAULT NULL,
  `trade_type` int(11) DEFAULT NULL,
  `logoff_time` datetime DEFAULT NULL,
  `company_phone` varchar(50) DEFAULT NULL,
  `post_code` varchar(50) DEFAULT NULL,
  `director` varchar(50) DEFAULT NULL,
  `director_phone` varchar(50) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `group_code` varchar(50) DEFAULT NULL,
  `manager_name` varchar(50) DEFAULT NULL,
  `manager_phone` varchar(50) DEFAULT NULL,
  `client_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_company_info
-- ----------------------------
INSERT INTO `tb_company_info` VALUES ('1', '北京市海淀区知春路甲48号3号楼2单元12B  ', null, null, null, '北京全亚通信技术有限公司', 'hulinxia@qytx.com.cn', null, '北京全亚通信技术有限公司（以下简称北京全亚）于2003年5月成立，注册资本1000万元人民币,是ICT系统方案集成商及软件开发服务商，同时还是中国移动新业务服务商、呼叫中心服务提供商。北京全亚总部设立在北京市海淀区知春路甲48号C座2单元12B，全资下设河南、重庆、吉林、江苏、陕西五家分公司。\r\n\r\n河南分公司坐落在郑州市农业路东33号英特大厦4楼，是一家致力于提供专业的语音通信业务、呼叫中心、电子商', '魏女士', 'LOGO/04/29/dc008284-5c75-4c6a-acb2-653fb05cebca.png', '演示数据平台', '01058732099', null, null, '全亚协同办公演示平台', null, '公司以“业务创新，品质服务”战略为指引，以科学发展观为指导，努力实现企业经营与社会责任的高度统一，致力于实现企业在经济、社会与环境方面的全面、协调、可持续发展，为相关方不断创造丰富价值，实现和谐发展。\r\n\r\n公司五大价值观——诚信、客户第一、团队、激情、学习。\r\n\r\n立业之本——创新进取、诚信专注\r\n\r\n公司理念——业务创新、品质服务\r\n\r\n服务理念——用户为本、专业服务\r\n\r\n核心价值——合作多羸、创造价值', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_data_filter_rule
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_filter_rule`;
CREATE TABLE `tb_data_filter_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `condition_jpql` varchar(540) DEFAULT NULL,
  `model_class_name` varchar(128) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operation_type` varchar(6) DEFAULT NULL,
  `relation_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_data_filter_rule
-- ----------------------------
INSERT INTO `tb_data_filter_rule` VALUES ('1', '1', 'compyId = :userOrgId', 'cn.com.qytx.oa.file.domain.FileContent', '只能添加本单位数据', 'CREATE', 'user_3');
INSERT INTO `tb_data_filter_rule` VALUES ('2', '1', 'keyWord like \'%我们%\'', 'cn.com.qytx.oa.file.domain.FileContent', '只能看到关键字包含 我们 的数据', 'READ', 'user_3');
INSERT INTO `tb_data_filter_rule` VALUES ('3', '1', 'createUserId = :userId', 'cn.com.qytx.oa.file.domain.FileContent', '只能删除自己的数据', 'DELETE', 'user_3');
INSERT INTO `tb_data_filter_rule` VALUES ('4', '1', 'subject like \'%测试数据%\'', 'cn.com.qytx.oa.file.domain.FileContent', '只能修改 测试数据', 'UPDATE', 'user_3');

-- ----------------------------
-- Table structure for tb_document_ext
-- ----------------------------
DROP TABLE IF EXISTS `tb_document_ext`;
CREATE TABLE `tb_document_ext` (
  `documentextid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attachid` int(11) DEFAULT NULL,
  `processinstance_id` varchar(100) DEFAULT NULL,
  `signdata` tinyblob,
  PRIMARY KEY (`documentextid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_document_ext
-- ----------------------------

-- ----------------------------
-- Table structure for tb_document_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_document_type`;
CREATE TABLE `tb_document_type` (
  `doctype_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attachment_id` int(11) DEFAULT NULL,
  `beginnum` int(11) DEFAULT NULL,
  `canupdate` varchar(10) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `doc_bm` varchar(2000) DEFAULT NULL,
  `doc_desc` varchar(1000) DEFAULT NULL,
  `doctemplateid` int(11) DEFAULT NULL,
  `doctype_name` varchar(100) DEFAULT NULL,
  `expr` varchar(100) DEFAULT NULL,
  `form_id` int(11) DEFAULT NULL,
  `gongwen_type` int(11) DEFAULT NULL,
  `num_length` int(11) DEFAULT NULL,
  `printcode` varchar(5000) DEFAULT NULL,
  `printtemplatecode` varchar(5000) DEFAULT NULL,
  `taoda` int(11) DEFAULT NULL,
  PRIMARY KEY (`doctype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_document_type
-- ----------------------------

-- ----------------------------
-- Table structure for tb_doc_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_doc_template`;
CREATE TABLE `tb_doc_template` (
  `doctemplate_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `categoryid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `doctemplate_name` varchar(50) DEFAULT NULL,
  `docurl` varchar(100) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `isdelete` int(11) DEFAULT NULL,
  `userids` varchar(2000) DEFAULT NULL,
  `user_names` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`doctemplate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_doc_template
-- ----------------------------

-- ----------------------------
-- Table structure for tb_gongwen_var
-- ----------------------------
DROP TABLE IF EXISTS `tb_gongwen_var`;
CREATE TABLE `tb_gongwen_var` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `creater_id` int(11) DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `forkgroup_id` int(11) DEFAULT NULL,
  `fromgroup` varchar(100) DEFAULT NULL,
  `gathersource` varchar(50) DEFAULT NULL,
  `gongwentypename` varchar(100) DEFAULT NULL,
  `huanji` varchar(50) DEFAULT NULL,
  `instanceid` varchar(50) DEFAULT NULL,
  `miji` varchar(50) DEFAULT NULL,
  `receivergroup` varchar(100) DEFAULT NULL,
  `signimg` varchar(1000) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `wenhao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_gongwen_var
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_info`;
CREATE TABLE `tb_group_info` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `Functions` varchar(500) DEFAULT NULL,
  `group_code` varchar(50) DEFAULT NULL,
  `group_name` varchar(50) DEFAULT NULL,
  `group_type` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `Memo` varchar(300) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `Path` varchar(50) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `assistant_id` int(11) DEFAULT NULL,
  `branch` varchar(50) DEFAULT NULL,
  `director_id` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `group_state` int(11) DEFAULT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  `public_level` int(11) DEFAULT NULL,
  `top_change_id` int(11) DEFAULT NULL,
  `top_director_id` int(11) DEFAULT NULL,
  `extension` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=393919 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_info
-- ----------------------------
INSERT INTO `tb_group_info` VALUES ('2', '2', '', null, '系统管理部', '1', '0', '2015-03-03 10:54:07', null, '4', '0', '2', '152222222222', '1', null, null, null, '0', '0', '0', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('32768', '1', '', null, '郑州大学', '1', '0', '2016-10-08 14:04:42', null, '1', '0', '32768', '03716778118', '4', null, null, null, '0', '0', '32768', null, '11612482', '11612415', null);
INSERT INTO `tb_group_info` VALUES ('393800', '1', '', null, '党委办公室（保密委员会办公室）', '1', '0', '2016-10-08 15:47:12', null, '2', '393811', '32768,393811,393800', '03716778100', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393801', '1', '', null, '党委组织部（党校）', '1', '0', '2016-05-04 13:49:10', null, '3', '393811', '32768,393811,393801', '03716778102', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393802', '1', '', null, '党委宣传部（新闻中心、精神文明建设办公室、校报编辑', '1', '0', '2016-05-04 13:43:41', null, '4', '393811', '32768,393811,393802', '03716778103', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393803', '1', '', null, '党委统战部', '1', '0', '2016-05-04 13:50:10', null, '5', '393811', '32768,393811,393803', '03716778103', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393804', '1', '', null, '人民武装部', '1', '0', '2016-05-04 13:43:41', null, '6', '393811', '32768,393811,393804', '03716778319', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393805', '1', '', null, '党委学生工作部', '1', '0', '2016-05-04 13:43:41', null, '7', '393811', '32768,393811,393805', '03716778111', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393806', '1', '', null, '党委研究生工作部', '1', '0', '2016-05-04 13:43:41', null, '8', '393811', '32768,393811,393806', '03716778005', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393807', '1', '', null, '纪律检查委员会（监察处）', '1', '0', '2016-05-04 11:52:44', null, '9', '393811', '32768,393811,393807', '03716778126', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393808', '1', '', null, '机关党委', '1', '0', '2016-05-04 13:50:10', null, '10', '393811', '32768,393811,393808', '03716778105', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393809', '1', '', null, '校工会（女职工委员会、人口与计划生育办公室）', '1', '0', '2016-05-04 13:50:09', null, '11', '393811', '32768,393811,393809', '03716778107', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393810', '1', '', null, '校团委', '1', '0', '2016-05-04 13:43:41', null, '12', '393811', '32768,393811,393810', '03716778315', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393811', '1', '', null, '党群机构', '1', '0', '2016-05-30 14:01:34', null, '3', '32768', '32768,393811', '03716778100', '1', null, null, null, '1', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393812', '1', '', null, '行政机构', '1', '0', '2016-05-30 14:01:34', null, '4', '32768', '32768,393812', '03716778311', '1', null, null, null, '1', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393813', '1', '', null, '校长办公室（法制办公室）', '1', '0', '2016-05-04 13:47:47', null, '1', '393812', '32768,393812,393813', '03716778311', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393814', '1', '', null, '发展规划处（信息化建设办公室）', '1', '0', '2016-05-04 13:47:47', null, '2', '393812', '32768,393812,393814', '03716778119', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393815', '1', '', null, '学术委员会办公室、学位委员会办公室', '1', '0', '2016-04-29 14:02:59', null, '3', '393812', '32768,393812,393815', '03716773953', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393816', '1', '', null, '学科与重点建设处', '1', '0', '2016-04-29 14:03:44', null, '4', '393812', '32768,393812,393816', '03716773960', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393817', '1', '', null, '人事处（教师发展中心、人才交流中心）', '1', '0', '2016-05-04 13:47:47', null, '5', '393812', '32768,393812,393817', '03716778108', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393818', '1', '', null, '高层次人才工作办公室', '1', '0', '2016-05-04 13:47:47', null, '6', '393812', '32768,393812,393818', '03716778939', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393819', '1', '', null, '教务处（教学质量管理办公室、文化素质教育办公室）', '1', '0', '2016-05-04 11:59:10', null, '7', '393812', '32768,393812,393819', '03716778109', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393820', '1', '', null, '学生处（心理健康教育中心）', '1', '0', '2016-04-29 14:37:22', null, '8', '393812', '32768,393812,393820', '03716778111', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393821', '1', '', null, '研究生院', '1', '0', '2016-04-29 14:37:46', null, '9', '393812', '32768,393812,393821', '03716778005', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393822', '1', '', null, '科学与技术研究院', '1', '0', '2016-04-29 14:38:27', null, '10', '393812', '32768,393812,393822', '03716778169', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393823', '1', '', null, '哲学社会科学研究院', '1', '0', '2016-04-29 14:39:13', null, '11', '393812', '32768,393812,393823', '03716778121', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393824', '1', '', null, '产业开发与科技合作院', '1', '0', '2016-04-29 14:39:41', null, '12', '393812', '32768,393812,393824', '03716778117', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393825', '1', '', null, '国际交流与合作处', '1', '0', '2016-04-29 14:40:06', null, '13', '393812', '32768,393812,393825', '03716778002', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393826', '1', '', null, '财务处（校园卡管理中心、会计核算中心）', '1', '0', '2016-04-29 14:40:28', null, '14', '393812', '32768,393812,393826', '03716778112', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393827', '1', '', null, '离退休职工工作处', '1', '0', '2016-05-04 13:49:10', null, '15', '393812', '32768,393812,393827', '03716778113', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393828', '1', '', null, '审计处', '1', '0', '2016-05-04 13:47:47', null, '16', '393812', '32768,393812,393828', '03716778115', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393829', '1', '', null, '后勤管理处（教职工生活区管理中心）', '1', '0', '2016-04-29 14:42:23', null, '17', '393812', '32768,393812,393829', '03716778003', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393830', '1', '', null, '基建处', '1', '0', '2016-04-29 14:42:45', null, '18', '393812', '32768,393812,393830', '03716778320', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393831', '1', '', null, '国有资产管理处', '1', '0', '2016-04-29 14:43:23', null, '19', '393812', '32768,393812,393831', '03716778156', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393832', '1', '', null, '保卫处', '1', '0', '2016-04-29 14:43:51', null, '20', '393812', '32768,393812,393832', '03716778326', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393833', '1', '', null, '对外联络办公室', '1', '0', '2016-04-29 14:45:36', null, '21', '393812', '32768,393812,393833', '03716778069', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393834', '1', '', null, '招生办公室', '1', '0', '2016-05-04 11:59:11', null, '22', '393812', '32768,393812,393834', '03716778118', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393835', '1', '', null, '大学生就业创业指导服务中心', '1', '0', '2016-04-29 14:46:16', null, '23', '393812', '32768,393812,393835', '03716778119', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393836', '1', '', null, '南校区综合管理中心', '1', '0', '2016-04-29 14:46:40', null, '24', '393812', '32768,393812,393836', '03716776200', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393837', '1', '', null, '北校区综合管理中心', '1', '0', '2016-04-29 14:47:04', null, '25', '393812', '32768,393812,393837', '03716388704', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393838', '1', '', null, '东校区综合管理中心', '1', '0', '2016-04-29 14:47:32', null, '26', '393812', '32768,393812,393838', '03716665800', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393839', '1', '', null, '郑州大学管理机构·行政机构', '1', '1', '2016-04-29 14:50:48', null, '1', '32768', '32768,393839', '03716778118', '1', null, null, null, '1', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393840', '1', '', null, '理工类院系', '1', '0', '2016-05-30 14:01:34', null, '5', '32768', '32768,393840', '03716778118', '1', null, null, null, '1', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393841', '1', '', null, '人文类院系', '1', '0', '2016-05-30 14:01:34', null, '6', '32768', '32768,393841', '03716778118', '1', null, null, null, '1', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393842', '1', '', null, '医学类院系', '1', '0', '2016-05-30 14:01:34', null, '7', '32768', '32768,393842', '03716778118', '1', null, null, null, '1', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393843', '1', '', null, '综合院系.中心', '1', '0', '2016-05-30 14:01:34', null, '8', '32768', '32768,393843', '03716778118', '1', null, null, null, '1', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393844', '1', '', null, '合作办学学院', '1', '0', '2016-05-30 14:01:34', null, '9', '32768', '32768,393844', '03716778118', '1', null, null, null, '1', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393845', '1', '数学与应用数学、信息与计算科学、统计学、金融数学', null, '数学与统计学院', '1', '0', '2016-04-29 14:56:28', null, '1', '393840', '32768,393840,393845', '03716778316', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393846', '1', '化学、应用化学', null, '化学与分子工程学院', '1', '0', '2016-04-29 14:57:25', null, '2', '393840', '32768,393840,393846', '03716778312', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393847', '1', '物理学、应用物理学 、测控技术与仪器、电子科学与技术、电子信息科学与技术', null, '物理工程学院', '1', '0', '2016-05-04 10:07:05', null, '3', '393840', '32768,393840,393847', '03716778317', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393848', '1', '电子信息工程 、通信工程、计算机科学与技术、软件工程', null, '信息工程学院', '1', '0', '2016-05-04 10:05:08', null, '4', '393840', '32768,393840,393848', '03716778310', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393849', '1', '自动化、电气工程及其自动化 、生物医学工程', null, '电气工程学院', '1', '0', '2016-05-04 10:06:49', null, '5', '393840', '32768,393840,393849', '03716778311', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393850', '1', '材料科学与工程、材料化学、包装工程、材料科学与工程（中日合办?本科）', null, '材料科学与工程学院', '1', '0', '2016-05-04 10:06:36', null, '6', '393840', '32768,393840,393850', '03716778159', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393851', '1', '工业设计、机械工程及自动化 、机械设计制造及其自动化', null, '机械工程学院', '1', '0', '2016-05-04 10:07:49', null, '7', '393840', '32768,393840,393851', '03716778123', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393852', '1', '土木工程、建筑环境与能源应用工程、交通工程、城市地下空间工程', null, '土木工程学院', '1', '0', '2016-05-04 10:08:54', null, '8', '393840', '32768,393840,393852', '03716778168', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393853', '1', '水利水电工程、环境工程、给水排水工程、地理信息系统、水文与水资源工程、道路桥梁与渡河工程', null, '水利与环境学院', '1', '0', '2016-05-04 10:10:07', null, '9', '393840', '32768,393840,393853', '03716778317', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393854', '1', '化学工程与工艺、制药工程、过程装备与控制工程 、热能与动力工程、环境科学、安全工程', null, '化工与能源学院', '1', '0', '2016-05-04 10:11:06', null, '10', '393840', '32768,393840,393854', '03716778180', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393855', '1', '建筑学、城乡规划学、风景园林学、艺术设计环境艺术设计方向', null, '建筑学院', '1', '0', '2016-05-04 10:10:59', null, '11', '393840', '32768,393840,393855', '03716778176', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393856', '1', '工业工程、工程管理、电子商务、物流管理', null, '管理工程学院', '1', '0', '2016-05-04 10:11:41', null, '12', '393840', '32768,393840,393856', '03716778158', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393857', '1', '工程力学、安全工程', null, '力学与工程科学学院', '1', '0', '2016-05-04 10:12:05', null, '13', '393840', '32768,393840,393857', '03716778311', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393858', '1', '生物技术、生物工程、生物信息', null, '生命科学学院', '1', '0', '2016-05-04 10:12:22', null, '14', '393840', '32768,393840,393858', '03716778323', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393859', '1', '', null, '郑州大学西亚斯国际学院', '1', '0', '2016-05-04 10:13:47', null, '1', '393844', '32768,393844,393859', '03716260083', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393860', '1', '', null, '郑州大学体育学院', '1', '0', '2016-05-04 10:13:52', null, '2', '393844', '32768,393844,393860', '03716363139', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393861', '1', '', null, '河南省资源与材料产业协同创新中心（河南省资源与材料', '1', '0', '2016-05-04 10:14:47', null, '1', '393843', '32768,393843,393861', '03716773975', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393862', '1', '', null, '橡塑模具国家工程研究中心', '1', '0', '2016-05-04 10:15:11', null, '2', '393843', '32768,393843,393862', '03716388760', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393863', '1', '', null, '产业技术研究院（大学科技园）', '1', '0', '2016-05-04 10:15:30', null, '3', '393843', '32768,393843,393863', '03716773961', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393864', '1', '', null, '远程教育学院（网络管理中心）', '1', '0', '2016-05-04 11:59:10', null, '4', '393843', '32768,393843,393864', '03716776314', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393865', '1', '', null, '继续教育学院', '1', '0', '2016-05-04 11:59:10', null, '5', '393843', '32768,393843,393865', '03716776322', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393866', '1', '', null, '软件与应用科技学院', '1', '0', '2016-05-04 10:18:40', null, '6', '393843', '32768,393843,393866', '03716388806', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393867', '1', '', null, '国际教育学院', '1', '0', '2016-05-04 10:19:04', null, '7', '393843', '32768,393843,393867', '03716778066', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393868', '1', '', null, '国际学院', '1', '0', '2016-05-05 15:50:30', null, '8', '393843', '32768,393843,393868', '03716776179', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393869', '1', '', null, '医学科学院', '1', '0', '2016-05-04 10:20:41', null, '1', '393842', '32768,393842,393869', '03716665800', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393870', '1', '', null, '基础医学院', '1', '0', '2016-05-04 10:22:08', null, '2', '393842', '32768,393842,393870', '03716778197', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393871', '1', '临床医学五年制、临床医学七年制、医学影像学 、麻醉学', null, '临床医学系', '1', '0', '2016-05-04 10:26:20', null, '3', '393842', '32768,393842,393871', '03716665806', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393872', '1', '医学检验', null, '医学检验系', '1', '0', '2016-05-04 10:48:52', null, '4', '393842', '32768,393842,393872', '03716665805', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393873', '1', '', null, '医学实验中心', '1', '0', '2016-05-04 10:49:17', null, '5', '393842', '32768,393842,393873', '03716665833', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393874', '1', '', null, '实验动物中心', '1', '0', '2016-05-04 10:49:37', null, '6', '393842', '32768,393842,393874', '03716665806', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393875', '1', '', null, '河南省医药科学研究院', '1', '0', '2016-05-04 10:50:35', null, '7', '393842', '32768,393842,393875', '03716696381', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393876', '1', '预防医学', null, '公共卫生学院', '1', '0', '2016-05-04 10:51:33', null, '8', '393842', '32768,393842,393876', '03716778139', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393877', '1', '', null, '护理学院', '1', '0', '2016-05-04 10:51:59', null, '9', '393842', '32768,393842,393877', '03718656500', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393878', '1', '', null, '药物研究院', '1', '0', '2016-05-04 10:52:44', null, '10', '393842', '32768,393842,393878', '03716778190', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393879', '1', '药学、药物制剂', null, '药学院', '1', '0', '2016-05-04 10:53:19', null, '11', '393842', '32768,393842,393879', '03716778190', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393880', '1', '', null, '第一临床学院', '1', '0', '2016-05-04 10:53:47', null, '12', '393842', '32768,393842,393880', '03716691303', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393881', '1', '', null, '第二临床学院', '1', '0', '2016-05-04 10:54:05', null, '13', '393842', '32768,393842,393881', '03716397469', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393882', '1', '', null, '第三临床学院', '1', '0', '2016-05-04 10:55:26', null, '14', '393842', '32768,393842,393882', '03716690306', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393883', '1', '', null, '口腔医学院', '1', '0', '2016-05-04 10:55:31', null, '15', '393842', '32768,393842,393883', '03716398147', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393884', '1', '', null, '第五临床学院', '1', '0', '2016-05-04 10:55:49', null, '16', '393842', '32768,393842,393884', '03716691678', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393885', '1', '工商管理、经济学、国际经济与贸易、金融学、统计学 、会计学', null, '商学院', '1', '0', '2016-05-04 10:57:43', null, '1', '393841', '32768,393841,393885', '03716778322', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393886', '1', '旅游管理、市场营销、音乐表演（空乘方向）、酒店管理', null, '旅游管理学院', '1', '0', '2016-05-04 10:58:34', null, '2', '393841', '32768,393841,393886', '03716778313', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393887', '1', '哲学、社会工作、行政管理、公共事业管理', null, '公共管理学院', '1', '0', '2016-05-04 10:58:59', null, '3', '393841', '32768,393841,393887', '03716778313', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393888', '1', '法学', null, '法学院', '1', '0', '2016-05-04 10:59:18', null, '4', '393841', '32768,393841,393888', '03716778310', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393889', '1', '汉语言文学、对外汉语', null, '文学院', '1', '0', '2016-05-04 11:03:19', null, '5', '393841', '32768,393841,393889', '03716778318', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393890', '1', '新闻学、广告学、广播电视新闻学播音与主持艺术方向', null, '新闻与传播学院', '1', '0', '2016-05-04 11:03:38', null, '6', '393841', '32768,393841,393890', '03716778009', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393891', '1', '英语、日语、俄语、德语', null, '外语学院', '1', '0', '2016-05-04 11:04:06', null, '7', '393841', '32768,393841,393891', '03716778122', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393892', '1', '思想政治教育学', null, '马克思主义学院', '1', '0', '2016-05-04 13:43:41', null, '8', '393841', '32768,393841,393892', '03716778133', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393893', '1', '教育学、应用心理学', null, '教育系', '1', '0', '2016-05-04 11:05:16', null, '9', '393841', '32768,393841,393893', '03716773915', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393894', '1', '考古学、历史学人文科学实验班', null, '历史学院', '1', '0', '2016-05-04 11:05:47', null, '10', '393841', '32768,393841,393894', '03716778316', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393895', '1', '信息管理与信息系统、图书馆学、档案学', null, '信息管理学院', '1', '0', '2016-05-04 11:06:04', null, '11', '393841', '32768,393841,393895', '03716778306', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393896', '1', '体育教育', null, '体育学院（校本部）', '1', '0', '2016-05-04 11:06:28', null, '12', '393841', '32768,393841,393896', '03716778315', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393897', '1', '音乐表演、音乐学', null, '音乐学院', '1', '0', '2016-05-04 11:07:33', null, '13', '393841', '32768,393841,393897', '03716776710', '1', null, null, null, '2', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393898', '1', '绘画、艺术设计、雕塑、书法', null, '美术学院', '1', '0', '2016-05-04 11:07:32', null, '14', '393841', '32768,393841,393898', '03716778096', '1', null, null, null, '2', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393899', '1', '', null, '综合办公室', '1', '0', '2016-05-05 15:51:07', null, '2', '393868', '32768,393843,393868,393899', '67763273', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393900', '1', '', null, '工会委员会', '1', '0', '2016-05-05 15:45:16', null, '3', '393868', '32768,393843,393868,393900', '67763279', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393901', '1', '', null, '院团委', '1', '0', '2016-05-05 15:45:16', null, '4', '393868', '32768,393843,393868,393901', '67763301', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393902', '1', '', null, '教工党支部', '1', '0', '2016-05-05 15:51:07', null, '5', '393868', '32768,393843,393868,393902', '67763279', '1', null, null, null, '3', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393903', '1', '', null, '12级本科生党支部', '1', '0', '2016-05-05 15:45:16', null, '6', '393868', '32768,393843,393868,393903', '67763272', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393904', '1', '', null, '13级本专科生党支部', '1', '0', '2016-05-05 15:45:16', null, '7', '393868', '32768,393843,393868,393904', '67763563', '1', null, null, null, '3', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393905', '1', '', null, '14级本科生党支部', '1', '0', '2016-05-05 15:45:16', null, '8', '393868', '32768,393843,393868,393905', '67762613', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393906', '1', '', null, '15级本科生党支部', '1', '0', '2016-05-05 15:45:16', null, '9', '393868', '32768,393843,393868,393906', '67762613', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393907', '1', '', null, '学生工作部', '1', '0', '2016-05-05 15:45:16', null, '10', '393868', '32768,393843,393868,393907', '67762612', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393908', '1', '', null, '教学办公室', '1', '0', '2016-05-05 15:45:16', null, '11', '393868', '32768,393843,393868,393908', '67763270', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393909', '1', '', null, '考务办公室', '1', '0', '2016-05-05 15:45:16', null, '12', '393868', '32768,393843,393868,393909', '67767233', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393910', '1', '', null, '招生咨询办公室', '1', '0', '2016-05-05 15:45:16', null, '13', '393868', '32768,393843,393868,393910', '67766117', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393911', '1', '', null, '就业指导办公室', '1', '0', '2016-05-05 15:45:16', null, '14', '393868', '32768,393843,393868,393911', '67763563', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393912', '1', '', null, '国际合作部', '1', '0', '2016-05-05 15:45:16', null, '15', '393868', '32768,393843,393868,393912', '67767772', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393913', '1', '', null, '教学保障部', '1', '0', '2016-05-05 15:45:16', null, '16', '393868', '32768,393843,393868,393913', '67761853', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393914', '1', '', null, '科研与学科建设办公室', '1', '0', '2016-05-05 15:45:16', null, '17', '393868', '32768,393843,393868,393914', '67762613', '1', null, null, null, '3', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393915', '1', '', null, '出国留学培训中心办公室', '1', '0', '2016-05-05 15:45:16', null, '18', '393868', '32768,393843,393868,393915', '67763121', '1', null, null, null, '3', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393916', '1', '', null, '出国留学培训中心培训部', '1', '0', '2016-05-05 15:45:16', null, '19', '393868', '32768,393843,393868,393916', '67763121', '1', null, null, null, '3', '0', '32768', null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393917', '1', '', null, '院长办公室', '1', '0', '2016-05-05 15:48:49', null, '1', '393868', '32768,393843,393868,393917', '67767732', '1', null, null, null, '3', '0', null, null, null, null, null);
INSERT INTO `tb_group_info` VALUES ('393918', '1', '', null, '院党委办公室', '1', '0', '2016-05-05 15:46:29', null, '0', '393868', '32768,393843,393868,393918', '67767730', '1', null, null, null, '3', '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_group_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_user`;
CREATE TABLE `tb_group_user` (
  `Vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`Vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_history_doc
-- ----------------------------
DROP TABLE IF EXISTS `tb_history_doc`;
CREATE TABLE `tb_history_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `history_doc_path` varchar(100) DEFAULT NULL,
  `instance_id` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_history_doc
-- ----------------------------

-- ----------------------------
-- Table structure for tb_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge`;
CREATE TABLE `tb_knowledge` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attachment_ids` varchar(255) DEFAULT NULL,
  `audit_sign` int(11) DEFAULT NULL,
  `audit_date` datetime DEFAULT NULL,
  `column_id` int(11) DEFAULT NULL,
  `contentInfo` varchar(255) DEFAULT NULL,
  `contentVoice` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `has_record_audio` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  `is_private` int(11) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `vox_path` varchar(255) DEFAULT NULL,
  `audit_user_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `knowledge_Type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  KEY `FKF71FDFCD9B9F8D7C` (`create_user_id`),
  KEY `FKF71FDFCDD6CA395B` (`audit_user_id`),
  KEY `FKF71FDFCDDE52C147` (`knowledge_Type_id`),
  CONSTRAINT `FKF71FDFCD9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKF71FDFCDD6CA395B` FOREIGN KEY (`audit_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKF71FDFCDDE52C147` FOREIGN KEY (`knowledge_Type_id`) REFERENCES `tb_knowledge_type` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for tb_knowledge_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_collect`;
CREATE TABLE `tb_knowledge_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `knowledge_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK84C4CAB85A053D45` (`seat_id`),
  KEY `FK84C4CAB8D7BFE3EA` (`knowledge_id`),
  CONSTRAINT `FK84C4CAB85A053D45` FOREIGN KEY (`seat_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK84C4CAB8D7BFE3EA` FOREIGN KEY (`knowledge_id`) REFERENCES `tb_knowledge` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_knowledge_collect
-- ----------------------------

-- ----------------------------
-- Table structure for tb_knowledge_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_type`;
CREATE TABLE `tb_knowledge_type` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `column_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  `is_private` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `orderIndex` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  KEY `FK9036406C9B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK9036406C9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_knowledge_type
-- ----------------------------

-- ----------------------------
-- Table structure for tb_module_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_module_info`;
CREATE TABLE `tb_module_info` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(50) DEFAULT NULL,
  `memo` varchar(500) DEFAULT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `module_level` int(11) DEFAULT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `module_type` int(11) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sys_name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `module_flag` varchar(255) DEFAULT NULL,
  `module_state` int(11) DEFAULT NULL,
  `module_class` varchar(50) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL,
  `default_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_module_info
-- ----------------------------
INSERT INTO `tb_module_info` VALUES ('1', '/images/module_icons/systemManager.png', null, '1', '1', '系统管理', '1', '8', '0', null, '', '0', null, null, 'xtgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('4', '/images/module_icons/inner_address_manager.png', null, '2', '2', '单位通讯录管理', '1', '1', '1', null, '', '0', null, null, 'dwtxlgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('5', '/images/module_icons/ggtxlgl.png', null, '3', '2', '外部通讯录管理', '1', '2', '1', null, '/logined/publicaddress/list_setupgroup.jsp', '0', null, null, 'wbtxlgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('6', '/images/module_icons/dlyhgl.png', null, '4', '2', '登录用户管理', '1', '3', '1', null, '/logined/user/loginUserList.jsp', '0', null, null, 'dlyh', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('7', '/images/module_icons/role.png', null, '5', '2', '角色权限管理', '1', '4', '1', null, '/logined/authority/roleList.jsp', '0', null, null, 'jsqx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('8', '/images/module_icons/systemSet.png', null, '6', '1', '基础设置', '1', '9', '0', null, '', '0', null, null, 'xtsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('9', '/images/module_icons/address.png', null, 'txl', '1', '通讯录', '1', '7', '0', null, '', '0', null, null, 'txl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('10', '/images/module_icons/ownSet.png', null, '8', '1', '个人设置', '1', '11', '0', null, '', '0', null, null, 'grsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('11', '/images/module_icons/myRecord.png', null, '9', '2', '个人信息', '1', '1', '10', null, '', '0', null, null, 'grxx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('12', '/images/module_icons/changePwd.png', null, '10', '2', '密码修改', '1', '2', '10', null, '/sysset/toPwdSet.action', '0', null, null, 'mmxg', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('13', '/images/module_icons/inner_address.png', null, '11', '2', '单位通讯录', '1', '1', '9', null, '/logined/user/user.jsp?type=view', '0', null, null, 'dwtxl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('14', '/images/module_icons/public_outer_address.png', null, '12', '2', '外部通讯录', '1', '2', '9', null, '/logined/publicaddress/list_address.jsp', '0', null, null, 'wbtxl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('15', '/images/module_icons/dwxx_provided.png', null, '13', '3', '单位信息设置', '1', '0', '4', null, '/company/view.action', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('16', '/images/module_icons/groupManager.png', null, '14', '3', '组织机构管理', '1', '1', '4', null, '/logined/group/group.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('17', '/images/module_icons/userManager.png', null, '15', '3', '人员管理', '1', '2', '4', null, '/logined/user/user.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('18', '/images/module_icons/groupManager.png', null, '16', '4', '部门管理', '2', '1', '16', null, '/logined/group/group.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('19', '', null, '17', '4', '部门列表', '2', '2', '16', null, 'group/findGradeGroupTree.action', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('20', '/images/module_icons/info_type.png', null, '18', '2', '通用设置', '1', '1', '8', null, '/sysset/toCommonSet.action', '1', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('21', '/images/module_icons/info_type.png', null, '19', '2', '数据字典设置', '1', '1', '8', null, '/logined/dict/dict.jsp?sysTag=-1', '0', null, null, 'sjzd', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('22', '/images/module_icons/task_manage.png', null, '20', '1', '任务管理', '1', '14', '0', null, '', '0', null, '0', 'rwgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('23', '/images/module_icons/wzjd.png', null, '21', '2', '我转交的', '1', '3', '22', null, '/logined/task/handedList.jsp', '0', null, '0', 'wzjd', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('24', '/images/module_icons/wfqd.png', null, '22', '2', '我发起的', '1', '1', '22', null, '/logined/task/addTaskList.jsp', '0', null, '0', 'wfqd', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('25', '/images/module_icons/wcbd.png', null, '23', '2', '我承办的', '1', '2', '22', null, '/logined/task/unfinishedList.jsp', '0', null, '0', 'wcbd', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('26', '', null, '24', '4', '未办结', '2', '1', '25', null, '/logined/task/unfinishedList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('27', '', null, '25', '4', '已办结', '2', '2', '25', null, '/logined/task/finishedList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('28', '/images/menu/mytable.png', null, '26', '1', '个人事务', '1', '3', '0', null, '', '0', null, '0', 'grsw', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('29', '/images/module_icons/myCalendar.png', null, 'rcgl', '2', '日程管理', '1', '3', '28', null, '/logined/programme/myProgramme.jsp', '0', null, '1', 'rcgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('30', '/images/menu/knowledge.png', null, 'zsgl', '1', '知识管理', '1', '5', '0', null, '', '0', null, '0', 'zsgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('31', '/images/module_icons/fileSearch.png', null, '29', '2', '资料查阅', '1', '1', '30', null, '/logined/file/viewFileContent.jsp?fileSort=31&type=1', '0', null, '0', 'zlcy', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('32', '/images/module_icons/fileManager.png', null, '30', '3', '资料管理', '1', '2', '30', null, '/logined/file/fileContent.jsp?fileSort=31&type=1', '0', null, '0', 'zlgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('33', '/images/menu/erp.png', null, '31', '1', '行政办公', '1', '4', '0', null, '', '0', null, '0', 'xzbg', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('34', '/images/module_icons/readnotify.png', null, '32', '2', '公告查看', '1', '1', '33', null, '/logined/notify/viewList.jsp?id=35', '0', null, '0', 'ggck', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('35', '/images/module_icons/gggl.png', null, 'gggl', '2', '公告发布', '1', '2', '33', null, '/logined/notify/list.jsp?id=35', '0', null, '0', 'gggl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('36', '/images/module_icons/notAuditing.png', null, '36', '2', '公告审批', '1', '3', '33', null, '/logined/notify/viewApproveNoList.jsp?id=35', '0', null, '0', 'ggsp', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('37', '/images/module_icons/notify_manager.png', null, '37', '2', '公告通知设置', '1', '4', '8', null, '/logined/notify/notifySet.jsp?id=35', '0', null, '0', 'ggtzsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('38', '/images/module_icons/inner_email.png', null, 'nbyj', '2', '内部邮件', '1', '4', '28', null, '/logined/email/emailMainPage.action', '0', null, '0', 'nbyj', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('39', '', null, 'xwzx', '2', '新闻中心', '1', '5', '33', null, '', '1', null, '0', 'xwzx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('40', '/images/module_icons/xwzxsz.png', null, '40', '2', '新闻中心设置', '1', '1', '8', null, '/logined/news/manage/list.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('41', '/images/module_icons/fbxw.png', null, '41', '3', '发布新闻', '1', '2', '39', null, '/logined/news/user/columnList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('42', '/images/module_icons/yflb.png', null, '42', '3', '已发列表', '1', '3', '39', null, '/logined/news/user/issueList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('43', '/images/module_icons/fbcg.png', null, '43', '3', '发布草稿', '1', '4', '39', null, '/logined/news/user/issueDraft.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('44', '/images/module_icons/qksc.png', null, '44', '3', '期刊素材', '1', '5', '39', null, '/logined/news/user/materialList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('45', null, null, '45', '2', '通讯助理公告管理', '1', '6', '33', null, '', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('46', '', null, '46', '3', '栏目管理', '1', '1', '45', null, '/logined/notice/manage/list.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('47', '', null, '47', '3', '发布公告', '1', '2', '45', null, '/logined/notice/user/columnList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('48', '', null, '48', '3', '公告列表', '1', '3', '45', null, '/logined/notice/user/issueList.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('49', '', null, '49', '3', '公告草稿', '1', '4', '45', null, '/logined/notice/user/issueDraft.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('50', '/images/module_icons/daily.png', null, 'gzrz', '2', '工作日志', '1', '1', '28', null, '', '0', null, '1', 'gzrz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('51', '/images/module_icons/xzrz.png', null, '51', '3', '新增日志', '1', '1', '50', null, '/daily/toDailyMain.action', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('52', '/images/module_icons/rzcx.png', null, '52', '3', '日志查询', '1', '2', '50', null, '/daily/toSearch.action?type=1', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('53', '/images/module_icons/logSumup.png', null, '53', '2', '系统日志管理', '1', '5', '1', null, '/logined/log/logSumup.jsp', '0', null, '0', 'xtrz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('54', '', null, '54', '3', '日志概况', '2', '1', '53', null, '/logined/log/logSumup.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('55', '', null, '55', '3', '日志查询', '2', '2', '53', null, '/logined/log/search.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('56', '/images/module_icons/rzcx.png', null, '56', '2', '日志管理', '1', '5', '33', null, '/logined/dailySearch/index.jsp', '0', null, '0', 'rzgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('57', '', null, '57', '4', '成员日志', '2', '1', '56', null, '/logined/dailySearch/index.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('58', '', null, '58', '4', '日志查询', '2', '2', '56', null, '/daily/toSearch.action?type=3', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('59', '/images/module_icons/gwglsz.png', null, '59', '2', '公文管理设置', '1', '2', '8', null, '', '0', null, null, 'gwglsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('60', '/images/module_icons/gwlxsz.png', null, '60', '3', '公文类型设置', '1', '1', '59', null, '/documentType/docType_docTypeList.action', '0', null, null, '', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('61', '/images/module_icons/thmb.png', null, '61', '3', '套红模板', '1', '2', '59', null, '/logined/docTemplate/docTemplateList.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('62', '/images/module_icons/yzgl.png', null, '62', '3', '印章管理', '1', '3', '59', null, '/logined/websign/ekeySignUtil.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('63', '/images/module_icons/bdqxsz.png', null, '63', '3', '表单权限设置', '1', '4', '59', null, '/logined/formAuthority/formList.jsp?firstPage=true', '1', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('64', '', null, '64', '4', '模板类型管理', '2', '2', '61', null, '/logined/docTemplate/docTemplateLCateryist.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('65', '', null, '65', '4', '模板管理', '2', '1', '61', null, '/logined/docTemplate/docTemplateList.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('66', '/images/module_icons/systemSet.png', '', '66', '2', '工作流设置', '1', '1', '8', '', '', '0', '', '0', 'gzlsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('67', '/images/module_icons/sjbd.png', '', '67', '3', '设计表单', '1', '1', '66', '', '/logined/customForm/addForm.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('68', '/images/module_icons/sjlc.png', '', '68', '3', '设计流程', '1', '2', '66', '', '/workflow/manager.action', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('69', '/images/module_icons/flsz.png', '', '69', '3', '分类设置', '1', '3', '66', '', '/logined/customForm/formCategoryList.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('70', '/images/module_icons/flsz.png', '', '70', '4', '新增表单', '2', '1', '67', '', '/logined/customForm/addForm.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('71', '/images/module_icons/flsz.png', '', '71', '4', '查询表单', '2', '2', '67', '', '/workflowForm/getFormCategorys.action', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('72', '/images/module_icons/flsz.png', '', '72', '4', '表单分类', '2', '1', '69', '', '/logined/customForm/formCategoryList.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('73', '/images/module_icons/flsz.png', '', '73', '4', '流程分类', '2', '2', '69', '', '/logined/customForm/processCategoryList.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('74', '/images/module_icons/gwgl.png', null, 'gwgl', '1', '公文管理', '1', '1', '0', null, '', '0', null, null, 'gwgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('75', '/images/module_icons/swgl.png', null, '75', '2', '收文管理', '1', '1', '74', null, '', '0', null, null, 'swgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('76', '/images/module_icons/fwgl.png', null, '76', '2', '发文管理', '1', '2', '74', null, '', '0', null, null, 'fwgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('77', '/images/module_icons/gdgl.png', null, '77', '2', '归档管理', '1', '3', '74', null, '', '0', null, null, 'gwgd', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('78', '/images/module_icons/swdj.png', null, '78', '3', '新建收文', '1', '1', '75', null, '/dom/gather!toAdd.action', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('79', '/images/module_icons/swjl.png', null, '79', '3', '收文登记', '1', '2', '75', null, '/dom/gather!gatherList.action?menu=1', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('80', '/images/module_icons/ldpy.png', null, '80', '3', '领导批阅', '1', '3', '75', null, '/dom/gather!gatherList.action?menu=2', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('81', '/images/module_icons/swff.png', null, '81', '3', '收文分发', '1', '4', '75', null, '/dom/gather!gatherList.action?menu=3', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('82', '/images/module_icons/swyd.png', null, 'swyd', '3', '收文阅读', '1', '5', '75', null, '/dom/gather!gatherList.action?menu=4', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('83', '/images/module_icons/fwng.png', null, '83', '3', '新建发文', '1', '1', '76', null, '/dom/dispatch!toAdd.action', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('84', '/images/module_icons/ngjl.png', null, '84', '3', '发文拟稿', '1', '2', '76', null, '/dom/dispatch!dispatchList.action?menu=5', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('85', '/images/module_icons/ffhg.png', null, '85', '3', '发文核稿', '1', '3', '76', null, '/dom/dispatch!dispatchList.action?menu=6', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('86', '/images/module_icons/thgz.png', null, '86', '3', '套红盖章', '1', '4', '76', null, '/dom/dispatch!dispatchList.action?menu=7', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('87', '/images/module_icons/fwff.png', null, '87', '3', '发文分发', '1', '5', '76', null, '/dom/dispatch!dispatchList.action?menu=8', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('88', '/images/module_icons/gwgd.png', null, '88', '3', '公文归档', '1', '1', '77', null, '/dom/gather!gatherList.action?menu=9', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('89', '/images/module_icons/gdcx.png', null, '89', '3', '归档查询', '1', '2', '77', null, 'dom/public!zipSearch.action', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('90', '', null, '90', '3', '归档统计', '1', '3', '77', null, '/dom/public!zipReport.action', '1', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('91', '/images/module_icons/gzl.png', '', 'wdgz', '1', '审批流转', '1', '3', '0', '', '', '0', '', '0', 'gzsq', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('92', '/images/module_icons/xzsq.png', '', '92', '2', '新增申请', '1', '1', '91', '', '/jbpmflow/listSearch!often.action', '0', '', '0', 'xzsq', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('93', '/images/module_icons/wdsq.png', '', '93', '2', '申请列表', '1', '2', '91', '', '/logined/jbpmApp/myjob/start.jsp', '0', '', '0', 'wdsq', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('94', '/images/module_icons/wdsp.png', '', '94', '2', '工作审批', '1', '3', '0', '', null, '0', '', '0', 'gzsp', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('95', '', '', '95', '4', '待审批', '2', '1', '165', '', '/logined/jbpmApp/myjob/management.jsp', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('96', '', null, '96', '4', '用户列表', '2', '1', '5', null, '/logined/publicaddress/list_address.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('97', '', null, '97', '4', '用户查询', '2', '2', '5', null, 'logined/address/list_address.jsp?publicSign=1', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('98', '', null, '98', '4', '待登记', '2', '1', '79', null, '/dom/gather!gatherList.action?menu=1', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('99', '', null, '99', '4', '已登记', '2', '2', '79', null, '/dom/gather!gatherList.action?menu=1&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('100', '', null, '100', '4', '待批阅', '2', '1', '80', null, '/dom/gather!gatherList.action?menu=2', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('101', '', null, '101', '4', '已批阅', '2', '2', '80', null, '/dom/gather!gatherList.action?menu=2&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('102', '', null, '102', '4', '待分发', '2', '1', '81', null, '/dom/gather!gatherList.action?menu=3', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('103', '', null, '103', '4', '已分发', '2', '2', '81', null, '/dom/gather!gatherList.action?menu=3&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('104', '', null, '104', '4', '待阅读', '2', '1', '82', null, '/dom/gather!gatherList.action?menu=4', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('105', '', null, '105', '4', '已阅读', '2', '2', '82', null, '/dom/gather!gatherList.action?menu=4&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('106', '', null, '106', '4', '未提交', '2', '1', '84', null, '/dom/dispatch!dispatchList.action?menu=5', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('107', '', null, '107', '4', '已提交', '2', '2', '84', null, '/dom/dispatch!dispatchList.action?menu=5&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('108', '', null, '108', '4', '待核稿', '2', '1', '85', null, '/dom/dispatch!dispatchList.action?menu=6', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('109', '', null, '109', '4', '已核稿', '2', '2', '85', null, '/dom/dispatch!dispatchList.action?menu=6&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('110', '', null, '110', '4', '待盖章', '2', '1', '86', null, '/dom/dispatch!dispatchList.action?menu=7', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('111', '', null, '111', '4', '已盖章', '2', '2', '86', null, '/dom/dispatch!dispatchList.action?menu=7&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('112', '', null, '112', '4', '待分发', '2', '1', '87', null, '/dom/dispatch!dispatchList.action?menu=8', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('113', '', null, '113', '4', '已分发', '2', '2', '87', null, '/dom/dispatch!dispatchList.action?menu=8&searchType=completed', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('114', '', null, '114', '4', '收文归档', '2', '1', '88', null, '/dom/gather!gatherList.action?menu=9', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('115', '', null, '115', '4', '发文归档', '2', '2', '88', null, '/dom/dispatch!dispatchList.action?menu=10', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('116', '/images/module_icons/affairs_manager.png', null, '116', '2', '事务提醒设置', '1', '3', '8', null, '/logined/affairs/affairs_manage.jsp', '0', null, null, 'swtxsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('117', '', null, '119', '3', '信息保密设置', '1', '3', '4', null, '/logined/user/secret_user.jsp', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('118', '', null, '120', '3', '群组管理', '1', '4', '4', null, '/logined/group_ext/group.jsp', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('119', '/images/module_icons/groupManager.png', null, '121', '3', '群组管理', '1', '5', '4', null, '/logined/group_ext/user.jsp', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('120', '/images/module_icons/wdkq.png', null, '123', '2', '我的考勤', '1', '1', '28', null, null, '0', null, null, 'wdkq', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('121', '/images/module_icons/wdkq.png', null, '126', '3', '考勤登记', '1', '0', '120', null, '/logined/attendance/attendanceRegister.jsp', '0', null, null, 'kqdj', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('122', '/images/module_icons/kqtj.png', null, '127', '3', '考勤记录', '1', '1', '120', null, '/logined/attendance/attendanceRecord.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('123', '/images/module_icons/kqsz.png', null, '128', '3', '考勤设置', '1', '0', '131', null, '/attendance/getIPList.action', '0', null, null, 'kqsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('124', '/images/module_icons/kqtj.png', null, '129', '3', '考勤统计', '1', '0', '131', null, '/logined/attendance/attendanceTj.jsp', '0', null, null, 'kqtj', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('125', '/images/menu/questionniare.png', null, 'dcwj', '1', '调查问卷', '1', '12', '0', null, null, '0', null, '0', 'tpdc', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('126', '/images/module_icons/wjlb.png', null, '131', '2', '问卷列表', '1', '0', '28', null, '/logined/question/issueList.jsp', '0', null, '0', 'wjlb', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('127', '/images/module_icons/wjgl.png', null, '132', '2', '调查问卷管理', '1', '1', '125', null, '/logined/question/questionnaire.jsp', '0', null, '0', 'wjgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('128', '/images/module_icons/ltgl.png', null, '133', '2', '论坛管理', '1', '6', '28', null, 'bbs', '1', null, '0', 'ltgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('129', '/images/module_icons/message.png', null, '134', '2', '即时消息', '1', '7', '28', null, '/logined/message/send_message.jsp', '0', null, '1', 'jsxx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('130', '/images/module_icons/affairs.png', null, '135', '2', '事务提醒', '1', '5', '28', null, '/logined/affairs/list_new_affairs.jsp', '0', null, '1', 'swtx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('131', '/images/menu/hr.png', null, '136', '1', '人力资源', '1', '6', '0', null, '', '0', null, '0', 'rlzy', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('132', '/images/module_icons/dack.png', null, '137', '2', '档案查看', '1', '1', '131', null, '/logined/record/main.action', '0', null, '0', 'dack', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('133', '/images/module_icons/dagl.png', null, '138', '2', '档案管理', '1', '2', '131', null, '/logined/record/list_manager.jsp', '0', null, '0', 'dagl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('134', '/images/module_icons/dacx.png', null, '139', '2', '档案查询', '1', '3', '131', null, '/logined/record/searchPage.action', '0', null, '0', 'dacx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('135', null, null, '140', '4', '已审批', '2', '2', '165', null, '/logined/jbpmApp/myjob/completed.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('136', null, null, '141', '4', '未阅', '2', '1', '130', null, '/logined/affairs/list_new_affairs.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('137', null, null, '142', '4', '已阅', '2', '2', '130', null, '/logined/affairs/list_receive_affairs.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('138', '/images/module_icons/bbgl.png', null, '143', '1', '报表管理', '1', '15', '0', null, null, '1', null, null, 'bbgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('139', '/images/module_icons/bbtb.png', null, '144', '2', '报表填报', '1', '0', '138', null, '/reportInfo/showReport.action?reportType=1', '1', null, null, 'bbtb', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('140', '/images/module_icons/bbsp.png', null, '145', '2', '报表审批', '1', '1', '138', null, '/reportInfo/showReport.action?reportType=2', '1', null, null, 'bbsp', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('141', '/images/module_icons/bbcy.png', null, '146', '2', '报表查阅', '1', '2', '138', null, '/reportInfo/showReport.action?reportType=3', '1', null, null, 'bbcy', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('142', '/images/module_icons/bbqxsz.png', null, '147', '2', '报表权限设置', '1', '3', '138', null, '/logined/report/list_manager_report.jsp', '1', null, null, 'bbqx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('143', '/images/module_icons/bbjc.png', null, '148', '2', '报表基础数据维护', '1', '4', '138', null, '/logined/report/projectBDList.jsp', '1', null, null, 'bbjc', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('144', null, null, '149', '4', '发起消息', '2', '1', '129', null, '/logined/message/send_message.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('145', null, null, '150', '4', '消息记录', '2', '2', '129', null, '/logined/message/message_main.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('146', null, null, '151', '4', '消息查询', '2', '3', '129', null, '/logined/message/list_all_message.jsp', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('147', '/images/module_icons/yjfk.png', null, '152', '2', '意见反馈列表', '1', '2', '125', null, 'question/commentIndex.action', '1', null, '0', 'yjfk', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('148', null, null, '153', '1', '抽奖管理', '1', '13', '0', null, null, '1', null, null, 'cjgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('149', '/images/module_icons/cjlb.png', null, '154', '2', '抽奖列表', '1', '1', '148', null, '/logined/lottery/lotteryList.jsp', '1', null, null, 'cjlb', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('150', '/images/menu/news_manage.png', '', '155', '4', '待审核', '2', '1', '36', '', '/logined/notify/viewApproveNoList.jsp?id=35', '0', '', '0', 'xwzx', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('151', '/images/module_icons/readnotify.png', '', '156', '4', '已审核', '2', '2', '36', '', '/logined/notify/viewApproveList.jsp?id=35', '0', '', '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('152', '/images/module_icons/notifyManager.png', null, 'zsgl', '1', '知识管理2', '1', '6', '0', null, null, '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('153', '/images/module_icons/notAuditing.png', null, '158', '2', '资料查阅2', '1', '1', '152', null, '/logined/file/viewFileContent.jsp?fileSort=157', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('154', '/images/module_icons/notify_manager.png', null, '159', '3', '资料管理2', '1', '2', '152', null, '/logined/file/fileContent.jsp?fileSort=157', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('155', '', null, '160', '2', '公告通知', '1', '1', '33', null, '', '0', null, '0', 'ggck', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('156', null, null, '161', '4', '我的日志', '2', '1', '52', null, '/daily/toSearch.action?type=1', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('157', null, null, '162', '4', '共享日志', '2', '2', '52', null, '/daily/toSearch.action?type=2', '0', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('158', '/images/module_icons/bmzl.png', null, 'gggl', '2', '部门专栏', '1', '16', '33', null, '', '1', null, null, 'bmzl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('159', '/images/module_icons/gggl.png', null, '164', '3', '公告管理', '1', '2', '158', null, '/logined/notify/list.jsp?id=163', '1', null, null, 'gggl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('160', '/images/module_icons/share.png', null, '165', '3', '公告审批', '1', '3', '158', null, '/logined/notify/viewApproveNoList.jsp?id=163', '1', null, null, 'ggsp', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('161', '/images/module_icons/notify_manager.png', null, '166', '3', '公告通知设置', '1', '4', '158', null, '/logined/notify/notifySet.jsp?id=163', '1', null, null, 'ggtzsz', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('162', null, null, '167', '4', '待审核', '2', '1', '160', null, '/logined/notify/viewApproveNoList.jsp?id=163', '1', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('163', null, null, '168', '4', '已审核', '2', '2', '160', null, '/logined/notify/viewApproveList.jsp?id=163', '1', null, null, null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('164', '/images/module_icons/readnotify.png', null, '169', '2', '公告查看', '1', '1', '158', null, '/logined/column/viewList.jsp?id=163', '1', null, null, 'ggck', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('165', '/images/module_icons/wdsp.png', null, '170', '2', '工作审批', '1', '4', '91', null, '/logined/jbpmApp/myjob/management.jsp', '0', null, null, 'wdsp', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('166', '/images/module_icons/ggtxlgl.png', null, '171', '2', '手机模块管理', '1', '7', '1', null, '/logined/module/index.jsp', '0', null, null, 'sjmkgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('167', '/images/module_icons/wdsq.png', '', '172', '2', '归档列表', '1', '5', '91', '', 'logined/jbpmApp/myjob/end.jsp', '0', '', '0', '', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('168', '', null, '173', '2', '管理工作流', '1', '8', '1', null, 'logined/flowManage/management.jsp', '1', null, '0', 'dwtxlgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('169', '', null, '174', '2', '开讲设置', '1', '9', '8', null, '/speak/speaker.action', '1', null, '0', 'dwtxlgl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('170', '/images/module_icons/flsz.png', null, '175', '3', '模块设置', '1', '4', '66', null, '/workflow/manager.action?type=4', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('171', null, null, '176', '2', '车辆管理', '1', '7', '33', null, null, '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('172', null, null, '177', '3', '新增申请', '1', '1', '171', null, '/workflowModule/detailSearch!toStartProcess.action?processId=183', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('173', null, null, '178', '3', '申请列表', '1', '2', '171', null, '/logined/workflowModule/start.jsp?processId=183', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('174', null, null, '174', '1', '语音通知', '1', '17', '0', null, '/logined/yytz/tzt.jsp', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('175', '/images/module_icons/flsz.png', null, '175', '2', '语音通知', '1', '1', '174', null, '/logined/tztIndex/tztIndex.jsp?infoType=notice', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('176', null, null, '758', '2', '短信通', '1', '2', '174', null, '/logined/tztIndex/tztIndex.jsp?infoType=sms', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('177', null, null, 'gggl', '1', '信息资讯', '1', '20', '0', null, null, '1', null, '0', 'gggl', '1', null, null);
INSERT INTO `tb_module_info` VALUES ('178', null, null, '760', '2', '资讯管理', '1', '1', '177', null, '/logined/notify/list.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('179', null, null, '761', '2', '资讯审批', '1', '2', '177', null, '/logined/notify/viewApproveNoList.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('180', null, null, '762', '2', '资讯设置', '1', '3', '177', null, '/logined/notify/notifySet.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('181', null, null, '763', '2', '资讯查看', '1', '4', '177', null, '/logined/column/viewList.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('182', null, null, '764', '4', '待审核', '2', '1', '179', null, '/logined/notify/viewApproveNoList.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('183', null, null, '765', '4', '已审核', '2', '2', '179', null, '/logined/notify/viewApproveList.jsp?id=759', '1', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('184', null, null, '766', '1', '在线课堂', '1', '21', '0', null, '', '0', null, '0', null, '1', null, null);
INSERT INTO `tb_module_info` VALUES ('185', null, null, '767', '2', '在线课堂管理', '1', '1', '184', null, '/logined/onlineExam/course/courseList.jsp?columnId=167', '0', null, '0', null, '1', null, null);

-- ----------------------------
-- Table structure for tb_module_info_mobile
-- ----------------------------
DROP TABLE IF EXISTS `tb_module_info_mobile`;
CREATE TABLE `tb_module_info_mobile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `code` varchar(150) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `order_list` varchar(50) DEFAULT NULL,
  `statue` int(11) DEFAULT NULL,
  `url` varchar(250) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54B081711248E351` (`parent_id`),
  CONSTRAINT `FK54B081711248E351` FOREIGN KEY (`parent_id`) REFERENCES `tb_module_info_mobile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_module_info_mobile
-- ----------------------------
INSERT INTO `tb_module_info_mobile` VALUES ('17', '1', 'question', 'module/09/16/46dcea8c-dbf4-4dbc-bf37-e37996ef4952.jpg', '调查问卷', '09', '1', 'wapQuestion/selectList.action', null);
INSERT INTO `tb_module_info_mobile` VALUES ('28', '1', 'calendar', null, '日程', '15', '1', null, null);
INSERT INTO `tb_module_info_mobile` VALUES ('29', '1', 'dialymanager', null, '日志管理', '10', '1', null, null);
INSERT INTO `tb_module_info_mobile` VALUES ('31', '1', 'repository', null, '知识库', '18', '1', null, null);
INSERT INTO `tb_module_info_mobile` VALUES ('32', '1', 'punchCard', '', '打卡', '19', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('35', '1', 'notifynotice', '', '通知公告', '3', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('68', null, '1234', 'module/08/13/2a6a2a37-a5a2-4420-95a7-302c39fa2752.jpg', '第一个菜单名称', '2', '2', 'http://10.200.17.193', null);
INSERT INTO `tb_module_info_mobile` VALUES ('165', '1', 'approve', '', '审批', '34', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('166', '1', 'didi', '', '嘀一下', '35', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('167', '1', 'onlineExam', '', '在线课堂', '9', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('168', '1', 'workManager', '', '任务管理', '12', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('169', '1', 'proclamationRead', '', '公文阅读', '14', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('170', '1', 'voiceNotice', '', '语音通知', '16', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('171', '1', 'dialy', '', 'H5日志', '17', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('172', '1', 'cyhm', '', '常用号码', '20', '1', '', null);
INSERT INTO `tb_module_info_mobile` VALUES ('173', '1', 'sendByMe', '', '我发起的', '1201', '1', '', '168');
INSERT INTO `tb_module_info_mobile` VALUES ('174', '1', 'doByMe', '', '我承办的', '1202', '1', '', '168');
INSERT INTO `tb_module_info_mobile` VALUES ('175', '1', 'changeByMe', '', '我转交的', '1203', '1', '', '168');

-- ----------------------------
-- Table structure for tb_oab_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_oab_address`;
CREATE TABLE `tb_oab_address` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `address_Group_Id` int(11) DEFAULT NULL,
  `allow_update_user_ids` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `children` varchar(100) DEFAULT NULL,
  `company_address` varchar(200) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `company_post_code` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_share_time` datetime DEFAULT NULL,
  `family_address` varchar(200) DEFAULT NULL,
  `family_email` varchar(255) DEFAULT NULL,
  `family_mobile_no` varchar(20) DEFAULT NULL,
  `family_phone_no` varchar(20) DEFAULT NULL,
  `family_post_code` varchar(20) DEFAULT NULL,
  `first_letter` varchar(10) NOT NULL,
  `groupname` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `office_fax` varchar(20) DEFAULT NULL,
  `office_phone` varchar(20) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `photo` varchar(1000) DEFAULT NULL,
  `post_info` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `share_to_user_ids` varchar(255) DEFAULT NULL,
  `start_share_time` datetime DEFAULT NULL,
  `wife_name` varchar(100) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  KEY `FKAAEB57349B9F8D7C` (`create_user_id`),
  CONSTRAINT `FKAAEB57349B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_oab_address
-- ----------------------------

-- ----------------------------
-- Table structure for tb_oab_address_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_oab_address_group`;
CREATE TABLE `tb_oab_address_group` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `addressid` int(11) NOT NULL,
  `groupid` int(11) NOT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_oab_address_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_oab_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_oab_group`;
CREATE TABLE `tb_oab_group` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `grouptype` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `maintaingroupids` varchar(255) DEFAULT NULL,
  `maintaingroupnames` varchar(255) DEFAULT NULL,
  `maintainuserids` varchar(255) DEFAULT NULL,
  `maintainusernames` varchar(255) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `order_no` int(11) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  KEY `FKB16A81BF9B9F8D7C` (`create_user_id`),
  CONSTRAINT `FKB16A81BF9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_oab_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_oc_check
-- ----------------------------
DROP TABLE IF EXISTS `tb_oc_check`;
CREATE TABLE `tb_oc_check` (
  `check_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `read_time` datetime DEFAULT NULL,
  `ref_id` int(11) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_oc_check
-- ----------------------------

-- ----------------------------
-- Table structure for tb_ohr_user_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_ohr_user_record`;
CREATE TABLE `tb_ohr_user_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `administration_level` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `bank_name_1` varchar(100) DEFAULT NULL,
  `bank_name_2` varchar(100) DEFAULT NULL,
  `bank_no_1` varchar(50) DEFAULT NULL,
  `bank_no_2` varchar(50) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `company_work_age` int(11) DEFAULT NULL,
  `computer_level` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `edu_level` int(11) DEFAULT NULL,
  `edu_qualifications` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `foreign_language1` varchar(255) DEFAULT NULL,
  `foreign_language2` varchar(255) DEFAULT NULL,
  `foreign_language3` varchar(255) DEFAULT NULL,
  `graduation_school` varchar(200) DEFAULT NULL,
  `graduation_time` datetime DEFAULT NULL,
  `group_names` varchar(100) DEFAULT NULL,
  `health_check_record` varchar(200) DEFAULT NULL,
  `health_info` varchar(300) DEFAULT NULL,
  `home_address` varchar(300) DEFAULT NULL,
  `identity_no` varchar(30) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `job_title` int(11) DEFAULT NULL,
  `job_title_level` int(11) DEFAULT NULL,
  `join_time` datetime DEFAULT NULL,
  `join_work_time` datetime DEFAULT NULL,
  `language_level1` varchar(255) DEFAULT NULL,
  `language_level2` varchar(255) DEFAULT NULL,
  `language_level3` varchar(255) DEFAULT NULL,
  `last_update_time` datetime NOT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `lunar_birthday` int(11) DEFAULT NULL,
  `marriage_status` int(11) DEFAULT NULL,
  `msn` varchar(100) DEFAULT NULL,
  `name_english` varchar(100) DEFAULT NULL,
  `name_old` varchar(100) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `nativity_place` varchar(200) DEFAULT NULL,
  `nativity_place_select` varchar(200) DEFAULT NULL,
  `other_contact_way` varchar(200) DEFAULT NULL,
  `party_time` datetime DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `phone2` varchar(50) DEFAULT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  `politics_face` int(11) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `post_state` varchar(200) DEFAULT NULL,
  `profession` varchar(100) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `record_no` varchar(50) NOT NULL,
  `registered_address` varchar(300) DEFAULT NULL,
  `registered_type` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `resume_info` varchar(255) DEFAULT NULL,
  `role_names` varchar(100) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `social_security_state` varchar(200) DEFAULT NULL,
  `start_pay_time` datetime DEFAULT NULL,
  `station` int(11) DEFAULT NULL,
  `strong_point` varchar(200) DEFAULT NULL,
  `total_work_age` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  `vouch_record` varchar(200) DEFAULT NULL,
  `work_no` varchar(50) DEFAULT NULL,
  `work_status` int(11) DEFAULT NULL,
  `work_type` varchar(100) DEFAULT NULL,
  `year_holiday` int(11) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  `last_update_user_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK6644B9EEA27BA512` (`last_update_user_id`),
  KEY `FK6644B9EEDBED01BF` (`user_id`),
  KEY `FK6644B9EE9B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK6644B9EE9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK6644B9EEA27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK6644B9EEDBED01BF` FOREIGN KEY (`user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_ohr_user_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_affairs
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_affairs`;
CREATE TABLE `tb_om_affairs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `remind_flag` int(11) NOT NULL,
  `remind_time` datetime DEFAULT NULL,
  `body_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9EDF5DDAEB48A10F` (`to_id`),
  KEY `FK9EDF5DDAB5267FBB` (`body_id`),
  KEY `FK9EDF5DDA9B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK9EDF5DDA9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK9EDF5DDAB5267FBB` FOREIGN KEY (`body_id`) REFERENCES `tb_om_affairs_body` (`id`),
  CONSTRAINT `FK9EDF5DDAEB48A10F` FOREIGN KEY (`to_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_affairs
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_affairs_body
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_affairs_body`;
CREATE TABLE `tb_om_affairs_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `content_info` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `remind_url` varchar(255) DEFAULT NULL,
  `send_time` datetime NOT NULL,
  `sms_type` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9C2CE7C1455580` (`from_id`),
  KEY `FK9C2CE79B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK9C2CE79B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK9C2CE7C1455580` FOREIGN KEY (`from_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_affairs_body
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_affairs_remind
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_affairs_remind`;
CREATE TABLE `tb_om_affairs_remind` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `times` int(11) NOT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_affairs_remind
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_affair_setting
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_affair_setting`;
CREATE TABLE `tb_om_affair_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `affair_priv` int(11) DEFAULT NULL,
  `module_code` varchar(255) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `push_priv` int(11) DEFAULT NULL,
  `sms_priv` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_affair_setting
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_attachment
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_attachment`;
CREATE TABLE `tb_om_attachment` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attach_file` varchar(200) NOT NULL,
  `attach_name` varchar(200) NOT NULL,
  `attach_size` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_message`;
CREATE TABLE `tb_om_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `content_info` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `msg_type` int(11) DEFAULT NULL,
  `remind_Flag` int(11) DEFAULT NULL,
  `send_time` datetime NOT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `to_uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK18B48D17B0ED603C` (`to_uid`),
  KEY `FK18B48D179B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK18B48D179B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK18B48D17B0ED603C` FOREIGN KEY (`to_uid`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_message
-- ----------------------------

-- ----------------------------
-- Table structure for tb_om_sys_para
-- ----------------------------
DROP TABLE IF EXISTS `tb_om_sys_para`;
CREATE TABLE `tb_om_sys_para` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `para_name` varchar(255) DEFAULT NULL,
  `para_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_om_sys_para
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_comment`;
CREATE TABLE `tb_onlineexam_comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `Content` varchar(255) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_course`;
CREATE TABLE `tb_onlineexam_course` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `columnid` int(11) DEFAULT NULL,
  `CourseName` varchar(100) DEFAULT NULL,
  `CoursePicUrl` varchar(100) DEFAULT NULL,
  `createID` int(11) DEFAULT NULL,
  `CreateName` varchar(20) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `fileType` int(11) DEFAULT NULL,
  `hasExam` int(11) DEFAULT NULL,
  `hasExercise` int(11) DEFAULT NULL,
  `hasTest` int(11) DEFAULT NULL,
  `Intro` varchar(255) DEFAULT NULL,
  `isComment` int(11) DEFAULT NULL,
  `isDelete` int(11) DEFAULT NULL,
  `publish_user_ids` text,
  `publish_user_names` text,
  `Speaker` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `unitExercises` int(11) DEFAULT NULL,
  `UnitItemNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_course
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_course_unit
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_course_unit`;
CREATE TABLE `tb_onlineexam_course_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `fileSize` varchar(255) DEFAULT NULL,
  `IconUrl` varchar(255) DEFAULT NULL,
  `isDelete` int(11) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `oldVideoName` varchar(255) DEFAULT NULL,
  `QuestionNum` int(11) DEFAULT NULL,
  `TimeLength` varchar(255) DEFAULT NULL,
  `VideoName` varchar(255) DEFAULT NULL,
  `VideoUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_course_unit
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_practice_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_practice_log`;
CREATE TABLE `tb_onlineexam_practice_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `IsDelete` int(11) DEFAULT NULL,
  `IsRight` int(11) DEFAULT NULL,
  `QuestionId` int(11) DEFAULT NULL,
  `unitId` int(11) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_practice_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_question`;
CREATE TABLE `tb_onlineexam_question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `DeleteTime` datetime DEFAULT NULL,
  `IsDelete` int(11) DEFAULT NULL,
  `IsExam` int(11) DEFAULT NULL,
  `IsTest` int(11) DEFAULT NULL,
  `OrderLevel` int(11) DEFAULT NULL,
  `questionParse` varchar(255) DEFAULT NULL,
  `QuestionType` int(11) DEFAULT NULL,
  `RightAnswer` varchar(255) DEFAULT NULL,
  `Score` double DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `UnitID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_question
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_question_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_question_item`;
CREATE TABLE `tb_onlineexam_question_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `isDelete` int(11) DEFAULT NULL,
  `ItemCode` varchar(100) DEFAULT NULL,
  `ItemName` varchar(100) DEFAULT NULL,
  `QuestionID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_question_item
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_question_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_question_log`;
CREATE TABLE `tb_onlineexam_question_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `AnswerTime` datetime DEFAULT NULL,
  `IsRight` int(11) DEFAULT NULL,
  `OrderLevel` int(11) DEFAULT NULL,
  `QuestionAnswer` varchar(20) DEFAULT NULL,
  `questionId` int(11) DEFAULT NULL,
  `QuestionItem` varchar(100) DEFAULT NULL,
  `questionParse` varchar(255) DEFAULT NULL,
  `QuestionTitle` varchar(50) DEFAULT NULL,
  `QuestionType` int(11) DEFAULT NULL,
  `Score` double DEFAULT NULL,
  `TestLogID` int(11) DEFAULT NULL,
  `UserAnswer` varchar(20) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_question_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_test
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_test`;
CREATE TABLE `tb_onlineexam_test` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `questionId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_test
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_test_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_test_config`;
CREATE TABLE `tb_onlineexam_test_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `GroupRule` int(11) DEFAULT NULL,
  `Intro` varchar(255) DEFAULT NULL,
  `isCache` int(11) DEFAULT NULL,
  `isRepeat` int(11) DEFAULT NULL,
  `OptionSet` int(11) DEFAULT NULL,
  `TestEndTime` datetime DEFAULT NULL,
  `TestName` varchar(255) DEFAULT NULL,
  `TestStartTime` datetime DEFAULT NULL,
  `TestTime` int(11) DEFAULT NULL,
  `TotalQuestionNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_test_config
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_test_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_test_log`;
CREATE TABLE `tb_onlineexam_test_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `ConsumeTime` bigint(20) DEFAULT NULL,
  `CourseId` int(11) DEFAULT NULL,
  `EndTime` datetime DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Score` double DEFAULT NULL,
  `StartTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_test_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_onlineexam_unit_finish_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_onlineexam_unit_finish_log`;
CREATE TABLE `tb_onlineexam_unit_finish_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `courseId` int(11) DEFAULT NULL,
  `isFinish` int(11) DEFAULT NULL,
  `unitId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_onlineexam_unit_finish_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_on_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_on_file`;
CREATE TABLE `tb_on_file` (
  `content_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `attachment_desc` varchar(255) DEFAULT NULL,
  `attachment_name` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `content_no` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `sort_no` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_ids` varchar(255) DEFAULT NULL,
  `user_names` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `file_sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  KEY `FKB0FCDB0B9EA9C11D` (`file_sort`),
  CONSTRAINT `FKB0FCDB0B9EA9C11D` FOREIGN KEY (`file_sort`) REFERENCES `tb_on_file_sort` (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_on_file
-- ----------------------------

-- ----------------------------
-- Table structure for tb_on_file_sort
-- ----------------------------
DROP TABLE IF EXISTS `tb_on_file_sort`;
CREATE TABLE `tb_on_file_sort` (
  `sort_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `sort_name` varchar(255) DEFAULT NULL,
  `sort_no` varchar(255) DEFAULT NULL,
  `sort_type` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_on_file_sort
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_affair
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_affair`;
CREATE TABLE `tb_op_affair` (
  `aff_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `begin_time_time` varchar(50) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `end_time_time` varchar(50) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_remaind` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `remind_date` varchar(50) DEFAULT NULL,
  `remind_time` varchar(50) DEFAULT NULL,
  `remind_type` int(11) DEFAULT NULL,
  `taker` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`aff_id`),
  UNIQUE KEY `aff_id` (`aff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_affair
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_calendar
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_calendar`;
CREATE TABLE `tb_op_calendar` (
  `calendar_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `before_remaind` varchar(50) DEFAULT NULL,
  `beg_time` datetime DEFAULT NULL,
  `cal_level` int(11) DEFAULT NULL,
  `cal_type` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_remaind` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `taker` varchar(255) DEFAULT NULL,
  `work_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`calendar_id`),
  UNIQUE KEY `calendar_id` (`calendar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_calendar
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_countdown
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_countdown`;
CREATE TABLE `tb_op_countdown` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `background_color` varchar(255) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `order_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_countdown
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_daily
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_daily`;
CREATE TABLE `tb_op_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `appendix` varchar(2000) DEFAULT NULL,
  `attach_name` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `daily_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_review` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `review_num` int(11) DEFAULT NULL,
  `review_time` datetime DEFAULT NULL,
  `send_type` varchar(255) DEFAULT NULL,
  `share_user_ids` varchar(2000) DEFAULT NULL,
  `share_user_names` varchar(2000) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_daily
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_daily_review
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_daily_review`;
CREATE TABLE `tb_op_daily_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `appendix` varchar(255) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `daily_id` int(11) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `type` int(11) NOT NULL,
  `review_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA045320BDBED01BF` (`user_id`),
  KEY `FKA045320B4335351B` (`review_id`),
  CONSTRAINT `FKA045320B4335351B` FOREIGN KEY (`review_id`) REFERENCES `tb_op_daily_review` (`id`),
  CONSTRAINT `FKA045320BDBED01BF` FOREIGN KEY (`user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_daily_review
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_desktop_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_desktop_module`;
CREATE TABLE `tb_op_desktop_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `desktop_page_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `module_info_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK7881367C31C6938A` (`module_info_id`),
  CONSTRAINT `FK7881367C31C6938A` FOREIGN KEY (`module_info_id`) REFERENCES `tb_module_info` (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_desktop_module
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_desktop_page
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_desktop_page`;
CREATE TABLE `tb_op_desktop_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_desktop_page
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_email_body
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_email_body`;
CREATE TABLE `tb_op_email_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `attachment_size` bigint(20) NOT NULL,
  `content_info` text,
  `copy_to_id` varchar(255) DEFAULT NULL,
  `copy_to_name` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `important_level` int(11) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime NOT NULL,
  `last_update_user_id` int(11) NOT NULL,
  `need_receipt` int(11) NOT NULL,
  `secret_to_id` varchar(255) DEFAULT NULL,
  `secret_to_name` varchar(255) DEFAULT NULL,
  `send_status` int(11) NOT NULL,
  `send_time` datetime DEFAULT NULL,
  `send_type` varchar(255) DEFAULT NULL,
  `sms_remind` int(11) NOT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `to_id` varchar(255) DEFAULT NULL,
  `to_name` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKC55D11729B9F8D7C` (`create_user_id`),
  CONSTRAINT `FKC55D11729B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_email_body
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_email_box
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_email_box`;
CREATE TABLE `tb_op_email_box` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `box_name` varchar(50) NOT NULL,
  `box_size` bigint(20) DEFAULT NULL,
  `box_type` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime NOT NULL,
  `last_update_user_id` int(11) NOT NULL,
  `order_no` int(11) DEFAULT NULL,
  `page_max` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_email_box
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_email_to
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_email_to`;
CREATE TABLE `tb_op_email_to` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `email_to_status` int(11) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `last_update_time` datetime NOT NULL,
  `last_update_user_id` int(11) NOT NULL,
  `mark_level` int(11) NOT NULL,
  `read_status` int(11) NOT NULL,
  `read_time` datetime DEFAULT NULL,
  `to_id` int(11) NOT NULL,
  `to_type` int(11) NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `email_body_id` int(11) NOT NULL,
  `email_box_id` int(11) DEFAULT NULL,
  `old_email_box_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK23A29A4BF1B37B25` (`email_body_id`),
  KEY `FK23A29A4B1858D3CF` (`email_box_id`),
  KEY `FK23A29A4B9B9F8D7C` (`create_user_id`),
  KEY `FK23A29A4B8C6B06E7` (`old_email_box_id`),
  CONSTRAINT `FK23A29A4B1858D3CF` FOREIGN KEY (`email_box_id`) REFERENCES `tb_op_email_box` (`id`),
  CONSTRAINT `FK23A29A4B8C6B06E7` FOREIGN KEY (`old_email_box_id`) REFERENCES `tb_op_email_box` (`id`),
  CONSTRAINT `FK23A29A4B9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK23A29A4BF1B37B25` FOREIGN KEY (`email_body_id`) REFERENCES `tb_op_email_body` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_email_to
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_programme
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_programme`;
CREATE TABLE `tb_op_programme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `alert_time` int(11) DEFAULT NULL,
  `beg_time` datetime DEFAULT NULL,
  `content` text,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `is_all_day` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_reminded` int(11) DEFAULT NULL,
  `is_sys_remaind` int(11) DEFAULT NULL,
  `remaind_time` varchar(255) DEFAULT NULL,
  `remaind_type` int(11) DEFAULT NULL,
  `remind_style` varchar(255) DEFAULT NULL,
  `repeat_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_programme
-- ----------------------------

-- ----------------------------
-- Table structure for tb_op_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_task`;
CREATE TABLE `tb_op_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `important` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_remaind` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `rate` varchar(5000) DEFAULT NULL,
  `subject` varchar(5000) DEFAULT NULL,
  `task_no` int(11) DEFAULT NULL,
  `task_status` int(11) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `total_time` varchar(50) DEFAULT NULL,
  `use_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_op_task
-- ----------------------------

-- ----------------------------
-- Table structure for tb_platform_parameter
-- ----------------------------
DROP TABLE IF EXISTS `tb_platform_parameter`;
CREATE TABLE `tb_platform_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `instenceid` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `module_name` varchar(200) DEFAULT NULL,
  `par_describe` varchar(200) DEFAULT NULL,
  `par_items` varchar(200) DEFAULT NULL,
  `par_value_coll` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_platform_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for tb_production_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tb_production_schedule`;
CREATE TABLE `tb_production_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `bothincome` double DEFAULT NULL,
  `coal_loading` double DEFAULT NULL,
  `compared_plan` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `daily_car_assembly` double DEFAULT NULL,
  `dead_load` double DEFAULT NULL,
  `door_to_door` double DEFAULT NULL,
  `door_to_stand` double DEFAULT NULL,
  `groceries` double DEFAULT NULL,
  `income` double DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `large_coal_mine` double DEFAULT NULL,
  `middle` double DEFAULT NULL,
  `rTime` datetime DEFAULT NULL,
  `send_person_plan` double DEFAULT NULL,
  `send_plan` double DEFAULT NULL,
  `send_tons` double DEFAULT NULL,
  `sender` double DEFAULT NULL,
  `small_coal_mine` double DEFAULT NULL,
  `stand_door` double DEFAULT NULL,
  `stop` double DEFAULT NULL,
  `transport_schedule` double DEFAULT NULL,
  `unload` double DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `use_of_vehicles` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_production_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for tb_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `item_num` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_list` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `info_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBBA5BA971AACB810` (`info_id`),
  KEY `FKBBA5BA97A27BA512` (`last_update_user_id`),
  KEY `FKBBA5BA979B9F8D7C` (`create_user_id`),
  CONSTRAINT `FKBBA5BA971AACB810` FOREIGN KEY (`info_id`) REFERENCES `tb_questionnaire` (`id`),
  CONSTRAINT `FKBBA5BA979B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKBBA5BA97A27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question
-- ----------------------------
INSERT INTO `tb_question` VALUES ('4', '1', null, '2016-10-12 16:02:41', '0', null, '你好吗？', '1', '1', '2016-10-12 16:02:41', '12', '1', '12');
INSERT INTO `tb_question` VALUES ('5', '1', null, '2016-10-12 16:02:41', '0', null, '你漂亮吗？', '2', '2', '2016-10-12 16:02:41', '12', '1', '12');
INSERT INTO `tb_question` VALUES ('6', '1', null, '2016-10-12 16:02:41', '0', null, '说说你的见解？', '3', '3', '2016-10-12 16:02:41', '12', '1', '12');

-- ----------------------------
-- Table structure for tb_questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `tb_questionnaire`;
CREATE TABLE `tb_questionnaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `is_anonymity` int(11) DEFAULT NULL,
  `is_award` bit(1) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_lottery` int(11) DEFAULT NULL,
  `publish_date` datetime DEFAULT NULL,
  `question_num` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `view_result` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30C61592A27BA512` (`last_update_user_id`),
  KEY `FK30C615929B9F8D7C` (`create_user_id`),
  CONSTRAINT `FK30C615929B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK30C61592A27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_questionnaire
-- ----------------------------
INSERT INTO `tb_questionnaire` VALUES ('1', '1', '2016-10-12 16:01:05', '测试问卷', '2016-10-12 16:04:11', '0', '', '0', '0', '2016-10-12 16:02:49', null, '4', '测试问卷', '2016-10-12 16:01:05', null, '12', '12');

-- ----------------------------
-- Table structure for tb_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `tb_question_answer`;
CREATE TABLE `tb_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `other_content` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `info_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFE60BA661AACB810` (`info_id`),
  KEY `FKFE60BA66A27BA512` (`last_update_user_id`),
  KEY `FKFE60BA669B9F8D7C` (`create_user_id`),
  KEY `FKFE60BA66C0A94B39` (`question_id`),
  CONSTRAINT `FKFE60BA661AACB810` FOREIGN KEY (`info_id`) REFERENCES `tb_questionnaire` (`id`),
  CONSTRAINT `FKFE60BA669B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKFE60BA66A27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKFE60BA66C0A94B39` FOREIGN KEY (`question_id`) REFERENCES `tb_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question_answer
-- ----------------------------

-- ----------------------------
-- Table structure for tb_question_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_question_item`;
CREATE TABLE `tb_question_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_answer` bit(1) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `order_list` int(11) DEFAULT NULL,
  `other_item` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2FF67FBBA27BA512` (`last_update_user_id`),
  KEY `FK2FF67FBB9B9F8D7C` (`create_user_id`),
  KEY `FK2FF67FBBC0A94B39` (`question_id`),
  CONSTRAINT `FK2FF67FBB9B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK2FF67FBBA27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK2FF67FBBC0A94B39` FOREIGN KEY (`question_id`) REFERENCES `tb_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question_item
-- ----------------------------

-- ----------------------------
-- Table structure for tb_question_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_question_user`;
CREATE TABLE `tb_question_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `comment1` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `statue` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `inquirer_id` int(11) DEFAULT NULL,
  `info_id` int(11) DEFAULT NULL,
  `survey_id` int(11) DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2FFBF07354E7CFEB` (`inquirer_id`),
  KEY `FK2FFBF0731AACB810` (`info_id`),
  KEY `FK2FFBF073A27BA512` (`last_update_user_id`),
  KEY `FK2FFBF0739B9F8D7C` (`create_user_id`),
  KEY `FK2FFBF073570367D0` (`survey_id`),
  CONSTRAINT `FK2FFBF0731AACB810` FOREIGN KEY (`info_id`) REFERENCES `tb_questionnaire` (`id`),
  CONSTRAINT `FK2FFBF07354E7CFEB` FOREIGN KEY (`inquirer_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK2FFBF073570367D0` FOREIGN KEY (`survey_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK2FFBF0739B9F8D7C` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FK2FFBF073A27BA512` FOREIGN KEY (`last_update_user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_question_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_info`;
CREATE TABLE `tb_role_info` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `Memo` varchar(100) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `role_code` varchar(50) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `role_type` int(11) DEFAULT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_info
-- ----------------------------
INSERT INTO `tb_role_info` VALUES ('1', '1', '0', '默认角色-不能删除', '0', 'common_role', '通用角色', '0', null);
INSERT INTO `tb_role_info` VALUES ('2', '1', '0', '收文登记员', '1', 'gather_register', '收文登记员', '0', null);

-- ----------------------------
-- Table structure for tb_role_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_module`;
CREATE TABLE `tb_role_module` (
  `Vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_module
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role_module_mobile
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_module_mobile`;
CREATE TABLE `tb_role_module_mobile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_module_mobile
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user`;
CREATE TABLE `tb_role_user` (
  `Vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Vid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_user
-- ----------------------------
INSERT INTO `tb_role_user` VALUES ('2', '1', '1', '1', '12');
INSERT INTO `tb_role_user` VALUES ('3', '1', '1', '1', '2');
INSERT INTO `tb_role_user` VALUES ('4', '1', '1', '1', '3');

-- ----------------------------
-- Table structure for tb_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedule`;
CREATE TABLE `tb_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instance_id` varchar(20) DEFAULT NULL,
  `content` varchar(1500) DEFAULT NULL,
  `complete_time` date DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_user_id` int(11) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `undone_reason` varchar(1500) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for tb_scheduleinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_scheduleinfo`;
CREATE TABLE `tb_scheduleinfo` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `actiontype` int(11) DEFAULT NULL,
  `cronexpression` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `schedulename` varchar(255) DEFAULT NULL,
  `schedulestate` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `vid` (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_scheduleinfo
-- ----------------------------

-- ----------------------------
-- Table structure for tb_schedule_lifecycle
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedule_lifecycle`;
CREATE TABLE `tb_schedule_lifecycle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` varchar(20) NOT NULL,
  `operation_time` datetime NOT NULL,
  `operation_content` varchar(1500) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_schedule_lifecycle
-- ----------------------------

-- ----------------------------
-- Table structure for tb_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_config`;
CREATE TABLE `tb_sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `checkpagemodule` varchar(255) DEFAULT NULL,
  `config_key` varchar(50) DEFAULT NULL,
  `config_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sys_config
-- ----------------------------
INSERT INTO `tb_sys_config` VALUES ('1', null, null, 'approve_widget', '1');
INSERT INTO `tb_sys_config` VALUES ('2', null, null, 'reader_widget', '1');
INSERT INTO `tb_sys_config` VALUES ('3', null, null, 'approve_comment', '1');
INSERT INTO `tb_sys_config` VALUES ('4', null, null, 'notice_update_password', '1');
INSERT INTO `tb_sys_config` VALUES ('5', null, null, 'dom_gather_register', '转领导批阅,转收文分发,转阅读,');
INSERT INTO `tb_sys_config` VALUES ('6', null, null, 'dom_gather_approve', '转领导批阅,转收文分发,转阅读,');
INSERT INTO `tb_sys_config` VALUES ('7', null, null, 'force_read', '1');
INSERT INTO `tb_sys_config` VALUES ('8', null, null, 'dom_gather_zip', '1');
INSERT INTO `tb_sys_config` VALUES ('9', null, null, 'dom_dis_nigao', '转核稿,转盖章,转发文分发,');
INSERT INTO `tb_sys_config` VALUES ('10', null, null, 'dom_dis_hegao', '转核稿,转盖章,转发文分发,');
INSERT INTO `tb_sys_config` VALUES ('11', null, null, 'dom_dis_red', '转核稿,转发文分发,');
INSERT INTO `tb_sys_config` VALUES ('12', null, null, 'dom_dis_zip', '1');
INSERT INTO `tb_sys_config` VALUES ('13', null, null, 'bumenzhuanlan', '32768,');
INSERT INTO `tb_sys_config` VALUES ('14', null, null, 'bumenzhuanlan_name', '郑州大学,');
INSERT INTO `tb_sys_config` VALUES ('16', null, null, 'COMMENT_LOG_APPROVE', '1');
INSERT INTO `tb_sys_config` VALUES ('17', null, null, 'COMMENT_LOG_REPLY', '1');

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `importance` varchar(255) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `reality_end_time` datetime DEFAULT NULL,
  `reality_start_time` datetime DEFAULT NULL,
  `reminder_status` int(11) DEFAULT NULL,
  `reminder_type` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `task_content` varchar(5000) DEFAULT NULL,
  `task_name` varchar(255) DEFAULT NULL,
  `vox` varchar(255) DEFAULT NULL,
  `publish_user` int(11) DEFAULT NULL,
  `undertake_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA4FEB4B67D09B05B` (`undertake_user`),
  KEY `FKA4FEB4B6759DECAB` (`publish_user`),
  CONSTRAINT `FKA4FEB4B6759DECAB` FOREIGN KEY (`publish_user`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKA4FEB4B67D09B05B` FOREIGN KEY (`undertake_user`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task
-- ----------------------------

-- ----------------------------
-- Table structure for tb_task_action
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_action`;
CREATE TABLE `tb_task_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `action_type` int(11) DEFAULT NULL,
  `finish_plan` int(11) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `report` varchar(5000) DEFAULT NULL,
  `vox` varchar(255) DEFAULT NULL,
  `operate_user` int(11) DEFAULT NULL,
  `receive_user` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKDB842DDFCCAA0156` (`operate_user`),
  KEY `FKDB842DDFB6B49B57` (`receive_user`),
  KEY `FKDB842DDF84765561` (`task_id`),
  CONSTRAINT `FKDB842DDF84765561` FOREIGN KEY (`task_id`) REFERENCES `tb_task` (`id`),
  CONSTRAINT `FKDB842DDFB6B49B57` FOREIGN KEY (`receive_user`) REFERENCES `tb_user_info` (`user_id`),
  CONSTRAINT `FKDB842DDFCCAA0156` FOREIGN KEY (`operate_user`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task_action
-- ----------------------------

-- ----------------------------
-- Table structure for tb_task_review
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_review`;
CREATE TABLE `tb_task_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `actionid` int(11) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `review` varchar(255) DEFAULT NULL,
  `reviewuserid` int(11) DEFAULT NULL,
  `reviewusername` varchar(255) DEFAULT NULL,
  `taskid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task_review
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_group`;
CREATE TABLE `tb_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `group_power` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB8E7977CDBED01BF` (`user_id`),
  CONSTRAINT `FKB8E7977CDBED01BF` FOREIGN KEY (`user_id`) REFERENCES `tb_user_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `alter_name` varchar(50) DEFAULT NULL,
  `Birthday` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `is_default` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_virtual` int(11) DEFAULT NULL,
  `Job` varchar(50) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `link_id` int(11) DEFAULT NULL,
  `login_name` varchar(50) DEFAULT NULL,
  `login_pass` varchar(50) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `phone_public` int(11) DEFAULT NULL,
  `Photo` varchar(300) DEFAULT NULL,
  `PY` varchar(50) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `Sex` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_state` int(11) DEFAULT NULL,
  `work_no` varchar(50) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `formatted_number` varchar(255) DEFAULT NULL,
  `full_py` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `home_tel` varchar(255) DEFAULT NULL,
  `mcn_type` int(11) DEFAULT NULL,
  `mobile_show_state` int(11) DEFAULT NULL,
  `ntko_url` varchar(100) DEFAULT NULL,
  `office_tel` varchar(255) DEFAULT NULL,
  `office_widget` int(11) DEFAULT NULL,
  `partition_companyid` int(11) DEFAULT NULL,
  `phone2` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `sign_name` varchar(255) DEFAULT NULL,
  `sign_type` int(11) DEFAULT NULL,
  `sign_url` varchar(100) DEFAULT NULL,
  `sign_widget` int(11) DEFAULT NULL,
  `skin_logo` int(11) DEFAULT NULL,
  `tao_da` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `turn_list` varchar(50) DEFAULT NULL,
  `turn_type` int(11) DEFAULT NULL,
  `user_num` varchar(255) DEFAULT NULL,
  `user_num_type` int(11) DEFAULT NULL,
  `user_power` int(11) DEFAULT NULL,
  `v_group` varchar(255) DEFAULT NULL,
  `v_num` varchar(255) DEFAULT NULL,
  `is_fork_group` int(11) DEFAULT NULL,
  `OrderChangedDate` datetime DEFAULT NULL,
  `OrderType` int(11) DEFAULT NULL,
  `hide_phone` int(11) DEFAULT NULL,
  `is_leader` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES ('1', '1', '管理员', '2016-10-08 10:03:11', '2016-10-08 10:03:13', '434557245@qq.com', '0', '0', null, null, '2016-10-08 14:37:03', '2016-10-08 15:43:06', null, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', '15838369365', null, null, null, null, null, '超级管理员', '0', '001', '1', null, null, null, null, null, null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user_info` VALUES ('2', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', '2016-10-11 11:28:21', '2016-10-11 11:28:22', null, '13500000001', 'e10adc3949ba59abbe56e057f20f883e', '2', '13500000001', null, null, 'pxx1', '2016-10-08 14:04:42', '1', '潘xx1', '0', '001', null, '79|799^|72699^|', 'Pan xx1', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '11', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('3', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '13523508179', 'e10adc3949ba59abbe56e057f20f883e', '3', '13523508179', null, null, 'pxx2', '2016-10-08 14:04:42', '1', '潘xx2', '0', '001', null, '79|799^|72699^|', 'Pan xx2', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('4', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15912345674', 'e10adc3949ba59abbe56e057f20f883e', '4', '15912345674', null, null, 'pxx3', '2016-10-08 14:04:42', '1', '潘xx3', '0', '001', null, '79|799^|72699^|', 'Pan xx3', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('5', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15978945641', 'e10adc3949ba59abbe56e057f20f883e', '5', '15978945641', null, null, 'pxx4', '2016-10-08 14:04:42', '1', '潘xx4', '0', '001', null, '79|799^|72699^|', 'Pan xx4', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('6', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15003843612', 'e10adc3949ba59abbe56e057f20f883e', '6', '15003843612', null, null, 'pxx5', '2016-10-08 14:04:42', '1', '潘xx5', '0', '001', null, '79|799^|72699^|', 'Pan xx5', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('7', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15978945643', 'e10adc3949ba59abbe56e057f20f883e', '7', '15978945643', null, null, 'pxx6', '2016-10-08 14:04:42', '1', '潘xx6', '0', '001', null, '79|799^|72699^|', 'Pan xx6', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('8', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15003843618', 'e10adc3949ba59abbe56e057f20f883e', '8', '15003843618', null, null, 'pxx7', '2016-10-08 14:04:42', '1', '潘xx7', '0', '001', null, '79|799^|72699^|', 'Pan xx7', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('9', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '15978945644', 'e10adc3949ba59abbe56e057f20f883e', '9', '15978945644', null, null, 'pxx8', '2016-10-08 14:04:42', '1', '潘xx8', '0', '001', null, '79|799^|72699^|', 'Pan xx8', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('10', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '13591991185', 'e10adc3949ba59abbe56e057f20f883e', '10', '13591991185', null, null, 'pxx9', '2016-10-08 14:04:42', '1', '潘xx9', '0', '001', null, '79|799^|72699^|', 'Pan xx9', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('11', '1', null, null, '2016-10-08 14:04:42', null, '1', '0', null, '学生', null, '2016-10-08 14:04:42', null, '13591991186', 'e10adc3949ba59abbe56e057f20f883e', '11', '13591991186', null, null, 'pxx10', '2016-10-08 14:04:42', '1', '潘xx10', '0', '001', null, '79|799^^|72699^^|', 'Pan xx10', '32768', null, null, '0', null, null, '0', '1', null, null, null, '0', null, '0', '1', '0', null, null, null, null, null, null, null, null, '32768', null, '0', null, null);
INSERT INTO `tb_user_info` VALUES ('12', '1', '潘博', '2016-10-08 00:00:00', '2016-10-08 15:47:01', '434557245@qq.com', '1', '0', null, '教授', '2016-10-12 16:53:22', '2016-10-12 18:20:50', null, '15512345679', 'e10adc3949ba59abbe56e057f20f883e', '1', '15512345679', '1', '', 'pb', '2016-10-08 15:47:01', '1', '潘博', '0', '0001111', null, '72|726|72626|', 'Pan Bo ', '393800', null, null, '0', null, '037111111111', '0', '1', null, null, null, '0', '', '0', '11', '0', null, null, null, null, null, null, null, null, '32768', null, '0', '0', '0');

-- ----------------------------
-- Table structure for userloginst
-- ----------------------------
DROP TABLE IF EXISTS `userloginst`;
CREATE TABLE `userloginst` (
  `Vid` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `FirstWebLoginTime` datetime DEFAULT NULL,
  `FirstClientLoginTime` datetime DEFAULT NULL,
  `LastWebLoginTime` datetime DEFAULT NULL,
  `LastClientLoginTime` datetime DEFAULT NULL,
  `WebLoginCount` int(11) DEFAULT NULL,
  `ClientLoginCount` int(11) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `ClientVersion` varchar(50) DEFAULT NULL,
  `SendedUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userloginst
-- ----------------------------
