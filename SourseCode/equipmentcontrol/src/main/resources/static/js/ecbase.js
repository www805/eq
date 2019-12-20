
//可以选择重复的设备
function getEtnumList() {
    var url=getUrl_manage().getBaseEc;
    var etnum=$("#etnum").val();
    // if (!isNotEmpty(etnum)) {
    //     $("#etnum_text").html("");
    //     $("#etnum_text").append('<p class="layui-select-none">无匹配项</p>');
    //     $("#etnum_text").css("display","block");
    //     return;
    // }
    var data={
        etnum:etnum
    };
    ajaxSubmitByJson(url,data,callgetEtnumList);
}

function select_etnum(obj) {
    $("#etnum_text").css("display","none");
    var etnum=$(obj).attr("lay-value");
    var etip=$(obj).attr("lay-ip");
    $("#etnum").val(etnum);
    $("#etip").val(etip);
    $(obj).focus();
    $("#etnum_text").html("");
}

function select_Etnumblur() {
    $("#etnum_text").css("display","none");
}


function callgetEtnumList(data) {
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var data=data.data;

            $("#etnum_text").html("");
            if (isNotEmpty(data)){
                for (var i = 0; i < data.length; i++) {
                    var userinfo = data[i];
                    // console.log(userinfo);
                    $("#etnum_text").append("<dd lay-value='" + userinfo.etnum + "' lay-ip='" + userinfo.etip + "' onmousedown='select_etnum(this);'>" + userinfo.etnum + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + userinfo.etip + "</dd>");
                }
            }else{
                $("#etnum_text").append('<p class="layui-select-none">无匹配项</p>');
            }
            $("#etnum_text").css("display","block");
        }
    }else{
        parent.layer.msg(data.message,{icon: 5});
    }
    layui.use('form', function(){
        var form =  layui.form;

        form.render();
    });
}