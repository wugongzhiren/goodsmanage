/**
 * Created by Administrator on 2017/3/30.
 */
$(function(){
    $("#navhead-ul a").click(function(){
        var that = this.id;
        if(that === 'shouyin'){
            urlLoad('#content','/sale');
        }else if(that === 'ruku'){
            urlLoad('#content','/income');
        }else if(that === 'kucun'){
            urlLoad('#content','/index');
        }else if(that === 'shouru'){
            urlLoad('#content','/index');
        }else if(that === 'huiyuan'){
            urlLoad('#content','/index');
        }
    });
});

//加载页面
function urlLoad(e, pageUrl) {
    $(e).load(pageUrl, function () {

    });
}
