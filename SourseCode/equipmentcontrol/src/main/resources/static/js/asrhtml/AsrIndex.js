var ssid;

function getAsrList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getAsrList;
    var language=$("input[name='language']").val();
    var port=$("input[name='port']").val();
    var asrkey=$("input[name='asrkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etypessid=$("#etypessid").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            language: language,
            port: port,
            asrkey: asrkey,
            etnum: etnum,
            etypessid: etypessid,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callAsrList);
}

function AsrIndex(language, port, asrkey, etnum, etypessid, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getAsrList;
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            language: language,
            port: port,
            asrkey: asrkey,
            etnum: etnum,
            etypessid: etypessid,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url, data, callAsrList);
}

//查询单个
function getAsrById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getAsrById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callAsrById);
}

//删除
function delAsr(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delAsr;
    ssid = ssidd;

    layer.confirm('真的要删除该识别服务器？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelAsr);
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

function AddOrUpdateAsr(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateAsr;

    var language=$("input[name='language']").val();
    var maxnum=$("input[name='maxnum']").val();
    var port=$("input[name='port']").val();
    var asrtype=$("input[name='asrtype']").val();
    var asrkey=$("input[name='asrkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var etypessid=$("#etypessid").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addAsr;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            language: language,
            maxnum: maxnum,
            port: port,
            asrtype: asrtype,
            asrkey: asrkey,
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
            setTimeout("window.location.href = \"/Asr/getAsrIndex\";",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callAsrList(data){
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

function callAsrById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var asr = data.data;

            $("input[name='language']").val(asr.language);
            $("input[name='maxnum']").val(asr.maxnum);
            $("input[name='port']").val(asr.port);
            $("input[name='asrtype']").val(asr.asrtype);
            $("input[name='asrkey']").val(asr.asrkey);
            $("input[name='etypessid']").val(asr.etypessid);
            $("input[name='etnum']").val(asr.etnum);
            $("input[name='etip']").val(asr.etip);
            $("#explain").text(asr.explain);

            var ettypeList = asr.ettypeList;
            var ettypeListHTML = "";
            for (var i = 0; i < ettypeList.length; i++) {
                var ettype = ettypeList[i];
                if(asr.etypessid == ettype.ssid){
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

function calldelAsr(data){
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
function getAsrListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getAsrList_init(currPage, pageSize);
    }  else if (len == 2) {
        AsrIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        AsrIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var language=$("input[name='language']").val();
        var port=$("input[name='port']").val();
        var asrkey=$("input[name='asrkey']").val();
        var etnum=$("input[name='etnum']").val();
        var etypessid=$("#etypessid").val();
        var arrparam=new Array();
        arrparam[0]=language;
        arrparam[1]=port;
        arrparam[2]=asrkey;
        arrparam[3]=etnum;
        arrparam[4]=etypessid;
        showpage("paging",arrparam,'getPolygraphListParam',currPage,pageCount,pageSize);
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
