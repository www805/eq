var ssid;
var fromContentData;

function getBaseEttypeList_init(currPage,pageSize) {
    // var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    var url = getUrl_manageZk().getBaseEttype;

    var keyword=$("input[name='keyword']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ettypenum: keyword,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url,data,callBaseEttypeList);
}

function getBaseEttypeList(keyword, currPage, pageSize) {
    // var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    var url = getUrl_manageZk().getBaseEttype;

    var data={
        token:INIT_CLIENTKEY,
        param:{
            ettypenum: keyword,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url, data, callBaseEttypeList);
}

function getBaseEttypeById(ssidd) {
    // var url=getActionURL(getactionid_manage().problemIndex_getTemplateTypeById);
    var url = getUrl_manageZk().getBaseEttypeById;

    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callBaseEttypeById);
}

function AddOrUpdateBaseEttype(version) {
    // var url=getActionURL(getactionid_manage().problemIndex_updateProblemType);
    var url = getUrl_manageZk().updateBaseEttype;

    var ettypenum=$("input[name='ettypenum']").val();
    var explain=$("textarea[name='explain']").val();
    if (isNotEmpty(version)) {
        //添加
        // url=getActionURL(getactionid_manage().problemIndex_addProblemType);
        url = getUrl_manageZk().addBaseEttype;
        // url = "/BaseEttype/addBaseEttype";
    }
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            ettypenum: ettypenum,
            explain: explain
        }
    };
    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function delBaseEttype(ssidd) {
    // var url=getActionURL(getactionid_manage().problemIndex_getTemplateTypeById);
    var url = getUrl_manageZk().delBaseEttype;

    ssid = ssidd;

    layer.confirm('真的要删除该设备类型？', {
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
            if (data.data != 0) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callBaseEttypeList(data){
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

function callBaseEttypeById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            opneModal_1(data.data);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

/**
 * 局部刷新
 */
function getBaseEttypeListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getBaseEttypeList_init(currPage, pageSize);
    }  else if (len == 2) {
        getBaseEttypeList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getBaseEttypeList(arguments[0], arguments[1], arguments[2]);
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
        showpage("paging",arrparam,'getBaseEttypeListParam',currPage,pageCount,pageSize);
    }
}

function opneModal_1(ettype) {

    var ettypenum = "";
    var explain = "";

    if(isNotEmpty(ettype)) {
        ettypenum = ettype.ettypenum==null?"":ettype.ettypenum;
        explain = ettype.explain==null?"":ettype.explain;
    }

    var html = '  <form class="layui-form site-inline" style="margin-top: 20px;padding:0 20px;">\
               <div class="layui-form-item">\
                   <label class="layui-form-label" style="width: 120px;padding-left:0px;"><span style="color: red;">*</span>设备类型标号</label>\
                    <div class="layui-input-block" style="margin-left: 140px;">\
                    <input type="text" name="ettypenum" lay-verify="ettypenum" autocomplete="off" placeholder="请输入设备类型标号" value="' + ettypenum + '" class="layui-input">\
                    </div>\
                </div>\
                <div class="layui-form-item">\
                    <label class="layui-form-label" style="width: 120px;padding-left:0px;"><span style="color: red;">*</span>设备类型设备注释</label>\
                    <div class="layui-input-block" style="margin-left: 140px;">\
                    <textarea name="explain" id="explain" lay-verify="explain" placeholder="请输入设备类型设备注释" class="layui-textarea">'+explain+'</textarea>\
                    </div>\
                </div>\
            </form>';

    layui.use('form', function(){
        var form = layui.form;
        var index = layer.open({
            type: 1,
            title: '设备类型编辑',
            content: html,
            area: ['550px', '320px'],
            btn: ['确定', '取消'],
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromContent').attr('lay-submit', '');//将按钮弄成能提交的
                form.render();
            },
            yes: function (index, layero) {

                //自定义验证规则
                form.verify({
                    ettypenum:[/\S/,'请输入设备类型标号'], explain: [/\S/,'请输入设备类型设备注释']
                });
                //监听提交
                form.on('submit(fromContent)', function(data){

                    console.log(ettype);

                    var fromContent = JSON.stringify(data.field);

                    if (fromContentData != fromContent) {
                        fromContentData = fromContent;

                        if (isNotEmpty(ettype) ) {
                            AddOrUpdateBaseEttype();//修改
                        } else {
                            AddOrUpdateBaseEttype(1);//新增
                        }
                    }
                    return false;
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });
    });
}