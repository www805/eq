
function getTtsetinfoList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getTtsetinfoList;
    var language=$("input[name='language']").val();
    var port=$("input[name='port']").val();
    var ttstype=$("input[name='ttstype']").val();
    var etnum=$("input[name='etnum']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            language: language,
            port: port,
            ttstype: ttstype,
            etnum: etnum,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callTtsetinfoList);
}

function TtsetinfoIndex(language, port, ttstype, etnum, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getTtsetinfoList;
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            language: language,
            port: port,
            ttstype: ttstype,
            etnum: etnum,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url, data, callTtsetinfoList);
}

//查询单个
function getTtsetinfoById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getTtsetinfoById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callTtsetinfoById);
}

//删除
function delTtsetinfo(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delTtsetinfo;
    ssid = ssidd;

    layer.confirm('真的要删除该文字转语音服务？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelttsetinfo);
    }, function(){
        layer.closeAll();
    });

}

function AddOrUpdateTtsetinfo(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updateTtsetinfo;

    var language=$("input[name='language']").val();
    var maxnum=$("input[name='maxnum']").val();
    var port=$("input[name='port']").val();
    var ttstype=$("input[name='ttstype']").val();
    var ttskeys=$("input[name='ttskeys']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addTtsetinfo;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            language: language,
            maxnum: maxnum,
            port: port,
            ttstype: ttstype,
            ttskeys: ttskeys,
            etnum: etnum,
            etip: etip,
            etypessid: etypessid,
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
            setTimeout("window.location.href = \"/ttsetinfo/getTtsetinfoIndex\";",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callTtsetinfoList(data){
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

function callTtsetinfoById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var ttsetinfo = data.data;

            $("input[name='language']").val(ttsetinfo.language==null?"":ttsetinfo.language);
            $("input[name='maxnum']").val(ttsetinfo.maxnum==null?"":ttsetinfo.maxnum);
            $("input[name='port']").val(ttsetinfo.port==null?"":ttsetinfo.port);
            $("input[name='ttstype']").val(ttsetinfo.ttstype==null?"":ttsetinfo.ttstype);
            $("input[name='ttskeys']").val(ttsetinfo.ttskeys==null?"":ttsetinfo.ttskeys);
            $("input[name='etnum']").val(ttsetinfo.etnum==null?"":ttsetinfo.etnum);
            $("input[name='etip']").val(ttsetinfo.etip==null?"":ttsetinfo.etip);
            $("#explain").text(ttsetinfo.explain==null?"":ttsetinfo.explain);

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function calldelttsetinfo(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data != 0) {
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
function getTtsetinfoListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getTtsetinfoList_init(currPage, pageSize);
    }  else if (len == 2) {
        TtsetinfoIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        TtsetinfoIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var language=$("input[name='language']").val();
        var port=$("input[name='port']").val();
        var ttstype=$("input[name='ttstype']").val();
        var etnum=$("input[name='etnum']").val();

        var arrparam=new Array();
        arrparam[0]=language;
        arrparam[1]=port;
        arrparam[2]=ttstype;
        arrparam[3]=etnum;
        showpage("paging",arrparam,'getTtsetinfoListParam',currPage,pageCount,pageSize);
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
