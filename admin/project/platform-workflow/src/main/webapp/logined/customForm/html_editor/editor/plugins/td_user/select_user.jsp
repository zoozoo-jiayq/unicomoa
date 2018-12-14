<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<!--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2008 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Text field dialog window.
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="noindex, nofollow" name="robots" />
	<script src="../../dialog/common/fck_dialog_common.js" type="text/javascript"></script>
  <script src="/inc/js/utility.js" type="text/javascript"></script>
	<script type="text/javascript">

var dialog	= window.parent ;//应该是对话框外壳所对应的窗口 by dq 090520
var oEditor = dialog.InnerDialogLoaded() ;//获得最外层界面所对应的window，该函数在对话框外壳程序中(fckdialog.html)定义 by dq 090520

// Gets the document DOM
var oDOM = oEditor.FCK.EditorDocument ;//获得编辑区域的iframe对应的窗口的document（定义及赋值：fck.js第942行-->114行-->fc,editingarea.js第28行-->220行），它是直接可编辑的 by dq 090520
//Selection是什么？(在fckselection.js第24行定义)，GetSelectedElement函数在哪定义？(在fckselection_ie.js第48行定义) by dq 090520
var oActiveEl = dialog.Selection.GetSelectedElement() ;//获得编辑区域里被选中的控件对象 by dq 090520
var item_id = "";
window.onload = function()//对话框弹出时，最内层的对话框显示完成后，执行这个onload函数 by dq 090520
{
	if ( oActiveEl && oActiveEl.getAttribute('tagName') == 'IMG')//编辑一个编辑区域内的控件时，走这个分支 by dq 090520
	{
		GetE('ITEM_NAME').value	= oActiveEl.getAttribute('value') ;
		GetE('SELECT_TYPE').value	= oActiveEl.getAttribute('type') ;
	}
	else//在编辑区域内新建一个控件时，走这个分支 by dq 090520
		oActiveEl = null ;

	dialog.SetOkButton( true ) ;//该函数在fckdialog.html 683行定义 by dq 090520
	dialog.SetAutoSize( true ) ;//该函数在fckdialog.html 213行定义 by dq 090520
	SelectField( 'ITEM_NAME' ) ;//在编辑区域设置刚创建的控件为选中状态，该函数在fck_dialog_common.js 第100行定义 by dq 090520
}

function Ok()
{
  //为“撤销”操作做准备，记录当前编辑区域的状态（猜的） by dq 090520
	oEditor.FCKUndo.SaveUndoStep() ;

  if(ITEM_NAME.value=="")
  {
     alert("控件名称不能为空");
     return;
  }
 	 var oNode=oEditor.FCK.EditorDocument.createTextNode(GetE("ITEM_NAME").value+":");    					
	oEditor.FCK.InsertElement( oNode );
	oActiveEl = CreateNamedElement( oEditor, oActiveEl, 'select', {name:GetE("ITEM_NAME").value,title:GetE("ITEM_NAME").value,} ) ;

	SetAttribute( oActiveEl, 'src'	, '/module/html_editor/editor/images/user.gif' ) ;
	SetAttribute( oActiveEl, 'class'	,  'USER') ;
	SetAttribute( oActiveEl, 'className'	,  'USER') ;
	SetAttribute( oActiveEl, 'align'	,  'absMiddle') ;
	SetAttribute( oActiveEl, 'border'	,  0) ;
	SetAttribute( oActiveEl, 'type'	,  SELECT_TYPE.value) ;
	SetAttribute( oActiveEl, 'value'	,  ITEM_NAME.value.replace("\"","&quot;")) ;
  
  oActiveEl.style.cursor = 'hand';

  return true;
}

</script>
</head>
<body style="overflow: hidden">

<table width="100%" style="height: 100%">
		<tr>
			<td align="center">
				<table cellspacing="3" cellpadding="0" border="0">
          <tr>
              <td nowrap><b>对应的输入框控件名称</b>：
              </td>
              <td nowrap>
               <Input id="ITEM_NAME" type="text" size="20">
              </td>
          </tr>
          
				</table>
			</td>
		</tr>
</table>
</body>
</html>


