var ssid;

function getFlushbonadingList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getFlushbonadingList;
    var livingurl=$("input[name='livingurl']").val();
    var user=$("input[name='user']").val();
    var etnum=$("input[name='etnum']").val();
    var etypessid=$("#etypessid").val();
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

function getFlushbonadingIndex(livingurl, user, etnum, etypessid, currPage, pageSize) {
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

//删除
function delFlushbonading(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delFlushbonading;
    ssid = ssidd;

    layer.confirm('真的要删除该审讯主机？', {
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

function AddOrUpdateFlushbonading(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateFlushbonading;
    var livingurl=$("input[name='livingurl']").val();
    var port=$("input[name='port']").val();
    var user=$("input[name='user']").val();
    var passwd=$("input[name='passwd']").val();
    var uploadbasepath=$("input[name='uploadbasepath']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var etypessid=$("#etypessid").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addFlushbonading;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            livingurl: livingurl,
            port: port,
            user: user,
            passwd: passwd,
            uploadbasepath: uploadbasepath,
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
            setTimeout("window.location.href = \"/Flushbonading/getFlushbonadingIndex\";",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
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

function callFlushbonadingById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var flushbonading = data.data;

            $("input[name='livingurl']").val(flushbonading.livingurl);
            $("input[name='port']").val(flushbonading.port);
            $("input[name='user']").val(flushbonading.user);
            $("input[name='passwd']").val(flushbonading.passwd);
            $("input[name='uploadbasepath']").val(flushbonading.uploadbasepath);
            $("input[name='etnum']").val(flushbonading.etnum);
            $("input[name='etip']").val(flushbonading.etip);
            $("#explain").text(flushbonading.explain);

            var ettypeList = flushbonading.ettypeList;
            var ettypeListHTML = $("#etypessid");
            for (var i = 0; i < ettypeList.length; i++) {
                var ettype = ettypeList[i];
                if(flushbonading.etypessid == ettype.ssid){
                    ettypeListHTML.append("<option selected value='" + ettype.ssid + "'>" + ettype.explain + "</option>")
                }else{
                    ettypeListHTML.append("<option value='" + ettype.ssid + "'>" + ettype.explain + "</option>")
                }
            }
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function calldelFlushbonading(data){
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
        var user=$("input[name='user']").val();
        var etnum=$("input[name='etnum']").val();
        var etypessid=$("#etypessid").val();
        var arrparam=new Array();
        arrparam[0]=livingurl;
        arrparam[1]=user;
        arrparam[2]=etnum;
        arrparam[3]=etypessid;
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

    form.render();
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
