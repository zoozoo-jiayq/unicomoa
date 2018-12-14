/*
Navicat SQL Server Data Transfer

Source Server         : 10.100.6.127
Source Server Version : 105000
Source Host           : 10.100.6.127:1433
Source Database       : cbbV3
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2014-10-14 09:29:22
*/


-- ----------------------------
-- Table structure for [dbo].[tb_cbb_notify]
-- ----------------------------

CREATE TABLE [dbo].[tb_cbb_notify] (
[id] int NOT NULL IDENTITY(1,1) ,
[create_user_id] int NULL ,
[create_date] datetime NULL ,
[last_update_user_id] int NULL ,
[update_date] datetime NULL ,
[is_delete] int NULL ,
[subject] varchar(100) NULL ,
[content] text NULL ,
[summary] varchar(60) NULL ,
[begin_date] datetime NULL ,
[end_date] datetime NULL ,
[notify_type] int NULL ,
[is_top] int NULL ,
[status] int NULL ,
[auditer] int NULL ,
[reason] varchar(1000) NULL ,
[approve_date] datetime NULL ,
[attment] varchar(500) NULL ,
[publish_user_ids] varchar(MAX) NULL ,
[publish_user_names] varchar(MAX) NULL ,
[view_count] int NULL ,
[columnid] int NULL ,
[images] varchar(1000) NULL ,
[sendType] varchar(10) NULL ,
[publish_group_id] varchar(100) NULL 
)
-- ----------------------------
-- Indexes structure for table tb_cbb_notify
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[tb_cbb_notify]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[tb_cbb_notify]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify] ADD FOREIGN KEY ([create_user_id]) REFERENCES [dbo].[tb_user_info] ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[tb_cbb_notify] ADD FOREIGN KEY ([last_update_user_id]) REFERENCES [dbo].[tb_user_info] ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
/*
Navicat SQL Server Data Transfer

Source Server         : 10.100.6.127
Source Server Version : 105000
Source Host           : 10.100.6.127:1433
Source Database       : cbbV3
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2014-10-14 09:30:58
*/


-- ----------------------------
-- Table structure for [dbo].[tb_cbb_notify_comment]
-- ----------------------------

CREATE TABLE [dbo].[tb_cbb_notify_comment] (
[id] int NOT NULL IDENTITY(1,1) ,
[create_date] datetime NULL ,
[create_user_id] int NULL ,
[comment] varchar(1000) NULL ,
[notify_id] int NULL 
)


GO

-- ----------------------------
-- Records of tb_cbb_notify_comment
-- ----------------------------
SET IDENTITY_INSERT [dbo].[tb_cbb_notify_comment] ON
GO
SET IDENTITY_INSERT [dbo].[tb_cbb_notify_comment] OFF
GO

-- ----------------------------
-- Indexes structure for table tb_cbb_notify_comment
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[tb_cbb_notify_comment]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify_comment] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[tb_cbb_notify_comment]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify_comment] ADD FOREIGN KEY ([notify_id]) REFERENCES [dbo].[tb_cbb_notify] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[tb_cbb_notify_comment] ADD FOREIGN KEY ([create_user_id]) REFERENCES [dbo].[tb_user_info] ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
/*
Navicat SQL Server Data Transfer

Source Server         : 10.100.6.127
Source Server Version : 105000
Source Host           : 10.100.6.127:1433
Source Database       : cbbV3
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2014-10-14 09:31:04
*/


-- ----------------------------
-- Table structure for [dbo].[tb_cbb_notify_view]
-- ----------------------------

CREATE TABLE [dbo].[tb_cbb_notify_view] (
[id] int NOT NULL IDENTITY(1,1) ,
[create_time] datetime NULL ,
[create_user_id] int NULL ,
[notify_id] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[tb_cbb_notify_view]', RESEED, 1686)
GO

-- ----------------------------
-- Records of tb_cbb_notify_view
-- ----------------------------
SET IDENTITY_INSERT [dbo].[tb_cbb_notify_view] ON
GO
SET IDENTITY_INSERT [dbo].[tb_cbb_notify_view] OFF
GO

-- ----------------------------
-- Indexes structure for table tb_cbb_notify_view
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[tb_cbb_notify_view]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify_view] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[tb_cbb_notify_view]
-- ----------------------------
ALTER TABLE [dbo].[tb_cbb_notify_view] ADD FOREIGN KEY ([notify_id]) REFERENCES [dbo].[tb_cbb_notify] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[tb_cbb_notify_view] ADD FOREIGN KEY ([create_user_id]) REFERENCES [dbo].[tb_user_info] ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
