var path = document.getElementById("cabpath").value;
document.write('<object id="ntkoocx" classid="clsid:DB5B521C-DA92-48e0-AE32-BDC944858D42" width="0" height="0"  ');
document.write('codebase="'+path+'/js/logined/ntko/ntkoWebSign.cab#version=4,0,2,2" width=40% height=20>   ');
// document.write('<param name="BackColor" value="16744576">   ');
// document.write('<param name="ForeColor" value="16777215">   ');
document.write('<SPAN STYLE="color:red">不能装载NTKO WebSignHelper 控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
document.write('</object>   ');