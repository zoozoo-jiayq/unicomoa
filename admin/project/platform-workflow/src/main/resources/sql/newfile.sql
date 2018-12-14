/*删除公文表单权限设置菜单，删除套红模板类型菜单*/
update tb_module_info set url = '/logined/docTemplate/docTemplateList.jsp' where module_id = 61


alter table tb_document_type add gongwen_type int;


alter table tb_cbb_node_form_attribute add time_set int;


alter table tb_cbb_workflow_var add last_update_time datetime;
