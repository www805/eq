var etypessid;
var ssid;
var goaction = false;
var fdtype = "FD_AVST";
var ipServer = "192.168.17.183";
var portServer = 80;
var userServer = "admin";
var passwordServer = "admin123";
var fieldFrom;
var fromFTPStr;

function getFlushbonadingList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getFlushbonadingList;
    var livingurl=$("input[name='livingurl']").val();
    var user=$("input[name='user']").val();
    var etnum=$("input[name='etnum']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            livingurl: livingurl,
            user: user,
            etnum: etnum,
            etypessid: etypessid,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callFlushbonadingList);
}

function getFlushbonadingIndex(livingurl, previewurl, user, etnum, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getFlushbonadingList;
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            livingurl: livingurl,
            user: user,
            etnum: etnum,
            etypessid: etypessid,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url, data, callFlushbonadingList);
}


//修改状态
function UpdateBurnboolFoDiskrecbool(ssidd, sta, b) {
    if (b == 1) {
        var url = getUrl_manageZk().UpdateDiskrecbool;
    } else {
        var url = getUrl_manageZk().UpdateBurnbool;
    }
    ssid = ssidd;

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            state: sta
        }
    };
    ajaxSubmitByJson(url, data, callUpdateBurnboolFoDiskrecbool);
}


function UpdateDefaulturlboolFoDiskrecbool(ssidd, sta, b) {
    if (b == 1) {
        var url = getUrl_manageZk().UpdateDiskrecbool;
    } else if (b == 2) {
        var url = getUrl_manageZk().updateDefaulturlbool;
    } else {
        var url = getUrl_manageZk().UpdateBurnbool;
    }
    ssid = ssidd;

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            state: sta
        }
    };
    ajaxSubmitByJson(url, data, callUpdateBurnboolFoDiskrecbool);
}

//查询单个
function getFlushbonadingById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getFlushbonadingById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callFlushbonadingById);
}

//获取片头列表
function getptdjconst() {
    var url = getUrl_manage().getptdjconst;

    var ptdjconst = $("#getptdjconstBtn").val();
    var mustUpdateBool = false;
    if (ptdjconst == "更新片头列表") {
        mustUpdateBool = true;
    }

    var data={
        token:INIT_CLIENTKEY,
        param: {
            fdType: fdtype,
            mustUpdateBool: mustUpdateBool,
            flushbonadingetinfossid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callptdjconst);
}


//集中存储管理中心，获取FTP上传配置
function getMiddFtp() {

    //使用模块
    var html='<form class="layui-form" style="margin-top: 20px;">\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务名</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="servicename" required  lay-verify="required" placeholder="请输入服务名" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否启用</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="enable" id="enable" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>本机设备ID</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="deviceid" required  lay-verify="required" placeholder="请输入本机设备ID" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">被动模式</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="pasvmode" id="pasvmode" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务器地址</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="serverip" required  lay-verify="required|setip" placeholder="请输入服务器地址" autocomplete="off" class="layui-input" onkeyup="this.value=value.replace(/[^\\d|.]/g,\'\');if(this.value==\'\')(this.value=\'\');">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>服务器端口</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="number" name="serverport" required  lay-verify="required|number" placeholder="请输入服务器端口" autocomplete="off" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>用户名</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="text" name="svrusr" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>密码</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="password" name="svrpwd" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否自动重启</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="restart" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>心跳服务器地址</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="text" name="hreadbeatip" required  lay-verify="required|setip" placeholder="请输入心跳服务器地址" autocomplete="off" class="layui-input"  onkeyup="this.value=value.replace(/[^\\d|.]/g,\'\');if(this.value==\'\')(this.value=\'\');">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>流控限速(kb)</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="number" name="limit_speed" required  lay-verify="required|number" placeholder="请输入流控限速" autocomplete="off" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">过滤条件</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="text" name="search_filter" id="datetj" lay-verify="" placeholder="过滤该日期之前不文件上传" autocomplete="off" class="layui-input">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label">是否过滤</label>\n' +
        '                    <div class="layui-input-inline">\n' +
        '                        <input type="checkbox" name="filter_enable" id="filter_enable" lay-skin="switch" lay-filter="switchTest">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>';


    layui.use(['form','laydate'], function() {
        var form = layui.form;
        var laydate = layui.laydate;
        var index = layer.open({
            type: 1,
            title: '集中存储管理中心',
            skin: 'layui-layer-rim', //加上边框
            area: ['800px', '580px'],
            btn: ['保存', '取消'],
            content: html,
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromFTP').attr('lay-submit', '');//将按钮弄成能提交的
                laydate.render({
                    elem: '#datetj' //指定元素
                });
                form.render();
            },
            yes:function(index, layero){
                //自定义验证规则
                form.verify({
                    setip: function(value, item){ //value：表单的值、item：表单的DOM对象
                        if(''==value){
                            goaction = true;
                            return "设备IP不能为空";
                        }
                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(value))){
                            goaction = true;
                            return '请输入一个正确的IP地址';
                        }
                        goaction = false;
                    }
                });
                //监听提交
                form.on('submit(fromFTP)', function(data){
                    // console.log(data);

                    var fromFTP = JSON.stringify(data.field);
                    if(!isNotEmpty(goaction) && fromFTPStr != fromFTP) {
                        fromFTPStr = fromFTP;
                        goaction = true;

                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(data.field.serverip))){
                            layer.msg("服务器地址IP，不是一个正确的IP",{icon: 5});
                            return false;
                        }
                        if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(data.field.hreadbeatip))){
                            layer.msg("心跳服务器地址，不是一个正确的IP",{icon: 5});
                            return false;
                        }

                        setMiddFtp();
                    }
                    return false;
                });
            },
            btn2:function(index, layero){
                layer.close(index);
            }
        });


    });

    var url = getUrl_manageZk().getMiddFtp;

    ipServer = $("input[name='etip']").val();
    portServer = $("input[name='port']").val();
    userServer = $("input[name='user']").val();
    passwordServer = $("input[name='passwd']").val();

    var data={
        ip:ipServer,
        port:portServer,
        user:userServer,
        password:passwordServer
    };
    ajaxSubmitByJson(url,data,callgetMiddFtp);
}

