
function getFlushbonadingEttdList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getFlushbonadingEttdList;
    var port=$("input[name='port']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callFlushbonadingEttdList);
}

function FlushbonadingEttdIndex(port, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getFlushbonadingEttdList;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            port: port,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url, data, callFlushbonadingEttdList);
}

//查询单个
function getFlushbonadingEttdById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getFlushbonadingEttdById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callFlushbonadingEttdById);
}

//删除
function delFlushbonadingEttd(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delFlushbonadingEttd;
    ssid = ssidd;

    layer.confirm('真的要删除该设备通道？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelFlushbonadingEttd);
    }, function(){
        layer.closeAll();
    });

}

function AddOrUpdateFlushbonadingEttd(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateFlushbonadingEttd;

    var tdnum=$("input[name='tdnum']").val();
    var pullflowurl=$("input[name='pullflowurl']").val();
    var tdtype=$("input[name='tdtype']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addFlushbonadingEttd;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            flushbonadingssid:MasterSsid,
            tdnum: tdnum,
            pullflowurl: pullflowurl,
            tdtype: tdtype,
        }
    };

    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 1});
            }else{
                layer.msg("操作失败",{icon: 2});
            }
            setTimeout("window.location.href = \"/FlushbonadingEttd/getFlushbonadingEttdIndex?ssid=\"+MasterSsid;",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callFlushbonadingEttdList(data){
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

function callFlushbonadingEttdById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var flushbonadingEttd = data.data;

            $("input[name='tdnum']").val(flushbonadingEttd.tdnum);
            $("input[name='pullflowurl']").val(flushbonadingEttd.pullflowurl);
            $("input[name='tdtype']").val(flushbonadingEttd.tdtype);

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function calldelFlushbonadingEttd(data){
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
function getFlushbonadingEttdListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getFlushbonadingEttdList_init(currPage, pageSize);
    }  else if (len == 2) {
        FlushbonadingEttdIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        FlushbonadingEttdIndex(arguments[0], arguments[1], arguments[2]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var keyword=$("input[name='port']").val();
        var arrparam=new Array();
        arrparam[0]=keyword;
        showpage("paging",arrparam,'getFlushbonadingEttdListParam',currPage,pageCount,pageSize);
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
