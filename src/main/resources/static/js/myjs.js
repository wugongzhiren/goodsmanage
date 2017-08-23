/**
 * Created by Administrator on 2017/3/30.
 */
$(function(){
    $("#navhead-ul a").click(function(){
        var that = this.id;
        if(that === 'shouyin'){
            urlLoad('#content','/sale');
        }else if(that === 'ruku'){
            urlLoad('#content','/instore');
        }else if(that === 'kucun'){
            urlLoad('#content','/goods');
        }else if(that === 'shouru'){
            urlLoad('#content','/income');
        }else if(that === 'huiyuan'){
            urlLoad('#content','/customerVip');
        }
        else if(that ==='saleSetting'){
            urlLoad('#content','/setting');
        }
    });
});

//加载页面
function urlLoad(e, pageUrl) {
    $(e).load(pageUrl, function () {

    });
}
