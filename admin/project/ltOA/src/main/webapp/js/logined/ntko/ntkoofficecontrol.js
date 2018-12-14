var ocxname = "ntko";
if(currentUser){
	ocxname = currentUser;
}
document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
document.write('<object id="TANGER_OCX"  classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EC0"    ');
document.write('codebase="'+basePath+'js/logined/ntko/OfficeControl.cab#version=5,0,2,9" width="100%" height="800">   ');
document.write('<param name="IsUseUTF8URL" value="-1">   ');
document.write('<param name="IsUseUTF8Data" value="-1">   ');
document.write('<param name="BorderStyle" value="1">   ');
document.write('<param name="BorderColor" value="14402205">   ');
document.write('<param name="TitlebarColor" value="15658734">   ');
document.write('<param name="TitlebarTextColor" value="0">   ');
document.write('<param name="MenubarColor" value="14402205">   ');
document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
document.write('<param name="MenuBarStyle" value="3">   ');
document.write('<param name="MenuButtonStyle" value="7">   ');
document.write('<param name="WebUserName" value="'+ocxname+'">   ');
document.write('<param name="ProductCaption" value="信阳市交通运输局">   ');
document.write('<param name="ProductKey" value="378A525EC6B4D4AC969E78A87251FCECF4E6C544">   ');
document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
document.write('</object>');
