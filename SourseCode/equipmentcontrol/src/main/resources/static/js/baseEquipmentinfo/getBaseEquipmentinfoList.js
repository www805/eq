var ssid;
var ettypeList;
var aaaaaaaaaaasss;

function getBaseEquipmentinfoList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    // var url = getUrl_manageZk.getEquipmentBasics;
    var url = "/BaseEquipmentinfo/getEquipmentBasics";
    var etnum=$("input[name='keyword']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            etnum: etnum,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url,data,callBaseEquipmentinfoList);
}

function getBaseEquipmentinfoList(etnum, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    // var url = getUrl_manageZk.getEquipmentBasics;
    var url = "/BaseEquipmentinfo/getEquipmentBasics";
    var data={
        token:INIT_CLIENTKEY,
        param:{
            etnum: etnum,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url, data, callBaseEquipmentinfoList);
}

function getBaseEquipmentinfoById(ssidd) {
    // var url=getActionURL(getactionid_manage().problemIndex_getTemplateTypeById);
    // var url = getUrl_manageZk.getEquipmentBasicsById;
    var url = "/BaseEquipmentinfo/getEquipmentBasicsById";
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callBaseEquipmentinfoById);
}

function AddOrUpdateBaseEquipmentinfo(version) {
    // var url=getActionURL(getactionid_manage().problemIndex_updateProblemType);
    // var url = getUrl_manageZk.updateEquipmentBasics;
    var url = "/BaseEquipmentinfo/updateEquipmentBasics";
    var etnum=$("input[name='etnum']").val();
    var etip=$("input[name='etip']").val();
    var etypessid = $("#typeSelect").val();
    var explain=$("textarea[name='explain']").val();
    if (isNotEmpty(version)) {
        //添加
        // url=getActionURL(getactionid_manage().problemIndex_addProblemType);
        // url = getUrl_manageZk.addEquipmentBasics;
        url = "/BaseEquipmentinfo/addEquipmentBasics";
    }


    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            etnum: etnum,
            etip: etip,
            etypessid: etypessid,
            explain: explain
        }
    };

    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function delEquipmentBasics(ssidd) {
    // var url=getActionURL(getactionid_manage().problemIndex_getTemplateTypeById);
    // var url = getUrl_manageZk.delEquipmentBasics;
    var url = "/BaseEquipmentinfo/delEquipmentBasics";
    ssid = ssidd;

    layer.confirm('真的要删除该设备基础？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var data={
            token:INIT_CLIENTKEY,
            param:{
                ssid: ssid
            }
        };
        ajaxSubmitByJson(url,data,callAddOrUpdate);
    }, function(){
        layer.closeAll();
    });

}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data == 1) {
                layer.msg("操作成功",{icon: 1});
            }else{
                layer.msg("操作失败",{icon: 2});
            }
            setTimeout("window.location.reload()",1500);

        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

function callBaseEquipmentinfoList(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            pageshow(data);
            var listcountsize = data.data.pageparam.recordCount;
            ettypeList = data.data.ettypeList;
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

function callBaseEquipmentinfoById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            opneModal_1(data.data);
        }
    }else{
        layer.msg(data.message,{icon: 2});
    }
}

/**
 * 局部刷新
 */
function getBaseEquipmentinfoListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getBaseEquipmentinfoList_init(currPage, pageSize);
    }  else if (len == 2) {
        getBaseEquipmentinfoList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getBaseEquipmentinfoList(arguments[0], arguments[1], arguments[2]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var keyword=$("input[name='keyword']").val();
        var arrparam=new Array();
        arrparam[0]=keyword;
        showpage("paging",arrparam,'getBaseEquipmentinfoListParam',currPage,pageCount,pageSize);
    }
}


function opneModal_1(equipmentinfo) {

    var etnum = "";
    var etip = "";
    var etypessid = "";
    var typeSelect = "";
    var explain = "";

    if(isNotEmpty(equipmentinfo)) {
        etnum = equipmentinfo.etnum==null?"":equipmentinfo.etnum;
        etip = equipmentinfo.etip==null?"":equipmentinfo.etip;
        etypessid = equipmentinfo.etypessid==null?"":equipmentinfo.etypessid;
        explain = equipmentinfo.explain==null?"":equipmentinfo.explain;
    }

    for (var i = 0; i < ettypeList.length; i++) {
        var ettype = ettypeList[i];

        if(ettype.ssid == etypessid){
            typeSelect += "<option selected value=\"" + ettype.ssid + "\">" + ettype.explain + "</option>";
        }else{
            typeSelect += "<option value=\"" + ettype.ssid + "\">" + ettype.explain + "</option>";
        }
    }

    var html = '  <form class="layui-form site-inline" style="margin-top: 20px">\
               <div class="layui-form-item">\
                   <label class="layui-form-label">设备编号</label>\
                    <div class="layui-input-block" style="margin-left: 120px;">\
                    <input type="text" name="etnum" lay-verify="title" autocomplete="off" placeholder="请输入设备编号" value="' + etnum + '" class="layui-input">\
                    </div>\
                </div>\
                <div class="layui-form-item">\
                   <label class="layui-form-label">设备IP</label>\
                    <div class="layui-input-block" style="margin-left: 120px;">\
                    <input type="text" name="etip" lay-verify="title" autocomplete="off" placeholder="请输入设备IP" value="' + etip + '" class="layui-input">\
                    </div>\
                </div>\
                <div class="layui-form-item">\
                   <label class="layui-form-label">设备类型</label>\
                    <div class="layui-input-block" style="margin-left: 120px;">\
                    <select name="typeSelect" lay-verify="" id="typeSelect" lay-filter="typeSelect">' + typeSelect + '</select>\
                    </div>\
                </div>\
                <div class="layui-form-item">\
                    <label class="layui-form-label" style="width: 90px;">设备中文解释\t</label>\
                    <div class="layui-input-block" style="margin-left: 120px;">\
                    <textarea name="explain" id="explain" placeholder="请输入设备类型中文解释" class="layui-textarea">'+explain+'</textarea>\
                    </div>\
                </div>\
            </form>';



        layui.use(['form','element'], function(){
            var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
            var element = layui.element; //只有执行了这一步，部分表单元素才会自动修饰成功

            //但是，如果你的HTML是动态生成的，自动渲染就会失效
            //因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();

            //……
            form.render();
            element.render();
        });



    var index = layer.open({
        title: '设备基础编辑',
        content: html,
        area: ['550px', '450px'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            layer.close(index);

            if (isNotEmpty(equipmentinfo) ) {
                AddOrUpdateBaseEquipmentinfo();//修改
            } else {
                AddOrUpdateBaseEquipmentinfo(1);//新增
            }

        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

