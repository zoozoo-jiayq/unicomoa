--tb_attendance
CREATE TABLE [dbo].[tb_attendance] (
[att_id] int NOT NULL IDENTITY(1,1) ,
[create_time] datetime NULL ,
[create_user_id] int NULL ,
[position] varchar(255) NULL ,
[longitude] varchar(50) NULL ,
[latitude] varchar(50) NULL ,
[att_Source] int NULL 
)
GO
DBCC CHECKIDENT(N'[dbo].[tb_attendance]', RESEED, 88)
GO
ALTER TABLE [dbo].[tb_attendance] ADD PRIMARY KEY ([att_id])
GO
ALTER TABLE [dbo].[tb_attendance] ADD UNIQUE ([att_id] ASC)
GO

--tb_attendance_days
CREATE TABLE [dbo].[tb_attendance_days] (
[att_day_id] int NOT NULL IDENTITY(1,1) ,
[day] datetime NULL ,
[week] varchar(255) NULL 
)
GO
DBCC CHECKIDENT(N'[dbo].[tb_attendance_days]', RESEED, 365)
GO
ALTER TABLE [dbo].[tb_attendance_days] ADD PRIMARY KEY ([att_day_id])
GO
ALTER TABLE [dbo].[tb_attendance_days] ADD UNIQUE ([att_day_id] ASC)
GO

--tb_attendance_ipset
CREATE TABLE [dbo].[tb_attendance_ipset] (
[ipset_id] int NOT NULL IDENTITY(1,1) ,
[begin_ip] varchar(100) NULL ,
[create_time] datetime NULL ,
[create_user_id] int NULL ,
[end_ip] varchar(100) NULL ,
[is_delete] int NULL ,
[last_update_time] datetime NULL ,
[last_update_user_id] int NULL 
)

GO
DBCC CHECKIDENT(N'[dbo].[tb_attendance_ipset]', RESEED, 7)
GO
ALTER TABLE [dbo].[tb_attendance_ipset] ADD PRIMARY KEY ([ipset_id])
GO
ALTER TABLE [dbo].[tb_attendance_ipset] ADD UNIQUE ([ipset_id] ASC)
GO