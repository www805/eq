var etypessid;
var ssid;
var Asr_btnData;

function getAsrList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().templateTypeList_getTemplateTypes);
    var url = getUrl_manageZk().getAsrList;
    var language=$("input[name='language']").val();
    var port=$("input[name='port']").val();
    var asrkey=$("input[name='asrkey']").val();
    var etnum=$("input[name='etnum']").val();
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

function AsrIndex(language, port, asrkey, etnum, currPage, pageSize) {
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
    var explain=$("textarea[name='explain']").val();
    if (!isNotEmpty(ssid)) {
        //添加
        // url=getActionURL(getactionid_manage().templateTypeList_addTemplateType);
        url=getUrl_manageZk().addAsr;
    }

    if (!isNumber(port)) {
        layer.msg("端口号必须由数字组成",{icon: 5});
        return;
    }
    if (!isNumber(maxnum)) {
        layer.msg("并发数必须由数字组成",{icon: 5});
        return;
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
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.href = \"/Asr/getAsrIndex?etypessid=\"+etypessid;",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
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
        layer.msg(data.message,{icon: 5});
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
            $("input[name='etnum']").val(asr.etnum);
            $("input[name='etip']").val(asr.etip);
            $("#explain").text(asr.explain);

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function calldelAsr(data){
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
function getAsrListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getAsrList_init(currPage, pageSize);
    }  else if (len == 2) {
        AsrIndex('', arguments[0], arguments[1]);
    } else if (len > 2) {
        AsrIndex(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
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
        var arrparam=new Array();
        arrparam[0]=language;
        arrparam[1]=port;
        arrparam[2]=asrkey;
        arrparam[3]=etnum;
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
            if(!(/([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}/.test(value))){
                return '请输入一个正确的IP地址';
            }
        }
    });

    form.on('submit(addOrUpdateAsr_btn)', function (data) {
        var Asr_btn = JSON.stringify(data.field);

        if (Asr_btnData != Asr_btn) {
            Asr_btnData = Asr_btn;
            AddOrUpdateAsr();
        }
        return false;
    });


});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
