USE [oacbbClean]
GO
/****** Object:  Table [dbo].[hibernate_sequences]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hibernate_sequences](
	[sequence_name] [varchar](255) NULL,
	[sequence_next_hi_value] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_user_info', 35)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_group_user', 30)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_company_info', 1)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_group_info', 13)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_role_module', 16)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_doc_template', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_document_type', 11)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_document_ext', 24)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_gongwen_var', 22)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_form_authority', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_history_doc', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_op_email_box', 38)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_om_affairs_body', 10)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_om_affairs', 10)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_notify_view', 101)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_oc_check', 2824)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_workflow_var', 5)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_op_daily', 33)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_data_priv', 4)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_op_desktop_page', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_op_desktop_module', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_role_info', 4)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_role_user', 23)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_om_attachment', 268)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_form_properties', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_form_property_value', 9)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_oab_address', 1)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_oab_address_group', 1)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_process_attribute', 1)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_questionnaire', 6)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_cbb_form_attribute', 2)
INSERT [dbo].[hibernate_sequences] ([sequence_name], [sequence_next_hi_value]) VALUES (N'tb_om_message', 1)
/****** Object:  Table [dbo].[td_form_category]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[td_form_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](100) NOT NULL,
	[compy_Id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[last_update_time] [datetime] NULL,
	[type] [int] NOT NULL,
	[OrderIndex] [int] NOT NULL,
	[domType] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[td_form_category] ON
INSERT [dbo].[td_form_category] ([category_id], [category_name], [compy_Id], [create_time], [last_update_time], [type], [OrderIndex], [domType]) VALUES (146, N'报表分类1', 1, CAST(0x0000A33F00F44468 AS DateTime), CAST(0x0000A33F00F44468 AS DateTime), 21, 1, NULL)
INSERT [dbo].[td_form_category] ([category_id], [category_name], [compy_Id], [create_time], [last_update_time], [type], [OrderIndex], [domType]) VALUES (147, N'报表分类2', 1, CAST(0x0000A33F00F44468 AS DateTime), CAST(0x0000A33F00F44468 AS DateTime), 21, 2, NULL)
SET IDENTITY_INSERT [dbo].[td_form_category] OFF
/****** Object:  Table [dbo].[tb_workflow_var]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_workflow_var](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[advice] [varchar](255) NULL,
	[attach] [varchar](255) NULL,
	[breforetaskname] [varchar](255) NULL,
	[beforeuser] [varchar](255) NULL,
	[candidate_persons] [varchar](255) NULL,
	[createtime] [datetime] NULL,
	[creater] [varchar](255) NULL,
	[current_state] [varchar](255) NULL,
	[currenttaskname] [varchar](255) NULL,
	[currentuser] [varchar](255) NULL,
	[instanceid] [varchar](255) NULL,
	[processattributeid] [int] NULL,
	[refprocess] [varchar](255) NULL,
	[rollbackinfo] [varchar](255) NULL,
	[sign_comments_map] [varchar](255) NULL,
	[suspendtime] [datetime] NULL,
	[title] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_user_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_user_info](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[alter_name] [varchar](50) NULL,
	[birthday] [date] NULL,
	[company_id] [int] NULL,
	[email] [varchar](50) NULL,
	[is_default] [int] NULL,
	[is_delete] [int] NULL,
	[job] [varchar](50) NULL,
	[last_login_time] [date] NULL,
	[login_name] [varchar](50) NULL,
	[login_pass] [varchar](50) NULL,
	[order_index] [int] NULL,
	[phone] [varchar](50) NULL,
	[phone_public] [int] NULL,
	[py] [varchar](50) NULL,
	[register_time] [date] NULL,
	[sex] [int] NULL,
	[user_name] [varchar](50) NULL,
	[work_no] [varchar](50) NULL,
	[skin_logo] [int] NULL,
	[ntko_url] [varchar](100) NULL,
	[phone2] [varchar](50) NULL,
	[sign_type] [int] NULL,
	[sign_url] [varchar](100) NULL,
	[title] [varchar](50) NULL,
	[user_state] [int] NULL,
	[office_widget] [int] NULL,
	[sign_widget] [int] NULL,
	[tao_da] [int] NULL,
	[dtype] [varchar](50) NULL,
	[group_id] [int] NULL,
	[home_tel] [varchar](255) NULL,
	[mcn_type] [int] NULL,
	[office_tel] [varchar](255) NULL,
	[role] [int] NULL,
	[turn_type] [int] NULL,
	[user_num] [varchar](255) NULL,
	[user_num_type] [int] NULL,
	[user_power] [int] NULL,
	[v_group] [int] NULL,
	[v_num] [varchar](255) NULL,
	[photo] [varchar](300) NULL,
	[sign_name] [varchar](255) NULL,
	[last_update_time] [datetime] NULL,
	[create_time] [datetime] NULL,
	[mobile_show_state] [int] NULL,
 CONSTRAINT [PK__tb_user___B9BE370F5911273F] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_user_info] ON
INSERT [dbo].[tb_user_info] ([user_id], [alter_name], [birthday], [company_id], [email], [is_default], [is_delete], [job], [last_login_time], [login_name], [login_pass], [order_index], [phone], [phone_public], [py], [register_time], [sex], [user_name], [work_no], [skin_logo], [ntko_url], [phone2], [sign_type], [sign_url], [title], [user_state], [office_widget], [sign_widget], [tao_da], [dtype], [group_id], [home_tel], [mcn_type], [office_tel], [role], [turn_type], [user_num], [user_num_type], [user_power], [v_group], [v_num], [photo], [sign_name], [last_update_time], [create_time], [mobile_show_state]) VALUES (1, N'admin', NULL, 1, NULL, 0, 0, NULL, CAST(0xEA380B00 AS Date), N'admin', N'e10adc3949ba59abbe56e057f20f883e', 1, N'15038293026', NULL, N'cjgly', CAST(0xDF380B00 AS Date), 1, N'超级管理员', NULL, 1, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, N'0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, CAST(0x0000A38401158F35 AS DateTime), CAST(0x0000A38400B8EBDB AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[tb_user_info] OFF
/****** Object:  Table [dbo].[tb_task_review]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_task_review](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actionid] [int] NULL,
	[ctime] [datetime] NULL,
	[pid] [int] NULL,
	[review] [varchar](255) NULL,
	[reviewuserid] [int] NULL,
	[reviewusername] [varchar](255) NULL,
	[taskid] [int] NULL,
	[action_id] [int] NULL,
	[c_time] [datetime] NULL,
	[review_user_id] [int] NULL,
	[review_user_name] [varchar](255) NULL,
	[task_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_process_attribute]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_process_attribute](
	[process_attribute_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[company_id] [int] NULL,
	[create_time] [datetime] NULL,
	[dept] [int] NULL,
	[directions] [varchar](255) NULL,
	[form_id] [int] NULL,
	[is_attach] [int] NULL,
	[printcode] [varchar](255) NULL,
	[printtemplatecode] [varchar](255) NULL,
	[process_define_byxml] [varchar](255) NULL,
	[process_define_id] [varchar](255) NULL,
	[process_name_num_length] [int] NULL,
	[process_name] [varchar](50) NULL,
	[process_name_begin_num] [int] NULL,
	[process_name_can_update] [int] NULL,
	[process_name_expr] [varchar](255) NULL,
	[process_order] [int] NULL,
	[process_state] [int] NULL,
	[process_define_byjson] [varchar](255) NULL,
	[redtemplate] [int] NULL,
	[selectusermode] [int] NULL,
	[taoda] [int] NULL,
	[update_time] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[process_attribute_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_platform_parameter]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_platform_parameter](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[instenceid] [int] NULL,
	[is_delete] [int] NULL,
	[module_name] [varchar](200) NULL,
	[par_describe] [varchar](200) NULL,
	[par_items] [varchar](200) NULL,
	[par_value_coll] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_platform_parameter] ON
INSERT [dbo].[tb_platform_parameter] ([id], [instenceid], [is_delete], [module_name], [par_describe], [par_items], [par_value_coll]) VALUES (1, 35, 0, N'通知公告', N'通知公告', N'cn.com.qytx.oa.notify.vo.TbColumnSetting', N'{"isComment":1,"isAuditing":"1","isSeeAttach":1,"showImage":1,"publishUserIds":"","publishUserNames":"","isSmipleText":1}')
INSERT [dbo].[tb_platform_parameter] ([id], [instenceid], [is_delete], [module_name], [par_describe], [par_items], [par_value_coll]) VALUES (4, 8, 0, N'部门专栏', N'部门专栏', N'cn.com.qytx.oa.notify.vo.TbColumnSetting', N'{"isComment":1,"isAuditing":"1","isSeeAttach":0,"showImage":1,"publishUserIds":"","publishUserNames":"","isSmipleText":0}')
SET IDENTITY_INSERT [dbo].[tb_platform_parameter] OFF
/****** Object:  Table [dbo].[tb_op_task]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_task](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[begin_date] [datetime] NULL,
	[begin_time] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [varchar](5000) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[end_date] [datetime] NULL,
	[end_time] [varchar](255) NULL,
	[finish_time] [datetime] NULL,
	[important] [int] NULL,
	[is_delete] [int] NULL,
	[is_remaind] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[rate] [varchar](5000) NULL,
	[subject] [varchar](5000) NULL,
	[task_no] [int] NULL,
	[task_status] [int] NULL,
	[task_type] [int] NULL,
	[total_time] [varchar](50) NULL,
	[use_time] [varchar](50) NULL,
 CONSTRAINT [PK__tb_op_ta__3213E83F5A5A5133] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_op_ta__3213E83E5D36BDDE] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_programme]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_programme](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[alert_time] [int] NULL,
	[beg_time] [datetime] NULL,
	[content] [text] NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[end_time] [datetime] NULL,
	[is_all_day] [int] NULL,
	[is_delete] [int] NULL,
	[is_sys_remaind] [int] NULL,
	[remaind_time] [varchar](255) NULL,
	[remaind_type] [int] NULL,
	[repeat_type] [int] NULL,
	[beg_time_str] [varchar](255) NULL,
	[create_user_name] [varchar](255) NULL,
	[end_time_str] [varchar](255) NULL,
	[is_modify] [int] NULL,
 CONSTRAINT [PK__tb_op_pr__3213E83F363CEC4E] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_op_pr__3213E83E391958F9] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_sys_para]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_om_sys_para](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[para_name] [varchar](255) NULL,
	[para_value] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_om_sys_para] ON
INSERT [dbo].[tb_om_sys_para] ([id], [para_name], [para_value]) VALUES (1, N'affairManage', N',|,')
INSERT [dbo].[tb_om_sys_para] ([id], [para_name], [para_value]) VALUES (4, N'notifySet', N'is_auditing:1@top_days:2@notify_auditing_manager:0@is_canedit:0@range_all_user:1,271,39,@range_exception_user:')
SET IDENTITY_INSERT [dbo].[tb_om_sys_para] OFF
/****** Object:  Table [dbo].[tb_op_desktop_page]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_op_desktop_page](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[is_delete] [int] NULL,
	[order_no] [int] NULL,
	[user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tb_op_desktop_page] ON
INSERT [dbo].[tb_op_desktop_page] ([id], [compy_id], [is_delete], [order_no], [user_id]) VALUES (59, 1, 0, 1, 1)
INSERT [dbo].[tb_op_desktop_page] ([id], [compy_id], [is_delete], [order_no], [user_id]) VALUES (60, 1, 0, 2, 1)
SET IDENTITY_INSERT [dbo].[tb_op_desktop_page] OFF
/****** Object:  Table [dbo].[tb_op_email_box]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_email_box](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[box_name] [varchar](50) NOT NULL,
	[box_size] [numeric](19, 0) NULL,
	[box_type] [int] NOT NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NOT NULL,
	[create_user_id] [int] NOT NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NOT NULL,
	[last_update_user_id] [int] NOT NULL,
	[order_no] [int] NULL,
	[page_max] [int] NOT NULL,
	[user_id] [int] NOT NULL,
 CONSTRAINT [PK__tb_op_em__3213E83F3F3C46A3] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_op_email_box] ON
INSERT [dbo].[tb_op_email_box] ([id], [box_name], [box_size], [box_type], [compy_id], [create_time], [create_user_id], [is_delete], [last_update_time], [last_update_user_id], [order_no], [page_max], [user_id]) VALUES (1, N'收件箱', CAST(0 AS Numeric(19, 0)), 1, 1, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, 15, 1)
INSERT [dbo].[tb_op_email_box] ([id], [box_name], [box_size], [box_type], [compy_id], [create_time], [create_user_id], [is_delete], [last_update_time], [last_update_user_id], [order_no], [page_max], [user_id]) VALUES (2, N'已发送', CAST(0 AS Numeric(19, 0)), 2, 1, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, 15, 1)
INSERT [dbo].[tb_op_email_box] ([id], [box_name], [box_size], [box_type], [compy_id], [create_time], [create_user_id], [is_delete], [last_update_time], [last_update_user_id], [order_no], [page_max], [user_id]) VALUES (3, N'草稿箱', CAST(0 AS Numeric(19, 0)), 3, 1, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, 15, 1)
INSERT [dbo].[tb_op_email_box] ([id], [box_name], [box_size], [box_type], [compy_id], [create_time], [create_user_id], [is_delete], [last_update_time], [last_update_user_id], [order_no], [page_max], [user_id]) VALUES (4, N'垃圾箱', CAST(0 AS Numeric(19, 0)), 4, 1, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, CAST(0x0000A38500B0E417 AS DateTime), 1, 0, 15, 1)
SET IDENTITY_INSERT [dbo].[tb_op_email_box] OFF
/****** Object:  Table [dbo].[JBPM4_HIST_TASK]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_HIST_TASK](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[EXECUTION_] [varchar](255) NULL,
	[OUTCOME_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[STATE_] [varchar](255) NULL,
	[CREATE_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
	[SUPERTASK_] [numeric](19, 0) NULL,
	[MY_SUPER_TASK] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_HIST_PROCINST]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_HIST_PROCINST](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[PROCDEFID_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[START_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[STATE_] [varchar](255) NULL,
	[ENDACTIVITY_] [varchar](255) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_DEPLOYMENT]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_DEPLOYMENT](
	[DBID_] [numeric](19, 0) NOT NULL,
	[NAME_] [varchar](255) NULL,
	[TIMESTAMP_] [numeric](19, 0) NULL,
	[STATE_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[JBPM4_DEPLOYMENT] ([DBID_], [NAME_], [TIMESTAMP_], [STATE_]) VALUES (CAST(1 AS Numeric(19, 0)), NULL, CAST(0 AS Numeric(19, 0)), N'active')
INSERT [dbo].[JBPM4_DEPLOYMENT] ([DBID_], [NAME_], [TIMESTAMP_], [STATE_]) VALUES (CAST(7 AS Numeric(19, 0)), NULL, CAST(0 AS Numeric(19, 0)), N'active')
/****** Object:  Table [dbo].[JBPM4_EXECUTION]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_EXECUTION](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ACTIVITYNAME_] [varchar](255) NULL,
	[PROCDEFID_] [varchar](255) NULL,
	[HASVARS_] [numeric](1, 0) NULL,
	[NAME_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[ID_] [varchar](255) NULL,
	[STATE_] [varchar](255) NULL,
	[SUSPHISTSTATE_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[HISACTINST_] [numeric](19, 0) NULL,
	[PARENT_] [numeric](19, 0) NULL,
	[INSTANCE_] [numeric](19, 0) NULL,
	[SUPEREXEC_] [numeric](19, 0) NULL,
	[SUBPROCINST_] [numeric](19, 0) NULL,
	[PARENT_IDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_PROPERTY]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_PROPERTY](
	[KEY_] [varchar](255) NOT NULL,
	[VERSION_] [numeric](10, 0) NOT NULL,
	[VALUE_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[KEY_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[JBPM4_PROPERTY] ([KEY_], [VERSION_], [VALUE_]) VALUES (N'next.dbid', CAST(1 AS Numeric(10, 0)), N'10001')
/****** Object:  Table [dbo].[JBPM4_ID_GROUP]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_ID_GROUP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[NAME_] [varchar](255) NULL,
	[TYPE_] [varchar](255) NULL,
	[PARENT_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_ID_USER]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_ID_USER](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[PASSWORD_] [varchar](255) NULL,
	[GIVENNAME_] [varchar](255) NULL,
	[FAMILYNAME_] [varchar](255) NULL,
	[BUSINESSEMAIL_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_jpush_user]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_jpush_user](
	[send_no] [int] IDENTITY(1,1) NOT NULL,
	[user_name] [varchar](255) NULL,
	[push_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[user_type] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[send_no] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_jpush]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_jpush](
	[push_id] [int] IDENTITY(1,1) NOT NULL,
	[insert_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[push_content] [varchar](5000) NULL,
	[push_time] [datetime] NULL,
	[show_content] [varchar](5000) NULL,
	[subject] [varchar](200) NOT NULL,
	[user_id] [int] NULL,
	[user_name] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[push_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_form_property_value]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_form_property_value](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[bean_id] [varchar](50) NULL,
	[bean_property_id] [int] NOT NULL,
	[bean_value] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK__tb_cbb_f__3213E83F6FAA73D4] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_form_properties]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_form_properties](
	[property_id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[form_id] [int] NULL,
	[html_type] [varchar](50) NULL,
	[update_time] [datetime] NULL,
	[property_name] [varchar](100) NULL,
	[property_name_ch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[property_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_form_category]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_form_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](100) NOT NULL,
	[compy_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[domtype] [int] NULL,
	[last_update_time] [datetime] NULL,
	[orderindex] [int] NULL,
	[type] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_form_authority]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_form_authority](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[candidate] [varchar](500) NULL,
	[formid] [int] NULL,
	[form_property_id] [int] NULL,
	[groups] [varchar](500) NULL,
	[propertyname] [varchar](50) NULL,
	[roles] [varchar](500) NULL,
 CONSTRAINT [PK__tb_cbb_f__3213E83F326C5B6A] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_form_attribute]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_form_attribute](
	[form_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[company_id] [int] NULL,
	[create_time] [datetime] NULL,
	[dept_id] [varchar](100) NULL,
	[form_name] [varchar](100) NULL,
	[form_source] [text] NULL,
	[is_new_version] [int] NULL,
	[update_time] [datetime] NULL,
	[version] [varchar](100) NULL,
 CONSTRAINT [PK__tb_cbb_f__190E16C96809520C] PRIMARY KEY CLUSTERED 
(
	[form_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_email_alarm]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_email_alarm](
	[Vid] [int] IDENTITY(1,1) NOT NULL,
	[Host] [varchar](50) NOT NULL,
	[Port] [int] NOT NULL,
	[Email] [varchar](500) NOT NULL,
	[PassWord] [varchar](150) NOT NULL,
	[UserName] [varchar](50) NOT NULL,
	[InsertTime] [datetime] NOT NULL,
	[Phone] [varchar](20) NOT NULL,
 CONSTRAINT [PK_tb_cbb_email_alarm] PRIMARY KEY CLUSTERED 
(
	[Vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_dict]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_dict](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_date] [datetime] NULL,
	[info_type] [varchar](100) NULL,
	[is_delete] [int] NULL,
	[modify_date] [datetime] NULL,
	[name] [varchar](50) NOT NULL,
	[record_user_id] [int] NULL,
	[sys_tag] [int] NULL,
	[value] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_cbb_dict] ON
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (1, CAST(0x0000A35800BBDB38 AS DateTime), N'notifyType35', 0, CAST(0x0000A35800BBDB38 AS DateTime), N'通知公告', 4, -1, 0)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (33, CAST(0x0000A35A0126C7E2 AS DateTime), N'notifyType35', 0, CAST(0x0000A37800BD97F5 AS DateTime), N'内网公告', 1, 1, 33)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (35, CAST(0x0000A35A0126D354 AS DateTime), N'notifyType35', 0, CAST(0x0000A35A0126D354 AS DateTime), N'外网公告', 1, 1, 35)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (36, CAST(0x0000A35A0126E26E AS DateTime), N'notifyType35', 0, CAST(0x0000A37800BE2982 AS DateTime), N'系部公告', 1, 1, 36)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (42, NULL, N'dom_category', 0, NULL, N'公文一级分类', 1, -1, 0)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (43, CAST(0x0000A35E00BCBF60 AS DateTime), N'dom_category', 0, CAST(0x0000A35E00BCBF60 AS DateTime), N'收文类型', 1, 1, 1)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (44, CAST(0x0000A35E00BCCA6B AS DateTime), N'dom_category', 0, CAST(0x0000A35E00BCCA6B AS DateTime), N'发文类型', 1, 1, 2)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (46, CAST(0x0000A36100A2673B AS DateTime), N'huanji', 0, CAST(0x0000A36100A2673B AS DateTime), N'缓急', 1, -1, 1)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (50, CAST(0x0000A36100A3E56E AS DateTime), N'huanji', 0, CAST(0x0000A36100A3E56E AS DateTime), N'紧急', 1, 1, 1)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (51, CAST(0x0000A36100A3ED6F AS DateTime), N'huanji', 0, CAST(0x0000A36700E9DAD9 AS DateTime), N'一般', 1, 1, 2)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (56, CAST(0x0000A36700E14291 AS DateTime), N'miji', 0, CAST(0x0000A36700E14291 AS DateTime), N'密级', 11612407, -1, 0)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (57, CAST(0x0000A36700E14F7B AS DateTime), N'miji', 0, CAST(0x0000A36700E14F7B AS DateTime), N'一般', 11612407, 1, 1)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (58, CAST(0x0000A36700E15BA4 AS DateTime), N'miji', 0, CAST(0x0000A36700E15BA4 AS DateTime), N'保密', 11612407, 1, 2)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (59, CAST(0x0000A36A0103BC56 AS DateTime), N'zhongyaodu', 0, CAST(0x0000A36A0108782F AS DateTime), N'重要度', 1, -1, 10)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (60, CAST(0x0000A36A0103D34D AS DateTime), N'zhongyaodu', 0, CAST(0x0000A36A0103D34D AS DateTime), N'一般', 1, 1, 1)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (61, CAST(0x0000A36A0103DDC8 AS DateTime), N'zhongyaodu', 0, CAST(0x0000A36A0103DDC8 AS DateTime), N'重要', 1, 1, 2)
INSERT [dbo].[tb_cbb_dict] ([id], [create_date], [info_type], [is_delete], [modify_date], [name], [record_user_id], [sys_tag], [value]) VALUES (128, CAST(0x0000A37F00BA3C66 AS DateTime), N'dom_category', 0, CAST(0x0000A37F00BA3C66 AS DateTime), N'国资委公文', 1, 1, 3)
SET IDENTITY_INSERT [dbo].[tb_cbb_dict] OFF
/****** Object:  Table [dbo].[tb_cbb_data_priv]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_data_priv](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[group_ids] [varchar](5000) NULL,
	[group_names] [varchar](5000) NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[module_name] [varchar](200) NULL,
	[ref_id] [int] NULL,
	[ref_name] [varchar](50) NULL,
	[role_ids] [varchar](5000) NULL,
	[role_names] [varchar](5000) NULL,
	[sub_module_name] [varchar](100) NULL,
	[user_ids] [text] NULL,
	[user_names] [text] NULL,
 CONSTRAINT [PK__tb_cbb_d__3213E83F48FABB07] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_affairs_body]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_affairs_body](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[content_info] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[from_id] [int] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[remind_url] [varchar](255) NULL,
	[send_time] [datetime] NULL,
	[sms_type] [varchar](200) NULL,
 CONSTRAINT [PK__tb_cbb_a__3213E83F1E105D02] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_affairs]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_cbb_affairs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[body_id] [int] NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[remind_flag] [int] NULL,
	[remind_time] [datetime] NULL,
	[to_id] [int] NULL,
 CONSTRAINT [PK__tb_cbb_a__3213E83F1A3FCC1E] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_attendance_ipset]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_attendance_ipset](
	[ipset_id] [int] IDENTITY(1,1) NOT NULL,
	[begin_ip] [varchar](100) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[end_ip] [varchar](100) NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ipset_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ipset_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_attendance_days]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_attendance_days](
	[att_day_id] [int] IDENTITY(1,1) NOT NULL,
	[day] [datetime] NULL,
	[week] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[att_day_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[att_day_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_attendance_days] ON
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (1, CAST(0x0000A35B00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (2, CAST(0x0000A35C00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (3, CAST(0x0000A35D00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (4, CAST(0x0000A35E00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (5, CAST(0x0000A35F00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (6, CAST(0x0000A36000C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (7, CAST(0x0000A36100C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (8, CAST(0x0000A36200C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (9, CAST(0x0000A36300C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (10, CAST(0x0000A36400C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (11, CAST(0x0000A36500C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (12, CAST(0x0000A36600C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (13, CAST(0x0000A36700C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (14, CAST(0x0000A36800C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (15, CAST(0x0000A36900C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (16, CAST(0x0000A36A00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (17, CAST(0x0000A36B00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (18, CAST(0x0000A36C00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (19, CAST(0x0000A36D00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (20, CAST(0x0000A36E00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (21, CAST(0x0000A36F00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (22, CAST(0x0000A37000C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (23, CAST(0x0000A37100C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (24, CAST(0x0000A37200C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (25, CAST(0x0000A37300C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (26, CAST(0x0000A37400C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (27, CAST(0x0000A37500C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (28, CAST(0x0000A37600C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (29, CAST(0x0000A37700C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (30, CAST(0x0000A37800C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (31, CAST(0x0000A37900C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (32, CAST(0x0000A37A00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (33, CAST(0x0000A37B00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (34, CAST(0x0000A37C00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (35, CAST(0x0000A37D00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (36, CAST(0x0000A37E00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (37, CAST(0x0000A37F00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (38, CAST(0x0000A38000C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (39, CAST(0x0000A38100C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (40, CAST(0x0000A38200C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (41, CAST(0x0000A38300C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (42, CAST(0x0000A38400C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (43, CAST(0x0000A38500C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (44, CAST(0x0000A38600C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (45, CAST(0x0000A38700C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (46, CAST(0x0000A38800C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (47, CAST(0x0000A38900C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (48, CAST(0x0000A38A00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (49, CAST(0x0000A38B00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (50, CAST(0x0000A38C00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (51, CAST(0x0000A38D00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (52, CAST(0x0000A38E00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (53, CAST(0x0000A38F00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (54, CAST(0x0000A39000C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (55, CAST(0x0000A39100C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (56, CAST(0x0000A39200C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (57, CAST(0x0000A39300C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (58, CAST(0x0000A39400C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (59, CAST(0x0000A39500C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (60, CAST(0x0000A39600C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (61, CAST(0x0000A39700C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (62, CAST(0x0000A39800C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (63, CAST(0x0000A39900C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (64, CAST(0x0000A39A00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (65, CAST(0x0000A39B00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (66, CAST(0x0000A39C00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (67, CAST(0x0000A39D00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (68, CAST(0x0000A39E00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (69, CAST(0x0000A39F00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (70, CAST(0x0000A3A000C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (71, CAST(0x0000A3A100C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (72, CAST(0x0000A3A200C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (73, CAST(0x0000A3A300C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (74, CAST(0x0000A3A400C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (75, CAST(0x0000A3A500C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (76, CAST(0x0000A3A600C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (77, CAST(0x0000A3A700C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (78, CAST(0x0000A3A800C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (79, CAST(0x0000A3A900C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (80, CAST(0x0000A3AA00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (81, CAST(0x0000A3AB00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (82, CAST(0x0000A3AC00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (83, CAST(0x0000A3AD00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (84, CAST(0x0000A3AE00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (85, CAST(0x0000A3AF00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (86, CAST(0x0000A3B000C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (87, CAST(0x0000A3B100C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (88, CAST(0x0000A3B200C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (89, CAST(0x0000A3B300C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (90, CAST(0x0000A3B400C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (91, CAST(0x0000A3B500C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (92, CAST(0x0000A3B600C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (93, CAST(0x0000A3B700C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (94, CAST(0x0000A3B800C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (95, CAST(0x0000A3B900C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (96, CAST(0x0000A3BA00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (97, CAST(0x0000A3BB00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (98, CAST(0x0000A3BC00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (99, CAST(0x0000A3BD00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (100, CAST(0x0000A3BE00C5C100 AS DateTime), N'3')
GO
print 'Processed 100 total records'
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (101, CAST(0x0000A3BF00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (102, CAST(0x0000A3C000C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (103, CAST(0x0000A3C100C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (104, CAST(0x0000A3C200C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (105, CAST(0x0000A3C300C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (106, CAST(0x0000A3C400C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (107, CAST(0x0000A3C500C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (108, CAST(0x0000A3C600C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (109, CAST(0x0000A3C700C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (110, CAST(0x0000A3C800C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (111, CAST(0x0000A3C900C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (112, CAST(0x0000A3CA00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (113, CAST(0x0000A3CB00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (114, CAST(0x0000A3CC00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (115, CAST(0x0000A3CD00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (116, CAST(0x0000A3CE00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (117, CAST(0x0000A3CF00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (118, CAST(0x0000A3D000C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (119, CAST(0x0000A3D100C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (120, CAST(0x0000A3D200C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (121, CAST(0x0000A3D300C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (122, CAST(0x0000A3D400C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (123, CAST(0x0000A3D500C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (124, CAST(0x0000A3D600C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (125, CAST(0x0000A3D700C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (126, CAST(0x0000A3D800C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (127, CAST(0x0000A3D900C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (128, CAST(0x0000A3DA00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (129, CAST(0x0000A3DB00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (130, CAST(0x0000A3DC00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (131, CAST(0x0000A3DD00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (132, CAST(0x0000A3DE00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (133, CAST(0x0000A3DF00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (134, CAST(0x0000A3E000C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (135, CAST(0x0000A3E100C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (136, CAST(0x0000A3E200C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (137, CAST(0x0000A3E300C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (138, CAST(0x0000A3E400C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (139, CAST(0x0000A3E500C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (140, CAST(0x0000A3E600C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (141, CAST(0x0000A3E700C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (142, CAST(0x0000A3E800C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (143, CAST(0x0000A3E900C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (144, CAST(0x0000A3EA00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (145, CAST(0x0000A3EB00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (146, CAST(0x0000A3EC00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (147, CAST(0x0000A3ED00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (148, CAST(0x0000A3EE00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (149, CAST(0x0000A3EF00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (150, CAST(0x0000A3F000C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (151, CAST(0x0000A3F100C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (152, CAST(0x0000A3F200C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (153, CAST(0x0000A3F300C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (154, CAST(0x0000A3F400C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (155, CAST(0x0000A3F500C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (156, CAST(0x0000A3F600C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (157, CAST(0x0000A3F700C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (158, CAST(0x0000A3F800C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (159, CAST(0x0000A3F900C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (160, CAST(0x0000A3FA00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (161, CAST(0x0000A3FB00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (162, CAST(0x0000A3FC00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (163, CAST(0x0000A3FD00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (164, CAST(0x0000A3FE00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (165, CAST(0x0000A3FF00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (166, CAST(0x0000A40000C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (167, CAST(0x0000A40100C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (168, CAST(0x0000A40200C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (169, CAST(0x0000A40300C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (170, CAST(0x0000A40400C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (171, CAST(0x0000A40500C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (172, CAST(0x0000A40600C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (173, CAST(0x0000A40700C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (174, CAST(0x0000A40800C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (175, CAST(0x0000A40900C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (176, CAST(0x0000A40A00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (177, CAST(0x0000A40B00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (178, CAST(0x0000A40C00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (179, CAST(0x0000A40D00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (180, CAST(0x0000A40E00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (181, CAST(0x0000A40F00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (182, CAST(0x0000A41000C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (183, CAST(0x0000A41100C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (184, CAST(0x0000A41200C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (185, CAST(0x0000A41300C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (186, CAST(0x0000A41400C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (187, CAST(0x0000A41500C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (188, CAST(0x0000A41600C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (189, CAST(0x0000A41700C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (190, CAST(0x0000A41800C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (191, CAST(0x0000A41900C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (192, CAST(0x0000A41A00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (193, CAST(0x0000A41B00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (194, CAST(0x0000A41C00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (195, CAST(0x0000A41D00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (196, CAST(0x0000A41E00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (197, CAST(0x0000A41F00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (198, CAST(0x0000A42000C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (199, CAST(0x0000A42100C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (200, CAST(0x0000A42200C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (201, CAST(0x0000A42300C5C100 AS DateTime), N'6')
GO
print 'Processed 200 total records'
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (202, CAST(0x0000A42400C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (203, CAST(0x0000A42500C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (204, CAST(0x0000A42600C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (205, CAST(0x0000A42700C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (206, CAST(0x0000A42800C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (207, CAST(0x0000A42900C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (208, CAST(0x0000A42A00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (209, CAST(0x0000A42B00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (210, CAST(0x0000A42C00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (211, CAST(0x0000A42D00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (212, CAST(0x0000A42E00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (213, CAST(0x0000A42F00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (214, CAST(0x0000A43000C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (215, CAST(0x0000A43100C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (216, CAST(0x0000A43200C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (217, CAST(0x0000A43300C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (218, CAST(0x0000A43400C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (219, CAST(0x0000A43500C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (220, CAST(0x0000A43600C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (221, CAST(0x0000A43700C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (222, CAST(0x0000A43800C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (223, CAST(0x0000A43900C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (224, CAST(0x0000A43A00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (225, CAST(0x0000A43B00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (226, CAST(0x0000A43C00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (227, CAST(0x0000A43D00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (228, CAST(0x0000A43E00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (229, CAST(0x0000A43F00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (230, CAST(0x0000A44000C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (231, CAST(0x0000A44100C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (232, CAST(0x0000A44200C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (233, CAST(0x0000A44300C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (234, CAST(0x0000A44400C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (235, CAST(0x0000A44500C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (236, CAST(0x0000A44600C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (237, CAST(0x0000A44700C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (238, CAST(0x0000A44800C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (239, CAST(0x0000A44900C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (240, CAST(0x0000A44A00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (241, CAST(0x0000A44B00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (242, CAST(0x0000A44C00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (243, CAST(0x0000A44D00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (244, CAST(0x0000A44E00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (245, CAST(0x0000A44F00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (246, CAST(0x0000A45000C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (247, CAST(0x0000A45100C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (248, CAST(0x0000A45200C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (249, CAST(0x0000A45300C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (250, CAST(0x0000A45400C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (251, CAST(0x0000A45500C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (252, CAST(0x0000A45600C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (253, CAST(0x0000A45700C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (254, CAST(0x0000A45800C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (255, CAST(0x0000A45900C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (256, CAST(0x0000A45A00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (257, CAST(0x0000A45B00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (258, CAST(0x0000A45C00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (259, CAST(0x0000A45D00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (260, CAST(0x0000A45E00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (261, CAST(0x0000A45F00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (262, CAST(0x0000A46000C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (263, CAST(0x0000A46100C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (264, CAST(0x0000A46200C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (265, CAST(0x0000A46300C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (266, CAST(0x0000A46400C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (267, CAST(0x0000A46500C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (268, CAST(0x0000A46600C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (269, CAST(0x0000A46700C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (270, CAST(0x0000A46800C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (271, CAST(0x0000A46900C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (272, CAST(0x0000A46A00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (273, CAST(0x0000A46B00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (274, CAST(0x0000A46C00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (275, CAST(0x0000A46D00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (276, CAST(0x0000A46E00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (277, CAST(0x0000A46F00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (278, CAST(0x0000A47000C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (279, CAST(0x0000A47100C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (280, CAST(0x0000A47200C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (281, CAST(0x0000A47300C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (282, CAST(0x0000A47400C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (283, CAST(0x0000A47500C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (284, CAST(0x0000A47600C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (285, CAST(0x0000A47700C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (286, CAST(0x0000A47800C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (287, CAST(0x0000A47900C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (288, CAST(0x0000A47A00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (289, CAST(0x0000A47B00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (290, CAST(0x0000A47C00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (291, CAST(0x0000A47D00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (292, CAST(0x0000A47E00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (293, CAST(0x0000A47F00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (294, CAST(0x0000A48000C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (295, CAST(0x0000A48100C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (296, CAST(0x0000A48200C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (297, CAST(0x0000A48300C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (298, CAST(0x0000A48400C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (299, CAST(0x0000A48500C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (300, CAST(0x0000A48600C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (301, CAST(0x0000A48700C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (302, CAST(0x0000A48800C5C100 AS DateTime), N'2')
GO
print 'Processed 300 total records'
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (303, CAST(0x0000A48900C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (304, CAST(0x0000A48A00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (305, CAST(0x0000A48B00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (306, CAST(0x0000A48C00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (307, CAST(0x0000A48D00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (308, CAST(0x0000A48E00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (309, CAST(0x0000A48F00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (310, CAST(0x0000A49000C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (311, CAST(0x0000A49100C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (312, CAST(0x0000A49200C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (313, CAST(0x0000A49300C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (314, CAST(0x0000A49400C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (315, CAST(0x0000A49500C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (316, CAST(0x0000A49600C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (317, CAST(0x0000A49700C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (318, CAST(0x0000A49800C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (319, CAST(0x0000A49900C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (320, CAST(0x0000A49A00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (321, CAST(0x0000A49B00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (322, CAST(0x0000A49C00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (323, CAST(0x0000A49D00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (324, CAST(0x0000A49E00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (325, CAST(0x0000A49F00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (326, CAST(0x0000A4A000C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (327, CAST(0x0000A4A100C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (328, CAST(0x0000A4A200C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (329, CAST(0x0000A4A300C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (330, CAST(0x0000A4A400C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (331, CAST(0x0000A4A500C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (332, CAST(0x0000A4A600C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (333, CAST(0x0000A4A700C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (334, CAST(0x0000A4A800C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (335, CAST(0x0000A4A900C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (336, CAST(0x0000A4AA00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (337, CAST(0x0000A4AB00C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (338, CAST(0x0000A4AC00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (339, CAST(0x0000A4AD00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (340, CAST(0x0000A4AE00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (341, CAST(0x0000A4AF00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (342, CAST(0x0000A4B000C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (343, CAST(0x0000A4B100C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (344, CAST(0x0000A4B200C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (345, CAST(0x0000A4B300C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (346, CAST(0x0000A4B400C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (347, CAST(0x0000A4B500C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (348, CAST(0x0000A4B600C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (349, CAST(0x0000A4B700C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (350, CAST(0x0000A4B800C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (351, CAST(0x0000A4B900C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (352, CAST(0x0000A4BA00C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (353, CAST(0x0000A4BB00C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (354, CAST(0x0000A4BC00C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (355, CAST(0x0000A4BD00C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (356, CAST(0x0000A4BE00C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (357, CAST(0x0000A4BF00C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (358, CAST(0x0000A4C000C5C100 AS DateTime), N'2')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (359, CAST(0x0000A4C100C5C100 AS DateTime), N'3')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (360, CAST(0x0000A4C200C5C100 AS DateTime), N'4')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (361, CAST(0x0000A4C300C5C100 AS DateTime), N'5')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (362, CAST(0x0000A4C400C5C100 AS DateTime), N'6')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (363, CAST(0x0000A4C500C5C100 AS DateTime), N'7')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (364, CAST(0x0000A4C600C5C100 AS DateTime), N'1')
INSERT [dbo].[tb_attendance_days] ([att_day_id], [day], [week]) VALUES (365, CAST(0x0000A4C700C5C100 AS DateTime), N'2')
SET IDENTITY_INSERT [dbo].[tb_attendance_days] OFF
/****** Object:  Table [dbo].[tb_attendance]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_attendance](
	[att_id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[position] [varchar](255) NULL,
	[longitude] [varchar](50) NULL,
	[latitude] [varchar](50) NULL,
	[att_Source] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[att_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[att_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[sp_SendSMS_Host]    Script Date: 08/27/2014 19:30:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<黄普友>
-- Create date: <2012-5-6>
-- Description:	<发送短信给网关>
-- =============================================
create PROCEDURE [dbo].[sp_SendSMS_Host] 
	@SourceAddr varchar(50),
	@Phone varchar(20),
	@Content varchar(200)
as
    --启用Ad Hoc Distributed Queries：
    exec sp_configure 'show advanced options',1
	reconfigure
	exec sp_configure 'Ad Hoc Distributed Queries',1
	reconfigure
	
	exec OPENDATASOURCE('SQLOLEDB','Data Source=10.100.2.101;User ID=sa;Password=82125').seatOffice.dbo.[sp_SendMTSMSFrame12581HighPriority]  @SourceAddr,@Phone,@Content
    
    -- 使用完成后，关闭Ad Hoc Distributed Queries：
	exec sp_configure 'Ad Hoc Distributed Queries',0
	reconfigure
	exec sp_configure 'show advanced options',0
	reconfigure
GO
/****** Object:  StoredProcedure [dbo].[sp_SendSms]    Script Date: 08/27/2014 19:30:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<黄普友>
-- Create date: <2013-3-29>
-- Description:	<发送短信>
-- =============================================
CREATE PROCEDURE [dbo].[sp_SendSms]
	@Phone varchar(20),
	@Content varchar(500),
	@SendTime datetime,
	@SrcId varchar(50),
	@SystemName varchar(50),
	@ModuleName varchar(50),
	@SystemId varchar(50),
	@MsgFmt int,
	@RegisteredDelivery int,
	@Ret varchar(20) output
AS
	set @Ret='0'
	declare @result int ---发送结果


--启用Ad Hoc Distributed Queries：
    exec sp_configure 'show advanced options',1
	reconfigure
	exec sp_configure 'Ad Hoc Distributed Queries',1
	reconfigure
	
	exec OPENDATASOURCE('SQLOLEDB','Data Source=10.100.6.127;User ID=sa;Password=82125').cbbsms.dbo.sp_SendSMS @Phone,@Content,@SendTime,@SrcId,@SystemName,@ModuleName,@SystemId,@MsgFmt,
	@RegisteredDelivery,@result output
	if @result>0
		set @Ret=CAST(@result as varchar)
    
    -- 使用完成后，关闭Ad Hoc Distributed Queries：
	exec sp_configure 'Ad Hoc Distributed Queries',0
	reconfigure
	exec sp_configure 'show advanced options',0
	reconfigure
GO
/****** Object:  StoredProcedure [dbo].[sp_DeleteSMS]    Script Date: 08/27/2014 19:30:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<黄普友>
-- Create date: <2013-3-29>
-- Description:	<删除待发送的短信>
-- =============================================
CREATE PROCEDURE [dbo].[sp_DeleteSMS]
	@SystemName varchar(50),
	@ModuleName varchar(50),
	@SystemId varchar(50),
	@Phone varchar(20),
	@Ret int output
AS
	
 --启用Ad Hoc Distributed Queries：
    exec sp_configure 'show advanced options',1
	reconfigure
	exec sp_configure 'Ad Hoc Distributed Queries',1
	reconfigure
	
	exec OPENDATASOURCE('SQLOLEDB','Data Source=10.100.6.127;User ID=sa;Password=82125').cbbsms.dbo.sp_DeleteSMS @SystemName,@ModuleName,@SystemId,
	@Phone,@Ret output
    
    -- 使用完成后，关闭Ad Hoc Distributed Queries：
	exec sp_configure 'Ad Hoc Distributed Queries',0
	reconfigure
	exec sp_configure 'show advanced options',0
	reconfigure
GO
/****** Object:  Table [dbo].[schema_version]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[schema_version](
	[version_rank] [int] NOT NULL,
	[installed_rank] [int] NOT NULL,
	[version] [nvarchar](50) NOT NULL,
	[description] [nvarchar](200) NULL,
	[type] [nvarchar](20) NOT NULL,
	[script] [nvarchar](1000) NOT NULL,
	[checksum] [int] NULL,
	[installed_by] [nvarchar](100) NOT NULL,
	[installed_on] [datetime] NOT NULL,
	[execution_time] [int] NOT NULL,
	[success] [bit] NOT NULL,
 CONSTRAINT [schema_version_pk] PRIMARY KEY CLUSTERED 
(
	[version] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[schema_version] ([version_rank], [installed_rank], [version], [description], [type], [script], [checksum], [installed_by], [installed_on], [execution_time], [success]) VALUES (1, 1, N'1', N'<< Flyway Init >>', N'INIT', N'<< Flyway Init >>', NULL, N'sa', CAST(0x0000A353010F165E AS DateTime), 0, 1)
/****** Object:  Table [dbo].[log]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[log](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[action] [varchar](64) NULL,
	[company_id] [int] NULL,
	[insert_time] [datetime] NULL,
	[ip] [varchar](255) NULL,
	[ip_address] [varchar](255) NULL,
	[is_delete] [int] NULL,
	[log_content] [varchar](255) NULL,
	[log_type] [int] NULL,
	[module_name] [varchar](255) NULL,
	[ref_id] [int] NULL,
	[remark] [varchar](255) NULL,
	[sys_name] [varchar](255) NULL,
	[type] [int] NULL,
	[user_id] [int] NULL,
	[user_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[log] ON
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3255, NULL, 1, CAST(0x0000A38B00E056C9 AS DateTime), N'0:0:0:0:0:0:0:1', NULL, 0, NULL, 1, N'user', 1, N'登录成功', N'xyoa3.0', 0, 1, N'超级管理员')
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3256, NULL, 1, CAST(0x0000A38B00E24294 AS DateTime), N'10.200.10.165', NULL, 0, NULL, 1, N'user', 1, N'登录成功', N'xyoa3.0', 0, 1, N'超级管理员')
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3257, NULL, 1, CAST(0x0000A38C008C3D7B AS DateTime), N'127.0.0.1', NULL, 0, NULL, 2, NULL, 1, N'密码错误', NULL, 0, 1, N'超级管理员')
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3258, NULL, 1, CAST(0x0000A38C008C4578 AS DateTime), N'127.0.0.1', NULL, 0, NULL, 1, N'user', 1, N'登录成功', N'xyoa3.0', 0, 1, N'超级管理员')
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3259, NULL, 1, CAST(0x0000A38F008F5613 AS DateTime), N'0:0:0:0:0:0:0:1', NULL, 0, NULL, 2, NULL, 1, N'密码错误', NULL, 0, 1, N'超级管理员')
INSERT [dbo].[log] ([vid], [action], [company_id], [insert_time], [ip], [ip_address], [is_delete], [log_content], [log_type], [module_name], [ref_id], [remark], [sys_name], [type], [user_id], [user_name]) VALUES (3260, NULL, 1, CAST(0x0000A38F008F5F3A AS DateTime), N'0:0:0:0:0:0:0:1', NULL, 0, NULL, 1, N'user', 1, N'登录成功', N'xyoa3.0', 0, 1, N'超级管理员')
SET IDENTITY_INSERT [dbo].[log] OFF
/****** Object:  Table [dbo].[tb_cbb_news_material_icons]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_material_icons](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[author] [varchar](25) NULL,
	[content] [text] NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[deltetid] [int] NULL,
	[distribution] [text] NULL,
	[distribution_name] [text] NULL,
	[iconurl] [varchar](100) NULL,
	[issue_datetime] [datetime] NULL,
	[materialid] [int] NOT NULL,
	[show_flag] [varchar](255) NULL,
	[status_flag] [varchar](1) NULL,
	[title] [varchar](65) NULL,
	[updated_datetime] [datetime] NULL,
	[userid] [int] NULL,
	[user_name] [varchar](50) NULL,
 CONSTRAINT [PK__tb_cbb_n__DDB00C7D47BC7E0B] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_cbb_n__DDB00C7C4A98EAB6] UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_material]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_material](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[author] [varchar](25) NULL,
	[content] [text] NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[deleteid] [int] NULL,
	[distribution] [text] NULL,
	[distribution_name] [text] NULL,
	[issue_datetime] [datetime] NULL,
	[issuer] [text] NULL,
	[issuer_name] [text] NULL,
	[show_flag] [varchar](1) NULL,
	[status_flag] [varchar](1) NULL,
	[title] [varchar](65) NULL,
	[title_icon] [varchar](100) NULL,
	[updated_datetime] [datetime] NULL,
	[userid] [int] NULL,
	[user_name] [varchar](50) NULL,
 CONSTRAINT [PK__tb_cbb_n__DDB00C7D410F807C] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_cbb_n__DDB00C7C43EBED27] UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_issue_range]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_issue_range](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[deleteid] [int] NULL,
	[issues_id] [int] NOT NULL,
	[read_flag] [varchar](1) NULL,
	[updated_datetime] [datetime] NULL,
	[user_comp] [int] NULL,
	[userid] [int] NOT NULL,
	[user_phone] [varchar](12) NULL,
	[user_unit] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_issue]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_issue](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[approve] [varchar](1) NULL,
	[approver] [varchar](20) NULL,
	[approverid] [int] NULL,
	[category] [int] NULL,
	[columnid] [int] NULL,
	[comments] [varchar](1000) NULL,
	[content] [text] NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[deleteid] [int] NULL,
	[distribution] [text] NULL,
	[distribution_name] [text] NULL,
	[issuer] [text] NULL,
	[issuerid] [text] NULL,
	[materialid] [int] NULL,
	[rangetype] [int] NULL,
	[status_flag] [varchar](1) NULL,
	[title] [varchar](100) NULL,
	[title_icon] [varchar](100) NULL,
	[updated_datetime] [datetime] NULL,
	[userid] [int] NULL,
	[user_name] [varchar](50) NULL,
 CONSTRAINT [PK__tb_cbb_n__DDB00C7D33B5855E] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_cbb_n__DDB00C7C3691F209] UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_column_summary]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_column_summary](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[category] [int] NULL,
	[category_name] [varchar](25) NULL,
	[columnid] [int] NULL,
	[created_datetime] [datetime] NULL,
	[issuer_count] [int] NULL,
	[title] [varchar](25) NULL,
	[title_icon] [varchar](100) NULL,
	[updated_datetime] [datetime] NULL,
	[userid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_column_range]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_column_range](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[column_id] [int] NOT NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[delete_id] [int] NULL,
	[read_flag] [varchar](1) NULL,
	[updated_datetime] [datetime] NULL,
	[user_comp] [int] NULL,
	[userid] [int] NOT NULL,
	[user_phone] [varchar](12) NULL,
	[user_unit] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_news_column]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_news_column](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[approve] [varchar](1) NULL,
	[approver] [varchar](20) NULL,
	[approverid] [int] NULL,
	[category] [int] NULL,
	[created_datetime] [datetime] NULL,
	[delete_datetime] [datetime] NULL,
	[deleteid] [int] NULL,
	[distribution] [text] NULL,
	[distribution_name] [text] NULL,
	[issuer] [text] NULL,
	[issuer_name] [text] NULL,
	[order_index] [int] NULL,
	[range_type] [int] NULL,
	[title] [varchar](25) NULL,
	[title_icon] [varchar](100) NULL,
	[unit_type] [int] NULL,
	[updated_datetime] [datetime] NULL,
	[userid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_my_wait_process]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_my_wait_process](
	[my_started_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](50) NOT NULL,
	[title] [varchar](50) NOT NULL,
	[instance_id] [varchar](50) NOT NULL,
	[processer_id] [int] NOT NULL,
	[processer_name] [varchar](50) NOT NULL,
	[start_time] [datetime] NOT NULL,
	[module_code] [varchar](50) NOT NULL,
	[creater_name] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[my_started_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_my_started]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_my_started](
	[my_started_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](50) NOT NULL,
	[title] [varchar](50) NOT NULL,
	[state] [varchar](50) NULL,
	[instance_id] [varchar](50) NOT NULL,
	[creater_id] [int] NOT NULL,
	[creater_name] [varchar](50) NOT NULL,
	[creater_time] [datetime] NOT NULL,
	[module_code] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[my_started_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_my_processed]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_my_processed](
	[my_started_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](50) NOT NULL,
	[title] [varchar](50) NOT NULL,
	[instance_id] [varchar](50) NOT NULL,
	[processer_id] [int] NOT NULL,
	[processer_name] [varchar](50) NOT NULL,
	[end_time] [datetime] NOT NULL,
	[module_code] [varchar](50) NOT NULL,
	[creater_name] [varchar](50) NOT NULL,
	[advice] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[my_started_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_module_priv]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_module_priv](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_user_id] [int] NOT NULL,
	[group_ids] [varchar](255) NULL,
	[group_names] [varchar](255) NULL,
	[module_name] [varchar](200) NULL,
	[range_type] [int] NULL,
	[role_ids] [varchar](255) NULL,
	[role_names] [varchar](255) NULL,
	[user_id] [int] NULL,
	[user_ids] [varchar](500) NULL,
	[user_names] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_module_infoTemp]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_module_infoTemp](
	[module_id] [int] NOT NULL,
	[icon] [varchar](50) NULL,
	[memo] [varchar](500) NULL,
	[module_code] [varchar](50) NULL,
	[module_level] [int] NULL,
	[module_name] [varchar](50) NULL,
	[module_type] [int] NULL,
	[order_index] [int] NULL,
	[parent_id] [int] NULL,
	[sys_name] [varchar](50) NULL,
	[url] [varchar](100) NULL,
	[is_delete] [int] NULL,
	[module_flag] [varchar](255) NULL,
	[module_state] [int] NULL,
	[module_class] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[module_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (1, N'/images/module_icons/systemManager.png', NULL, N'1', 1, N'系统管理', 1, 1, 0, NULL, N'', 0, NULL, NULL, N'xtgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (2, N'/images/module_icons/inner_address_manager.png', NULL, N'2', 2, N'单位通讯录管理', 1, 1, 1, NULL, N'', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (3, N'/images/module_icons/ggtxlgl.png', NULL, N'3', 2, N'外部通讯录管理', 1, 2, 1, NULL, N'/logined/publicaddress/list_setupgroup.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (4, N'/images/module_icons/dlyhgl.png', NULL, N'4', 2, N'登录用户管理', 1, 3, 1, NULL, N'/logined/user/loginUserList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (5, N'/images/module_icons/role.png', NULL, N'5', 2, N'角色权限管理', 1, 4, 1, NULL, N'/logined/authority/roleList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (6, N'/images/module_icons/systemSet.png', NULL, N'6', 1, N'系统设置', 1, 2, 0, NULL, N'', 0, NULL, NULL, N'xtsz')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (7, N'/images/module_icons/address.png', NULL, N'7', 1, N'通讯录', 1, 3, 0, NULL, N'', 0, NULL, NULL, N'txl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (8, N'/images/module_icons/ownSet.png', NULL, N'8', 1, N'个人设置', 1, 4, 0, NULL, N'', 0, NULL, NULL, N'grsz')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (9, N'/images/module_icons/myRecord.png', NULL, N'9', 2, N'个人信息', 1, 1, 8, NULL, N'/sysset/toRecordSet.action', 0, NULL, NULL, N'grxx')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (10, N'/images/module_icons/changePwd.png', NULL, N'10', 2, N'密码修改', 1, 2, 8, NULL, N'/sysset/toPwdSet.action', 0, NULL, NULL, N'mmxg')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (11, N'/images/module_icons/inner_address.png', NULL, N'11', 2, N'单位通讯录', 1, 1, 7, NULL, N'/logined/user/user.jsp?type=view', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (12, N'/images/module_icons/public_outer_address.png', NULL, N'12', 2, N'外部通讯录', 1, 2, 7, NULL, N'/logined/publicaddress/list_address.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (13, N'', NULL, N'13', 3, N'单位信息设置', 1, 0, 2, NULL, N'/company/view.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (14, N'/images/module_icons/groupManager.png', NULL, N'14', 3, N'组织机构管理', 1, 1, 2, NULL, N'/logined/group/group.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (15, N'/images/module_icons/userManager.png', NULL, N'15', 3, N'人员管理', 1, 2, 2, NULL, N'/logined/user/user.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (16, N'', NULL, N'16', 4, N'部门管理', 2, 1, 14, NULL, N'/logined/group/group.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (17, N'', NULL, N'17', 4, N'部门列表', 2, 2, 14, NULL, N'group/findGradeGroupTree.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (18, N'/images/module_icons/info_type.png', NULL, N'18', 2, N'通用设置', 1, 1, 6, NULL, N'/sysset/toCommonSet.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (19, N'/images/module_icons/info_type.png', NULL, N'19', 2, N'数据字典设置', 1, 1, 6, NULL, N'/logined/dict/dict.jsp?sysTag=-1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (20, N'/images/module_icons/task_manage.png', NULL, N'20', 1, N'任务管理', 1, 14, 0, NULL, N'', 0, NULL, 0, N'rwgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (21, N'/images/module_icons/wzjd.png', NULL, N'21', 2, N'我转交的', 1, 3, 20, NULL, N'/logined/task/handedList.jsp', 0, NULL, 0, N'wzjd')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (22, N'/images/module_icons/wfqd.png', NULL, N'22', 2, N'我发起的', 1, 1, 20, NULL, N'/logined/task/addTaskList.jsp', 0, NULL, 0, N'wfqd')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (23, N'/images/module_icons/wcbd.png', NULL, N'23', 2, N'我承办的', 1, 2, 20, NULL, N'/logined/task/unfinishedList.jsp', 0, NULL, 0, N'wcbd')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (24, N'', NULL, N'24', 4, N'未办结', 2, 1, 23, NULL, N'/logined/task/unfinishedList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (25, N'', NULL, N'25', 4, N'已办结', 2, 2, 23, NULL, N'/logined/task/finishedList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (26, N'/images/menu/mytable.png', NULL, N'26', 1, N'个人事务', 1, 3, 0, NULL, N'', 0, NULL, 0, N'grsw')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (27, N'/images/module_icons/myCalendar.png', NULL, N'27', 2, N'日程管理', 1, 3, 26, NULL, N'/logined/programme/myProgramme.jsp', 0, NULL, 1, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (28, N'/images/menu/knowledge.png', NULL, N'28', 1, N'知识管理', 1, 5, 0, NULL, N'', 0, NULL, 0, N'zsgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (29, N'/images/module_icons/fileSearch.png', NULL, N'29', 2, N'资料查阅', 1, 1, 28, NULL, N'/logined/file/viewFileContent.jsp?fileSort=0', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (30, N'/images/module_icons/fileManager.png', NULL, N'30', 3, N'资料管理', 1, 2, 28, NULL, N'/logined/file/fileContent.jsp?fileSort=0', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (31, N'/images/menu/erp.png', NULL, N'31', 1, N'行政办公', 1, 4, 0, NULL, N'', 0, NULL, 0, N'xzbg')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (32, N'/images/module_icons/readnotify.png', NULL, N'32', 2, N'公告查看', 1, 1, 31, NULL, N'/logined/notify/viewList.jsp?id=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (35, N'/images/module_icons/notifyManager.png', NULL, N'35', 2, N'公告管理', 1, 2, 31, NULL, N'/logined/notify/list.jsp?id=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (36, N'/images/module_icons/notAuditing.png', NULL, N'36', 2, N'公告审批', 1, 3, 31, NULL, N'/logined/notify/viewApproveNoList.jsp?id=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (37, N'/images/module_icons/notify_manager.png', NULL, N'37', 2, N'公告通知设置', 1, 4, 31, NULL, N'/logined/notify/notifySet.jsp?id=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (38, N'/images/module_icons/inner_email.png', NULL, N'38', 2, N'内部邮件', 1, 4, 26, NULL, N'/logined/email/emailMainPage.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (39, N'', NULL, N'39', 1, N'新闻管理', 1, 5, 0, NULL, N'', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (40, N'', NULL, N'40', 2, N'栏目管理', 1, 1, 39, NULL, N'/logined/news/manage/list.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (41, N'', NULL, N'41', 2, N'发布新闻', 1, 2, 39, NULL, N'/logined/news/user/columnList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (42, N'', NULL, N'42', 2, N'新闻列表', 1, 3, 39, NULL, N'/logined/news/user/issueList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (43, N'', NULL, N'43', 2, N'新闻草稿', 1, 4, 39, NULL, N'/logined/news/user/issueDraft.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (44, N'', NULL, N'44', 2, N'期刊素材', 1, 5, 39, NULL, N'/logined/news/user/materialList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (46, N'', NULL, N'46', 3, N'栏目管理', 1, 1, 45, NULL, N'/logined/notice/manage/list.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (47, N'', NULL, N'47', 3, N'发布公告', 1, 2, 45, NULL, N'/logined/notice/user/columnList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (48, N'', NULL, N'48', 3, N'公告列表', 1, 3, 45, NULL, N'/logined/notice/user/issueList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (49, N'', NULL, N'49', 3, N'公告草稿', 1, 4, 45, NULL, N'/logined/notice/user/issueDraft.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (50, N'/images/module_icons/daily.png', NULL, N'50', 2, N'工作日志', 1, 1, 26, NULL, N'/daily/toDailyMain.action', 0, NULL, 1, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (51, N'', NULL, N'51', 4, N'新建日志', 2, 1, 50, NULL, N'/daily/toDailyMain.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (52, N'', NULL, N'52', 4, N'日志查询', 2, 2, 50, NULL, N'/daily/toSearch.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (53, N'/images/module_icons/logSumup.png', NULL, N'53', 2, N'系统日志管理', 1, 5, 1, NULL, N'/logined/log/logSumup.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (54, N'', NULL, N'54', 3, N'日志概况', 2, 1, 53, NULL, N'/logined/log/logSumup.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (55, N'', NULL, N'55', 3, N'日志查询', 2, 2, 53, NULL, N'/logined/log/search.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (56, N'/images/module_icons/rzcx.png', NULL, N'56', 2, N'日志管理', 1, 5, 31, NULL, N'/logined/dailySearch/index.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (57, N'', NULL, N'57', 4, N'成员日志', 2, 1, 56, NULL, N'/logined/dailySearch/index.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (58, N'', NULL, N'58', 4, N'日志查询', 2, 2, 56, NULL, N'/daily/toAllDailySearch.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (59, N'/images/module_icons/gwglsz.png', NULL, N'59', 2, N'公文管理设置', 1, 2, 6, NULL, N'', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (60, N'/images/module_icons/gwlxsz.png', NULL, N'60', 3, N'公文类型设置', 1, 1, 59, NULL, N'/documentType/docType_docTypeList.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (61, N'/images/module_icons/thmb.png', NULL, N'61', 3, N'套红模板', 1, 2, 59, NULL, N'/logined/docTemplate/docTemplateLCategoryist.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (62, N'/images/module_icons/yzgl.png', NULL, N'62', 3, N'印章管理', 1, 3, 59, NULL, N'/logined/websign/ekeySignUtil.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (63, N'/images/module_icons/bdqxsz.png', NULL, N'63', 3, N'表单权限设置', 1, 4, 59, NULL, N'/logined/formAuthority/formList.jsp?firstPage=true', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (64, N'', NULL, N'64', 4, N'模板类型管理', 2, 1, 61, NULL, N'/logined/docTemplate/docTemplateLCategoryist.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (65, N'', NULL, N'65', 4, N'模板管理', 2, 2, 61, NULL, N'/logined/docTemplate/docTemplateList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (66, N'/images/module_icons/systemSet.png', N'', N'66', 2, N'工作流设置', 1, 1, 6, N'', N'', 0, N'', 0, N'gzlsz')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (67, N'/images/module_icons/sjbd.png', N'', N'67', 3, N'设计表单', 1, 1, 66, N'', N'/logined/customForm/addForm.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (68, N'/images/module_icons/sjlc.png', N'', N'68', 3, N'设计流程', 1, 2, 66, N'', N'/workflow/manager.action', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (69, N'/images/module_icons/flsz.png', N'', N'69', 3, N'分类设置', 1, 3, 66, N'', N'/logined/customForm/formCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (70, N'/images/module_icons/flsz.png', N'', N'70', 4, N'新建表单', 2, 1, 67, N'', N'/logined/customForm/addForm.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (71, N'/images/module_icons/flsz.png', N'', N'71', 4, N'查询表单', 2, 2, 67, N'', N'/workflowForm/getFormCategorys.action', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (72, N'/images/module_icons/flsz.png', N'', N'72', 4, N'表单分类', 2, 1, 69, N'', N'/logined/customForm/formCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (73, N'/images/module_icons/flsz.png', N'', N'73', 4, N'流程分类', 2, 2, 69, N'', N'/logined/customForm/processCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (74, N'/images/module_icons/gwgl.png', NULL, N'74', 1, N'公文管理', 1, 1, 0, NULL, N'', 0, NULL, NULL, N'gwgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (75, N'/images/module_icons/swgl.png', NULL, N'75', 2, N'收文管理', 1, 1, 74, NULL, N'', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (76, N'/images/module_icons/fwgl.png', NULL, N'76', 2, N'发文管理', 1, 2, 74, NULL, N'', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (77, N'/images/module_icons/gdgl.png', NULL, N'77', 2, N'归档管理', 1, 3, 74, NULL, N'', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (78, N'/images/module_icons/swdj.png', NULL, N'78', 3, N'新建收文', 1, 1, 75, NULL, N'/dom/gather!toAdd.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (79, N'/images/module_icons/swjl.png', NULL, N'79', 3, N'收文登记', 1, 2, 75, NULL, N'/dom/gather!gatherList.action?menu=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (80, N'/images/module_icons/ldpy.png', NULL, N'80', 3, N'领导批阅', 1, 3, 75, NULL, N'/dom/gather!gatherList.action?menu=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (81, N'/images/module_icons/swff.png', NULL, N'81', 3, N'收文分发', 1, 4, 75, NULL, N'/dom/gather!gatherList.action?menu=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (82, N'/images/module_icons/swyd.png', NULL, N'82', 3, N'收文阅读', 1, 5, 75, NULL, N'/dom/gather!gatherList.action?menu=4', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (83, N'/images/module_icons/fwng.png', NULL, N'83', 3, N'新建发文', 1, 1, 76, NULL, N'/dom/dispatch!toAdd.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (84, N'/images/module_icons/ngjl.png', NULL, N'84', 3, N'发文拟稿', 1, 2, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=5', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (85, N'/images/module_icons/ffhg.png', NULL, N'85', 3, N'发文核稿', 1, 3, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=6', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (86, N'/images/module_icons/thgz.png', NULL, N'86', 3, N'套红盖章', 1, 4, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=7', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (87, N'/images/module_icons/fwff.png', NULL, N'87', 3, N'发文分发', 1, 5, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=8', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (88, N'', NULL, N'88', 3, N'公文归档', 1, 1, 77, NULL, N'/dom/gather!gatherList.action?menu=9', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (89, N'/images/module_icons/gdcx.png', NULL, N'89', 3, N'归档查询', 1, 2, 77, NULL, N'dom/public!zipSearch.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (90, N'', NULL, N'90', 3, N'归档统计', 1, 3, 77, NULL, N'/dom/public!zipReport.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (91, N'/images/module_icons/gzl.png', N'', N'91', 1, N'工作流', 1, 3, 0, N'', N'', 0, N'', 0, N'gzl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (92, N'', N'', N'92', 2, N'新建申请', 1, 1, 91, N'', N'/jbpmflow/listSearch!often.action', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (93, N'/images/module_icons/mystart.png', N'', N'93', 2, N'我的申请', 1, 2, 91, N'', N'/logined/jbpmApp/myjob/start.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (94, N'', N'', N'94', 2, N'待办审批', 1, 3, 91, N'', N'/logined/jbpmApp/myjob/management.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (95, N'', N'', N'95', 2, N'已办审批', 1, 4, 91, N'', N'/logined/jbpmApp/myjob/completed.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (96, N'', NULL, N'96', 4, N'用户列表', 2, 1, 12, NULL, N'/logined/publicaddress/list_address.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (97, N'', NULL, N'97', 4, N'用户查询', 2, 2, 12, NULL, N'logined/address/list_address.jsp?publicSign=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (98, N'', NULL, N'98', 4, N'待登记', 2, 1, 79, NULL, N'/dom/gather!gatherList.action?menu=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (99, N'', NULL, N'99', 4, N'已登记', 2, 2, 79, NULL, N'/dom/gather!gatherList.action?menu=1&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (100, N'', NULL, N'100', 4, N'待批阅', 2, 1, 80, NULL, N'/dom/gather!gatherList.action?menu=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (101, N'', NULL, N'101', 4, N'已批阅', 2, 2, 80, NULL, N'/dom/gather!gatherList.action?menu=2&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (102, N'', NULL, N'102', 4, N'待分发', 2, 1, 81, NULL, N'/dom/gather!gatherList.action?menu=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (103, N'', NULL, N'103', 4, N'已分发', 2, 2, 81, NULL, N'/dom/gather!gatherList.action?menu=3&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (104, N'', NULL, N'104', 4, N'待阅读', 2, 1, 82, NULL, N'/dom/gather!gatherList.action?menu=4', 0, NULL, NULL, NULL)
GO
print 'Processed 100 total records'
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (105, N'', NULL, N'105', 4, N'已阅读', 2, 2, 82, NULL, N'/dom/gather!gatherList.action?menu=4&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (106, N'', NULL, N'106', 4, N'未提交', 2, 1, 84, NULL, N'/dom/dispatch!dispatchList.action?menu=5', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (107, N'', NULL, N'107', 4, N'已提交', 2, 2, 84, NULL, N'/dom/dispatch!dispatchList.action?menu=5&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (108, N'', NULL, N'108', 4, N'待核稿', 2, 1, 85, NULL, N'/dom/dispatch!dispatchList.action?menu=6', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (109, N'', NULL, N'109', 4, N'已核稿', 2, 2, 85, NULL, N'/dom/dispatch!dispatchList.action?menu=6&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (110, N'', NULL, N'110', 4, N'待盖章', 2, 1, 86, NULL, N'/dom/dispatch!dispatchList.action?menu=7', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (111, N'', NULL, N'111', 4, N'已盖章', 2, 2, 86, NULL, N'/dom/dispatch!dispatchList.action?menu=7&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (112, N'', NULL, N'112', 4, N'待分发', 2, 1, 87, NULL, N'/dom/dispatch!dispatchList.action?menu=8', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (113, N'', NULL, N'113', 4, N'已分发', 2, 2, 87, NULL, N'/dom/dispatch!dispatchList.action?menu=8&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (114, N'', NULL, N'114', 4, N'收文归档', 2, 1, 88, NULL, N'/dom/gather!gatherList.action?menu=9', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (115, N'', NULL, N'115', 4, N'发文归档', 2, 2, 88, NULL, N'/dom/dispatch!dispatchList.action?menu=10', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (116, N'/images/module_icons/affairs_manager.png', NULL, N'116', 2, N'事务提醒设置', 1, 3, 6, NULL, N'/logined/affairs/affairs_manage.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (119, N'', NULL, N'119', 3, N'信息保密设置', 1, 3, 2, NULL, N'/logined/user/secret_user.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (120, N'', NULL, N'120', 3, N'群组管理', 1, 4, 2, NULL, N'/logined/group_ext/group.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (121, N'', NULL, N'121', 3, N'群组人员管理', 1, 5, 2, NULL, N'/logined/group_ext/user.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (122, NULL, NULL, N'122', 1, N'考勤管理', 1, 1, 0, NULL, NULL, 1, NULL, NULL, N'kqgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (123, NULL, NULL, N'123', 2, N'我的考勤', 1, 1, 122, NULL, NULL, 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (124, NULL, NULL, N'124', 2, N'系统管理', 1, 2, 122, NULL, NULL, 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (125, NULL, NULL, N'125', 2, N'考勤管理', 1, 3, 122, NULL, NULL, 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (126, NULL, NULL, N'126', 3, N'考勤登记', 1, 0, 123, NULL, N'/logined/attendance/attendanceRegister.jsp', 1, NULL, NULL, N'kqdj')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (127, NULL, NULL, N'127', 3, N'考勤记录', 1, 1, 123, NULL, N'/logined/attendance/attendanceRecord.jsp', 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (128, NULL, NULL, N'128', 3, N'考勤IP设置', 1, 0, 124, NULL, N'/attendance/getIPList.action', 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (129, NULL, NULL, N'129', 3, N'考勤统计', 1, 0, 125, NULL, N'/logined/attendance/attendanceTj.jsp', 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (130, N'/images/menu/questionniare.png', NULL, N'99920', 1, N'调查问卷', 1, 15, 0, NULL, NULL, 0, NULL, 0, N'xzwj')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (131, N'/images/module_icons/wjlb.png', NULL, N'99998', 2, N'调查问卷', 1, 0, 130, NULL, N'/logined/question/issueList.jsp', 0, NULL, 0, N'tpdc')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (132, N'/images/module_icons/wjgl.png', NULL, N'99921', 2, N'调查问卷管理', 1, 1, 130, NULL, N'/logined/question/questionnaire.jsp', 0, NULL, 0, N'wjgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (133, N'', NULL, N'3000800', 2, N'论坛管理', 1, 6, 26, NULL, N'bbs', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (134, N'/images/module_icons/message.png', NULL, N'134', 2, N'即时消息', 1, 7, 26, NULL, N'/logined/message/send_message.jsp', 0, NULL, 1, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (135, N'/images/module_icons/affairs.png', NULL, N'135', 2, N'事务提醒', 1, 5, 26, NULL, N'/logined/affairs/list_new_affairs.jsp', 0, NULL, 1, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (136, N'/images/menu/hr.png', NULL, N'136', 1, N'人力资源', 1, 6, 0, NULL, N'', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (137, N'/images/module_icons/dack.png', NULL, N'137', 2, N'档案查看', 1, 1, 136, NULL, N'/logined/record/main.action', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (138, N'/images/module_icons/dagl.png', NULL, N'138', 2, N'档案管理', 1, 2, 136, NULL, N'/logined/record/list_manager.jsp', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (139, N'/images/module_icons/dacx.png', NULL, N'139', 2, N'档案查询', 1, 3, 136, NULL, N'/logined/record/searchPage.action', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (143, NULL, NULL, N'143', 1, N'报表管理', 1, 0, 0, NULL, NULL, 0, NULL, NULL, N'bbgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (144, NULL, NULL, N'144', 2, N'报表填报', 1, 0, 143, NULL, N'/reportInfo/showReport.action?reportType=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (145, NULL, NULL, N'145', 2, N'报表审批', 1, 1, 143, NULL, N'/reportInfo/showReport.action?reportType=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (146, NULL, NULL, N'146', 2, N'报表查阅', 1, 2, 143, NULL, N'/reportInfo/showReport.action?reportType=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (147, NULL, NULL, N'147', 2, N'报表权限设置', 1, 3, 143, NULL, N'/logined/report/list_manager_report.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (148, NULL, NULL, N'148', 2, N'报表基础数据维护', 1, 4, 143, NULL, N'/logined/report/projectBDList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (153, NULL, NULL, N'153', 1, N'抽奖管理', 1, 0, 0, NULL, NULL, 0, NULL, NULL, N'cjgl')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (154, N'/images/module_icons/cjlb.png', NULL, N'154', 2, N'抽奖列表', 1, 1, 153, NULL, N'/logined/lottery/lotteryList.jsp', 0, NULL, NULL, N'cjlb')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (155, N'/images/menu/news_manage.png', NULL, N'155', 1, N'新闻管理', 1, 5, 0, NULL, N'', 0, NULL, 0, N'xwzx')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (156, N'/images/module_icons/readnotify.png', NULL, N'156', 2, N'新闻查看', 1, 1, 155, NULL, N'/logined/notify/viewList.jsp?id=2', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (157, N'/images/module_icons/notifyManager.png', NULL, N'157', 2, N'新闻管理', 1, 2, 155, NULL, N'/logined/notify/list.jsp?id=2', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (158, N'/images/module_icons/notAuditing.png', NULL, N'158', 2, N'新闻审批', 1, 3, 155, NULL, N'/logined/notify/viewApproveList.jsp?id=2', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (159, N'/images/module_icons/notify_manager.png', NULL, N'159', 2, N'新闻设置', 1, 4, 155, NULL, N'/logined/notify/notifySet.jsp?id=2', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (160, N'/images/module_icons/notAuditing.png', N'', N'160', 4, N'待审批', 2, 1, 36, N'', N'/logined/notify/viewApproveNoList.jsp?id=1', 0, N'', 0, N'')
INSERT [dbo].[tb_module_infoTemp] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (161, N'/images/module_icons/notAuditing.png', N'', N'161', 4, N'已审批', 2, 2, 36, N'', N'/logined/notify/viewApproveList.jsp?id=1', 0, N'', 0, N'')
/****** Object:  Table [dbo].[tb_module_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_module_info](
	[module_id] [int] NOT NULL,
	[icon] [varchar](50) NULL,
	[memo] [varchar](500) NULL,
	[module_code] [varchar](50) NULL,
	[module_level] [int] NULL,
	[module_name] [varchar](50) NULL,
	[module_type] [int] NULL,
	[order_index] [int] NULL,
	[parent_id] [int] NULL,
	[sys_name] [varchar](50) NULL,
	[url] [varchar](100) NULL,
	[is_delete] [int] NULL,
	[module_flag] [varchar](255) NULL,
	[module_state] [int] NULL,
	[module_class] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[module_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (1, N'/images/module_icons/systemManager.png', NULL, N'1', 1, N'系统管理', 1, 8, 0, NULL, N'', 0, NULL, NULL, N'xtgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (2, N'/images/module_icons/inner_address_manager.png', NULL, N'2', 2, N'单位通讯录管理', 1, 1, 1, NULL, N'', 0, NULL, NULL, N'dwtxlgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (3, N'/images/module_icons/ggtxlgl.png', NULL, N'3', 2, N'外部通讯录管理', 1, 2, 1, NULL, N'/logined/publicaddress/list_setupgroup.jsp', 0, NULL, NULL, N'wbtxlgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (4, N'/images/module_icons/dlyhgl.png', NULL, N'4', 2, N'登录用户管理', 1, 3, 1, NULL, N'/logined/user/loginUserList.jsp', 0, NULL, NULL, N'dlyh')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (5, N'/images/module_icons/role.png', NULL, N'5', 2, N'角色权限管理', 1, 4, 1, NULL, N'/logined/authority/roleList.jsp', 0, NULL, NULL, N'jsqx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (6, N'/images/module_icons/systemSet.png', NULL, N'6', 1, N'系统设置', 1, 9, 0, NULL, N'', 0, NULL, NULL, N'xtsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (7, N'/images/module_icons/address.png', NULL, N'txl', 1, N'通讯录', 1, 7, 0, NULL, N'', 0, NULL, NULL, N'txl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (8, N'/images/module_icons/ownSet.png', NULL, N'8', 1, N'个人设置', 1, 6, 0, NULL, N'', 0, NULL, NULL, N'grsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (9, N'/images/module_icons/myRecord.png', NULL, N'9', 2, N'个人信息', 1, 1, 8, NULL, N'/sysset/toRecordSet.action', 0, NULL, NULL, N'grxx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (10, N'/images/module_icons/changePwd.png', NULL, N'10', 2, N'密码修改', 1, 2, 8, NULL, N'/sysset/toPwdSet.action', 0, NULL, NULL, N'mmxg')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (11, N'/images/module_icons/inner_address.png', NULL, N'11', 2, N'单位通讯录', 1, 1, 7, NULL, N'/logined/user/user.jsp?type=view', 0, NULL, NULL, N'dwtxl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (12, N'/images/module_icons/public_outer_address.png', NULL, N'12', 2, N'外部通讯录', 1, 2, 7, NULL, N'/logined/publicaddress/list_address.jsp', 0, NULL, NULL, N'wbtxl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (13, N'', NULL, N'13', 3, N'单位信息设置', 1, 0, 2, NULL, N'/company/view.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (14, N'/images/module_icons/groupManager.png', NULL, N'14', 3, N'组织机构管理', 1, 1, 2, NULL, N'/logined/group/group.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (15, N'/images/module_icons/userManager.png', NULL, N'15', 3, N'人员管理', 1, 2, 2, NULL, N'/logined/user/user.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (16, N'', NULL, N'16', 4, N'部门管理', 2, 1, 14, NULL, N'/logined/group/group.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (17, N'', NULL, N'17', 4, N'部门列表', 2, 2, 14, NULL, N'group/findGradeGroupTree.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (18, N'/images/module_icons/info_type.png', NULL, N'18', 2, N'通用设置', 1, 1, 6, NULL, N'/sysset/toCommonSet.action', 1, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (19, N'/images/module_icons/info_type.png', NULL, N'19', 2, N'数据字典设置', 1, 1, 6, NULL, N'/logined/dict/dict.jsp?sysTag=-1', 0, NULL, NULL, N'sjzd')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (20, N'/images/module_icons/task_manage.png', NULL, N'20', 1, N'任务管理', 1, 14, 0, NULL, N'', 0, NULL, 0, N'rwgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (21, N'/images/module_icons/wzjd.png', NULL, N'21', 2, N'我转交的', 1, 3, 20, NULL, N'/logined/task/handedList.jsp', 0, NULL, 0, N'wzjd')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (22, N'/images/module_icons/wfqd.png', NULL, N'22', 2, N'我发起的', 1, 1, 20, NULL, N'/logined/task/addTaskList.jsp', 0, NULL, 0, N'wfqd')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (23, N'/images/module_icons/wcbd.png', NULL, N'23', 2, N'我承办的', 1, 2, 20, NULL, N'/logined/task/unfinishedList.jsp', 0, NULL, 0, N'wcbd')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (24, N'', NULL, N'24', 4, N'未办结', 2, 1, 23, NULL, N'/logined/task/unfinishedList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (25, N'', NULL, N'25', 4, N'已办结', 2, 2, 23, NULL, N'/logined/task/finishedList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (26, N'/images/menu/mytable.png', NULL, N'26', 1, N'个人事务', 1, 3, 0, NULL, N'', 0, NULL, 0, N'grsw')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (27, N'/images/module_icons/myCalendar.png', NULL, N'rcgl', 2, N'日程管理', 1, 3, 26, NULL, N'/logined/programme/myProgramme.jsp', 0, NULL, 1, N'rcgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (28, N'/images/menu/knowledge.png', NULL, N'zsgl', 1, N'知识管理', 1, 5, 0, NULL, N'', 0, NULL, 0, N'zsgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (29, N'/images/module_icons/fileSearch.png', NULL, N'29', 2, N'资料查阅', 1, 1, 28, NULL, N'/logined/file/viewFileContent.jsp?fileSort=28', 0, NULL, 0, N'zlcy')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (30, N'/images/module_icons/fileManager.png', NULL, N'30', 3, N'资料管理', 1, 2, 28, NULL, N'/logined/file/fileContent.jsp?fileSort=28', 0, NULL, 0, N'zlgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (31, N'/images/menu/erp.png', NULL, N'31', 1, N'行政办公', 1, 4, 0, NULL, N'', 0, NULL, 0, N'xzbg')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (32, N'/images/module_icons/readnotify.png', NULL, N'32', 2, N'公告查看', 1, 1, 160, NULL, N'/logined/notify/viewList.jsp?id=35', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (35, N'/images/module_icons/notifyManager.png', NULL, N'gggl', 2, N'公告管理', 1, 2, 160, NULL, N'/logined/notify/list.jsp?id=35', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (36, N'/images/module_icons/notAuditing.png', NULL, N'36', 2, N'公告审批', 1, 3, 160, NULL, N'/logined/notify/viewApproveNoList.jsp?id=35', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (37, N'/images/module_icons/notify_manager.png', NULL, N'37', 2, N'公告通知设置', 1, 4, 160, NULL, N'/logined/notify/notifySet.jsp?id=35', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (38, N'/images/module_icons/inner_email.png', NULL, N'nbyj', 2, N'内部邮件', 1, 4, 26, NULL, N'/logined/email/emailMainPage.action', 0, NULL, 0, N'nbyj')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (39, N'', NULL, N'xwzx', 2, N'新闻中心', 1, 5, 31, NULL, N'', 0, NULL, 0, N'xwzx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (40, N'', NULL, N'40', 3, N'栏目管理', 1, 1, 39, NULL, N'/logined/news/manage/list.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (41, N'', NULL, N'41', 3, N'发布新闻', 1, 2, 39, NULL, N'/logined/news/user/columnList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (42, N'', NULL, N'42', 3, N'新闻列表', 1, 3, 39, NULL, N'/logined/news/user/issueList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (43, N'', NULL, N'43', 3, N'新闻草稿', 1, 4, 39, NULL, N'/logined/news/user/issueDraft.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (44, N'', NULL, N'44', 3, N'期刊素材', 1, 5, 39, NULL, N'/logined/news/user/materialList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (45, NULL, NULL, N'45', 2, N'通讯助理公告管理', 1, 6, 31, NULL, N'', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (46, N'', NULL, N'46', 3, N'栏目管理', 1, 1, 45, NULL, N'/logined/notice/manage/list.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (47, N'', NULL, N'47', 3, N'发布公告', 1, 2, 45, NULL, N'/logined/notice/user/columnList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (48, N'', NULL, N'48', 3, N'公告列表', 1, 3, 45, NULL, N'/logined/notice/user/issueList.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (49, N'', NULL, N'49', 3, N'公告草稿', 1, 4, 45, NULL, N'/logined/notice/user/issueDraft.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (50, N'/images/module_icons/daily.png', NULL, N'gzrz', 2, N'工作日志', 1, 1, 26, NULL, N'/daily/toDailyMain.action', 0, NULL, 1, N'gzrz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (51, N'', NULL, N'51', 4, N'新建日志', 2, 1, 50, NULL, N'/daily/toDailyMain.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (52, N'', NULL, N'52', 4, N'日志查询', 2, 2, 50, NULL, N'/daily/toSearch.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (53, N'/images/module_icons/logSumup.png', NULL, N'53', 2, N'系统日志管理', 1, 5, 1, NULL, N'/logined/log/logSumup.jsp', 0, NULL, 0, N'xtrz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (54, N'', NULL, N'54', 3, N'日志概况', 2, 1, 53, NULL, N'/logined/log/logSumup.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (55, N'', NULL, N'55', 3, N'日志查询', 2, 2, 53, NULL, N'/logined/log/search.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (56, N'/images/module_icons/rzcx.png', NULL, N'56', 2, N'日志管理', 1, 5, 31, NULL, N'/logined/dailySearch/index.jsp', 0, NULL, 0, N'rzgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (57, N'', NULL, N'57', 4, N'成员日志', 2, 1, 56, NULL, N'/logined/dailySearch/index.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (58, N'', NULL, N'58', 4, N'日志查询', 2, 2, 56, NULL, N'/daily/toAllDailySearch.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (59, N'/images/module_icons/gwglsz.png', NULL, N'59', 2, N'公文管理设置', 1, 2, 6, NULL, N'', 0, NULL, NULL, N'gwglsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (60, N'/images/module_icons/gwlxsz.png', NULL, N'60', 3, N'公文类型设置', 1, 1, 59, NULL, N'/documentType/docType_docTypeList.action', 0, NULL, NULL, N'')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (61, N'/images/module_icons/thmb.png', NULL, N'61', 3, N'套红模板', 1, 2, 59, NULL, N'/logined/docTemplate/docTemplateLCategoryist.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (62, N'/images/module_icons/yzgl.png', NULL, N'62', 3, N'印章管理', 1, 3, 59, NULL, N'/logined/websign/ekeySignUtil.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (63, N'/images/module_icons/bdqxsz.png', NULL, N'63', 3, N'表单权限设置', 1, 4, 59, NULL, N'/logined/formAuthority/formList.jsp?firstPage=true', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (64, N'', NULL, N'64', 4, N'模板类型管理', 2, 1, 61, NULL, N'/logined/docTemplate/docTemplateLCategoryist.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (65, N'', NULL, N'65', 4, N'模板管理', 2, 2, 61, NULL, N'/logined/docTemplate/docTemplateList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (66, N'/images/module_icons/systemSet.png', N'', N'66', 2, N'工作流设置', 1, 1, 6, N'', N'', 0, N'', 0, N'gzlsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (67, N'/images/module_icons/sjbd.png', N'', N'67', 3, N'设计表单', 1, 1, 66, N'', N'/logined/customForm/addForm.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (68, N'/images/module_icons/sjlc.png', N'', N'68', 3, N'设计流程', 1, 2, 66, N'', N'/workflow/manager.action', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (69, N'/images/module_icons/flsz.png', N'', N'69', 3, N'分类设置', 1, 3, 66, N'', N'/logined/customForm/formCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (70, N'/images/module_icons/flsz.png', N'', N'70', 4, N'新建表单', 2, 1, 67, N'', N'/logined/customForm/addForm.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (71, N'/images/module_icons/flsz.png', N'', N'71', 4, N'查询表单', 2, 2, 67, N'', N'/workflowForm/getFormCategorys.action', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (72, N'/images/module_icons/flsz.png', N'', N'72', 4, N'表单分类', 2, 1, 69, N'', N'/logined/customForm/formCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (73, N'/images/module_icons/flsz.png', N'', N'73', 4, N'流程分类', 2, 2, 69, N'', N'/logined/customForm/processCategoryList.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (74, N'/images/module_icons/gwgl.png', NULL, N'gwgl', 1, N'公文管理', 1, 1, 0, NULL, N'', 0, NULL, NULL, N'gwgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (75, N'/images/module_icons/swgl.png', NULL, N'75', 2, N'收文管理', 1, 1, 74, NULL, N'', 0, NULL, NULL, N'swgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (76, N'/images/module_icons/fwgl.png', NULL, N'76', 2, N'发文管理', 1, 2, 74, NULL, N'', 0, NULL, NULL, N'fwgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (77, N'/images/module_icons/gdgl.png', NULL, N'77', 2, N'公文归档', 1, 3, 74, NULL, N'', 0, NULL, NULL, N'gwgd')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (78, N'/images/module_icons/swdj.png', NULL, N'78', 3, N'新建收文', 1, 1, 75, NULL, N'/dom/gather!toAdd.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (79, N'/images/module_icons/swjl.png', NULL, N'79', 3, N'收文登记', 1, 2, 75, NULL, N'/dom/gather!gatherList.action?menu=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (80, N'/images/module_icons/ldpy.png', NULL, N'80', 3, N'领导批阅', 1, 3, 75, NULL, N'/dom/gather!gatherList.action?menu=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (81, N'/images/module_icons/swff.png', NULL, N'81', 3, N'收文分发', 1, 4, 75, NULL, N'/dom/gather!gatherList.action?menu=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (82, N'/images/module_icons/swyd.png', NULL, N'swyd', 3, N'收文阅读', 1, 5, 75, NULL, N'/dom/gather!gatherList.action?menu=4', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (83, N'/images/module_icons/fwng.png', NULL, N'83', 3, N'新建发文', 1, 1, 76, NULL, N'/dom/dispatch!toAdd.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (84, N'/images/module_icons/ngjl.png', NULL, N'84', 3, N'发文拟稿', 1, 2, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=5', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (85, N'/images/module_icons/ffhg.png', NULL, N'85', 3, N'发文核稿', 1, 3, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=6', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (86, N'/images/module_icons/thgz.png', NULL, N'86', 3, N'套红盖章', 1, 4, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=7', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (87, N'/images/module_icons/fwff.png', NULL, N'87', 3, N'发文分发', 1, 5, 76, NULL, N'/dom/dispatch!dispatchList.action?menu=8', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (88, N'', NULL, N'88', 3, N'公文归档', 1, 1, 77, NULL, N'/dom/gather!gatherList.action?menu=9', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (89, N'/images/module_icons/gdcx.png', NULL, N'89', 3, N'归档查询', 1, 2, 77, NULL, N'dom/public!zipSearch.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (90, N'', NULL, N'90', 3, N'归档统计', 1, 3, 77, NULL, N'/dom/public!zipReport.action', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (91, N'/images/module_icons/gzl.png', N'', N'wdgz', 1, N'我的工作', 1, 3, 0, N'', N'', 0, N'', 0, N'gzl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (92, N'', N'', N'92', 2, N'新建申请', 1, 1, 91, N'', N'/jbpmflow/listSearch!often.action', 1, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (93, N'/images/module_icons/mystart.png', N'', N'93', 2, N'我的申请', 1, 2, 91, N'', N'/logined/jbpmApp/myjob/start.jsp', 0, N'', 0, N'wdsq')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (94, N'/images/module_icons/wdsp.png', N'', N'94', 2, N'我的审批', 1, 3, 91, N'', N'/logined/jbpmApp/myjob/management.jsp', 0, N'', 0, N'wdsp')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (95, N'', N'', N'95', 4, N'待审批', 2, 1, 94, N'', N'/logined/jbpmApp/myjob/management.jsp', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (96, N'', NULL, N'96', 4, N'用户列表', 2, 1, 12, NULL, N'/logined/publicaddress/list_address.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (97, N'', NULL, N'97', 4, N'用户查询', 2, 2, 12, NULL, N'logined/address/list_address.jsp?publicSign=1', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (98, N'', NULL, N'98', 4, N'待登记', 2, 1, 79, NULL, N'/dom/gather!gatherList.action?menu=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (99, N'', NULL, N'99', 4, N'已登记', 2, 2, 79, NULL, N'/dom/gather!gatherList.action?menu=1&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (100, N'', NULL, N'100', 4, N'待批阅', 2, 1, 80, NULL, N'/dom/gather!gatherList.action?menu=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (101, N'', NULL, N'101', 4, N'已批阅', 2, 2, 80, NULL, N'/dom/gather!gatherList.action?menu=2&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (102, N'', NULL, N'102', 4, N'待分发', 2, 1, 81, NULL, N'/dom/gather!gatherList.action?menu=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (103, N'', NULL, N'103', 4, N'已分发', 2, 2, 81, NULL, N'/dom/gather!gatherList.action?menu=3&searchType=completed', 0, NULL, NULL, NULL)
GO
print 'Processed 100 total records'
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (104, N'', NULL, N'104', 4, N'待阅读', 2, 1, 82, NULL, N'/dom/gather!gatherList.action?menu=4', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (105, N'', NULL, N'105', 4, N'已阅读', 2, 2, 82, NULL, N'/dom/gather!gatherList.action?menu=4&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (106, N'', NULL, N'106', 4, N'未提交', 2, 1, 84, NULL, N'/dom/dispatch!dispatchList.action?menu=5', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (107, N'', NULL, N'107', 4, N'已提交', 2, 2, 84, NULL, N'/dom/dispatch!dispatchList.action?menu=5&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (108, N'', NULL, N'108', 4, N'待核稿', 2, 1, 85, NULL, N'/dom/dispatch!dispatchList.action?menu=6', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (109, N'', NULL, N'109', 4, N'已核稿', 2, 2, 85, NULL, N'/dom/dispatch!dispatchList.action?menu=6&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (110, N'', NULL, N'110', 4, N'待盖章', 2, 1, 86, NULL, N'/dom/dispatch!dispatchList.action?menu=7', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (111, N'', NULL, N'111', 4, N'已盖章', 2, 2, 86, NULL, N'/dom/dispatch!dispatchList.action?menu=7&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (112, N'', NULL, N'112', 4, N'待分发', 2, 1, 87, NULL, N'/dom/dispatch!dispatchList.action?menu=8', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (113, N'', NULL, N'113', 4, N'已分发', 2, 2, 87, NULL, N'/dom/dispatch!dispatchList.action?menu=8&searchType=completed', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (114, N'', NULL, N'114', 4, N'收文归档', 2, 1, 88, NULL, N'/dom/gather!gatherList.action?menu=9', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (115, N'', NULL, N'115', 4, N'发文归档', 2, 2, 88, NULL, N'/dom/dispatch!dispatchList.action?menu=10', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (116, N'/images/module_icons/affairs_manager.png', NULL, N'116', 2, N'事务提醒设置', 1, 3, 6, NULL, N'/logined/affairs/affairs_manage.jsp', 0, NULL, NULL, N'swtxsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (119, N'', NULL, N'119', 3, N'信息保密设置', 1, 3, 2, NULL, N'/logined/user/secret_user.jsp', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (120, N'', NULL, N'120', 3, N'群组管理', 1, 4, 2, NULL, N'/logined/group_ext/group.jsp', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (121, N'', NULL, N'121', 3, N'群组人员管理', 1, 5, 2, NULL, N'/logined/group_ext/user.jsp', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (123, NULL, NULL, N'123', 2, N'我的考勤', 1, 1, 26, NULL, NULL, 0, NULL, NULL, N'wdkq')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (126, N'/images/module_icons/wdkq.png', NULL, N'126', 3, N'考勤登记', 1, 0, 123, NULL, N'/logined/attendance/attendanceRegister.jsp', 0, NULL, NULL, N'kqdj')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (127, NULL, NULL, N'127', 3, N'考勤记录', 1, 1, 123, NULL, N'/logined/attendance/attendanceRecord.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (128, N'/images/module_icons/kqsz.png', NULL, N'128', 3, N'考勤设置', 1, 0, 136, NULL, N'/attendance/getIPList.action', 0, NULL, NULL, N'kqsz')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (129, N'/images/module_icons/kqtj.png', NULL, N'129', 3, N'考勤统计', 1, 0, 136, NULL, N'/logined/attendance/attendanceTj.jsp', 0, NULL, NULL, N'kqtj')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (130, N'/images/menu/questionniare.png', NULL, N'dcwj', 1, N'调查问卷', 1, 15, 0, NULL, NULL, 0, NULL, 0, N'xzwj')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (131, N'/images/module_icons/wjlb.png', NULL, N'99998', 2, N'调查问卷', 1, 0, 130, NULL, N'/logined/question/issueList.jsp', 0, NULL, 0, N'tpdc')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (132, N'/images/module_icons/wjgl.png', NULL, N'99921', 2, N'调查问卷管理', 1, 1, 130, NULL, N'/logined/question/questionnaire.jsp', 0, NULL, 0, N'wjgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (133, N'', NULL, N'3000800', 2, N'论坛管理', 1, 6, 26, NULL, N'bbs', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (134, N'/images/module_icons/message.png', NULL, N'134', 2, N'即时消息', 1, 7, 26, NULL, N'/logined/message/send_message.jsp', 0, NULL, 1, N'jsxx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (135, N'/images/module_icons/affairs.png', NULL, N'135', 2, N'事务提醒', 1, 5, 26, NULL, N'/logined/affairs/list_new_affairs.jsp', 0, NULL, 1, N'swtx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (136, N'/images/menu/hr.png', NULL, N'136', 1, N'人力资源', 1, 6, 0, NULL, N'', 1, NULL, 0, N'rlzy')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (137, N'/images/module_icons/dack.png', NULL, N'137', 2, N'档案查看', 1, 1, 136, NULL, N'/logined/record/main.action', 0, NULL, 0, N'dack')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (138, N'/images/module_icons/dagl.png', NULL, N'138', 2, N'档案管理', 1, 2, 136, NULL, N'/logined/record/list_manager.jsp', 0, NULL, 0, N'dagl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (139, N'/images/module_icons/dacx.png', NULL, N'139', 2, N'档案查询', 1, 3, 136, NULL, N'/logined/record/searchPage.action', 0, NULL, 0, N'dacx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (140, NULL, NULL, N'140', 4, N'已审批', 2, 2, 94, NULL, N'/logined/jbpmApp/myjob/completed.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (141, NULL, NULL, N'141', 4, N'未阅', 2, 1, 135, NULL, N'/logined/affairs/list_new_affairs.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (142, NULL, NULL, N'142', 4, N'已阅', 2, 2, 135, NULL, N'/logined/affairs/list_receive_affairs.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (143, NULL, NULL, N'143', 1, N'报表管理', 1, 0, 0, NULL, NULL, 0, NULL, NULL, N'bbgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (144, NULL, NULL, N'144', 2, N'报表填报', 1, 0, 143, NULL, N'/reportInfo/showReport.action?reportType=1', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (145, NULL, NULL, N'145', 2, N'报表审批', 1, 1, 143, NULL, N'/reportInfo/showReport.action?reportType=2', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (146, NULL, NULL, N'146', 2, N'报表查阅', 1, 2, 143, NULL, N'/reportInfo/showReport.action?reportType=3', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (147, NULL, NULL, N'147', 2, N'报表权限设置', 1, 3, 143, NULL, N'/logined/report/list_manager_report.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (148, NULL, NULL, N'148', 2, N'报表基础数据维护', 1, 4, 143, NULL, N'/logined/report/projectBDList.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (149, NULL, NULL, N'149', 4, N'发起消息', 2, 1, 134, NULL, N'/logined/message/send_message.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (150, NULL, NULL, N'150', 4, N'消息记录', 2, 2, 134, NULL, N'/logined/message/message_main.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (151, NULL, NULL, N'151', 4, N'消息查询', 2, 3, 134, NULL, N'/logined/message/list_all_message.jsp', 0, NULL, NULL, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (152, NULL, NULL, N'152', 2, N'意见反馈列表', 1, 2, 130, NULL, N'question/commentIndex.action', 0, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (153, NULL, NULL, N'153', 1, N'抽奖管理', 1, 0, 0, NULL, NULL, 0, NULL, NULL, N'cjgl')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (154, N'/images/module_icons/cjlb.png', NULL, N'154', 2, N'抽奖列表', 1, 1, 153, NULL, N'/logined/lottery/lotteryList.jsp', 0, NULL, NULL, N'cjlb')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (155, N'/images/menu/news_manage.png', N'', N'155', 4, N'待审核', 2, 1, 36, N'', N'/logined/notify/viewApproveNoList.jsp?id=35', 0, N'', 0, N'xwzx')
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (156, N'/images/module_icons/readnotify.png', N'', N'156', 4, N'已审核', 2, 2, 36, N'', N'/logined/notify/viewApproveList.jsp?id=35', 0, N'', 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (157, N'/images/module_icons/notifyManager.png', NULL, N'157', 1, N'知识管理2', 1, 6, 0, NULL, NULL, 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (158, N'/images/module_icons/notAuditing.png', NULL, N'158', 2, N'资料查阅2', 1, 1, 157, NULL, N'/logined/file/viewFileContent.jsp?fileSort=1', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (159, N'/images/module_icons/notify_manager.png', NULL, N'159', 3, N'资料管理2', 1, 2, 157, NULL, N'/logined/file/fileContent.jsp?fileSort=1', 1, NULL, 0, NULL)
INSERT [dbo].[tb_module_info] ([module_id], [icon], [memo], [module_code], [module_level], [module_name], [module_type], [order_index], [parent_id], [sys_name], [url], [is_delete], [module_flag], [module_state], [module_class]) VALUES (160, N'', NULL, N'32', 2, N'公告通知', 1, 1, 31, NULL, N'', 0, NULL, 0, NULL)
/****** Object:  Table [dbo].[tb_info_type]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_info_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_date] [datetime] NULL,
	[info_type] [varchar](30) NULL,
	[is_delete] [int] NULL,
	[modify_date] [datetime] NULL,
	[name] [varchar](100) NULL,
	[record_user_id] [int] NULL,
	[sys_tag] [int] NULL,
	[value] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_history_doc]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_history_doc](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[history_doc_path] [varchar](100) NULL,
	[instance_id] [varchar](100) NULL,
	[update_time] [datetime] NULL,
	[update_user] [varchar](50) NULL,
 CONSTRAINT [PK__tb_histo__3213E83F1A94D1D9] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_group_user]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_group_user](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[company_id] [int] NULL,
	[group_id] [int] NULL,
	[user_id] [int] NULL,
 CONSTRAINT [PK__tb_group__DDB00C7D45FE52CB] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_group_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_group_info](
	[group_id] [int] IDENTITY(1,1) NOT NULL,
	[company_id] [int] NULL,
	[functions] [varchar](500) NULL,
	[group_code] [varchar](50) NULL,
	[group_name] [varchar](50) NULL,
	[group_type] [int] NULL,
	[is_delete] [int] NULL,
	[memo] [varchar](300) NULL,
	[order_index] [int] NULL,
	[parent_id] [int] NULL,
	[path] [varchar](50) NULL,
	[phone] [varchar](50) NULL,
	[assistant_id] [int] NULL,
	[branch] [varchar](50) NULL,
	[director_id] [int] NULL,
	[grade] [int] NULL,
	[group_state] [int] NULL,
	[is_fork_group] [int] NULL,
	[top_change_id] [int] NULL,
	[top_director_id] [int] NULL,
	[user_id] [int] NULL,
	[public_level] [int] NULL,
	[last_update_time] [datetime] NULL,
 CONSTRAINT [PK__tb_group__D57795A0422DC1E7] PRIMARY KEY CLUSTERED 
(
	[group_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_gongwen_var]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_gongwen_var](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[createtime] [datetime] NULL,
	[creater] [varchar](50) NULL,
	[endtime] [datetime] NULL,
	[forkgroup_id] [int] NULL,
	[fromgroup] [varchar](100) NULL,
	[gathersource] [varchar](50) NULL,
	[gongwentypename] [varchar](100) NULL,
	[huanji] [varchar](50) NULL,
	[instanceid] [varchar](50) NULL,
	[miji] [varchar](50) NULL,
	[receivergroup] [varchar](100) NULL,
	[signimg] [varchar](1000) NULL,
	[state] [varchar](50) NULL,
	[title] [varchar](100) NULL,
	[wenhao] [varchar](100) NULL,
	[creater_id] [int] NULL,
 CONSTRAINT [PK__tb_gongw__3213E83F16C440F5] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_document_type]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_document_type](
	[doctype_id] [int] IDENTITY(1,1) NOT NULL,
	[beginnum] [int] NULL,
	[canupdate] [varchar](10) NULL,
	[category_id] [int] NULL,
	[company_id] [int] NULL,
	[disapprove] [varchar](100) NULL,
	[disnigao] [varchar](100) NULL,
	[distaohong] [varchar](100) NULL,
	[disautozip] [int] NULL,
	[do_transform] [int] NULL,
	[doc_bm] [varchar](2000) NULL,
	[doc_desc] [varchar](1000) NULL,
	[doctemplateid] [int] NULL,
	[doctype_name] [varchar](100) NULL,
	[expr] [varchar](100) NULL,
	[force_seal] [int] NULL,
	[form_id] [int] NULL,
	[gatherforceread] [int] NULL,
	[gatherleaderapprove] [varchar](100) NULL,
	[gatherregister] [varchar](100) NULL,
	[gatherautozip] [int] NULL,
	[num_length] [int] NULL,
	[printcode] [varchar](5000) NULL,
	[printtemplatecode] [varchar](5000) NULL,
	[taoda] [int] NULL,
 CONSTRAINT [PK__tb_docum__3E2C625D0781FD65] PRIMARY KEY CLUSTERED 
(
	[doctype_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_document_ext]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_document_ext](
	[documentextid] [int] IDENTITY(1,1) NOT NULL,
	[attachid] [int] NULL,
	[doctypeid] [int] NULL,
	[processinstance_id] [varchar](100) NULL,
	[signdata] [image] NULL,
 CONSTRAINT [PK__tb_docum__DD3EBFC903B16C81] PRIMARY KEY CLUSTERED 
(
	[documentextid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_doc_template]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_doc_template](
	[doctemplate_id] [int] IDENTITY(1,1) NOT NULL,
	[categoryid] [int] NULL,
	[company_id] [int] NULL,
	[create_time] [datetime] NULL,
	[doctemplate_name] [varchar](50) NULL,
	[docurl] [varchar](100) NULL,
	[filename] [varchar](100) NULL,
	[isdelete] [int] NULL,
	[userids] [varchar](2000) NULL,
	[user_names] [varchar](255) NULL,
 CONSTRAINT [PK__tb_doc_t__DAB24DAE7FE0DB9D] PRIMARY KEY CLUSTERED 
(
	[doctemplate_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_data_filter_rule]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_data_filter_rule](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[company_id] [int] NULL,
	[condition_jpql] [varchar](540) NULL,
	[model_class_name] [varchar](128) NULL,
	[name] [varchar](255) NULL,
	[operation_type] [varchar](6) NULL,
	[relation_id] [varchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_data_filter_rule] ON
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (1, 1, N'compyId = :userOrgId', N'cn.com.qytx.oa.file.domain.FileContent', N'只能添加本单位数据', N'CREATE', N'user_3')
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (2, 1, N'keyWord like ''%我们%''', N'cn.com.qytx.oa.file.domain.FileContent', N'只能看到关键字包含 我们 的数据', N'READ', N'user_3')
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (3, 1, N'createUserId = :userId', N'cn.com.qytx.oa.file.domain.FileContent', N'只能删除自己的数据', N'DELETE', N'user_3')
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (4, 1, N'subject like ''%测试数据%''', N'cn.com.qytx.oa.file.domain.FileContent', N'只能修改 测试数据', N'UPDATE', N'user_3')
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (5, 1, N'groupId = :groupId', N'cn.com.qytx.platform.org.domain.UserInfo', N'只能读取本部门数据', N'READ', N'all')
INSERT [dbo].[tb_data_filter_rule] ([id], [company_id], [condition_jpql], [model_class_name], [name], [operation_type], [relation_id]) VALUES (6, 1, N'publishGroupId=:groupId', N'cn.com.qytx.cbb.notify.domain.Notify', N'只能读取本部门数据', N'READ', N'all')
SET IDENTITY_INSERT [dbo].[tb_data_filter_rule] OFF
/****** Object:  Table [dbo].[tb_company_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_company_info](
	[company_id] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](150) NULL,
	[bank] [varchar](100) NULL,
	[bank_account] [varchar](50) NULL,
	[company_code] [varchar](50) NULL,
	[company_name] [varchar](150) NULL,
	[email] [varchar](50) NULL,
	[fax] [varchar](50) NULL,
	[introduction] [varchar](200) NULL,
	[link_man] [varchar](150) NULL,
	[log_url] [varchar](100) NULL,
	[short_name] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[web_site] [varchar](100) NULL,
	[zip_code] [varchar](50) NULL,
	[sys_name] [varchar](50) NULL,
 CONSTRAINT [PK__tb_compa__3E2672353E5D3103] PRIMARY KEY CLUSTERED 
(
	[company_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_company_info] ON
INSERT [dbo].[tb_company_info] ([company_id], [address], [bank], [bank_account], [company_code], [company_name], [email], [fax], [introduction], [link_man], [log_url], [short_name], [tel], [web_site], [zip_code], [sys_name]) VALUES (1, N'河南', NULL, NULL, NULL, N'全亚通信', N'111@email.com', NULL, N'呵呵', N'张三5', N'LOGO/07/31/49fdefe2-a142-4259-8187-fab51b7bdf10.png', N'BJQY01', N'123456789', NULL, NULL, N'全亚世界杯风暴2')
SET IDENTITY_INSERT [dbo].[tb_company_info] OFF
/****** Object:  Table [dbo].[tb_cbb_workflow_var]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_workflow_var](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[advice] [varchar](255) NULL,
	[attach] [varchar](255) NULL,
	[breforetaskname] [varchar](50) NULL,
	[beforeuser] [varchar](50) NULL,
	[candidate_persons] [varchar](50) NULL,
	[createtime] [datetime] NULL,
	[creater] [varchar](50) NULL,
	[current_state] [varchar](50) NULL,
	[currenttaskname] [varchar](50) NULL,
	[currentuser] [varchar](50) NULL,
	[instanceid] [varchar](255) NULL,
	[processattributeid] [int] NULL,
	[refprocess] [varchar](255) NULL,
	[rollbackinfo] [varchar](255) NULL,
	[sign_comments_map] [varchar](255) NULL,
	[suspendtime] [datetime] NULL,
	[title] [varchar](50) NULL,
 CONSTRAINT [PK__tb_cbb_w__3213E83F40BA7AC1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_process_attribute]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_process_attribute](
	[process_attribute_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[company_id] [int] NULL,
	[create_time] [datetime] NULL,
	[dept] [int] NULL,
	[directions] [varchar](255) NULL,
	[form_id] [int] NULL,
	[is_attach] [int] NULL,
	[printcode] [varchar](255) NULL,
	[printtemplatecode] [text] NULL,
	[process_define_byxml] [text] NULL,
	[process_define_id] [varchar](50) NULL,
	[process_name_num_length] [int] NULL,
	[process_name] [varchar](50) NULL,
	[process_name_begin_num] [int] NULL,
	[process_name_can_update] [int] NULL,
	[process_name_expr] [varchar](50) NULL,
	[process_order] [int] NULL,
	[process_state] [int] NULL,
	[process_define_byjson] [text] NULL,
	[redtemplate] [int] NULL,
	[selectusermode] [int] NULL,
	[taoda] [int] NULL,
	[update_time] [datetime] NULL,
	[process_name_beginnum] [int] NULL,
 CONSTRAINT [PK__tb_cbb_p__3B223862774B959C] PRIMARY KEY CLUSTERED 
(
	[process_attribute_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_attachment]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_om_attachment](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[attach_file] [varchar](200) NOT NULL,
	[attach_name] [varchar](200) NOT NULL,
	[compy_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NOT NULL,
	[is_delete] [int] NULL,
	[attach_size] [numeric](19, 0) NULL,
 CONSTRAINT [PK__tb_om_at__DDB00C7D5FA91635] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_daily]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_daily](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appendix] [varchar](5000) NULL,
	[attach_name] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [text] NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[daily_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[is_review] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[review_num] [int] NULL,
	[title] [varchar](500) NULL,
	[type] [int] NULL,
	[send_type] [varchar](255) NULL,
 CONSTRAINT [PK__tb_op_da__3213E83F0BBCA29D] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_countdown]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_countdown](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[background_color] [varchar](255) NULL,
	[begin_time] [datetime] NULL,
	[compy_id] [int] NULL,
	[content] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[end_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[order_no] [varchar](50) NULL,
 CONSTRAINT [PK__tb_op_co__3213E83F4D005615] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_op_co__3213E83E4FDCC2C0] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_calendar]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_calendar](
	[calendar_id] [int] IDENTITY(1,1) NOT NULL,
	[before_remaind] [varchar](50) NULL,
	[beg_time] [datetime] NULL,
	[beg_time_str] [varchar](255) NULL,
	[cal_level] [int] NULL,
	[cal_type] [int] NULL,
	[calendar_type] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[create_user_name] [varchar](255) NULL,
	[end_time] [datetime] NULL,
	[end_time_str] [varchar](255) NULL,
	[is_delete] [int] NULL,
	[is_remaind] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[owner] [varchar](255) NULL,
	[status] [int] NULL,
	[taker] [varchar](255) NULL,
	[taker_user_names] [varchar](255) NULL,
	[work_type] [int] NULL,
 CONSTRAINT [PK__tb_op_ca__584C134446535886] PRIMARY KEY CLUSTERED 
(
	[calendar_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_op_ca__584C1345492FC531] UNIQUE NONCLUSTERED 
(
	[calendar_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_affair]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_affair](
	[aff_id] [int] IDENTITY(1,1) NOT NULL,
	[authority] [varchar](255) NULL,
	[begin_time] [datetime] NULL,
	[begin_time_time] [varchar](50) NULL,
	[calendar_type] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[end_time] [datetime] NULL,
	[end_time_time] [varchar](50) NULL,
	[is_delete] [int] NULL,
	[is_remaind] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[remind_date] [varchar](50) NULL,
	[remind_time] [varchar](50) NULL,
	[remind_type] [int] NULL,
	[taker] [varchar](50) NULL,
	[type] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[aff_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[aff_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_on_file_sort]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_on_file_sort](
	[sort_id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[create_user] [varchar](255) NULL,
	[create_user_id] [int] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[parent_id] [int] NULL,
	[path] [varchar](255) NULL,
	[sort_name] [varchar](255) NULL,
	[sort_no] [varchar](255) NULL,
	[sort_type] [varchar](255) NULL,
	[version] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[sort_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_oab_address_group]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_oab_address_group](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[addressid] [int] NOT NULL,
	[groupid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_oc_check]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_oc_check](
	[check_id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[create_user] [varchar](255) NULL,
	[create_user_id] [int] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[module_name] [varchar](255) NULL,
	[read_time] [datetime] NULL,
	[ref_id] [int] NULL,
	[version] [varchar](255) NULL,
 CONSTRAINT [PK__tb_oc_ch__C0EB8718546C6DB3] PRIMARY KEY CLUSTERED 
(
	[check_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_affair_setting]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_om_affair_setting](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[affair_priv] [int] NULL,
	[module_name] [varchar](255) NULL,
	[push_priv] [int] NULL,
	[sms_priv] [int] NULL,
	[module_code] [varchar](255) NULL,
 CONSTRAINT [PK__tb_om_af__3213E83F78FED3E4] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_om_af__3213E83E7BDB408F] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_om_affair_setting] ON
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (1, 1, N'工作日志', 1, 1, N'gzrz')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (2, 1, N'内部邮件', 1, 1, N'nbyj')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (3, 1, N'公告通知', 0, 1, N'ggtz')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (4, 1, N'工作流：审批通过', 1, 0, N'gzl_txxybjbr')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (5, 1, N'工作流：驳回', 1, 0, N'gzl_bohui')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (6, 1, N'工作流：撤销', 1, 0, N'gzl_chexiao')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (7, 1, N'公文管理：发文核稿', 1, 0, N'gwgl_fwhg')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (8, 1, N'公文管理：套红盖章', 1, 0, N'gwgl_thgz')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (9, 1, N'公文管理：发文分发', 1, 0, N'gwgl_fwff')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (10, 1, N'公文管理：收文登记', 1, 0, N'gwgl_swdj')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (11, 1, N'公文管理：领导批阅', 1, 0, N'gwgl_ldpy')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (12, 1, N'公文管理：收文分发', 0, 0, N'gwgl_swff')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (13, 1, N'公文管理：发文拟稿', 0, 0, N'gwgl_fwng')
INSERT [dbo].[tb_om_affair_setting] ([id], [affair_priv], [module_name], [push_priv], [sms_priv], [module_code]) VALUES (14, 1, N'任务管理', 0, 0, N'rwgl')
SET IDENTITY_INSERT [dbo].[tb_om_affair_setting] OFF
/****** Object:  Table [dbo].[tb_report_ProjectDic]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ProjectDic](
	[projectId] [int] IDENTITY(1,1) NOT NULL,
	[projectName] [varchar](100) NULL,
	[createTime] [datetime] NULL,
	[creater] [varchar](50) NULL,
	[createUserId] [int] NULL,
	[note] [varchar](2000) NULL,
	[isDelete] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[projectId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_KongGuGongSiCaiWuQingKuang]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_KongGuGongSiCaiWuQingKuang](
	[id] [varchar](100) NOT NULL,
	[companyName] [varchar](100) NULL,
	[level] [varchar](50) NULL,
	[companyType] [varchar](200) NULL,
	[mainBusiness] [varchar](200) NULL,
	[registFund] [float] NULL,
	[holdingsScale] [float] NULL,
	[votingRightScale] [float] NULL,
	[getType] [varchar](50) NULL,
	[isCombine] [int] NULL,
	[employeeNum] [int] NULL,
	[companyIntroduce] [varchar](1000) NULL,
	[financeTarget] [varchar](1000) NULL,
	[creater] [varchar](50) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](50) NULL,
	[createGroupID] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[reportTime] [datetime] NULL,
	[applyBatch] [varchar](100) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'企业名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'companyName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'级次' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'level'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'企业类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'companyType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主营业务' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'mainBusiness'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'注册资金' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'registFund'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'持股比例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'holdingsScale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'享有表决权的比例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'votingRightScale'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'取得方式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'getType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否纳入合并范围' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'isCombine'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'职工人数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'employeeNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公司介绍' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'companyIntroduce'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'财务指标分析' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_KongGuGongSiCaiWuQingKuang', @level2type=N'COLUMN',@level2name=N'financeTarget'
GO
/****** Object:  Table [dbo].[tb_report_holdCompanyMonthSummary]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_holdCompanyMonthSummary](
	[serialNumber] [varchar](100) NOT NULL,
	[companyName] [varchar](200) NULL,
	[lastYearSignTotal] [float] NULL,
	[lastYearInvestTotal] [float] NULL,
	[thisYearSign] [float] NULL,
	[thisYearInvest] [float] NULL,
	[thisYearProjectInvest] [float] NULL,
	[thisYearOtherInvest] [float] NULL,
	[monthSignPlan] [float] NULL,
	[monthSignComplete] [float] NULL,
	[monthInvestPlan] [float] NULL,
	[monthInvestComplete] [float] NULL,
	[monthProjectInvestPlan] [float] NULL,
	[monthProjectInvestComplete] [float] NULL,
	[monthOtherInvestPlan] [float] NULL,
	[monthOtherInvestConplete] [float] NULL,
	[investYear] [varchar](100) NULL,
	[investMonth] [varchar](100) NULL,
	[createTime] [datetime] NULL,
	[updateTime] [datetime] NULL,
	[reportDate] [date] NULL,
	[creater] [varchar](100) NULL,
	[createID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[createGroupId] [int] NULL,
	[unit] [varchar](100) NULL,
	[applyBatch] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_GuDingZiChangBianDong]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_GuDingZiChangBianDong](
	[serialNumber] [varchar](100) NOT NULL,
	[companyName] [varchar](500) NULL,
	[projectNumber] [int] NULL,
	[projectName] [varchar](100) NULL,
	[beginMoney] [float] NULL,
	[increaseMoney] [float] NULL,
	[reduceMoney] [float] NULL,
	[endMoney] [float] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[creater] [varchar](50) NULL,
	[createID] [int] NULL,
	[groupID] [int] NULL,
	[groupName] [varchar](100) NULL,
	[updateTime] [datetime] NULL,
	[createTime] [datetime] NULL,
	[parentNumber] [int] NULL,
	[sortNo] [int] NULL,
	[applyBatch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_DangBaoDiYaHeTongTaiZhang]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_DangBaoDiYaHeTongTaiZhang](
	[serialNumber] [varchar](100) NOT NULL,
	[assureUnit] [varchar](100) NULL,
	[securedUnit] [varchar](100) NULL,
	[contractNumber] [varchar](100) NULL,
	[assureMoney] [float] NULL,
	[assureTime] [varchar](100) NULL,
	[assureWay] [varchar](100) NULL,
	[assureBank] [varchar](100) NULL,
	[state] [varchar](100) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[createTime] [datetime] NULL,
	[updateTime] [datetime] NULL,
	[createID] [int] NULL,
	[createUserName] [varchar](100) NULL,
	[groupId] [int] NULL,
	[groupName] [varchar](50) NULL,
	[applyBatch] [varchar](100) NULL,
	[companyName] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ChanQuanGuQuan]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ChanQuanGuQuan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[companyName] [varchar](100) NULL,
	[compantType] [int] NULL,
	[grade] [varchar](12) NULL,
	[construction] [float] NULL,
	[capital] [float] NULL,
	[stockProportion] [float] NULL,
	[lastProperty] [float] NULL,
	[legalPerson] [varchar](100) NULL,
	[leader] [varchar](100) NULL,
	[employeeNumber] [int] NULL,
	[manageRange] [varchar](256) NULL,
	[business] [varchar](256) NULL,
	[comment] [varchar](200) NULL,
	[applyBatch] [varchar](100) NULL,
	[createTime] [datetime] NULL,
	[intputYear] [int] NULL,
	[intputMonth] [int] NULL,
 CONSTRAINT [PK__tb_repor__3213E83F0000D72E] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ChanQuan_GuQuanInvestPlan]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan](
	[serialNumber] [varchar](100) NOT NULL,
	[companyName] [varchar](100) NULL,
	[projectName] [varchar](100) NULL,
	[projectContent] [varchar](100) NULL,
	[hangyeClass] [varchar](100) NULL,
	[investDirection] [int] NULL,
	[investObject] [varchar](100) NULL,
	[projectTotalInvest] [float] NULL,
	[companyInvest] [float] NULL,
	[companyPrivateMoney] [float] NULL,
	[companyBankLoan] [float] NULL,
	[companyOtherMoney] [float] NULL,
	[companyShare] [float] NULL,
	[investCondition] [varchar](500) NULL,
	[preInvestReturn] [float] NULL,
	[note] [varchar](1000) NULL,
	[reportName] [varchar](100) NULL,
	[phone] [varchar](100) NULL,
	[email] [varchar](100) NULL,
	[reportDate] [date] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[createTime] [datetime] NULL,
	[creater] [varchar](100) NULL,
	[createID] [int] NULL,
	[groupName] [varchar](100) NULL,
	[groupID] [int] NULL,
	[updateTime] [datetime] NULL,
	[applyBatch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1代表"主业投资" 2代表"非主业投资"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ChanQuan_GuQuanInvestPlan', @level2type=N'COLUMN',@level2name=N'investDirection'
GO
/****** Object:  Table [dbo].[tb_report_applyBatch]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_applyBatch](
	[id] [varchar](50) NOT NULL,
	[createTime] [datetime] NULL,
	[createUserId] [int] NULL,
	[createUserName] [varchar](50) NULL,
	[groupName] [varchar](50) NULL,
	[reportName] [varchar](100) NULL,
	[status] [int] NULL,
	[approveId] [int] NULL,
	[approveName] [varchar](50) NULL,
	[updateTime] [datetime] NULL,
	[attachmentIds] [varchar](100) NULL,
	[isTrue] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_sys_config]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_sys_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[checkpagemodule] [varchar](255) NULL,
	[config_key] [varchar](50) NULL,
	[config_value] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_sys_config] ON
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (216, NULL, N'dom_gather_zip', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (217, NULL, N'dom_dis_zip', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (218, NULL, N'force_read', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (219, NULL, N'dom_gather_register', N'转领导批阅,转收文分发,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (220, NULL, N'dom_gather_approve', N'转领导批阅,转收文分发,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (221, NULL, N'dom_dis_nigao', N'转核稿,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (222, NULL, N'dom_dis_hegao', N'转核稿,转盖章,转发文分发,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (223, NULL, N'dom_dis_red', N'转核稿,转发文分发,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (224, NULL, N'approve_widget', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (225, NULL, N'reader_widget', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (226, NULL, N'approve_comment', N'1')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (227, NULL, N'notice_update_password', N'yes')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (228, NULL, N'bumenzhuanlan', N'32768,393254,393228,')
INSERT [dbo].[tb_sys_config] ([id], [checkpagemodule], [config_key], [config_value]) VALUES (229, NULL, N'bumenzhuanlan_name', N'技术部,测试最大长度测试,综合部,')
SET IDENTITY_INSERT [dbo].[tb_sys_config] OFF
/****** Object:  Table [dbo].[tb_scheduleinfo]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_scheduleinfo](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[action] [varchar](255) NULL,
	[actiontype] [int] NULL,
	[cronexpression] [varchar](255) NULL,
	[memo] [varchar](255) NULL,
	[schedulename] [varchar](255) NULL,
	[schedulestate] [int] NULL,
 CONSTRAINT [PK__tb_sched__DDB00C7D1CE72E9F] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_sched__DDB00C7C1FC39B4A] UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_schedule_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_schedule_info](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[action] [varchar](255) NULL,
	[action_type] [int] NULL,
	[cron_expression] [varchar](255) NULL,
	[memo] [varchar](255) NULL,
	[schedule_name] [varchar](255) NULL,
	[schedule_state] [int] NULL,
 CONSTRAINT [PK__tb_sched__DDB00C7D2F05DEDA] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UQ__tb_sched__DDB00C7C31E24B85] UNIQUE NONCLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_role_user]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_role_user](
	[role_id] [int] NULL,
	[is_delete] [int] NULL,
	[memo] [varchar](100) NULL,
	[order_index] [int] NULL,
	[role_code] [varchar](50) NULL,
	[role_name] [varchar](50) NULL,
	[role_type] [int] NULL,
	[company_id] [int] NULL,
	[type] [int] NULL,
	[user_id] [int] NULL,
	[vid] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_tb_role_user] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_role_user] ON
INSERT [dbo].[tb_role_user] ([role_id], [is_delete], [memo], [order_index], [role_code], [role_name], [role_type], [company_id], [type], [user_id], [vid]) VALUES (1, 0, NULL, NULL, NULL, NULL, NULL, 1, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[tb_role_user] OFF
/****** Object:  Table [dbo].[tb_role_module]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_role_module](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NULL,
	[role_id] [int] NULL,
 CONSTRAINT [PK__tb_role___DDB00C7D51700577] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tb_role_module] ON
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (1, 1, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (2, 2, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (3, 3, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (4, 4, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (5, 5, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (6, 6, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (7, 7, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (8, 8, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (9, 9, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (10, 10, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (11, 11, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (12, 12, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (13, 13, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (14, 14, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (15, 15, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (16, 16, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (17, 17, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (18, 18, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (19, 19, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (20, 20, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (21, 21, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (22, 22, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (23, 23, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (24, 24, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (25, 25, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (26, 26, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (27, 27, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (28, 28, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (29, 29, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (30, 30, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (31, 31, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (32, 32, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (33, 33, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (34, 34, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (35, 35, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (36, 36, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (37, 37, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (38, 38, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (39, 39, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (40, 40, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (41, 41, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (42, 42, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (43, 43, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (44, 44, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (45, 45, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (46, 46, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (47, 47, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (48, 48, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (49, 49, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (50, 50, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (51, 51, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (52, 52, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (53, 53, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (54, 54, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (55, 55, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (56, 56, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (57, 57, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (58, 58, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (59, 59, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (60, 60, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (61, 61, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (62, 62, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (63, 63, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (64, 64, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (65, 65, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (66, 66, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (67, 67, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (68, 68, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (69, 69, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (70, 70, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (71, 71, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (72, 72, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (73, 73, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (74, 74, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (75, 75, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (76, 76, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (77, 77, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (78, 78, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (79, 79, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (80, 80, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (81, 81, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (82, 82, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (83, 83, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (84, 84, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (85, 85, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (86, 86, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (87, 87, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (88, 88, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (89, 89, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (90, 90, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (91, 91, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (92, 92, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (93, 93, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (94, 94, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (95, 95, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (96, 96, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (97, 97, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (98, 98, 1)
INSERT [dbo].[tb_role_module] ([vid], [module_id], [role_id]) VALUES (99, 99, 1)
SET IDENTITY_INSERT [dbo].[tb_role_module] OFF
GO
print 'Processed 100 total records'
/****** Object:  Table [dbo].[tb_role_info]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_role_info](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[is_delete] [int] NULL,
	[memo] [varchar](100) NULL,
	[order_index] [int] NULL,
	[role_code] [varchar](50) NULL,
	[role_name] [varchar](50) NULL,
	[role_type] [int] NULL,
 CONSTRAINT [PK__tb_role___760965CC4D9F7493] PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_role_info] ON
INSERT [dbo].[tb_role_info] ([role_id], [is_delete], [memo], [order_index], [role_code], [role_name], [role_type]) VALUES (1, 0, N'', 0, N'admin', N'管理员', 0)
INSERT [dbo].[tb_role_info] ([role_id], [is_delete], [memo], [order_index], [role_code], [role_name], [role_type]) VALUES (2, 0, N'收文登记员', 0, N'收文登记员', N'收文登记员', 0)
SET IDENTITY_INSERT [dbo].[tb_role_info] OFF
/****** Object:  Table [dbo].[tb_reportInfo]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_reportInfo](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[reportName] [varchar](100) NULL,
	[reportCategory] [int] NULL,
	[reportType] [int] NOT NULL,
	[roles] [text] NULL,
	[roleNames] [text] NULL,
	[url] [varchar](300) NULL,
	[memo] [varchar](4000) NULL,
	[orderIndex] [int] NULL,
	[compy_Id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_reportInfo] ON
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (1, N'招标控制价审批表', 36, 1, N',1,', N'超级管理员', N'/reportJsp/controlPrice/findControlPriceApplyBatch.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (2, N'招标结果备案表', 37, 1, N',1,', N'超级管理员', N'//reportJsp/tenderResult/tenderResultBatch.jsp', NULL, 2, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (3, N'招标结果备案表', 37, 2, N',1,', N'超级管理员', N'reportJsp/tenderResult/tenderResultApproveGroup.jsp', NULL, 2, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (5, N'招标结果备案表', 37, 3, N',1,', N'超级管理员', N'reportJsp/tenderResult/tenderResultTotal.jsp', NULL, 2, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (7, N'招标控制价审批表', 36, 2, N',1,', N'超级管理员', N'reportJsp/controlPrice/controlPriceApproveBatchGroup.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (8, N'招标控制价审批表', 36, 3, N',1,', N'超级管理员', N'reportJsp/controlPrice/allControlPrice.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (10, N'固定资产投资表', 36, 1, N',1,', N'超级管理员', N'reportJsp/investProjectPlan/findInvestPlanApplyBatch.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (11, N'固定资产投资表', 36, 2, N',1,', N'超级管理员', N'reportJsp/investProjectPlan/investPorjectPlanApproveBatchGroup.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (12, N'固定资产投资表', 36, 3, N',1,', N'超级管理员', N'reportJsp/investProjectPlan/InvestPlanSummary.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (17, N'招标方案报送表', 37, 1, N',1,', N'超级管理员', N'reportJsp/fangAnBaoSong/batch.jsp', NULL, 3, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (18, N'招标方案报送表', 37, 2, N',1,', N'超级管理员', N'reportJsp/fangAnBaoSong/approveGroup.jsp', NULL, 3, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (19, N'招标方案报送表', 37, 3, N',1,', N'超级管理员', N'reportJsp/fangAnBaoSong/search.jsp', NULL, 3, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (20, N'控股公司月度投资汇总', 36, 1, N',1,', N'超级管理员', N'reportJsp/holdCompanyMonthSummary/showBatchList.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (21, N'控股公司月度投资汇总', 36, 2, N',1,', N'超级管理员', N'reportJsp/holdCompanyMonthSummary/approve.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (22, N'控股公司月度投资汇总', 36, 3, N',1,', N'超级管理员', N'reportJsp/holdCompanyMonthSummary/findApply.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (23, N'资金借入合同', 36, 1, N',1,', N'超级管理员', N'reportJsp/ziJinJieRuHeTong/showBatchList.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (24, N'资金借入合同', 36, 2, N',1,', N'超级管理员', N'reportJsp/ziJinJieRuHeTong/approve.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (25, N'资金借入合同', 36, 3, N',1,', N'超级管理员', N'reportJsp/ziJinJieRuHeTong/summary.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (26, N'担保、抵质押合同明细台账', 36, 1, N',1,', N'超级管理员', N'reportJsp/dangBaoDiYaHeTong/showBatchList.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (27, N'担保、抵质押合同明细台账', 36, 2, N',1,', N'超级管理员', N'reportJsp/dangBaoDiYaHeTong/approve.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (30, N'担保、抵质押合同明细台账', 36, 3, N',1,', N'超级管理员', N'reportJsp/dangBaoDiYaHeTong/summary.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (31, N'月度固定资产投资项目进度表', 37, 1, N',1,', N'超级管理员', N'reportJsp/monthInvestSchedule/monthInvestScheduleBatch.jsp', NULL, 4, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (32, N'月度固定资产投资项目进度表', 37, 2, N',1,', N'超级管理员', N'reportJsp/monthInvestSchedule/monthInvestScheduleApproveGroup.jsp', NULL, 4, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (33, N'月度固定资产投资项目进度表', 37, 3, N',1,', N'超级管理员', N'reportJsp/monthInvestSchedule/monthInvestScheduleTotal.jsp', NULL, 4, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (34, N'产权收购、股权投资计划表', 36, 1, N',1,', N'超级管理员', N'reportJsp/equityInvestPlan/showBatchList.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (35, N'产权收购、股权投资计划表', 36, 2, N',1,', N'超级管理员', N'reportJsp/equityInvestPlan/approve.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (36, N'产权收购、股权投资计划表', 36, 3, N',1,', N'超级管理员', N'reportJsp/equityInvestPlan/summary.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (37, N'资金使用计划表', 37, 1, N',1,', N'超级管理员', N'reportJsp/monthInvestUsePlan/monthInvestUsePlanBatch.jsp', NULL, 5, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (38, N'资金使用计划表', 37, 2, N',1,', N'超级管理员', N'reportJsp/monthInvestUsePlan/monthInvestUsePlanApproveGroup.jsp', NULL, 5, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (39, N'资金使用计划表', 37, 3, N',1,', N'超级管理员', N'reportJsp/monthInvestUsePlan/monthInvestUsePlanSearch.jsp', NULL, 5, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (40, N'固定资产变动表', 36, 1, N',1,', N'超级管理员', N'reportJsp/guDingZiChan/showBatchList.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (41, N'固定资产变动表', 36, 2, N',1,', N'超级管理员', N'reportJsp/guDingZiChan/approve.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (44, N'固定资产变动表', 36, 3, N',1,', N'超级管理员', N'reportJsp/guDingZiChan/summary.jsp', NULL, 1, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (45, N'控股公司财务情况说明表', 37, 1, N',1,', N'超级管理员', N'reportJsp/holdingCompanyFinance/batch.jsp', NULL, 6, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (46, N'控股公司财务情况说明表', 37, 2, N',1,', N'超级管理员', N'reportJsp/holdingCompanyFinance/approveGroup.jsp', NULL, 6, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (47, N'控股公司财务情况说明表', 37, 3, N',1,', N'超级管理员', N'reportJsp/holdingCompanyFinance/search.jsp', NULL, 6, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (48, N'应交税费变动表', 37, 1, N',1,', N'超级管理员', N'reportJsp/yingJiaoShuiFeiBianDong/batch.jsp', NULL, 7, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (49, N'应交税费变动表', 37, 2, N',1,', N'超级管理员', N'reportJsp/yingJiaoShuiFeiBianDong/approveGroup.jsp', NULL, 7, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (50, N'应交税费变动表', 37, 3, N',1,', N'超级管理员', N'reportJsp/yingJiaoShuiFeiBianDong/total.jsp', NULL, 7, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (51, N'在建项目进度表', 37, 1, N',1,', N'超级管理员', N'reportJsp/constructingProject/batch.jsp', NULL, 8, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (52, N'在建项目进度表', 37, 2, N',1,', N'超级管理员', N'reportJsp/constructingProject/approveGroup.jsp', NULL, 8, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (54, N'在建项目进度表', 37, 3, N',1,', N'超级管理员', N'reportJsp/constructingProject/total.jsp', NULL, 8, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (56, N'应付职工薪酬变动表', 37, 1, N',1,', N'超级管理员', N'reportJsp/zhiGongXinChouBianDong/batch.jsp', NULL, 9, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (57, N'应付职工薪酬变动表', 37, 2, N',1,', N'超级管理员', N'reportJsp/zhiGongXinChouBianDong/approveGroup.jsp', NULL, 9, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (58, N'应付职工薪酬变动表', 37, 3, N',1,', N'超级管理员', N'reportJsp/zhiGongXinChouBianDong/total.jsp', NULL, 9, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (59, N'产权收购、股权投资等投资项目汇总表', 37, 1, N',11612431,11612416,1,', N'111,张玉莹,超级管理员', N'reportJsp/chanQuanGuQuan/batch.jsp', NULL, 10, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (60, N'产权收购、股权投资等投资项目汇总表', 37, 2, N',1,', N'超级管理员', N'reportJsp/chanQuanGuQuan/approveGroup.jsp', NULL, 10, 1)
INSERT [dbo].[tb_reportInfo] ([vid], [reportName], [reportCategory], [reportType], [roles], [roleNames], [url], [memo], [orderIndex], [compy_Id]) VALUES (61, N'产权收购、股权投资等投资项目汇总表', 37, 3, N',1,', N'超级管理员', N'reportJsp/chanQuanGuQuan/total.jsp', NULL, 10, 1)
SET IDENTITY_INSERT [dbo].[tb_reportInfo] OFF
/****** Object:  Table [dbo].[tb_report_ZiJinJieRuHeTong]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZiJinJieRuHeTong](
	[serialNumber] [varchar](100) NOT NULL,
	[companyName] [varchar](100) NULL,
	[financialGroup] [varchar](100) NULL,
	[creditMoney] [float] NULL,
	[borrowMoney] [float] NULL,
	[borrowDate] [varchar](100) NULL,
	[borrowTimeLimit] [varchar](100) NULL,
	[interestRate] [varchar](100) NULL,
	[jieXiWay] [varchar](100) NULL,
	[borrowWay] [varchar](100) NULL,
	[state] [varchar](100) NULL,
	[createID] [int] NULL,
	[creater] [varchar](100) NULL,
	[groupId] [int] NULL,
	[groupName] [varchar](100) NULL,
	[createTime] [datetime] NULL,
	[updateTime] [datetime] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[applyBatch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ZhaoBiaoKongZhiJiaShenPi]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZhaoBiaoKongZhiJiaShenPi](
	[serialNumber] [varchar](100) NOT NULL,
	[projectName] [varchar](200) NOT NULL,
	[batch] [varchar](100) NOT NULL,
	[sectionName] [varchar](200) NOT NULL,
	[sectionNumber] [varchar](100) NOT NULL,
	[bidControlPrice] [float] NULL,
	[bidControlName] [varchar](200) NULL,
	[creater] [varchar](100) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[cteateGroupID] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[applyBatch] [varchar](100) NULL,
	[reportTime] [date] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ZhaoBiaoJieGuoBeiAn]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZhaoBiaoJieGuoBeiAn](
	[serialNumber] [varchar](100) NOT NULL,
	[projectName] [varchar](200) NOT NULL,
	[batch] [varchar](100) NOT NULL,
	[bidTime] [varchar](50) NOT NULL,
	[sectionName] [varchar](200) NOT NULL,
	[sectionNumber] [varchar](100) NOT NULL,
	[bidderName] [varchar](200) NOT NULL,
	[bidMoney] [float] NULL,
	[creater] [varchar](100) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[cteateGroupID] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[approveName] [varchar](100) NULL,
	[approveID] [int] NULL,
	[applyBatch] [varchar](100) NULL,
	[reportTime] [date] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[serialNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ZhaoBiaoFangAnBD]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZhaoBiaoFangAnBD](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sectionName] [varchar](200) NOT NULL,
	[sectionNumber] [varchar](100) NOT NULL,
	[bidMode] [varchar](50) NULL,
	[sectionContent] [varchar](200) NULL,
	[requirement] [varchar](200) NULL,
	[bidTime] [varchar](100) NULL,
	[parentId] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ZhaoBiaoFangAnBaoSong]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZhaoBiaoFangAnBaoSong](
	[id] [varchar](100) NOT NULL,
	[projectName] [varchar](200) NOT NULL,
	[buildName] [varchar](200) NOT NULL,
	[bidPersonName] [varchar](100) NOT NULL,
	[projectAddress] [varchar](200) NOT NULL,
	[bidContactPerson] [varchar](100) NOT NULL,
	[bidContactPhone] [varchar](100) NOT NULL,
	[investment] [varchar](100) NOT NULL,
	[capitalResource] [varchar](100) NOT NULL,
	[projectProfile] [varchar](500) NULL,
	[bidMode] [varchar](100) NULL,
	[organizationMode] [varchar](100) NULL,
	[agencyName] [varchar](100) NULL,
	[agencyContactPerson] [varchar](100) NULL,
	[agencyContactPhone] [varchar](100) NULL,
	[bidBatch] [varchar](100) NULL,
	[alreadyCondition] [varchar](500) NULL,
	[creater] [varchar](100) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[cteateGroupID] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[approveName] [varchar](100) NULL,
	[approveID] [int] NULL,
	[applyBatch] [varchar](100) NULL,
	[status] [int] NULL,
	[inputYear] [int] NULL,
	[inputMonth] [int] NULL,
	[reportTime] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_ZaiJianXiangMuJinDu]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_ZaiJianXiangMuJinDu](
	[id] [varchar](100) NOT NULL,
	[projectNum] [int] NULL,
	[projectName] [varchar](100) NULL,
	[subprojectNum] [int] NULL,
	[subprojectName] [varchar](100) NULL,
	[sectionNum] [varchar](50) NULL,
	[sectionName] [varchar](100) NULL,
	[undertaker] [varchar](50) NULL,
	[contractContent] [varchar](500) NULL,
	[planInvest] [float] NULL,
	[planDate] [varchar](50) NULL,
	[schedule] [varchar](500) NULL,
	[thisMonthPaid] [float] NULL,
	[accPaid] [float] NULL,
	[totalPricePercentage] [float] NULL,
	[thisMonthComInvest] [float] NULL,
	[nextMonthInvestPlan] [float] NULL,
	[yearAccumulative] [float] NULL,
	[yearAccPercentage] [float] NULL,
	[startToNowInvest] [float] NULL,
	[totalinvestPercentage] [float] NULL,
	[startDate] [date] NULL,
	[endDate] [date] NULL,
	[clearingDate] [date] NULL,
	[alterItems] [varchar](100) NULL,
	[remark] [varchar](100) NULL,
	[creater] [varchar](100) NULL,
	[createrId] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[createGroupId] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[applyBatch] [varchar](100) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[companyName] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'projectNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'projectName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'子项目序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'subprojectNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'子项目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'subprojectName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标段序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'sectionNum'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标段名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'sectionName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'承包方' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'undertaker'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'合同内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'contractContent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计划投资总额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'planInvest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计划工期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'planDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工程形象进度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'schedule'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月支付工程款' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'thisMonthPaid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'累计支付工程款' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'accPaid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'占总价款比例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'totalPricePercentage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'thisMonthComInvest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下月资金使用计划' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'nextMonthInvestPlan'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'年度累计完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'yearAccumulative'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'年度累计投资占年度计划投资比例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'yearAccPercentage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开工以来累计完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'startToNowInvest'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'占总投资比例' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'totalinvestPercentage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'实际开工日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'startDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'完工日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'endDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'竣工结算日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'clearingDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'变更事项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'alterItems'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_ZaiJianXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'remark'
GO
/****** Object:  Table [dbo].[tb_report_YueFenZiJinShiYongJiHua]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_YueFenZiJinShiYongJiHua](
	[id] [varchar](100) NOT NULL,
	[projectName] [varchar](100) NULL,
	[totalInvest] [float] NULL,
	[earlierPaidMoney] [float] NULL,
	[thisMonthPlan] [float] NULL,
	[remark] [varchar](500) NULL,
	[creater] [varchar](50) NULL,
	[createrId] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[createGroupId] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[reportTime] [datetime] NULL,
	[applyBatch] [varchar](100) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[companyName] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenZiJinShiYongJiHua', @level2type=N'COLUMN',@level2name=N'projectName'
GO
/****** Object:  Table [dbo].[tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu](
	[id] [varchar](100) NOT NULL,
	[unitName] [varchar](100) NULL,
	[projectName] [varchar](200) NULL,
	[projectDetails] [varchar](500) NULL,
	[startDate] [varchar](50) NULL,
	[totalAmount] [float] NULL,
	[startToNowAmount] [float] NULL,
	[yearPlanAmount] [float] NULL,
	[yearToNowAmount] [float] NULL,
	[monthToNowAmount] [float] NULL,
	[schedule] [varchar](500) NULL,
	[endDate] [varchar](50) NULL,
	[remark] [varchar](500) NULL,
	[creater] [varchar](100) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[cteateGroupID] [int] NULL,
	[createTime] [date] NULL,
	[updateTime] [date] NULL,
	[applyBatch] [varchar](100) NULL,
	[reportTime] [datetime] NULL,
	[reporterPhone] [varchar](20) NULL,
	[reporterFax] [varchar](20) NULL,
	[reporterEmail] [varchar](200) NULL,
	[receiveUnit] [varchar](100) NULL,
	[contact] [varchar](20) NULL,
	[cPhone] [varchar](20) NULL,
	[cEmail] [varchar](200) NULL,
	[reporter] [varchar](20) NULL,
	[reportGroupName] [varchar](200) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键,表中唯一标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'填报单位名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'unitName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'projectName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'projectDetails'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开工日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'startDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投资总额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'totalAmount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'自开工止累计完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'startToNowAmount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本年计划投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'yearPlanAmount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本年累计完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'yearToNowAmount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月完成投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'monthToNowAmount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目形象进度及存在问题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'schedule'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'竣工日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'endDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'remark'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'填报人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'creater'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'填报部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'createGroupName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'批次' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'applyBatch'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'填报时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'reportTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'受表单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'receiveUnit'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'contact'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'cPhone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YueFenGuDingZiChanTouZiXiangMuJinDu', @level2type=N'COLUMN',@level2name=N'cEmail'
GO
/****** Object:  Table [dbo].[tb_report_YingJiaoShuiFeiBianDong]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_YingJiaoShuiFeiBianDong](
	[id] [varchar](100) NOT NULL,
	[companyName] [varchar](100) NULL,
	[projectNum] [int] NULL,
	[projectName] [varchar](100) NULL,
	[beginMoney] [float] NULL,
	[monthShouldPay] [float] NULL,
	[accShould] [float] NULL,
	[monthPaid] [float] NULL,
	[accPaid] [float] NULL,
	[endMoney] [float] NULL,
	[creater] [varchar](50) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](50) NULL,
	[createGroupID] [int] NULL,
	[createTime] [datetime] NULL,
	[updateTime] [datetime] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[sortNo] [int] NULL,
	[applyBatch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月应交数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YingJiaoShuiFeiBianDong', @level2type=N'COLUMN',@level2name=N'monthShouldPay'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月应交累计数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YingJiaoShuiFeiBianDong', @level2type=N'COLUMN',@level2name=N'accShould'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月已交数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YingJiaoShuiFeiBianDong', @level2type=N'COLUMN',@level2name=N'monthPaid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'本月已交累计数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YingJiaoShuiFeiBianDong', @level2type=N'COLUMN',@level2name=N'accPaid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'期末余额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_YingJiaoShuiFeiBianDong', @level2type=N'COLUMN',@level2name=N'endMoney'
GO
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'02bab777-63f8-4329-9fbc-db0aa3d50a8e', N'444444', 6, N'城市维护建设税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61E8 AS DateTime), CAST(0x0000A34100EE61E8 AS DateTime), 2014, 8, 6, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'04cf3fb6-7f88-4d6c-a606-eb382fc28481', N'测试3', 5, N'企业所得税', 5, 5, 5, 5, 5, 5, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBBF AS DateTime), CAST(0x0000A3570135C189 AS DateTime), 2010, 1, 5, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'05cb36cd-3166-48ee-97cd-ce21d5bab55e', N'444444', 13, N'车船使用税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE6270 AS DateTime), CAST(0x0000A34100EE6270 AS DateTime), 2014, 8, 13, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'0b8b604b-f242-4d7f-82bf-d24935796391', N'测试1测试1测试1测试1', 5, N'企业所得税', 5, 5, 5, 5, 5, 5, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49C3 AS DateTime), CAST(0x0000A33D011D18D2 AS DateTime), 2014, 6, 5, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'0c169334-cdbb-4a27-bb59-7ecf05209ba8', N'测试1测试1测试1测试1', 14, N'土地增值税', 14, 14, 14, 14, 14, 14, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49F6 AS DateTime), CAST(0x0000A33D011D190C AS DateTime), 2014, 6, 14, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'0d150b90-f84a-43c0-963e-529e3e49ea54', N'测试1测试1测试1测试1', 10, N'教育费附加', 10, 10, 10, 10, 10, 10, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49E2 AS DateTime), CAST(0x0000A33D011D18E6 AS DateTime), 2014, 6, 10, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'0f2a84b7-14df-4c8c-b1e9-44cd3cd7f871', N'444444', 14, N'土地增值税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE627E AS DateTime), CAST(0x0000A34100EE627E AS DateTime), 2014, 8, 14, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'109cdec6-6eed-46c6-bc68-0e845ab5efc1', N'111111', 6, N'城市维护建设税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF91D AS DateTime), CAST(0x0000A34100ED2C95 AS DateTime), 2014, 7, 6, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1134c7b7-8291-4e5d-8bd2-2fed5af6ef5f', N'测试3', 9, N'个人所得税', 9, 9, 9, 9, 9, 9, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBCB AS DateTime), CAST(0x0000A3570135C1D1 AS DateTime), 2010, 1, 9, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1195b313-29e9-4838-9ae8-48ad00e3c21e', N'测试1测试1测试1测试1', 14, N'土地增值税', 14, 14, 14, 14, 14, 14, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B7990B AS DateTime), CAST(0x0000A34100B8A204 AS DateTime), 2013, 12, 14, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'199cd3ca-30d8-4eb2-b46e-501bbda5645a', N'测试2', 10, N'教育费附加', 10, 10, 10, 10, 10, 10, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E07B AS DateTime), CAST(0x0000A35000F12B07 AS DateTime), 2014, 6, 10, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'19a46940-57b0-4c68-af78-cadbe8b3dd43', N'测试2', 12, N'印花税', 12, 12, 12, 12, 12, 12, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E084 AS DateTime), CAST(0x0000A35000F12BE2 AS DateTime), 2014, 6, 12, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'19b55704-185b-48ba-b87e-931038bd5180', N'测试1测试1测试1测试1', 10, N'教育费附加', 10, 10, 10, 10, 10, 10, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798F9 AS DateTime), CAST(0x0000A34100B8A1F7 AS DateTime), 2013, 12, 10, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1b32236d-d583-4650-91cb-2ba9fd6c00e3', N'33333', 5, N'企业所得税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BE3 AS DateTime), CAST(0x0000A34100EE0BE3 AS DateTime), 2014, 7, 5, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1b4673d4-e3d7-472d-bf05-ab8b97188f53', N'22222', 2, N'消费税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D39 AS DateTime), CAST(0x0000A34100ED8BF7 AS DateTime), 2014, 8, 2, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1b9de3c9-bf17-4985-aa0b-7ff6d45e25ba', N'测试2', 3, N'营业税', 3, 3, 3, 3, 3, 3, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E055 AS DateTime), CAST(0x0000A35000F12AEA AS DateTime), 2014, 6, 3, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1c2cbe29-7032-4ef6-bbd2-e3080f318b8f', N'测试3', 16, N'其他税费', 16, 16, 16, 16, 16, 16, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBE2 AS DateTime), CAST(0x0000A3570135C163 AS DateTime), 2010, 1, 16, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1ca22c2d-514a-4082-8f18-724d96511f00', N'测试2', 8, N'土地使用税', 8, 8, 8, 8, 8, 8, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E071 AS DateTime), CAST(0x0000A35000F12B03 AS DateTime), 2014, 6, 8, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'1d0ca669-b404-4e56-8f27-2a41735968af', N'测试2', 2, N'消费税', 2, 2, 2, 2, 2, 2, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E052 AS DateTime), CAST(0x0000A35000F12AE8 AS DateTime), 2014, 6, 2, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'20ef4ce9-a038-4f58-a59c-064b81ca2fb9', N'测试2', 4, N'资源税', 4, 4, 4, 4, 4, 4, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E05C AS DateTime), CAST(0x0000A35000F12AF7 AS DateTime), 2014, 6, 4, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'2357e4fa-cb99-4ba8-a397-ad1c5d9bc87f', N'测试3', 3, N'营业税', 3, 3, 3, 3, 3, 3, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBB8 AS DateTime), CAST(0x0000A3570135C16B AS DateTime), 2010, 1, 3, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'2814b080-4cb4-4b8f-a695-8f35fda035c1', N'测试1测试1测试1测试1', 16, N'其他税费', 116, 116, 16, 16, 16, 116, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B79914 AS DateTime), CAST(0x0000A34100B8A20C AS DateTime), 2013, 12, 16, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'2921465b-e93f-4644-afe3-e8993bc6464c', N'22222', 1, N'增值税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D21 AS DateTime), CAST(0x0000A34100ED8BF3 AS DateTime), 2014, 8, 1, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'2f993181-0886-4d1f-9b6b-29b2764e9de7', N'22222', 8, N'土地使用税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DA0 AS DateTime), CAST(0x0000A34100ED8C47 AS DateTime), 2014, 8, 8, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'319a9b6e-92de-4781-bd69-3c2c548155f7', N'111111', 7, N'房产税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF92B AS DateTime), CAST(0x0000A34100ED2C99 AS DateTime), 2014, 7, 7, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'32236d11-b292-4db0-8107-4bb0b83a0bbe', N'22222', 11, N'地方教育费附加', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DCA AS DateTime), CAST(0x0000A34100ED8C50 AS DateTime), 2014, 8, 11, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'35f98fb1-7484-4634-a1ac-8f7c967947cc', N'测试1测试1测试1测试1', 2, N'消费税', 2, 2, 2, 2, 2, 2, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798CC AS DateTime), CAST(0x0000A34100B8A1D8 AS DateTime), 2013, 12, 2, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'364bc90a-05b0-441a-89b1-43df01c7d303', N'测试2', 15, N'固定资产投资方向调节税', 15, 15, 15, 15, 15, 15, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E09E AS DateTime), CAST(0x0000A35000F12BBB AS DateTime), 2014, 6, 15, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'3655b8f4-8904-487e-8fa9-fbaf2e2e878b', N'111111', 16, N'其他税费', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF9A4 AS DateTime), CAST(0x0000A34100ED2CB5 AS DateTime), 2014, 7, 16, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'365c74a2-4f83-4e10-a7f1-39eae655132c', N'111111', 10, N'教育费附加', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF950 AS DateTime), CAST(0x0000A34100ED2C9E AS DateTime), 2014, 7, 10, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'386b64bc-6107-45b2-9c3f-9622d640de92', N'33333', 15, N'固定资产投资方向调节税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C6F AS DateTime), CAST(0x0000A34100EE0C6F AS DateTime), 2014, 7, 15, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'3d32d0e3-492d-434f-95a8-4e1f119b37fd', N'测试1测试1测试1测试1', 3, N'营业税', 3, 3, 3, 3, 3, 3, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49B3 AS DateTime), CAST(0x0000A33D011D18C9 AS DateTime), 2014, 6, 3, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'3f048eaa-8ec7-4261-bfd9-53a1e335764b', N'111111', 2, N'消费税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF8DA AS DateTime), CAST(0x0000A34100ED2C8D AS DateTime), 2014, 7, 2, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'42e3457d-a06c-4a9c-a9e1-0eef00430af8', N'22222', 14, N'土地增值税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7E15 AS DateTime), CAST(0x0000A34100ED8C59 AS DateTime), 2014, 8, 14, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'43ad7cbf-edc3-49bb-b36b-6703f47e0178', N'33333', 4, N'资源税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BD4 AS DateTime), CAST(0x0000A34100EE0BD4 AS DateTime), 2014, 7, 4, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'446f330c-e00e-45c9-ae60-e29b7b0d205b', N'测试1测试1测试1测试1', 15, N'固定资产投资方向调节税', 115, 115, 15, 15, 15, 115, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B7990F AS DateTime), CAST(0x0000A34100B8A20A AS DateTime), 2013, 12, 15, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'4e17a46f-7d36-479d-97b7-b855efb71c30', N'测试2', 5, N'企业所得税', 5, 5, 5, 5, 5, 5, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E060 AS DateTime), CAST(0x0000A35000F12AFE AS DateTime), 2014, 6, 5, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'4e24f67b-6588-4126-97ce-77de191e10ea', N'444444', 8, N'土地使用税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE620E AS DateTime), CAST(0x0000A34100EE620E AS DateTime), 2014, 8, 8, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'51aa9a1f-3051-4204-bfe7-5ea57ca74141', N'测试2', 6, N'城市维护建设税', 6, 6, 6, 6, 6, 6, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E064 AS DateTime), CAST(0x0000A35000F12B00 AS DateTime), 2014, 6, 6, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'51b98461-0149-4bae-98ad-3c6dd6e127c5', N'测试1测试1测试1测试1', 12, N'印花税', 12, 12, 12, 12, 12, 12, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B79903 AS DateTime), CAST(0x0000A34100B8A1FF AS DateTime), 2013, 12, 12, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'51e57fe2-c3b0-427e-9981-4223fd3026a1', N'测试1测试1测试1测试1', 1, N'增值税', 11, 1, 1, 1, 1, 11, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798C6 AS DateTime), CAST(0x0000A34100B8A1D4 AS DateTime), 2013, 12, 1, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'533d2d1d-f411-4ba8-8058-cd42c78be549', N'测试2', 11, N'地方教育费附加', 11, 11, 11, 11, 11, 11, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E080 AS DateTime), CAST(0x0000A35000F12B0C AS DateTime), 2014, 6, 11, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'54e0ce3d-9803-4f0c-82a5-f55b19d03421', N'测试1测试1测试1测试1', 12, N'印花税', 12, 12, 12, 12, 12, 12, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49E9 AS DateTime), CAST(0x0000A33D011D18F8 AS DateTime), 2014, 6, 12, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'55c4b566-f802-43f3-926d-69a430da9d6c', N'444444', 16, N'其他税费', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE6290 AS DateTime), CAST(0x0000A34100EE6290 AS DateTime), 2014, 8, 16, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'5a47cf82-20b3-4052-995e-63e9cec24a75', N'测试2', 9, N'个人所得税', 9, 9, 9, 9, 9, 9, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E075 AS DateTime), CAST(0x0000A35000F12B06 AS DateTime), 2014, 6, 9, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'5d2da55f-2ef7-44b3-8c22-bc9e52f44343', N'测试1测试1测试1测试1', 6, N'城市维护建设税', 6, 6, 6, 6, 6, 6, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798DF AS DateTime), CAST(0x0000A34100B8A1DF AS DateTime), 2013, 12, 6, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'5df5d190-2985-481f-aa1e-b7eca43ef944', N'33333', 1, N'增值税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0B90 AS DateTime), CAST(0x0000A34100EE0B90 AS DateTime), 2014, 7, 1, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'602e3d38-3105-472e-b42f-1d9a53461c12', N'测试1测试1测试1测试1', 1, N'增值税', 1, 1, 1, 1, 1, 1, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF499A AS DateTime), CAST(0x0000A33D011D18BA AS DateTime), 2014, 6, 1, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'622d8157-2a69-4c51-91c6-9285ab199c35', N'33333', 16, N'其他税费', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C82 AS DateTime), CAST(0x0000A34100EE0C82 AS DateTime), 2014, 7, 16, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'62bfe08d-78c3-406c-ab56-500a8db5fd83', N'22222', 4, N'资源税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D5A AS DateTime), CAST(0x0000A34100ED8C13 AS DateTime), 2014, 8, 4, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'6695b1d4-edd4-43c9-9f4a-9cad98a6198b', N'测试3', 10, N'教育费附加', 10, 10, 10, 10, 10, 10, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBCF AS DateTime), CAST(0x0000A3570135C1EF AS DateTime), 2010, 1, 10, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'67c0b697-b54f-41d0-9bee-7d11e4654fff', N'测试3', 1, N'增值税', 1, 1, 1, 1, 1, 1, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBB2 AS DateTime), CAST(0x0000A3570135C143 AS DateTime), 2010, 1, 1, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'6bcb2c45-70cb-416a-b22e-878b3c376d3b', N'33333', 11, N'地方教育费附加', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C29 AS DateTime), CAST(0x0000A34100EE0C29 AS DateTime), 2014, 7, 11, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'6dbbdd30-6f9d-4be3-b8d9-fd2b693b9b66', N'测试1测试1测试1测试1', 6, N'城市维护建设税', 6, 6, 6, 6, 6, 6, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49CD AS DateTime), CAST(0x0000A33D011D18D6 AS DateTime), 2014, 6, 6, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'6e832f1d-dc9c-4d2e-8594-0fdfb3c3e09d', N'33333', 2, N'消费税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BAE AS DateTime), CAST(0x0000A34100EE0BAE AS DateTime), 2014, 7, 2, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'70226c48-f4a9-439a-a440-d252937eeb15', N'33333', 9, N'个人所得税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C0D AS DateTime), CAST(0x0000A34100EE0C0D AS DateTime), 2014, 7, 9, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'754c0f98-f597-4d2b-947f-12b789286110', N'测试1测试1测试1测试1', 11, N'地方教育费附加', 11, 11, 11, 11, 11, 11, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798FC AS DateTime), CAST(0x0000A34100B8A1F9 AS DateTime), 2013, 12, 11, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'76d361bf-560d-4c3c-bcd4-b3143d181fec', N'测试3', 2, N'消费税', 2, 2, 2, 2, 2, 2, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBB5 AS DateTime), CAST(0x0000A3570135C162 AS DateTime), 2010, 1, 2, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'77681c4d-a107-415b-b12f-4c41f1c9a1b4', N'测试3', 4, N'资源税', 4, 4, 4, 4, 4, 4, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBBB AS DateTime), CAST(0x0000A3570135C180 AS DateTime), 2010, 1, 4, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'790ebf63-3243-4926-bddf-56cca688d7d5', N'111111', 5, N'企业所得税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF913 AS DateTime), CAST(0x0000A34100ED2C95 AS DateTime), 2014, 7, 5, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'7d3cecc2-1a28-4350-b639-1d29fd2bd5dd', N'22222', 7, N'房产税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D8E AS DateTime), CAST(0x0000A34100ED8C42 AS DateTime), 2014, 8, 7, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'7d624d78-123c-4aea-94a5-73332f20b987', N'测试1测试1测试1测试1', 9, N'个人所得税', 9, 9, 9, 9, 9, 9, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798F0 AS DateTime), CAST(0x0000A34100B8A1F5 AS DateTime), 2013, 12, 9, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'7e133459-9d0e-4582-a91a-922c861d96ce', N'444444', 15, N'固定资产投资方向调节税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE6287 AS DateTime), CAST(0x0000A34100EE6287 AS DateTime), 2014, 8, 15, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'7e4ff738-c72f-4bd6-960c-39e69daeb80b', N'测试3', 12, N'印花税', 12, 12, 12, 12, 12, 12, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBD4 AS DateTime), CAST(0x0000A3570135C20D AS DateTime), 2010, 1, 12, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'84ab38bd-d0e6-4fee-85c7-0adb0281b474', N'测试3', 7, N'房产税', 7, 7, 7, 7, 7, 7, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBC5 AS DateTime), CAST(0x0000A3570135C1B3 AS DateTime), 2010, 1, 7, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'86bb1f50-f6e9-4250-9eef-6ef266806f83', N'444444', 12, N'印花税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE625D AS DateTime), CAST(0x0000A34100EE625D AS DateTime), 2014, 8, 12, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'87461bdd-6393-4601-84c7-58b8c9e6718a', N'测试3', 6, N'城市维护建设税', 6, 6, 6, 6, 6, 6, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBC1 AS DateTime), CAST(0x0000A3570135C19E AS DateTime), 2010, 1, 6, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'8c6ad802-7fb2-473f-84b5-30357cd3f907', N'测试3', 13, N'车船使用税', 13, 13, 13, 13, 13, 13, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBD8 AS DateTime), CAST(0x0000A3570135C234 AS DateTime), 2010, 1, 13, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'8d0fb249-e12d-4a40-bdfa-da3ef660d175', N'22222', 15, N'固定资产投资方向调节税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7E32 AS DateTime), CAST(0x0000A34100ED8C59 AS DateTime), 2014, 8, 15, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'8d62962d-9b14-4b3c-80e0-3ddabd311f19', N'测试2', 14, N'土地增值税', 14, 14, 14, 14, 14, 14, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E096 AS DateTime), CAST(0x0000A35000F12B7F AS DateTime), 2014, 6, 14, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'8fd8dbf3-dca7-43b7-8538-12a60a8864a8', N'33333', 12, N'印花税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C37 AS DateTime), CAST(0x0000A34100EE0C37 AS DateTime), 2014, 7, 12, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'92e0f0d0-7bae-455c-be92-9dabef059fdb', N'22222', 10, N'教育费附加', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DBD AS DateTime), CAST(0x0000A34100ED8C4B AS DateTime), 2014, 8, 10, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'94db4f7e-d778-4197-9321-b2982ac58d28', N'111111', 1, N'增值税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF8C5 AS DateTime), CAST(0x0000A34100ED2C84 AS DateTime), 2014, 7, 1, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'960a7d14-3fb2-488e-a3c9-dd4404e3dfa3', N'33333', 8, N'土地使用税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C03 AS DateTime), CAST(0x0000A34100EE0C03 AS DateTime), 2014, 7, 8, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'982716ec-af42-4450-b6fe-0932074ccc03', N'22222', 6, N'城市维护建设税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D84 AS DateTime), CAST(0x0000A34100ED8C2A AS DateTime), 2014, 8, 6, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9886eae9-c05f-4bf9-be09-c5181c53f950', N'33333', 3, N'营业税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BC2 AS DateTime), CAST(0x0000A34100EE0BC2 AS DateTime), 2014, 7, 3, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9cb1f986-b2dd-4f9d-b10b-d303c5d89fac', N'测试1测试1测试1测试1', 13, N'车船使用税', 13, 13, 13, 13, 13, 13, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49EE AS DateTime), CAST(0x0000A33D011D1904 AS DateTime), 2014, 6, 13, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9cd79d56-6828-470b-8a69-493df3fab6ae', N'444444', 11, N'地方教育费附加', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE6241 AS DateTime), CAST(0x0000A34100EE6241 AS DateTime), 2014, 8, 11, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9df852c3-0dc0-493c-9595-aa330043c19a', N'测试3', 14, N'土地增值税', 14, 14, 14, 14, 14, 14, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBDB AS DateTime), CAST(0x0000A3570135C249 AS DateTime), 2010, 1, 14, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9eb1e257-0deb-434a-b681-f48c90ce46bb', N'测试2', 7, N'房产税', 7, 7, 7, 7, 7, 7, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E06D AS DateTime), CAST(0x0000A35000F12B02 AS DateTime), 2014, 6, 7, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9edccfd9-4f4c-44a5-9797-12f89b31d9c1', N'444444', 3, N'营业税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61C3 AS DateTime), CAST(0x0000A34100EE61C3 AS DateTime), 2014, 8, 3, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9eed12b3-a23c-4e78-948f-91ff27a51daf', N'22222', 9, N'个人所得税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DAE AS DateTime), CAST(0x0000A34100ED8C47 AS DateTime), 2014, 8, 9, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'9f82e531-a2a6-4a94-b2db-f4c9a556d75f', N'22222', 5, N'企业所得税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D72 AS DateTime), CAST(0x0000A34100ED8C1C AS DateTime), 2014, 8, 5, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a02bb91c-aa7e-49cc-84a6-f0591c71c145', N'测试1测试1测试1测试1', 15, N'固定资产投资方向调节税', 15, 15, 15, 15, 15, 15, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49FF AS DateTime), CAST(0x0000A33D011D190F AS DateTime), 2014, 6, 15, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a0ef0b58-e5b3-4a43-a543-43e75f4c8a8d', N'111111', 9, N'个人所得税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF942 AS DateTime), CAST(0x0000A34100ED2C9E AS DateTime), 2014, 7, 9, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a1893d1e-bafc-459f-bdde-2826e904812d', N'测试2', 16, N'其他税费', 16, 16, 16, 16, 16, 16, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E0A3 AS DateTime), CAST(0x0000A35000F12BC4 AS DateTime), 2014, 6, 16, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a8652dbe-cc92-4da5-b934-053fb143683a', N'测试1测试1测试1测试1', 9, N'个人所得税', 9, 9, 9, 9, 9, 9, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49DD AS DateTime), CAST(0x0000A33D011D18E2 AS DateTime), 2014, 6, 9, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a8bb9ca5-cc27-4615-ae7e-e775fca6d1aa', N'测试1测试1测试1测试1', 4, N'资源税', 4, 4, 4, 4, 4, 4, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49BA AS DateTime), CAST(0x0000A33D011D18CF AS DateTime), 2014, 6, 4, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'a92c6e46-9941-4686-8b8f-6885e6be35fa', N'111111', 8, N'土地使用税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF939 AS DateTime), CAST(0x0000A34100ED2C99 AS DateTime), 2014, 7, 8, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'aa01fcdf-4f3d-464a-be9c-b54e8e36fbd9', N'测试1测试1测试1测试1', 8, N'土地使用税', 8, 8, 8, 8, 8, 8, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798EB AS DateTime), CAST(0x0000A34100B8A1F1 AS DateTime), 2013, 12, 8, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ac17975b-1520-49b8-a8a6-b00221be55e1', N'测试1测试1测试1测试1', 11, N'地方教育费附加', 11, 11, 11, 11, 11, 11, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49E5 AS DateTime), CAST(0x0000A33D011D18EB AS DateTime), 2014, 6, 11, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ac9399ad-34e1-4d17-aa6b-28c221637bbf', N'22222', 16, N'其他税费', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7E81 AS DateTime), CAST(0x0000A34100ED8C5E AS DateTime), 2014, 8, 16, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ade31d20-e448-4604-bd6f-1cc8fc9b36fd', N'测试3', 11, N'地方教育费附加', 11, 11, 11, 11, 11, 11, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBD1 AS DateTime), CAST(0x0000A3570135C201 AS DateTime), 2010, 1, 11, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'b0ded263-51c3-4d3c-b88c-677875241c80', N'444444', 5, N'企业所得税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61DF AS DateTime), CAST(0x0000A34100EE61DF AS DateTime), 2014, 8, 5, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'b596801a-a253-4c80-b5d7-cbe16e2c9307', N'33333', 7, N'房产税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BFE AS DateTime), CAST(0x0000A34100EE0BFE AS DateTime), 2014, 7, 7, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'b7173534-3fe5-4f7d-8cde-fd4197c3ec62', N'444444', 7, N'房产税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61FF AS DateTime), CAST(0x0000A34100EE61FF AS DateTime), 2014, 8, 7, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ba14aa36-c258-4f15-81a6-9f513a29233e', N'测试3', 8, N'土地使用税', 8, 8, 8, 8, 8, 8, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBC8 AS DateTime), CAST(0x0000A3570135C1C5 AS DateTime), 2010, 1, 8, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'c4885f15-615f-4d4e-b1af-c2d068a168ac', N'33333', 10, N'教育费附加', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C16 AS DateTime), CAST(0x0000A34100EE0C16 AS DateTime), 2014, 7, 10, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'c4f4c66a-a50d-4d38-8abb-1e5fc9b6fdd3', N'111111', 11, N'地方教育费附加', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF963 AS DateTime), CAST(0x0000A34100ED2CA2 AS DateTime), 2014, 7, 11, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'c7b1fe3f-ffee-4efe-a63c-b83a6744bc55', N'33333', 13, N'车船使用税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C40 AS DateTime), CAST(0x0000A34100EE0C40 AS DateTime), 2014, 7, 13, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'c7bcf4f4-62a2-4262-9930-c982dbb00fd6', N'444444', 2, N'消费税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61B9 AS DateTime), CAST(0x0000A34100EE61B9 AS DateTime), 2014, 8, 2, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
GO
print 'Processed 100 total records'
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'c7f89644-a522-4f7f-a309-c38741559f60', N'22222', 12, N'印花税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DD8 AS DateTime), CAST(0x0000A34100ED8C50 AS DateTime), 2014, 8, 12, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'd2b194dd-029c-42c8-bfe3-88717147fd89', N'测试1测试1测试1测试1', 2, N'消费税', 2, 2, 2, 2, 2, 2, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49A6 AS DateTime), CAST(0x0000A33D011D18C1 AS DateTime), 2014, 6, 2, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'd3718630-dfcd-4cdc-8206-16da601d4bc5', N'测试1测试1测试1测试1', 8, N'土地使用税', 8, 8, 8, 8, 8, 8, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49D7 AS DateTime), CAST(0x0000A33D011D18E0 AS DateTime), 2014, 6, 8, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'd4d7b51b-5a82-4a45-909d-97e4cbcc32fe', N'测试2', 13, N'车船使用税', 13, 13, 13, 13, 13, 13, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E090 AS DateTime), CAST(0x0000A35000F12B43 AS DateTime), 2014, 6, 13, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'd704c9ed-ea59-4ba9-a6a7-6224b909e267', N'444444', 10, N'教育费附加', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE6233 AS DateTime), CAST(0x0000A34100EE6233 AS DateTime), 2014, 8, 10, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'd8f19df4-2976-46c3-97e8-cc2033cdd425', N'111111', 13, N'车船使用税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF988 AS DateTime), CAST(0x0000A34100ED2CA7 AS DateTime), 2014, 7, 13, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'db0dc2d3-893a-435c-9fb4-155878c0da76', N'测试1测试1测试1测试1', 7, N'房产税', 7, 7, 7, 7, 7, 7, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF49D2 AS DateTime), CAST(0x0000A33D011D18DA AS DateTime), 2014, 6, 7, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'dbdd5a41-e2f0-4eda-8b53-e2753024aaf7', N'444444', 1, N'增值税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61AB AS DateTime), CAST(0x0000A34100EE61AB AS DateTime), 2014, 8, 1, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'dd2acfdc-7d9c-4531-86cc-24cdb93cb6e2', N'测试1测试1测试1测试1', 16, N'其他税费', 16, 16, 16, 16, 16, 16, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000EF4A07 AS DateTime), CAST(0x0000A33D011D1914 AS DateTime), 2014, 6, 16, N'c69cb33c-ddb8-435c-92f9-473acff8c71e')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e3104794-f924-4ab7-8f64-d08980ff5f3c', N'测试1测试1测试1测试1', 7, N'房产税', 7, 7, 7, 7, 7, 7, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798E4 AS DateTime), CAST(0x0000A34100B8A1E3 AS DateTime), 2013, 12, 7, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e3a229eb-d2c7-49a5-8f76-df103177d2e9', N'444444', 4, N'资源税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE61D1 AS DateTime), CAST(0x0000A34100EE61D1 AS DateTime), 2014, 8, 4, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e429c4f1-992d-466d-8ebf-dc6d4a75fbf3', N'22222', 3, N'营业税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7D4C AS DateTime), CAST(0x0000A34100ED8C01 AS DateTime), 2014, 8, 3, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e4952724-ac1e-433b-a7ee-ab78606e28ff', N'测试2', 1, N'增值税', 1, 1, 1, 1, 1, 1, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A35000F0E04D AS DateTime), CAST(0x0000A35000F12AE5 AS DateTime), 2014, 6, 1, N'abcccbca-420a-45e9-80a1-1fab712c666f')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e49b3cf3-18b7-4ad4-9447-67121d70f320', N'111111', 14, N'土地增值税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF996 AS DateTime), CAST(0x0000A34100ED2CAC AS DateTime), 2014, 7, 14, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'e8ebb4f3-2d14-4753-bb9d-cbbbf9a429b6', N'测试1测试1测试1测试1', 3, N'营业税', 3, 3, 3, 3, 3, 3, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798D2 AS DateTime), CAST(0x0000A34100B8A1DB AS DateTime), 2013, 12, 3, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'eaca5578-e271-40f7-9bde-aa59448c8074', N'测试3', 15, N'固定资产投资方向调节税', 15, 15, 15, 15, 15, 15, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A3570134CBDE AS DateTime), CAST(0x0000A3570135C252 AS DateTime), 2010, 1, 15, N'3792b6ee-e628-43ff-85a6-b6e4256ec8eb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ec1c85bd-1633-4984-90ab-7facfb90af79', N'111111', 15, N'固定资产投资方向调节税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF99B AS DateTime), CAST(0x0000A34100ED2CB1 AS DateTime), 2014, 7, 15, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'ed65de72-216c-49c6-97d7-97c23271174b', N'33333', 6, N'城市维护建设税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0BF5 AS DateTime), CAST(0x0000A34100EE0BF5 AS DateTime), 2014, 7, 6, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'edecdb11-1613-4441-8b85-d1ab542849bb', N'测试1测试1测试1测试1', 5, N'企业所得税', 5, 5, 5, 5, 5, 5, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798DB AS DateTime), CAST(0x0000A34100B8A1DE AS DateTime), 2013, 12, 5, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f029fc3f-5d42-4698-ba46-2acba7fd4076', N'111111', 4, N'资源税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF905 AS DateTime), CAST(0x0000A34100ED2C90 AS DateTime), 2014, 7, 4, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f24d5790-a5ef-41c3-923e-6c75e5fdfa23', N'111111', 12, N'印花税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF975 AS DateTime), CAST(0x0000A34100ED2CA2 AS DateTime), 2014, 7, 12, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f2d7e696-553d-48da-b757-6429cc244d93', N'22222', 13, N'车船使用税', 2, 2, 2, 2, 2, 2, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ED7DF5 AS DateTime), CAST(0x0000A34100ED8C55 AS DateTime), 2014, 8, 13, N'd6e6de4d-b5c8-439c-bb38-0ecbca91d4d0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f37305bb-4296-4af5-8886-1dd2bdf8969f', N'测试1测试1测试1测试1', 4, N'资源税', 4, 4, 4, 4, 4, 4, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B798D9 AS DateTime), CAST(0x0000A34100B8A1DD AS DateTime), 2013, 12, 4, N'1ecc031e-f12b-452d-8268-5571de73dea0')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f38ab33b-0bdc-4919-9d2e-4548f4f743ac', N'444444', 9, N'个人所得税', 4, 4, 4, 4, 4, 4, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE621B AS DateTime), CAST(0x0000A34100EE621B AS DateTime), 2014, 8, 9, N'0e9cf6d4-c900-4b71-9399-7bb44a044a91')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'f6009a23-dea8-4822-9999-68df1f712368', N'111111', 3, N'营业税', 1, 1, 1, 1, 1, 1, N'报表1', 11612422, N'报表测试部门', 393239, CAST(0x0000A34100ECF8F2 AS DateTime), CAST(0x0000A34100ED2C90 AS DateTime), 2014, 7, 3, N'7c9c09cc-48d7-4e4a-9ab1-142375187647')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'fb47f17b-f0ef-4b56-90eb-5e32d184bfb8', N'33333', 14, N'土地增值税', 3, 3, 3, 3, 3, 3, N'报表2', 11612423, N'报表测试部门', 393239, CAST(0x0000A34100EE0C4E AS DateTime), CAST(0x0000A34100EE0C4E AS DateTime), 2014, 7, 14, N'e6a8a7f4-6088-42b1-911c-fc41134251cb')
INSERT [dbo].[tb_report_YingJiaoShuiFeiBianDong] ([id], [companyName], [projectNum], [projectName], [beginMoney], [monthShouldPay], [accShould], [monthPaid], [accPaid], [endMoney], [creater], [createrID], [createGroupName], [createGroupID], [createTime], [updateTime], [thisYear], [thisMonth], [sortNo], [applyBatch]) VALUES (N'fea94949-1fb6-402b-a2d9-7b91666d65cf', N'测试1测试1测试1测试1', 13, N'车船使用税', 13, 13, 13, 13, 13, 13, N'马青青', 1, N'信阳交通运输局', 80, CAST(0x0000A34100B79908 AS DateTime), CAST(0x0000A34100B8A202 AS DateTime), 2013, 12, 13, N'1ecc031e-f12b-452d-8268-5571de73dea0')
/****** Object:  Table [dbo].[tb_report_YingFuZhiGongXinChouBianDong]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_YingFuZhiGongXinChouBianDong](
	[id] [varchar](100) NOT NULL,
	[companyName] [varchar](100) NULL,
	[projectNum] [int] NULL,
	[projectName] [varchar](100) NULL,
	[beginMoney] [float] NULL,
	[monthIncrease] [float] NULL,
	[AccIncrease] [float] NULL,
	[monthReduse] [float] NULL,
	[AccReduse] [float] NULL,
	[endMoney] [float] NULL,
	[creater] [varchar](50) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](50) NULL,
	[createGroupID] [int] NULL,
	[createTime] [datetime] NULL,
	[updateTime] [datetime] NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[parentNum] [int] NULL,
	[sortNo] [int] NULL,
	[applyBatch] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_report_TouZiProjectPlan]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_TouZiProjectPlan](
	[investProId] [varchar](200) NOT NULL,
	[projectClass] [int] NOT NULL,
	[projectName] [varchar](200) NOT NULL,
	[hangyeClass] [varchar](200) NOT NULL,
	[investDirection] [int] NOT NULL,
	[investObject] [varchar](1000) NULL,
	[constScale] [varchar](1000) NULL,
	[constPlace] [varchar](1000) NULL,
	[constTime] [varchar](100) NULL,
	[totalMoney] [float] NULL,
	[privateMoney] [float] NULL,
	[bankLoan] [float] NULL,
	[otherMoney] [float] NULL,
	[totalInvest] [float] NULL,
	[newProduction] [float] NULL,
	[newSaleMoney] [float] NULL,
	[newProfit] [float] NULL,
	[investReturn] [float] NULL,
	[workCondition] [varchar](1000) NULL,
	[note] [varchar](1000) NULL,
	[createTime] [datetime] NULL,
	[reportDate] [date] NULL,
	[updateTime] [datetime] NULL,
	[creater] [varchar](100) NULL,
	[createrID] [int] NULL,
	[createGroupName] [varchar](100) NULL,
	[cteateGroupID] [int] NULL,
	[applyBatch] [varchar](1000) NULL,
	[unit] [varchar](50) NULL,
	[email] [varchar](100) NULL,
	[companyName] [varchar](100) NULL,
	[phone] [varchar](100) NULL,
	[investMoney] [float] NULL,
	[constContent] [varchar](300) NULL,
	[reportUserName] [varchar](100) NULL,
	[thisYear] [int] NULL,
	[thisMonth] [int] NULL,
	[objectLevel] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[investProId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 1代表"续建项目" 2代表"新开工项目" 3代表"前期项目"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_TouZiProjectPlan', @level2type=N'COLUMN',@level2name=N'projectClass'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'投资方向包括：1.主业投资 2.非主业投资' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tb_report_TouZiProjectPlan', @level2type=N'COLUMN',@level2name=N'investDirection'
GO
/****** Object:  Table [dbo].[tb_report_sectionDic]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_report_sectionDic](
	[sectionId] [int] IDENTITY(1,1) NOT NULL,
	[sectionName] [varchar](100) NULL,
	[creater] [varchar](100) NULL,
	[createUserId] [int] NULL,
	[createTime] [datetime] NULL,
	[projectId] [int] NULL,
	[note] [varchar](2000) NULL,
	[sectionNumber] [varchar](100) NULL,
	[projectName] [varchar](100) NULL,
	[isDelete] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[sectionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_questionnaire]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_questionnaire](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[begin_time] [datetime] NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[des] [varchar](3000) NULL,
	[end_time] [datetime] NULL,
	[is_award] [tinyint] NOT NULL,
	[is_delete] [int] NULL,
	[publish_date] [datetime] NULL,
	[publish_user_id] [int] NULL,
	[state] [int] NULL,
	[title] [varchar](255) NULL,
	[update_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[last_update_user_id] [int] NULL,
	[is_anonymity] [int] NULL,
	[is_lottery] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_ohr_user_record]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_ohr_user_record](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[administration_level] [varchar](100) NULL,
	[age] [int] NULL,
	[attachment] [varchar](255) NULL,
	[bank_name_1] [varchar](100) NULL,
	[bank_name_2] [varchar](100) NULL,
	[bank_no_1] [varchar](50) NULL,
	[bank_no_2] [varchar](50) NULL,
	[company_work_age] [int] NULL,
	[computer_level] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NOT NULL,
	[edu_level] [int] NULL,
	[edu_qualifications] [int] NULL,
	[foreign_language1] [varchar](255) NULL,
	[foreign_language2] [varchar](255) NULL,
	[foreign_language3] [varchar](255) NULL,
	[graduation_school] [varchar](200) NULL,
	[graduation_time] [datetime] NULL,
	[health_check_record] [varchar](200) NULL,
	[health_info] [varchar](300) NULL,
	[home_address] [varchar](300) NULL,
	[identity_no] [varchar](30) NULL,
	[is_delete] [int] NULL,
	[job_title] [int] NULL,
	[job_title_level] [int] NULL,
	[join_time] [datetime] NULL,
	[join_work_time] [datetime] NULL,
	[language_level1] [varchar](255) NULL,
	[language_level2] [varchar](255) NULL,
	[language_level3] [varchar](255) NULL,
	[last_update_time] [datetime] NOT NULL,
	[lunar_birthday] [int] NULL,
	[marriage_status] [int] NULL,
	[msn] [varchar](100) NULL,
	[name_english] [varchar](100) NULL,
	[name_old] [varchar](100) NULL,
	[nationality] [varchar](255) NULL,
	[nativity_place] [varchar](200) NULL,
	[nativity_place_select] [varchar](200) NULL,
	[other_contact_way] [varchar](200) NULL,
	[party_time] [datetime] NULL,
	[photo_url] [varchar](200) NULL,
	[politics_face] [int] NULL,
	[position] [varchar](100) NULL,
	[post_state] [varchar](200) NULL,
	[profession] [varchar](100) NULL,
	[qq] [varchar](50) NULL,
	[record_no] [varchar](50) NOT NULL,
	[registered_address] [varchar](300) NULL,
	[registered_type] [int] NULL,
	[remark] [varchar](255) NULL,
	[resume_info] [varchar](255) NULL,
	[social_security_state] [varchar](200) NULL,
	[start_pay_time] [datetime] NULL,
	[station] [int] NULL,
	[strong_point] [varchar](200) NULL,
	[total_work_age] [int] NULL,
	[user_type] [int] NULL,
	[vouch_record] [varchar](200) NULL,
	[work_status] [int] NULL,
	[work_type] [varchar](100) NULL,
	[year_holiday] [int] NULL,
	[create_user_id] [int] NOT NULL,
	[last_update_user_id] [int] NULL,
	[user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_oab_group]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_oab_group](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[grouptype] [int] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[maintaingroupids] [varchar](255) NULL,
	[maintaingroupnames] [varchar](255) NULL,
	[maintainuserids] [text] NULL,
	[maintainusernames] [text] NULL,
	[name] [varchar](100) NOT NULL,
	[order_no] [int] NULL,
	[remark] [varchar](500) NULL,
	[create_user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_oab_address]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_oab_address](
	[vid] [int] IDENTITY(1,1) NOT NULL,
	[address_group_id] [int] NULL,
	[allow_update_user_ids] [varchar](255) NULL,
	[birthday] [datetime] NULL,
	[children] [varchar](100) NULL,
	[company_address] [varchar](200) NULL,
	[company_name] [varchar](100) NULL,
	[company_post_code] [varchar](20) NULL,
	[compy_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[end_share_time] [datetime] NULL,
	[family_address] [varchar](200) NULL,
	[family_email] [varchar](255) NULL,
	[family_mobile_no] [varchar](20) NULL,
	[family_phone_no] [varchar](20) NULL,
	[family_post_code] [varchar](20) NULL,
	[first_letter] [varchar](10) NOT NULL,
	[groupname] [varchar](255) NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[name] [varchar](100) NOT NULL,
	[nickname] [varchar](100) NULL,
	[office_fax] [varchar](20) NULL,
	[office_phone] [varchar](20) NULL,
	[order_no] [int] NULL,
	[photo] [varchar](1000) NULL,
	[post_info] [varchar](100) NULL,
	[remark] [varchar](500) NULL,
	[sex] [int] NULL,
	[share_to_user_ids] [varchar](255) NULL,
	[start_share_time] [datetime] NULL,
	[wife_name] [varchar](100) NULL,
	[create_user_id] [int] NULL,
 CONSTRAINT [PK__tb_oab_a__DDB00C7D5C629536] PRIMARY KEY CLUSTERED 
(
	[vid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_on_file]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_on_file](
	[content_id] [int] IDENTITY(1,1) NOT NULL,
	[attachment] [varchar](255) NULL,
	[attachment_desc] [varchar](255) NULL,
	[attachment_name] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [varchar](255) NULL,
	[content_no] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[create_user] [varchar](255) NULL,
	[create_user_id] [int] NULL,
	[is_delete] [int] NULL,
	[keyword] [varchar](255) NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[sort_no] [varchar](255) NULL,
	[subject] [varchar](255) NULL,
	[version] [int] NULL,
	[file_sort] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[content_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_affairs_body]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_om_affairs_body](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[content_info] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[remind_url] [varchar](255) NULL,
	[send_time] [datetime] NOT NULL,
	[sms_type] [varchar](255) NULL,
	[create_user_id] [int] NULL,
	[from_id] [int] NULL,
 CONSTRAINT [PK__tb_om_af__3213E83F7DA38D70] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_om_affairs_body] ON
INSERT [dbo].[tb_om_affairs_body] ([id], [compy_id], [content_info], [create_time], [is_delete], [last_update_time], [last_update_user_id], [remind_url], [send_time], [sms_type], [create_user_id], [from_id]) VALUES (295326, 1, N'您有一条来自超级管理员的公告,标题:testz', CAST(0x0000A38F0096072E AS DateTime), 0, NULL, NULL, N'logined/notify/view.jsp?notifyId=73&columnId=1&returnType=2', CAST(0x0000A38F0096072E AS DateTime), N'公告管理', 1, 1)
SET IDENTITY_INSERT [dbo].[tb_om_affairs_body] OFF
/****** Object:  Table [dbo].[tb_cbb_notify]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_notify](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_user_id] [int] NULL,
	[create_date] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[update_date] [datetime] NULL,
	[is_delete] [int] NULL,
	[subject] [varchar](100) NULL,
	[content] [text] NULL,
	[summary] [varchar](60) NULL,
	[begin_date] [datetime] NULL,
	[end_date] [datetime] NULL,
	[notify_type] [int] NULL,
	[is_top] [int] NULL,
	[status] [int] NULL,
	[auditer] [int] NULL,
	[reason] [varchar](1000) NULL,
	[approve_date] [datetime] NULL,
	[attment] [varchar](500) NULL,
	[publish_user_ids] [varchar](max) NULL,
	[publish_user_names] [varchar](max) NULL,
	[view_count] [int] NULL,
	[columnid] [int] NULL,
	[images] [varchar](1000) NULL,
	[sendType] [varchar](10) NULL,
	[publish_group_id] [varchar](100) NULL,
 CONSTRAINT [PK__tb_cbb_n__3213E83F548C6944] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tb_cbb_notify] ON
INSERT [dbo].[tb_cbb_notify] ([id], [create_user_id], [create_date], [last_update_user_id], [update_date], [is_delete], [subject], [content], [summary], [begin_date], [end_date], [notify_type], [is_top], [status], [auditer], [reason], [approve_date], [attment], [publish_user_ids], [publish_user_names], [view_count], [columnid], [images], [sendType], [publish_group_id]) VALUES (73, 1, CAST(0x0000A38F009606F2 AS DateTime), 1, CAST(0x0000A38F009606F2 AS DateTime), 0, N'testz', N'sdfsd asdfasdfa', NULL, NULL, NULL, 33, 1, 1, NULL, NULL, NULL, N'', N'1,', N'超级管理员,', 0, 35, N'', N'1_1_0', N'0')
SET IDENTITY_INSERT [dbo].[tb_cbb_notify] OFF
/****** Object:  Table [dbo].[tb_cbb_node_form_attribute]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_node_form_attribute](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[candidate] [varchar](255) NULL,
	[create_time] [datetime] NULL,
	[decison_into] [varchar](255) NULL,
	[decison_out] [varchar](255) NULL,
	[depts] [varchar](255) NULL,
	[descri] [varchar](255) NULL,
	[editdoc] [int] NULL,
	[el] [varchar](255) NULL,
	[is_mydep_can_accept] [int] NULL,
	[node_name] [varchar](255) NULL,
	[node_order] [int] NULL,
	[node_type] [varchar](255) NULL,
	[roles] [varchar](255) NULL,
	[secret_properties] [varchar](255) NULL,
	[update_time] [datetime] NULL,
	[writeable_properties] [varchar](255) NULL,
	[process_attribute_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_lottery]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_lottery](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[is_delete] [int] NOT NULL,
	[lottery_time] [datetime] NULL,
	[status] [int] NOT NULL,
	[title] [varchar](255) NULL,
	[create_user_id] [int] NULL,
	[is_repeat] [int] NULL,
	[user_source] [int] NULL,
	[question_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_ID_MEMBERSHIP]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_ID_MEMBERSHIP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[USER_] [numeric](19, 0) NULL,
	[GROUP_] [numeric](19, 0) NULL,
	[NAME_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_HIST_ACTINST]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_HIST_ACTINST](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[TYPE_] [varchar](255) NULL,
	[EXECUTION_] [varchar](255) NULL,
	[ACTIVITY_NAME_] [varchar](255) NULL,
	[START_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[TRANSITION_] [varchar](255) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_HIST_VAR]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_HIST_VAR](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[PROCINSTID_] [varchar](255) NULL,
	[EXECUTIONID_] [varchar](255) NULL,
	[VARNAME_] [varchar](255) NULL,
	[VALUE_] [varchar](5000) NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_SWIMLANE]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_SWIMLANE](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[NAME_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_LOB]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[JBPM4_LOB](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[BLOB_VALUE_] [image] NULL,
	[DEPLOYMENT_] [numeric](19, 0) NULL,
	[NAME_] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[JBPM4_LOB] ([DBID_], [DBVERSION_], [BLOB_VALUE_], [DEPLOYMENT_], [NAME_]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(10, 0)), 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A0A3C70726F63657373206E616D653D226761746865722220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E342F6A70646C223E0D0A2020203C737461727420673D2234312C33312C34382C343822206E616D653D22737461727431223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE694B6E69687E799BBE8AEB02220746F3D22E694B6E69687E799BBE8AEB0222F3E0D0A2020203C2F73746172743E0D0A2020203C7461736B2061737369676E65653D22237B637265617465727D2220673D223235372C32382C39322C353222206E616D653D22E694B6E69687E799BBE8AEB0223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E9A286E5AFBCE689B9E99885222F3E0D0A2020202020203C7472616E736974696F6E20673D222D34332C2D323122206E616D653D22E8BDACE694B6E69687E58886E58F912220746F3D22E694B6E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D222D33302C2D323122206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223235382C3138392C39322C353222206E616D653D22E9A286E5AFBCE689B9E99885223E0D0A2020202020203C7472616E736974696F6E20673D222D36392C2D323022206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E8BDACE9A286E5AFBCE689B9E99885222F3E0D0A2020202020203C7472616E736974696F6E20673D222D33372C2D323222206E616D653D22E8BDACE694B6E69687E58886E58F912220746F3D22E694B6E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D222D34322C313022206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C73637269707420657870723D226C6F6F702220673D2233352C3138332C39322C353222206E616D653D22E8BDACE9A286E5AFBCE689B9E99885223E0D0A2020202020203C7472616E736974696F6E20673D223139392C3331373A2D34362C2D313522206E616D653D22E8BDACE9A286E5AFBCE689B9E998852220746F3D22E9A286E5AFBCE689B9E99885222F3E0D0A2020203C2F7363726970743E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223435332C3138362C39322C353222206E616D653D22E694B6E69687E58886E58F91223E0D0A2020202020203C7472616E736974696F6E20673D222D33362C2D323122206E616D653D22E8BDACE99885E8AFBB2220746F3D22E694B6E69687E99885E8AFBB222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B20673D223434362C33352C39322C353222206E616D653D22E694B6E69687E99885E8AFBB223E0D0A2020203C61737369676E6D656E742D68616E646C657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E696D706C2E4D756C746941737369676E5461736B223E200D0A20202020202020203C2F61737369676E6D656E742D68616E646C65723E0D0A2020202020203C7472616E736974696F6E20673D222D32312C2D323022206E616D653D22E5B7B2E998852220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D227A697065722220673D223637382C34302C38302C343022206E616D653D22E5BD92E6A1A3223E0A202020203C6F6E206576656E743D2274696D656F7574223E0A2020202020203C74696D657220647565646174653D2231207365636F6E6473222F3E0A2020202020203C6576656E742D6C697374656E657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E5A69704265686176696F724C697374656E657222202F3E0A202020203C2F6F6E3E0A20202009093C7472616E736974696F6E20673D222D32352C2D313522206E616D653D22E5BD92E6A1A32220746F3D22656E6431222F3E0D0A2020203C2F7461736B3E0D0A2020203C656E6420673D223831372C33342C34382C343822206E616D653D22656E6431222F3E2020200D0A3C2F70726F636573733E, CAST(1 AS Numeric(19, 0)), N'cn/com/qytx/cbb/publicDom/service/gather-file.jpdl.xml')
INSERT [dbo].[JBPM4_LOB] ([DBID_], [DBVERSION_], [BLOB_VALUE_], [DEPLOYMENT_], [NAME_]) VALUES (CAST(8 AS Numeric(19, 0)), CAST(0 AS Numeric(10, 0)), 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A0A3C70726F63657373206E616D653D2264697370617463682220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E342F6A70646C223E0D0A2020203C737461727420673D2234392C3136322C34382C343822206E616D653D22737461727431223E0D0A2020202020203C7472616E736974696F6E20673D222D34302C2D323122206E616D653D22E8BDACE58F91E69687E68B9FE7A8BF2220746F3D22E58F91E69687E68B9FE7A8BF222F3E0D0A2020203C2F73746172743E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223435382C3135342C39322C353222206E616D653D22E58F91E69687E6A0B8E7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D222D33352C2D323622206E616D653D22E8BDACE79B96E7ABA02220746F3D22E5A597E7BAA2E79B96E7ABA0222F3E0D0A2020202020203C7472616E736974696F6E20673D223439382C3433303A2D35342C2D313822206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E7BBA7E7BBADE6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D223734322C3332323A2D34372C2D3922206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223733372C3336313A2D31382C2D3522206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223636322C3134392C39322C353222206E616D653D22E5A597E7BAA2E79B96E7ABA0223E0D0A2020202020203C7472616E736974696F6E20673D222D33312C2D313422206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223734302C3238323A2D33372C2D333222206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D22313131342C3238363A2D31362C2D3322206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B637265617465727D2220673D223231322C3135352C39322C353222206E616D653D22E58F91E69687E68B9FE7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D223338372C3138303A2D34312C2D3922206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020202020203C7472616E736974696F6E20673D223639332C3131383A2D32382C2D3122206E616D653D22E8BDACE79B96E7ABA02220746F3D22E5A597E7BAA2E79B96E7ABA0222F3E0D0A2020202020203C7472616E736974696F6E20673D223638392C37323A2D34312C2D323322206E616D653D22E8BDACE58F91E69687E58886E58F912220746F3D22E58F91E69687E58886E58F91222F3E0D0A2020202020203C7472616E736974696F6E20673D223637362C32353A2D33312C2D313622206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A2020203C7461736B2061737369676E65653D22237B61737369676E65727D2220673D223932312C3135362C39322C353222206E616D653D22E58F91E69687E58886E58F91223E0D0A2020202020203C7472616E736974696F6E20673D222D32382C2D3522206E616D653D22E8BDACE58886E58F912220746F3D22E5BD92E6A1A3222F3E0D0A2020203C2F7461736B3E0D0A0D0A2020203C656E6420673D22313332362C3136312C34382C343822206E616D653D22656E6431222F3E0D0A2020203C73637269707420657870723D226C6F6F702220673D223230392C3430332C39322C353222206E616D653D22E7BBA7E7BBADE6A0B8E7A8BF223E0D0A2020202020203C7472616E736974696F6E20673D222D33382C2D323222206E616D653D22E8BDACE6A0B8E7A8BF2220746F3D22E58F91E69687E6A0B8E7A8BF222F3E0D0A2020203C2F7363726970743E0D0A2020203C7461736B2061737369676E65653D227A697065722220673D22313134322C3135342C39322C353222206E616D653D22E5BD92E6A1A3223E0A20202020202020203C6F6E206576656E743D2274696D656F7574223E0A2020202020203C74696D657220647565646174653D2231207365636F6E6473222F3E0A2020202020203C6576656E742D6C697374656E657220636C6173733D22636E2E636F6D2E717974782E6362622E7075626C6963446F6D2E736572766963652E5A69704265686176696F724C697374656E657222202F3E0A202020203C2F6F6E3E0D0A2020202020203C7472616E736974696F6E20673D222D32302C2D323122206E616D653D22E5BD92E6A1A32220746F3D22656E6431222F3E0D0A2020203C2F7461736B3E0D0A3C2F70726F636573733E, CAST(7 AS Numeric(19, 0)), N'cn/com/qytx/cbb/publicDom/service/dispatch-file.jpdl.xml')
/****** Object:  Table [dbo].[JBPM4_DEPLOYPROP]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_DEPLOYPROP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DEPLOYMENT_] [numeric](19, 0) NULL,
	[OBJNAME_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[STRINGVAL_] [varchar](255) NULL,
	[LONGVAL_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(1 AS Numeric(19, 0)), N'gather', N'langid', N'jpdl-4.4', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(4 AS Numeric(19, 0)), CAST(1 AS Numeric(19, 0)), N'gather', N'pdid', N'gather-1', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(5 AS Numeric(19, 0)), CAST(1 AS Numeric(19, 0)), N'gather', N'pdkey', N'gather', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(6 AS Numeric(19, 0)), CAST(1 AS Numeric(19, 0)), N'gather', N'pdversion', NULL, CAST(1 AS Numeric(19, 0)))
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(9 AS Numeric(19, 0)), CAST(7 AS Numeric(19, 0)), N'dispatch', N'langid', N'jpdl-4.4', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(10 AS Numeric(19, 0)), CAST(7 AS Numeric(19, 0)), N'dispatch', N'pdid', N'dispatch-1', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(11 AS Numeric(19, 0)), CAST(7 AS Numeric(19, 0)), N'dispatch', N'pdkey', N'dispatch', NULL)
INSERT [dbo].[JBPM4_DEPLOYPROP] ([DBID_], [DEPLOYMENT_], [OBJNAME_], [KEY_], [STRINGVAL_], [LONGVAL_]) VALUES (CAST(12 AS Numeric(19, 0)), CAST(7 AS Numeric(19, 0)), N'dispatch', N'pdversion', NULL, CAST(1 AS Numeric(19, 0)))
/****** Object:  StoredProcedure [dbo].[init_attendance_days]    Script Date: 08/27/2014 19:30:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		CQL
-- Create date: 2014-07-14
-- Description:	初始化打卡时间表
-- =============================================
CREATE PROCEDURE [dbo].[init_attendance_days]
	@BeginDay varchar(50)  , 
	@EndDay varchar(50)
AS
declare @dayCount bigint --总天数
declare @week varchar(20)
declare @day datetime
BEGIN
	SET NOCOUNT ON;
	set @dayCount =(select datediff(day,@BeginDay,@EndDay))+1
	set @day = convert(datetime,@BeginDay+' 12:00:00')
	set @day =  ( dateadd(day,-1,@day))
	 delete from tb_attendance_days where day>@BeginDay and day <@EndDay
	declare @i int
	set @i=1
	while @i<@dayCount
	begin
	 set @day=( dateadd(day,1,@day))
     set @week= DATEPART(W,@day) 
	 if(@week=1) set @week=7
	  else set @week=@week-1
 
	  insert into tb_attendance_days(day,week) values(@day,@week)
	 
	 set @i=@i+1
	end
     
END
GO
/****** Object:  Table [dbo].[tb_op_email_body]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_email_body](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[attachment] [text] NULL,
	[attachment_size] [numeric](19, 0) NOT NULL,
	[compy_id] [int] NULL,
	[content_info] [text] NULL,
	[copy_to_id] [text] NULL,
	[copy_to_name] [text] NULL,
	[create_time] [datetime] NOT NULL,
	[important_level] [int] NOT NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NOT NULL,
	[last_update_user_id] [int] NOT NULL,
	[need_receipt] [int] NOT NULL,
	[secret_to_id] [text] NULL,
	[secret_to_name] [text] NULL,
	[send_status] [int] NOT NULL,
	[send_time] [datetime] NULL,
	[sms_remind] [int] NOT NULL,
	[subject] [varchar](200) NULL,
	[to_id] [text] NULL,
	[to_name] [text] NULL,
	[create_user_id] [int] NOT NULL,
	[send_type] [varchar](50) NULL,
 CONSTRAINT [PK__tb_op_em__3213E83F5066D2A5] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_desktop_module]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_op_desktop_module](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[desktop_page_id] [int] NULL,
	[is_delete] [int] NULL,
	[order_no] [int] NULL,
	[user_id] [int] NULL,
	[module_info_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tb_op_desktop_module] ON
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (564, 1, 59, 0, 0, 1, 3)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (565, 1, 59, 0, 1, 1, 4)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (566, 1, 59, 0, 2, 1, 5)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (567, 1, 59, 0, 3, 1, 9)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (568, 1, 59, 0, 4, 1, 10)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (569, 1, 59, 0, 5, 1, 11)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (570, 1, 59, 0, 6, 1, 12)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (571, 1, 59, 0, 7, 1, 13)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (572, 1, 59, 0, 8, 1, 14)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (573, 1, 59, 0, 9, 1, 15)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (574, 1, 59, 0, 10, 1, 19)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (575, 1, 59, 0, 11, 1, 21)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (576, 1, 59, 0, 12, 1, 22)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (577, 1, 59, 0, 13, 1, 23)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (578, 1, 59, 0, 14, 1, 27)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (579, 1, 60, 0, 0, 1, 29)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (580, 1, 60, 0, 1, 1, 30)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (581, 1, 60, 0, 2, 1, 32)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (582, 1, 60, 0, 3, 1, 35)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (583, 1, 60, 0, 4, 1, 36)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (584, 1, 60, 0, 5, 1, 37)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (585, 1, 60, 0, 6, 1, 38)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (586, 1, 60, 0, 7, 1, 40)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (587, 1, 60, 0, 8, 1, 41)
INSERT [dbo].[tb_op_desktop_module] ([id], [compy_id], [desktop_page_id], [is_delete], [order_no], [user_id], [module_info_id]) VALUES (588, 1, 60, 0, 9, 1, 42)
SET IDENTITY_INSERT [dbo].[tb_op_desktop_module] OFF
/****** Object:  Table [dbo].[tb_op_daily_review]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_op_daily_review](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appendix] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[content] [text] NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[daily_id] [int] NOT NULL,
	[is_delete] [int] NULL,
	[is_read] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[time] [datetime] NULL,
	[type] [int] NOT NULL,
	[review_id] [int] NULL,
	[user_id] [int] NULL,
 CONSTRAINT [PK__tb_op_da__3213E83F35B2DC69] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_message]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_om_message](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[content_info] [text] NULL,
	[create_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[msg_type] [int] NULL,
	[remind_flag] [int] NULL,
	[send_time] [datetime] NOT NULL,
	[create_user_id] [int] NULL,
	[to_uid] [int] NULL,
 CONSTRAINT [PK__tb_om_me__3213E83F2FA4FD58] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_task]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_task](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[importance] [varchar](50) NULL,
	[reality_end_time] [datetime] NULL,
	[reality_start_time] [datetime] NULL,
	[start_time] [datetime] NULL,
	[status] [int] NULL,
	[task_content] [text] NULL,
	[task_name] [varchar](255) NULL,
	[publish_user] [int] NULL,
	[undertake_user] [int] NULL,
	[end_time] [datetime] NULL,
	[reminder_type] [int] NULL,
	[reminder_status] [int] NULL,
	[map] [varchar](255) NULL,
	[picture] [varchar](255) NULL,
	[vox] [varchar](255) NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK__tb_task__3213E83F2D52A092] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_task_action]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_task_action](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[action_type] [int] NULL,
	[finish_plan] [int] NULL,
	[operate_time] [datetime] NULL,
	[report] [text] NULL,
	[operate_user] [int] NULL,
	[receive_user] [int] NULL,
	[task_id] [int] NULL,
	[map] [varchar](255) NULL,
	[picture] [varchar](255) NULL,
	[vox] [varchar](255) NULL,
 CONSTRAINT [PK__tb_task___3213E83F31233176] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_question]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_question](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[answer] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[file_id] [int] NULL,
	[is_delete] [int] NULL,
	[name] [varchar](600) NULL,
	[order_list] [int] NULL,
	[score] [int] NULL,
	[type_id] [int] NULL,
	[update_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[info_id] [int] NULL,
	[last_update_user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_op_email_to]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_op_email_to](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NOT NULL,
	[email_to_status] [int] NOT NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NOT NULL,
	[last_update_user_id] [int] NOT NULL,
	[mark_level] [int] NOT NULL,
	[read_status] [int] NOT NULL,
	[read_time] [datetime] NULL,
	[to_id] [int] NOT NULL,
	[to_type] [int] NOT NULL,
	[create_user_id] [int] NOT NULL,
	[email_body_id] [int] NOT NULL,
	[email_box_id] [int] NULL,
	[old_email_box_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[JBPM4_HIST_DETAIL]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_HIST_DETAIL](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[USERID_] [varchar](255) NULL,
	[TIME_] [datetime] NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[HPROCIIDX_] [numeric](10, 0) NULL,
	[HACTI_] [numeric](19, 0) NULL,
	[HACTIIDX_] [numeric](10, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
	[HTASKIDX_] [numeric](10, 0) NULL,
	[HVAR_] [numeric](19, 0) NULL,
	[HVARIDX_] [numeric](10, 0) NULL,
	[MESSAGE_] [varchar](255) NULL,
	[OLD_STR_] [varchar](255) NULL,
	[NEW_STR_] [varchar](255) NULL,
	[OLD_INT_] [numeric](10, 0) NULL,
	[NEW_INT_] [numeric](10, 0) NULL,
	[OLD_TIME_] [datetime] NULL,
	[NEW_TIME_] [datetime] NULL,
	[PARENT_] [numeric](19, 0) NULL,
	[PARENT_IDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_JOB]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_JOB](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[DUEDATE_] [datetime] NULL,
	[STATE_] [varchar](255) NULL,
	[ISEXCLUSIVE_] [numeric](1, 0) NULL,
	[LOCKOWNER_] [varchar](255) NULL,
	[LOCKEXPTIME_] [datetime] NULL,
	[EXCEPTION_] [text] NULL,
	[RETRIES_] [numeric](10, 0) NULL,
	[PROCESSINSTANCE_] [numeric](19, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[CFG_] [numeric](19, 0) NULL,
	[SIGNAL_] [varchar](255) NULL,
	[EVENT_] [varchar](255) NULL,
	[REPEAT_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_TASK]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_TASK](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [char](1) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[NAME_] [varchar](255) NULL,
	[DESCR_] [text] NULL,
	[STATE_] [varchar](255) NULL,
	[SUSPHISTSTATE_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[FORM_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[CREATE_] [datetime] NULL,
	[DUEDATE_] [datetime] NULL,
	[PROGRESS_] [numeric](10, 0) NULL,
	[SIGNALLING_] [numeric](1, 0) NULL,
	[EXECUTION_ID_] [varchar](255) NULL,
	[ACTIVITY_NAME_] [varchar](255) NULL,
	[HASVARS_] [numeric](1, 0) NULL,
	[SUPERTASK_] [numeric](19, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[PROCINST_] [numeric](19, 0) NULL,
	[SWIMLANE_] [numeric](19, 0) NULL,
	[TASKDEFNAME_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_lottery_user]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_lottery_user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_department] [varchar](255) NULL,
	[lottery_id] [int] NULL,
	[user_name] [varchar](255) NULL,
	[user_no] [varchar](255) NULL,
	[user_phone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_om_affairs]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_om_affairs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[last_update_time] [datetime] NULL,
	[last_update_user_id] [int] NULL,
	[remind_flag] [int] NOT NULL,
	[remind_time] [datetime] NULL,
	[body_id] [int] NULL,
	[create_user_id] [int] NULL,
	[to_id] [int] NULL,
 CONSTRAINT [PK__tb_om_af__3213E83F79D2FC8C] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tb_om_affairs] ON
INSERT [dbo].[tb_om_affairs] ([id], [compy_id], [create_time], [is_delete], [last_update_time], [last_update_user_id], [remind_flag], [remind_time], [body_id], [create_user_id], [to_id]) VALUES (295792, 1, CAST(0x0000A38F0096072E AS DateTime), 0, NULL, NULL, 0, CAST(0x0000A38F0096072E AS DateTime), 295326, 1, 1)
SET IDENTITY_INSERT [dbo].[tb_om_affairs] OFF
/****** Object:  Table [dbo].[tb_cbb_lottery_item]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_lottery_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[lottery_id] [int] NULL,
	[item_amount] [int] NULL,
	[order_index] [int] NULL,
	[status] [int] NULL,
	[item_name] [varchar](255) NULL,
	[item_content] [varchar](255) NULL,
 CONSTRAINT [PK_tb_cbb_lottery_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_notify_view]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_cbb_notify_view](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[notify_id] [int] NULL,
 CONSTRAINT [PK__tb_cbb_n__3213E83F07EC11B9] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_cbb_notify_comment]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_cbb_notify_comment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_date] [datetime] NULL,
	[create_user_id] [int] NULL,
	[comment] [varchar](1000) NULL,
	[notify_id] [int] NULL,
 CONSTRAINT [PK__tb_cbb_n__3213E83F50BBD860] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_question_user]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_question_user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[begin_time] [datetime] NULL,
	[comment] [varchar](255) NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[evaluate] [int] NULL,
	[is_delete] [int] NULL,
	[name] [varchar](255) NULL,
	[next_questionnaire_user] [varbinary](255) NULL,
	[prev_questionnaire_user] [varbinary](255) NULL,
	[statue] [int] NULL,
	[update_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[inquirer_id] [int] NULL,
	[info_id] [int] NULL,
	[survey_id] [int] NULL,
	[last_update_user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_question_item]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_question_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[compy_id] [int] NULL,
	[content] [varchar](600) NULL,
	[create_time] [datetime] NULL,
	[file_id] [int] NULL,
	[is_answer] [tinyint] NOT NULL,
	[is_delete] [int] NULL,
	[order_list] [int] NULL,
	[update_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[question_id] [int] NULL,
	[last_update_user_id] [int] NULL,
	[string_order] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_question_answer]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tb_question_answer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[answer] [varchar](2000) NULL,
	[compy_id] [int] NULL,
	[create_time] [datetime] NULL,
	[is_delete] [int] NULL,
	[update_time] [datetime] NULL,
	[create_user_id] [int] NULL,
	[question_id] [int] NULL,
	[info_id] [int] NULL,
	[last_update_user_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tb_cbb_lottery_result]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_cbb_lottery_result](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[item_id] [int] NULL,
	[user_id] [int] NULL,
	[lottery_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[JBPM4_VARIABLE]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_VARIABLE](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[KEY_] [varchar](255) NULL,
	[CONVERTER_] [varchar](255) NULL,
	[HIST_] [numeric](1, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[TASK_] [numeric](19, 0) NULL,
	[LOB_] [numeric](19, 0) NULL,
	[DATE_VALUE_] [datetime] NULL,
	[DOUBLE_VALUE_] [float] NULL,
	[CLASSNAME_] [varchar](255) NULL,
	[LONG_VALUE_] [numeric](19, 0) NULL,
	[STRING_VALUE_] [varchar](5000) NULL,
	[TEXT_VALUE_] [text] NULL,
	[EXESYS_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[JBPM4_PARTICIPATION]    Script Date: 08/27/2014 19:30:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[JBPM4_PARTICIPATION](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[GROUPID_] [varchar](255) NULL,
	[USERID_] [varchar](255) NULL,
	[TYPE_] [varchar](255) NULL,
	[TASK_] [numeric](19, 0) NULL,
	[SWIMLANE_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Default [DF__schema_ve__insta__0F183235]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[schema_version] ADD  DEFAULT (getdate()) FOR [installed_on]
GO
/****** Object:  Default [DF__tb_cbb_ne__categ__2196D523]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_news_column] ADD  DEFAULT ((0)) FOR [category]
GO
/****** Object:  Default [DF__tb_cbb_ne__order__228AF95C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_news_column] ADD  DEFAULT ((0)) FOR [order_index]
GO
/****** Object:  Default [DF__tb_cbb_ne__unitt__237F1D95]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_news_column] ADD  DEFAULT ((0)) FOR [unit_type]
GO
/****** Object:  Default [DF__tb_report__proje__5E9FE363]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [projectTotalInvest]
GO
/****** Object:  Default [DF__tb_report__compa__5F94079C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [companyInvest]
GO
/****** Object:  Default [DF__tb_report__compa__60882BD5]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [companyPrivateMoney]
GO
/****** Object:  Default [DF__tb_report__compa__617C500E]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [companyBankLoan]
GO
/****** Object:  Default [DF__tb_report__compa__62707447]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [companyOtherMoney]
GO
/****** Object:  Default [DF__tb_report__compa__63649880]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [companyShare]
GO
/****** Object:  Default [DF__tb_report__preIn__6458BCB9]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ChanQuan_GuQuanInvestPlan] ADD  DEFAULT ((0)) FOR [preInvestReturn]
GO
/****** Object:  Default [DF__tb_report__statu__72A6DC10]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_ZhaoBiaoFangAnBaoSong] ADD  DEFAULT ((1)) FOR [status]
GO
/****** Object:  ForeignKey [FK__JBPM4_DEP__DEPLO__4B6D135E]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_DEPLOYPROP]  WITH CHECK ADD FOREIGN KEY([DEPLOYMENT_])
REFERENCES [dbo].[JBPM4_DEPLOYMENT] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_EXE__INSTA__4E498009]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD FOREIGN KEY([INSTANCE_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_EXE__PAREN__4C613797]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD FOREIGN KEY([PARENT_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_EXE__SUBPR__4D555BD0]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD FOREIGN KEY([SUBPROCINST_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_EXE__SUPER__4F3DA442]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD FOREIGN KEY([SUPEREXEC_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HPROC__5031C87B]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_ACTINST]  WITH CHECK ADD FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HTASK__5125ECB4]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_ACTINST]  WITH CHECK ADD FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HACTI__521A10ED]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD FOREIGN KEY([HACTI_])
REFERENCES [dbo].[JBPM4_HIST_ACTINST] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HPROC__530E3526]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HTASK__5402595F]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HVAR___54F67D98]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD FOREIGN KEY([HVAR_])
REFERENCES [dbo].[JBPM4_HIST_VAR] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__SUPER__55EAA1D1]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_TASK]  WITH CHECK ADD FOREIGN KEY([SUPERTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HPROC__56DEC60A]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_VAR]  WITH CHECK ADD FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_HIS__HTASK__57D2EA43]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_HIST_VAR]  WITH CHECK ADD FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_ID___PAREN__58C70E7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_ID_GROUP]  WITH CHECK ADD FOREIGN KEY([PARENT_])
REFERENCES [dbo].[JBPM4_ID_GROUP] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_ID___GROUP__59BB32B5]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_ID_MEMBERSHIP]  WITH CHECK ADD FOREIGN KEY([GROUP_])
REFERENCES [dbo].[JBPM4_ID_GROUP] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_ID___USER___5AAF56EE]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_ID_MEMBERSHIP]  WITH CHECK ADD FOREIGN KEY([USER_])
REFERENCES [dbo].[JBPM4_ID_USER] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_JOB__CFG___5BA37B27]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_JOB]  WITH CHECK ADD FOREIGN KEY([CFG_])
REFERENCES [dbo].[JBPM4_LOB] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_LOB__DEPLO__5C979F60]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_LOB]  WITH CHECK ADD FOREIGN KEY([DEPLOYMENT_])
REFERENCES [dbo].[JBPM4_DEPLOYMENT] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_PAR__SWIML__5D8BC399]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_PARTICIPATION]  WITH CHECK ADD FOREIGN KEY([SWIMLANE_])
REFERENCES [dbo].[JBPM4_SWIMLANE] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_PAR__TASK___5E7FE7D2]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_PARTICIPATION]  WITH CHECK ADD FOREIGN KEY([TASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_SWI__EXECU__5F740C0B]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_SWIMLANE]  WITH CHECK ADD FOREIGN KEY([EXECUTION_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_TAS__SUPER__615C547D]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_TASK]  WITH CHECK ADD FOREIGN KEY([SUPERTASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_TAS__SWIML__60683044]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_TASK]  WITH CHECK ADD FOREIGN KEY([SWIMLANE_])
REFERENCES [dbo].[JBPM4_SWIMLANE] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_VAR__EXECU__625078B6]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD FOREIGN KEY([EXECUTION_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_VAR__EXESY__63449CEF]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD FOREIGN KEY([EXESYS_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_VAR__TASK___652CE561]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD FOREIGN KEY([TASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
GO
/****** Object:  ForeignKey [FK__JBPM4_VARI__LOB___6438C128]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD FOREIGN KEY([LOB_])
REFERENCES [dbo].[JBPM4_LOB] ([DBID_])
GO
/****** Object:  ForeignKey [FKFE08A29C9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_lottery]  WITH CHECK ADD  CONSTRAINT [FKFE08A29C9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_cbb_lottery] CHECK CONSTRAINT [FKFE08A29C9B9F8D7C]
GO
/****** Object:  ForeignKey [FK7ACD2D6EF7E0992]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_lottery_item]  WITH CHECK ADD  CONSTRAINT [FK7ACD2D6EF7E0992] FOREIGN KEY([lottery_id])
REFERENCES [dbo].[tb_cbb_lottery] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_lottery_item] CHECK CONSTRAINT [FK7ACD2D6EF7E0992]
GO
/****** Object:  ForeignKey [FKDE523B208220803B]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_lottery_result]  WITH CHECK ADD  CONSTRAINT [FKDE523B208220803B] FOREIGN KEY([item_id])
REFERENCES [dbo].[tb_cbb_lottery_item] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_lottery_result] CHECK CONSTRAINT [FKDE523B208220803B]
GO
/****** Object:  ForeignKey [FKDE523B20FB3E253B]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_lottery_result]  WITH CHECK ADD  CONSTRAINT [FKDE523B20FB3E253B] FOREIGN KEY([user_id])
REFERENCES [dbo].[tb_cbb_lottery_user] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_lottery_result] CHECK CONSTRAINT [FKDE523B20FB3E253B]
GO
/****** Object:  ForeignKey [FK7B2438EEF7E0992]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_lottery_user]  WITH CHECK ADD  CONSTRAINT [FK7B2438EEF7E0992] FOREIGN KEY([lottery_id])
REFERENCES [dbo].[tb_cbb_lottery] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_lottery_user] CHECK CONSTRAINT [FK7B2438EEF7E0992]
GO
/****** Object:  ForeignKey [FK4504AF912D02BD3]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_node_form_attribute]  WITH CHECK ADD  CONSTRAINT [FK4504AF912D02BD3] FOREIGN KEY([process_attribute_id])
REFERENCES [dbo].[tb_cbb_process_attribute] ([process_attribute_id])
GO
ALTER TABLE [dbo].[tb_cbb_node_form_attribute] CHECK CONSTRAINT [FK4504AF912D02BD3]
GO
/****** Object:  ForeignKey [FKA88295969B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify]  WITH CHECK ADD  CONSTRAINT [FKA88295969B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_cbb_notify] CHECK CONSTRAINT [FKA88295969B9F8D7C]
GO
/****** Object:  ForeignKey [FKA8829596A27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify]  WITH CHECK ADD  CONSTRAINT [FKA8829596A27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_cbb_notify] CHECK CONSTRAINT [FKA8829596A27BA512]
GO
/****** Object:  ForeignKey [FK806C34B655AADD7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify_comment]  WITH CHECK ADD  CONSTRAINT [FK806C34B655AADD7C] FOREIGN KEY([notify_id])
REFERENCES [dbo].[tb_cbb_notify] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_notify_comment] CHECK CONSTRAINT [FK806C34B655AADD7C]
GO
/****** Object:  ForeignKey [FK806C34B69B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify_comment]  WITH CHECK ADD  CONSTRAINT [FK806C34B69B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_cbb_notify_comment] CHECK CONSTRAINT [FK806C34B69B9F8D7C]
GO
/****** Object:  ForeignKey [FKA03B6BAE55AADD7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify_view]  WITH CHECK ADD  CONSTRAINT [FKA03B6BAE55AADD7C] FOREIGN KEY([notify_id])
REFERENCES [dbo].[tb_cbb_notify] ([id])
GO
ALTER TABLE [dbo].[tb_cbb_notify_view] CHECK CONSTRAINT [FKA03B6BAE55AADD7C]
GO
/****** Object:  ForeignKey [FKA03B6BAE9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_cbb_notify_view]  WITH CHECK ADD  CONSTRAINT [FKA03B6BAE9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_cbb_notify_view] CHECK CONSTRAINT [FKA03B6BAE9B9F8D7C]
GO
/****** Object:  ForeignKey [FKAAEB57349B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_oab_address]  WITH CHECK ADD  CONSTRAINT [FKAAEB57349B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_oab_address] CHECK CONSTRAINT [FKAAEB57349B9F8D7C]
GO
/****** Object:  ForeignKey [FKB16A81BF9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_oab_group]  WITH CHECK ADD  CONSTRAINT [FKB16A81BF9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_oab_group] CHECK CONSTRAINT [FKB16A81BF9B9F8D7C]
GO
/****** Object:  ForeignKey [FK6644B9EE9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_ohr_user_record]  WITH CHECK ADD  CONSTRAINT [FK6644B9EE9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_ohr_user_record] CHECK CONSTRAINT [FK6644B9EE9B9F8D7C]
GO
/****** Object:  ForeignKey [FK6644B9EEA27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_ohr_user_record]  WITH CHECK ADD  CONSTRAINT [FK6644B9EEA27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_ohr_user_record] CHECK CONSTRAINT [FK6644B9EEA27BA512]
GO
/****** Object:  ForeignKey [FK6644B9EEDBED01BF]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_ohr_user_record]  WITH CHECK ADD  CONSTRAINT [FK6644B9EEDBED01BF] FOREIGN KEY([user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_ohr_user_record] CHECK CONSTRAINT [FK6644B9EEDBED01BF]
GO
/****** Object:  ForeignKey [FK9EDF5DDA9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_affairs]  WITH CHECK ADD  CONSTRAINT [FK9EDF5DDA9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_affairs] CHECK CONSTRAINT [FK9EDF5DDA9B9F8D7C]
GO
/****** Object:  ForeignKey [FK9EDF5DDABBEE9C08]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_affairs]  WITH CHECK ADD  CONSTRAINT [FK9EDF5DDABBEE9C08] FOREIGN KEY([body_id])
REFERENCES [dbo].[tb_om_affairs_body] ([id])
GO
ALTER TABLE [dbo].[tb_om_affairs] CHECK CONSTRAINT [FK9EDF5DDABBEE9C08]
GO
/****** Object:  ForeignKey [FK9EDF5DDAEB48A10F]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_affairs]  WITH CHECK ADD  CONSTRAINT [FK9EDF5DDAEB48A10F] FOREIGN KEY([to_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_affairs] CHECK CONSTRAINT [FK9EDF5DDAEB48A10F]
GO
/****** Object:  ForeignKey [FK9C2CE79B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_affairs_body]  WITH CHECK ADD  CONSTRAINT [FK9C2CE79B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_affairs_body] CHECK CONSTRAINT [FK9C2CE79B9F8D7C]
GO
/****** Object:  ForeignKey [FK9C2CE7C1455580]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_affairs_body]  WITH CHECK ADD  CONSTRAINT [FK9C2CE7C1455580] FOREIGN KEY([from_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_affairs_body] CHECK CONSTRAINT [FK9C2CE7C1455580]
GO
/****** Object:  ForeignKey [FK18B48D179B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_message]  WITH CHECK ADD  CONSTRAINT [FK18B48D179B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_message] CHECK CONSTRAINT [FK18B48D179B9F8D7C]
GO
/****** Object:  ForeignKey [FK18B48D17B0ED603C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_om_message]  WITH CHECK ADD  CONSTRAINT [FK18B48D17B0ED603C] FOREIGN KEY([to_uid])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_om_message] CHECK CONSTRAINT [FK18B48D17B0ED603C]
GO
/****** Object:  ForeignKey [FKB0FCDB0B9EA9C11D]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_on_file]  WITH CHECK ADD  CONSTRAINT [FKB0FCDB0B9EA9C11D] FOREIGN KEY([file_sort])
REFERENCES [dbo].[tb_on_file_sort] ([sort_id])
GO
ALTER TABLE [dbo].[tb_on_file] CHECK CONSTRAINT [FKB0FCDB0B9EA9C11D]
GO
/****** Object:  ForeignKey [FKA045320BCA0200A8]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_daily_review]  WITH CHECK ADD  CONSTRAINT [FKA045320BCA0200A8] FOREIGN KEY([review_id])
REFERENCES [dbo].[tb_op_daily_review] ([id])
GO
ALTER TABLE [dbo].[tb_op_daily_review] CHECK CONSTRAINT [FKA045320BCA0200A8]
GO
/****** Object:  ForeignKey [FKA045320BDBED01BF]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_daily_review]  WITH CHECK ADD  CONSTRAINT [FKA045320BDBED01BF] FOREIGN KEY([user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_op_daily_review] CHECK CONSTRAINT [FKA045320BDBED01BF]
GO
/****** Object:  ForeignKey [FK7881367C31C6938A]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_desktop_module]  WITH CHECK ADD  CONSTRAINT [FK7881367C31C6938A] FOREIGN KEY([module_info_id])
REFERENCES [dbo].[tb_module_info] ([module_id])
GO
ALTER TABLE [dbo].[tb_op_desktop_module] CHECK CONSTRAINT [FK7881367C31C6938A]
GO
/****** Object:  ForeignKey [FKC55D11729B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_email_body]  WITH CHECK ADD  CONSTRAINT [FKC55D11729B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_op_email_body] CHECK CONSTRAINT [FKC55D11729B9F8D7C]
GO
/****** Object:  ForeignKey [FK23A29A4B1858D3CF]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_email_to]  WITH CHECK ADD  CONSTRAINT [FK23A29A4B1858D3CF] FOREIGN KEY([email_box_id])
REFERENCES [dbo].[tb_op_email_box] ([id])
GO
ALTER TABLE [dbo].[tb_op_email_to] CHECK CONSTRAINT [FK23A29A4B1858D3CF]
GO
/****** Object:  ForeignKey [FK23A29A4B9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_email_to]  WITH CHECK ADD  CONSTRAINT [FK23A29A4B9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_op_email_to] CHECK CONSTRAINT [FK23A29A4B9B9F8D7C]
GO
/****** Object:  ForeignKey [FK23A29A4BF1B37B25]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_op_email_to]  WITH CHECK ADD  CONSTRAINT [FK23A29A4BF1B37B25] FOREIGN KEY([email_body_id])
REFERENCES [dbo].[tb_op_email_body] ([id])
GO
ALTER TABLE [dbo].[tb_op_email_to] CHECK CONSTRAINT [FK23A29A4BF1B37B25]
GO
/****** Object:  ForeignKey [FKBBA5BA974BC21CA3]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question]  WITH CHECK ADD  CONSTRAINT [FKBBA5BA974BC21CA3] FOREIGN KEY([info_id])
REFERENCES [dbo].[tb_questionnaire] ([id])
GO
ALTER TABLE [dbo].[tb_question] CHECK CONSTRAINT [FKBBA5BA974BC21CA3]
GO
/****** Object:  ForeignKey [FKBBA5BA979B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question]  WITH CHECK ADD  CONSTRAINT [FKBBA5BA979B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question] CHECK CONSTRAINT [FKBBA5BA979B9F8D7C]
GO
/****** Object:  ForeignKey [FKBBA5BA97A27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question]  WITH CHECK ADD  CONSTRAINT [FKBBA5BA97A27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question] CHECK CONSTRAINT [FKBBA5BA97A27BA512]
GO
/****** Object:  ForeignKey [FKFE60BA66477616C6]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_answer]  WITH CHECK ADD  CONSTRAINT [FKFE60BA66477616C6] FOREIGN KEY([question_id])
REFERENCES [dbo].[tb_question] ([id])
GO
ALTER TABLE [dbo].[tb_question_answer] CHECK CONSTRAINT [FKFE60BA66477616C6]
GO
/****** Object:  ForeignKey [FKFE60BA664BC21CA3]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_answer]  WITH CHECK ADD  CONSTRAINT [FKFE60BA664BC21CA3] FOREIGN KEY([info_id])
REFERENCES [dbo].[tb_questionnaire] ([id])
GO
ALTER TABLE [dbo].[tb_question_answer] CHECK CONSTRAINT [FKFE60BA664BC21CA3]
GO
/****** Object:  ForeignKey [FKFE60BA669B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_answer]  WITH CHECK ADD  CONSTRAINT [FKFE60BA669B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_answer] CHECK CONSTRAINT [FKFE60BA669B9F8D7C]
GO
/****** Object:  ForeignKey [FKFE60BA66A27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_answer]  WITH CHECK ADD  CONSTRAINT [FKFE60BA66A27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_answer] CHECK CONSTRAINT [FKFE60BA66A27BA512]
GO
/****** Object:  ForeignKey [FK2FF67FBB477616C6]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_item]  WITH CHECK ADD  CONSTRAINT [FK2FF67FBB477616C6] FOREIGN KEY([question_id])
REFERENCES [dbo].[tb_question] ([id])
GO
ALTER TABLE [dbo].[tb_question_item] CHECK CONSTRAINT [FK2FF67FBB477616C6]
GO
/****** Object:  ForeignKey [FK2FF67FBB9B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_item]  WITH CHECK ADD  CONSTRAINT [FK2FF67FBB9B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_item] CHECK CONSTRAINT [FK2FF67FBB9B9F8D7C]
GO
/****** Object:  ForeignKey [FK2FF67FBBA27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_item]  WITH CHECK ADD  CONSTRAINT [FK2FF67FBBA27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_item] CHECK CONSTRAINT [FK2FF67FBBA27BA512]
GO
/****** Object:  ForeignKey [FK2FFBF0734BC21CA3]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_user]  WITH CHECK ADD  CONSTRAINT [FK2FFBF0734BC21CA3] FOREIGN KEY([info_id])
REFERENCES [dbo].[tb_questionnaire] ([id])
GO
ALTER TABLE [dbo].[tb_question_user] CHECK CONSTRAINT [FK2FFBF0734BC21CA3]
GO
/****** Object:  ForeignKey [FK2FFBF07354E7CFEB]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_user]  WITH CHECK ADD  CONSTRAINT [FK2FFBF07354E7CFEB] FOREIGN KEY([inquirer_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_user] CHECK CONSTRAINT [FK2FFBF07354E7CFEB]
GO
/****** Object:  ForeignKey [FK2FFBF073570367D0]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_user]  WITH CHECK ADD  CONSTRAINT [FK2FFBF073570367D0] FOREIGN KEY([survey_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_user] CHECK CONSTRAINT [FK2FFBF073570367D0]
GO
/****** Object:  ForeignKey [FK2FFBF0739B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_user]  WITH CHECK ADD  CONSTRAINT [FK2FFBF0739B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_user] CHECK CONSTRAINT [FK2FFBF0739B9F8D7C]
GO
/****** Object:  ForeignKey [FK2FFBF073A27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_question_user]  WITH CHECK ADD  CONSTRAINT [FK2FFBF073A27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_question_user] CHECK CONSTRAINT [FK2FFBF073A27BA512]
GO
/****** Object:  ForeignKey [FK30C615929B9F8D7C]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_questionnaire]  WITH CHECK ADD  CONSTRAINT [FK30C615929B9F8D7C] FOREIGN KEY([create_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_questionnaire] CHECK CONSTRAINT [FK30C615929B9F8D7C]
GO
/****** Object:  ForeignKey [FK30C61592A27BA512]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_questionnaire]  WITH CHECK ADD  CONSTRAINT [FK30C61592A27BA512] FOREIGN KEY([last_update_user_id])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_questionnaire] CHECK CONSTRAINT [FK30C61592A27BA512]
GO
/****** Object:  ForeignKey [FK__tb_report__proje__38453051]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_report_sectionDic]  WITH CHECK ADD FOREIGN KEY([projectId])
REFERENCES [dbo].[tb_report_ProjectDic] ([projectId])
GO
/****** Object:  ForeignKey [FKA4FEB4B6759DECAB]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_task]  WITH CHECK ADD  CONSTRAINT [FKA4FEB4B6759DECAB] FOREIGN KEY([publish_user])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_task] CHECK CONSTRAINT [FKA4FEB4B6759DECAB]
GO
/****** Object:  ForeignKey [FKA4FEB4B67D09B05B]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_task]  WITH CHECK ADD  CONSTRAINT [FKA4FEB4B67D09B05B] FOREIGN KEY([undertake_user])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_task] CHECK CONSTRAINT [FKA4FEB4B67D09B05B]
GO
/****** Object:  ForeignKey [FKDB842DDF8B3E71AE]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_task_action]  WITH CHECK ADD  CONSTRAINT [FKDB842DDF8B3E71AE] FOREIGN KEY([task_id])
REFERENCES [dbo].[tb_task] ([id])
GO
ALTER TABLE [dbo].[tb_task_action] CHECK CONSTRAINT [FKDB842DDF8B3E71AE]
GO
/****** Object:  ForeignKey [FKDB842DDFB6B49B57]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_task_action]  WITH CHECK ADD  CONSTRAINT [FKDB842DDFB6B49B57] FOREIGN KEY([receive_user])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_task_action] CHECK CONSTRAINT [FKDB842DDFB6B49B57]
GO
/****** Object:  ForeignKey [FKDB842DDFCCAA0156]    Script Date: 08/27/2014 19:30:39 ******/
ALTER TABLE [dbo].[tb_task_action]  WITH CHECK ADD  CONSTRAINT [FKDB842DDFCCAA0156] FOREIGN KEY([operate_user])
REFERENCES [dbo].[tb_user_info] ([user_id])
GO
ALTER TABLE [dbo].[tb_task_action] CHECK CONSTRAINT [FKDB842DDFCCAA0156]
GO
