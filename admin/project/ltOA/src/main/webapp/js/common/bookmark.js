/**
 * 加入收藏夹
 **/

function addBookmark(title,url) {
    if (document.all){
        try{
            window.external.addFavorite(url,title);
        }catch(e){
            alert( "加入收藏失败，请使用Ctrl+D进行添加" );
        }
    }else if (window.sidebar){
        window.sidebar.addPanel(title, url, "");
    }else{
        alert( "加入收藏失败，请使用Ctrl+D进行添加" );
    }
}