//删除
function delFlushbonading(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delFlushbonading;
    ssid = ssidd;

    layer.confirm('真的要删除该审讯设备？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelFlushbonading);
    }, function(){
        layer.closeAll();
    });

}

function setMiddFtp() {
    var url = getUrl_manageZk().setMiddleware_FTP;
    var servicename=$("input[name='servicename']").val();
    var enable=$("#enable").prop("checked")==true?1:0;
    var deviceid=$("input[name='deviceid']").val();
    var pasvmode=$("#pasvmode").prop("checked")==true?1:0;
    var serverip=$("input[name='serverip']").val();
    var serverport=$("input[name='serverport']").val();
    var svrusr=$("input[name='svrusr']").val();
    var svrpwd=$("input[name='svrpwd']").val();
    var restart=$("#restart").prop("checked")==true?1:0;
    var hreadbeatip=$("input[name='hreadbeatip']").val();
    var limit_speed=$("input[name='limit_speed']").val();
    var search_filter=$("input[name='search_filter']").val();
    var filter_enable=$("#filter_enable").prop("checked")==true?1:0;

    ipServer = $("input[name='etip']").val();
    portServer = $("input[name='port']").val();
    userServer = $("input[name='user']").val();
    passwordServer = $("input[name='passwd']").val();

    var data = {
        servicename: servicename,
        enable: enable.toString(),
        deviceid: deviceid,
        pasvmode: pasvmode.toString(),
        serverip: serverip,
        serverport: serverport,
        svrusr: svrusr,
        svrpwd: svrpwd,
        restart: restart.toString(),
        hreadbeatip: hreadbeatip,
        limit_speed: limit_speed,
        search_filter: search_filter,
        filter_enable: filter_enable.toString(),
        ip:ipServer,
        port:portServer,
        user:userServer,
        password:passwordServer
    };

    ajaxSubmitByJson(url, data, callSetMiddFtp);
}

function AddOrUpdateFlushbonading(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateFlushbonading;
    var livingurl=$("input[name='livingurl']").val();
    var previewurl=$("input[name='previewurl']").val();
    var port=$("input[name='port']").val();
    var user=$("input[name='user']").val();
    var passwd=$("input[name='passwd']").val();
    var uploadbasepath=$("input[name='uploadbasepath']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();

    var diskrecbool=$("#diskrecbool").prop("checked")==true?1:0;
    var burnbool=$("#burnbool").prop("checked")==true?1:0;
    var defaulturlbool=$("#defaulturlbool").prop("checked")==true?1:0;
    var burntime = $("#burntime").val();
    var ptshowtime = $("#ptshowtime").val();
    var ptjson=$("input[name='ptjson']").val();

    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addFlushbonading;
    }

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            livingurl: livingurl,
            previewurl: previewurl,
            port: port,
            user: user,
            passwd: passwd,
            uploadbasepath: uploadbasepath,
            etypessid: etypessid,
            etnum: etnum,
            etip: etip,
            diskrecbool: diskrecbool,
            burnbool: burnbool,
            defaulturlbool: defaulturlbool,
            burntime: burntime,
            ptshowtime: ptshowtime,
            ptjson: ptjson,
            explain: explain
        }
    };

    // console.log(data);

    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.href = \"/Flushbonading/getFlushbonadingIndex?etypessid=\"+etypessid;",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


function callSetMiddFtp(data){

    if(null!=data&&data.actioncode=='SUCCESS'){
        layer.msg("操作成功",{icon: 6});
        setTimeout("layer.closeAll();",1500);
    }else{
        layer.msg(data.message,{icon: 5});
    }
    goaction = false;
}

