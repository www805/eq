var etypessid;
var ssid;
var Polygraph_btnData;

function getPolygraphList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getPolygraphList;
    var port=$("input[name='port']").val();
    var polygraphkey=$("input[name='polygraphkey']").val();
    var etnum=$("input[name='etnum']").val();

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

function polygraphIndex(port, polygraphkey, etnum, currPage, pageSize) {
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

function AddOrUpdatePolygraph(version) {
    // var url=getActionURL(getactionid_manage().templateTypeList_updateTemplateType);

    var url = getUrl_manageZk().updatePolygraph;

    var port=$("input[name='port']").val();
    var polygraphtype=$("input[name='polygraphtype']").val();
    var polygraphkey=$("input[name='polygraphkey']").val();
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addPolygraph;
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
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.href = \"/Polygraph/getPolygraphIndex?etypessid=\"+etypessid;",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
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
        layer.msg(data.message,{icon: 5});
    }
}

function callPolygraphById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var polygraph = data.data;

            $("input[name='port']").val(polygraph.port);
            $("input[name='polygraphtype']").val(polygraph.polygraphtype);
            $("input[name='polygraphkey']").val(polygraph.polygraphkey);
            $("input[name='etnum']").val(polygraph.etnum).attr("disabled",true);
            $("input[name='etip']").val(polygraph.etip).attr("disabled",true);
            $("#explain").text(polygraph.explain);

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function calldelPolygraph(data){
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
function getPolygraphListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getPolygraphList_init(currPage, pageSize);
    }  else if (len == 2) {
        polygraphIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        polygraphIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
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
        var arrparam=new Array();
        arrparam[0]=port;
        arrparam[1]=polygraphkey;
        arrparam[2]=etnum;
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

    form.on('submit(addOrUpdatePolygraph_btn)', function (data) {
        // var Polygraph_btn = JSON.stringify(data.field);
        //
        // if (Polygraph_btnData != Polygraph_btn) {
        //     Polygraph_btnData = Polygraph_btn;
        // }
        AddOrUpdatePolygraph();
        return false;
    });
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
