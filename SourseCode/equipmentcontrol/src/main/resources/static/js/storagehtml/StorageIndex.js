var ssid;

function getStorageList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getStorageList;
    var port=$("input[name='port']").val();
    var totalcapacity=$("input[name='totalcapacity']").val();
    var etnum=$("input[name='etnum']").val();
    var etypessid=$("#etypessid").val();
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

function StorageIndex(port, totalcapacity, etnum, etypessid, currPage, pageSize) {
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

    layer.confirm('真的要删除该存储设备？', {
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

//查询设备类型
function getBaseEttype() {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getFlushbonadingBaseEttype;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callBaseEttype);
}

function AddOrUpdateStorage(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateStorage;

    var totalcapacity=$("input[name='totalcapacity']").val();
    var port=$("input[name='port']").val();
    var sstype=$("input[name='sstype']").val();
    var datasavebasepath=$("input[name='datasavebasepath']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var etypessid=$("#etypessid").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addStorage;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            totalcapacity: totalcapacity,
            port: port,
            sstype: sstype,
            datasavebasepath: datasavebasepath,
            etypessid: etypessid,
            etnum: etnum,
            etip: etip,
            explain: explain
        }
    };

    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data == 1) {
                layer.msg("操作成功",{icon: 1});
            }else{
                layer.msg("操作失败",{icon: 2});
            }
            setTimeout("window.location.href = \"/Storage/getStorageIndex\";",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
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
        layer.msg(data.message,{icon: 2});
    }
}

function callBaseEttype(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var ettypeList = data.data.ettypeList;
            var ettypeListHTML = $("#etypessid");
            for (var i = 0; i < ettypeList.length; i++) {
                var ettype = ettypeList[i];
                ettypeListHTML.append("<option value='" + ettype.ssid + "'>" + ettype.explain + "</option>");
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callStorageById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var storage = data.data;

            $("input[name='totalcapacity']").val(storage.totalcapacity);
            $("input[name='port']").val(storage.port);
            $("input[name='sstype']").val(storage.sstype);
            $("input[name='datasavebasepath']").val(storage.datasavebasepath);
            $("input[name='etypessid']").val(storage.etypessid);
            $("input[name='etnum']").val(storage.etnum);
            $("input[name='etip']").val(storage.etip);
            $("#explain").text(storage.explain);

            var ettypeList = storage.ettypeList;
            var ettypeListHTML = "";
            for (var i = 0; i < ettypeList.length; i++) {
                var ettype = ettypeList[i];
                if(storage.etypessid == ettype.ssid){
                    ettypeListHTML += "<option selected value='" + ettype.ssid + "'>" + ettype.explain + "</option>";
                }else{
                    ettypeListHTML += "<option value='" + ettype.ssid + "'>" + ettype.explain + "</option>";
                }
            }
            $("#etypessid").html(ettypeListHTML);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function calldelStorage(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data == 1) {
                layer.msg("删除成功",{icon: 1});
            }else{
                layer.msg("删除失败",{icon: 2});
            }
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
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
        StorageIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
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
        var etypessid=$("#etypessid").val();
        var arrparam=new Array();
        arrparam[0]=port;
        arrparam[1]=totalcapacity;
        arrparam[2]=etnum;
        arrparam[3]=etypessid;
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

    form.render();
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}