function callgetMiddFtp(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != null) {

                var middFTP = data.data;

                $("input[name='servicename']").val(middFTP.servicename);
                $("#enable").prop("checked", middFTP.enable == 1);
                $("input[name='deviceid']").val(middFTP.deviceid);
                $("#pasvmode").prop("checked", middFTP.passvmode == 1);
                $("input[name='serverip']").val(middFTP.serverip);
                $("input[name='serverport']").val(middFTP.serviceport);
                $("input[name='svrusr']").val(middFTP.user);
                $("input[name='svrpwd']").val(middFTP.pass);
                $("input[name='hreadbeatip']").val(middFTP.hreadbeatip);
                $("input[name='limit_speed']").val(middFTP.limit_speed);
                $("input[name='search_filter']").attr("value", middFTP.search_filter);
                $("#filter_enable").prop("checked", middFTP.filter_enable == 1);

                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                });

            }else{
                layer.msg("获取列表失败",{icon: 5});
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callptdjconst(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != null) {
                $("#ptjson").val(data.data);
                $("#getptdjconstBtn").val("更新片头列表");
                layer.msg("获取列表成功",{icon: 6});
            }else{
                layer.msg("获取列表失败",{icon: 5});
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callFlushbonadingList(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            pageshow(data);
            var listcountsize = data.data.pageparam.recordCount;
            if (listcountsize == 0) {
                $("#wushuju").show();
                $("#pagelistview").hide();
            } else {
                $("#wushuju").hide();
                $("#pagelistview").show();
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callFlushbonadingById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var flushbonading = data.data;

            $("input[name='livingurl']").val(flushbonading.livingurl);
            $("input[name='previewurl']").val(flushbonading.previewurl);
            $("input[name='port']").val(flushbonading.port);
            $("input[name='user']").val(flushbonading.user);
            $("input[name='passwd']").val(flushbonading.passwd);
            $("input[name='uploadbasepath']").val(flushbonading.uploadbasepath);
            $("input[name='etnum']").val(flushbonading.etnum);
            $("input[name='etip']").val(flushbonading.etip);
            $("#diskrecbool").prop("checked", flushbonading.diskrecbool == 1);
            $("#burnbool").prop("checked", flushbonading.burnbool == 1);
            $("#defaulturlbool").prop("checked", flushbonading.defaulturlbool == 1);
            $("#burntime").find("option[value='" + flushbonading.burntime + "']").attr("selected", true);
            $("#ptshowtime").find("option[value='" + flushbonading.ptshowtime + "']").attr("selected", true);
            $("input[name='ptjson']").val(flushbonading.ptjson);
            $("#explain").text(flushbonading.explain);

            if (isNotEmpty(flushbonading.ptjson)) {
                $("#getptdjconstBtn").val("更新片头列表");
            }

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callUpdateBurnboolFoDiskrecbool(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
                layer.msg("修改成功",{icon: 6});
            }else{
                layer.msg("修改失败",{icon: 5});
            }
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function calldelFlushbonading(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data == 1) {
                layer.msg("删除成功",{icon: 6});
            }else{
                layer.msg("删除失败",{icon: 5});
            }
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

/**
 * 局部刷新
 */
function getFlushbonadingListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getFlushbonadingList_init(currPage, pageSize);
    }  else if (len == 2) {
        getFlushbonadingIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getFlushbonadingIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var livingurl=$("input[name='livingurl']").val();
        var previewurl=$("input[name='previewurl']").val();
        var user=$("input[name='user']").val();
        var etnum=$("input[name='etnum']").val();
        var arrparam=new Array();
        arrparam[0]=livingurl;
        arrparam[1]=previewurl;
        arrparam[2]=user;
        arrparam[3]=etnum;
        showpage("paging",arrparam,'getFlushbonadingListParam',currPage,pageCount,pageSize);
    }
}

layui.use(['laypage', 'form', 'layer', 'layedit', 'laydate', 'table'], function () {
    var $ = layui.$ //由于layer弹层依赖jQuery，所以可以直接得到
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , laypage = layui.laypage;
    var table = layui.table;

    //监听是否需要硬盘录像开关
    form.on('switch(diskrecbool)', function(data){
        var shieldbool = 0;
        if(this.checked){
            shieldbool = 1;
        }
        UpdateBurnboolFoDiskrecbool(data.value, shieldbool, 1);
    });
    //监听是否需要光盘同刻开关
    form.on('switch(burnbool)', function(data){
        var shieldbool = 0;
        if(this.checked){
            shieldbool = 1;
        }
        UpdateBurnboolFoDiskrecbool(data.value, shieldbool, 0);
    });
    //设为默认设备
    form.on('switch(defaulturlbool)', function(data){
        var shieldbool = 0;
        if(this.checked){
            shieldbool = 1;
        }
        UpdateDefaulturlboolFoDiskrecbool(data.value, shieldbool, 2);
    });
    form.verify({
        setip: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(''==value){
                return "设备IP不能为空";
            }
            if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(value))){
                return '请输入一个正确的IP地址';
            }
        }
    });

    form.on('submit(addOrUpdateFlushbonading_btn)', function (data) {
        // console.log(data);

        var fieldFromStr = JSON.stringify(data.field);

        //判断如果提交的数据不是一样，就提交
        if (fieldFrom != fieldFromStr) {
            fieldFrom = fieldFromStr;
            AddOrUpdateFlushbonading();
        }
        return false;
    });

    form.render();
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
