var ssid;

function getPolygraphList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getPolygraphList;
    var port=$("input[name='port']").val();
    var polygraphkey=$("input[name='polygraphkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etypessid=$("#etypessid").val();

    var data={
        token:INIT_CLIENTKEY,
        param:{
            port: port,
            polygraphkey: polygraphkey,
            etnum: etnum,
            etypessid: etypessid,
            currPage:currPage,
            pageSize:pageSize
        }
    };

    ajaxSubmitByJson(url,data,callPolygraphList);
}

function polygraphIndex(port, polygraphkey, etnum, etypessid, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getPolygraphList;
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            port: port,
            polygraphkey: polygraphkey,
            etnum: etnum,
            etypessid: etypessid,
            currPage: currPage,
            pageSize: pageSize
        }
    };
    ajaxSubmitByJson(url, data, callPolygraphList);
}

//查询单个
function getPolygraphById(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().getPolygraphById;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callPolygraphById);
}

//删除
function delPolygraph(ssidd) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypeById);
    var url = getUrl_manageZk().delPolygraph;
    ssid = ssidd;

    layer.confirm('真的要删除该测谎仪？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,calldelPolygraph);
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

function AddOrUpdatePolygraph(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updatePolygraph;

    var port=$("input[name='port']").val();
    var polygraphtype=$("input[name='polygraphtype']").val();
    var polygraphkey=$("input[name='polygraphkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var etypessid=$("#etypessid").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addPolygraph;
    }

    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            port: port,
            polygraphtype: polygraphtype,
            polygraphkey: polygraphkey,
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
            setTimeout("window.location.href = \"/Polygraph/getPolygraphIndex\";",1500);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callPolygraphList(data){
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

function callPolygraphById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var polygraph = data.data;

            $("input[name='port']").val(polygraph.port);
            $("input[name='polygraphtype']").val(polygraph.polygraphtype);
            $("input[name='polygraphkey']").val(polygraph.polygraphkey);
            $("input[name='etnum']").val(polygraph.etnum);
            $("input[name='etip']").val(polygraph.etip);
            $("#explain").text(polygraph.explain);

            var ettypeList = polygraph.ettypeList;
            var ettypeListHTML = "";
            for (var i = 0; i < ettypeList.length; i++) {
                var ettype = ettypeList[i];
                if(polygraph.etypessid == ettype.ssid){
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

function calldelPolygraph(data){
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
function getPolygraphListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getPolygraphList_init(currPage, pageSize);
    }  else if (len == 2) {
        polygraphIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        polygraphIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var port=$("input[name='port']").val();
        var polygraphkey=$("input[name='polygraphkey']").val();
        var etnum=$("input[name='etnum']").val();
        var etypessid=$("#etypessid").val();
        var arrparam=new Array();
        arrparam[0]=port;
        arrparam[0]=polygraphkey;
        arrparam[0]=etnum;
        arrparam[0]=etypessid;
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
