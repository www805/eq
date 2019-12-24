var etypessid;
var ssid;
var Storage_btnData;

function getStorageList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getStorageList;
    var port=$("input[name='port']").val();
    var totalcapacity=$("input[name='totalcapacity']").val();
    var etnum=$("input[name='etnum']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            port: port,
            totalcapacity: totalcapacity,
            etnum: etnum,
            etypessid: etypessid,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callStorageList);
}

function StorageIndex(port, totalcapacity, etnum, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getStorageList;
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            port: port,
            totalcapacity: totalcapacity,
            etnum: etnum,
            etypessid: etypessid,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url, data, callStorageList);
}

//查询单个
function getStorageById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getStorageById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callStorageById);
}

//删除
function delStorage(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delStorage;
    ssid = ssidd;

    layer.confirm('真的要删除该存储服务？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelStorage);
    }, function(){
        layer.closeAll();
    });

}

function AddOrUpdateStorage(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateStorage;

    var port=$("input[name='port']").val();
    var sstype=$("input[name='sstype']").val();
    var datasavebasepath=$("input[name='datasavebasepath']").val();
    var ssstatic=$("input[name='ssstatic']").val();
    var xytype=$("input[name='xytype']").val();
    var ssstate=$("#ssstate").val();
    var defaultsavebool=$("#defaultsavebool").val();
    var user=$("input[name='user']").val();
    var passwd=$("input[name='passwd']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addStorage;
    }

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            port: port,
            sstype: sstype,
            datasavebasepath: datasavebasepath,
            etypessid: etypessid,
            ssstatic: ssstatic,
            xytype: xytype,
            ssstate: parseInt(ssstate),
            defaultsavebool: parseInt(defaultsavebool),
            user: user,
            passwd: passwd,
            etnum: etnum,
            etip: etip,
            explain: explain
        }
    };

    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function updateDefaultsavebool(ssid, defaultsavebool) {

    var url=getUrl_manageZk().updateDefaultsavebool;
    var data = {
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid,
            defaultsavebool: defaultsavebool
        }
    };

    ajaxSubmitByJson(url,data,callAddOrUpdate);
}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.href = \"/Storage/getStorageIndex?etypessid=\"+etypessid;",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callStorageList(data){
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

function callStorageById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var storage = data.data;

            $("input[name='port']").val(storage.port);
            $("input[name='sstype']").val(storage.sstype);
            $("input[name='datasavebasepath']").val(storage.datasavebasepath);
            $("input[name='etypessid']").val(storage.etypessid);
            $("input[name='etnum']").val(storage.etnum);
            $("input[name='etip']").val(storage.etip);
            $("input[name='user']").val(storage.user);
            $("input[name='passwd']").val(storage.passwd);
            $("input[name='ssstatic']").val(storage.ssstatic);
            $("input[name='xytype']").val(storage.xytype);

            $("#defaultsavebool").find("option[value='" + storage.defaultsavebool + "']").attr("selected", true);
            $("#ssstate").find("option[value='" + storage.ssstate + "']").attr("selected", true);

            $("#explain").text(storage.explain);

            base_etip = storage.etip;
            base_etnum = storage.etnum;
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function calldelStorage(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
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
function getStorageListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getStorageList_init(currPage, pageSize);
    }  else if (len == 2) {
        StorageIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        StorageIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var port=$("input[name='port']").val();
        var totalcapacity=$("input[name='totalcapacity']").val();
        var etnum=$("input[name='etnum']").val();
        var arrparam=new Array();
        arrparam[0]=port;
        arrparam[1]=totalcapacity;
        arrparam[2]=etnum;
        showpage("paging",arrparam,'getStorageListParam',currPage,pageCount,pageSize);
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

    form.verify({
        setip: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(''==value){
                return "设备IP不能为空";
            }
            if(!(/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value))){
                return '请输入一个正确的IP地址';
            }
        },
        portnum: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(''==value){
                return "端口不能为空";
            }
            if(value.length > 10){
                return '端口的长度不能超过10个字符';
            }
        },
        zhongwen: function(value, item){ //value：表单的值、item：表单的DOM对象
            if((/[\u4E00-\u9FA5]/g.test(value))){
                return '不能输入中文';
            }
        }
    });

    //设为默认设备
    form.on('switch(switchDefaultsavebool)', function(data){
        var shieldbool = 0;
        if(this.checked){
            shieldbool = 1;
        }
        updateDefaultsavebool(data.value, shieldbool);
    });

    form.on('submit(addOrUpdateStorage_btn)', function (data) {

        // var Storage_btn = JSON.stringify(data.field);
        //
        // if (Storage_btnData != Storage_btn) {
        //     Storage_btnData = Storage_btn;
        // }
        AddOrUpdateStorage();
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
