
function login_login(){

    var url=getUrl_manage().logining;

    var loginaccount =$('input[name="loginaccount"]').val();
    var password =$('input[name="password"]').val();
    var rememberpassword = $("#rememberpassword").is(":checked");//获取是否选中
    // var bool = checkjs(loginaccount, password);//自定义校验

    var bool = true;
    if(bool){
        var data={
            loginaccount:loginaccount,
            password:password,
            rememberpassword:rememberpassword,
        };
        ajaxSubmit(url,data,callbackgetAdminInfoPage);
    }

}

function getNavList() {
    var url=getUrl_manage().getNavList;
    ajaxSubmitByJson(url,null,callgetNavList);
}

function callbackgetAdminInfoPage(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        var url=getUrl_manage().main;
        window.location.href=url;
    }else{
        layer.msg(data.message, {icon: 2});
    }
    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });
}

function login_logout(){

    var url=getUrl_manage().logout;

    ajaxSubmit(url,null,callLogout);
}

function callLogout(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        //alert(data.message);
        var url=getUrl_manage().gotologin;
        window.location.href=url;
    }else{
        // alert(data.message);
        layer.msg(data.message, {time: 5000, icon:6});
    }

}

function callgetNavList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){

        if (isNotEmpty(data.data.data)) {
            var appCache = data.data;

            if (isNotEmpty(appCache.data.title)) {
                //页头
                var Top_Title = appCache.data.title;
                $("#topTitle").html("欢迎进入" + Top_Title);
                $("#spanTitle").html("欢迎进入" + Top_Title);
            }

            if (isNotEmpty(appCache.data.bottom)) {
                if (!isNotEmpty(appCache.data.bottom) || !isNotEmpty(appCache.data.bottom.name) || !isNotEmpty(appCache.data.bottom.declaration)) {
                    return;
                }
                //页脚
                var bottom_html = "";
                var bottom_name = appCache.data.bottom.name;
                var bottom_declaration = appCache.data.bottom.declaration;
                var bottom_url = appCache.data.bottom.url;
                if(!isNotEmpty(bottom_url)){
                    bottom_url = "#";
                }

                if (isNotEmpty(appCache.data.bottom.image.src) && appCache.data.bottom.image.src != '/') {
                    $(".systemlogo").css({"background-image":"url(" + appCache.data.bottom.image.src + ")"});
                    if(isNotEmpty(appCache.data.bottom.image.width) && isNotEmpty(appCache.data.bottom.image.height)){
                        $(".systemlogo").css("background-size", appCache.data.bottom.image.width + "px " + appCache.data.bottom.image.height + 'px');
                    }
                }else{
                    $(".systemlogo").css({"background-image":"url(/uimaker/images/loginlogo-5aa2cf210dbf067bd57c42a470703719.png)"});
                }

                bottom_html = bottom_declaration + " <a href=\"" + bottom_url + "\">" + bottom_name + "</a>";
                $("#bottom_mian").html(bottom_html);
            }

            if (isNotEmpty(appCache.data.guidepageUrl)) {
                //设置引导页a标签
                $("#guidepage").attr("href", appCache.data.guidepageUrl);
            }


        }
        layui.use('element', function(){
            var element =  layui.element;
            element.render();
        });
    }else{
        layer.msg(data.message);
    }
}

function checkjs(loginaccount, password) {

    if (!isNotEmpty(loginaccount)) {
        layer.msg("用户名不能为空", {icon: 2});return false;
    }else if(!isNotEmpty(password)){
        layer.msg("密码不能为空", {icon: 2});return false;
    }

    return true;
}

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

layui.use(['form', 'layer'], function(){
    var $ = layui.$ //由于layer弹层依赖jQuery，所以可以直接得到
        ,form = layui.form
        ,layer = layui.layer;


    form.verify({
        loginaccount:function (value) {
            if (!(/\S/).test(value)) {
                return "请输入登录账号";
            }
            if (!(/^(?!.*\s).{5,12}$/.test(value))) {
                return "请输入5-12个字符不含空格的登录账号";
            }
        },
        password:function (value) {
            if (!(/\S/).test(value)) {
                return "请输入密码";
            }
        }
    });

    // 监听提交
    form.on('submit(loginbtn)', function(data){

        if(isNotEmpty(data.field.loginaccount) && isNotEmpty(data.field.password)){

            // var tdJsonStr = JSON.stringify(data.field);
            // if(tdStr != tdJsonStr){
            //     tdStr = tdJsonStr;
            // }
            login_login();
        }
        return false;
    });

    form.render();
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if(r!=null)return  unescape(r[2]); return null;
}



//记住密码----------------------------------------start
function getrememberpassword(){
    var url=getUrl_manage().getLoginCookie;
    var data={};
    ajaxSubmit(url,data,function (d) {
        if(null!=d&&d.actioncode=='SUCCESS')
            var data=d.data;
        if (isNotEmpty(data)){
            var loginaccount=data.loginaccount==null?"":data.loginaccount;
            var password=data.password==null?"":data.password;
            $('.loginuser').val(loginaccount);
            $('.loginpwd').val(password);
            if (isNotEmpty(loginaccount)&&isNotEmpty(password)){
                $("#rememberpassword").attr("checked","true");
            }
        }else{
            layer.msg(d.message,{icon: 5});
        }
        layui.use(['form'], function(){
            var form=layui.form;
            form.render()
        });
    });
}
//记住密码----------------------------------------end
