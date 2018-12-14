 $(document).ready(function(){
    var bro=$.browser;
    var binfo="";
    if(bro.msie)  {
        binfo= "Microsoft Internet Explorer " +bro.version;
        if(eval(parseInt($.browser.version))<10) {
        		 $("#iecheck").show();
        }          
    }
    if(bro.mozilla)  {
        binfo= "Mozilla Firefox "+bro.version;
    }
    if(bro.safari) {
        binfo= "Apple Safari "+bro.version;
    }
    if(bro.opera)  {
        binfo= "Opera "+bro.version;
    }
});

 