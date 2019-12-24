var base_etip;
var base_etnum;

//可以选择重复的设备
function getEtipList() {
    var url=getUrl_manage().getBaseEc;
    var etip=$("#etip").val();
    // if (!isNotEmpty(etnum)) {
    //     $("#etnum_text").html("");
    //     $("#etnum_text").append('<p class="layui-select-none">无匹配项</p>');
    //     $("#etnum_text").css("display","block");
    //     return;
    // }
    var data={
        etip:etip
    };
    ajaxSubmitByJson(url,data,callgetEtipList);
}

function select_etnum(obj) {
    $("#etip_text").css("display","none");
    var etnum=$(obj).attr("lay-value");
    var etip=$(obj).attr("lay-ip");
    $("#etnum").val(etnum);
    $("#etip").val(etip);
    $(obj).focus();
    $("#etip_text").html("");
}

function select_Etipblur() {
    $("#etip_text").css("display","none");
}


function callgetEtipList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;

            if(isNotEmpty(base_etip) && isNotEmpty(base_etnum)){
                var baseinfo = {
                    etnum : base_etnum,
                    etip : base_etip
                };
                data.push(baseinfo);
            }

            $("#etip_text").html("");
            if (isNotEmpty(data)){
                for (var i = 0; i < data.length; i++) {
                    var userinfo = data[i];
                    // console.log(userinfo);
                    $("#etip_text").append("<dd lay-value='" + userinfo.etnum + "' lay-ip='" + userinfo.etip + "' onmousedown='select_etnum(this);'>" + userinfo.etip + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + userinfo.etnum + "</dd>");
                }
            }else{
                $("#etip_text").append('<p class="layui-select-none">无匹配项</p>');
            }
            $("#etip_text").css("display","block");
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
    layui.use('form', function(){
        var form =  layui.form;

        form.render();
    });
}