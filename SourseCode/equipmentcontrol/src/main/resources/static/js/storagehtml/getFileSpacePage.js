var etypessid;
var ssid;

//查询单个
function getFileSpaceByssid(ssidd) {
    var url = getUrl_manageZk().getFileSpaceByssid;
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callGetFileSpaceByssid);
}

function callGetFileSpaceByssid(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var storage = data.data;
            console.log(storage);


            var filePathSpace = storage.filePathSpace;
            var filePathSpaceByParentNodePath = storage.filePathSpaceByParentNodePath;


            var sysVersion = "";
            if(isNotEmpty(filePathSpace.driverPath)){
                var driverPath = filePathSpace.driverPath;
                var drinum = driverPath.indexOf(":");
                if (drinum < 0) {
                    drinum = driverPath.indexOf("：");
                }
                sysVersion = driverPath.substring(0,drinum);
            }

            $("#sysVersion").html(sysVersion + "盘");
            $("#companyname").html(filePathSpace.totalSpace_str);
            $("#sysStartTime").html(filePathSpace.freeSpace_str);
            $("#workTime").html(filePathSpace.driverPath);
            $("#basenum").html(filePathSpace.useSpace_str);

            var divallHTML = "";
            if(isNotEmpty(filePathSpaceByParentNodePath)){
                for (var i = 0; i < filePathSpaceByParentNodePath.length; i++) {
                    var driver = filePathSpaceByParentNodePath[i];
                    var pngClass = "";
                    $(".menu-one").show();
                    if(!driver.folderBool){
                        pngClass = "img_file";
                        $(".menu-one").hide();
                    }

                    divallHTML += '<li class="' + pngClass + '" value="' + driver.driverPath + '"><span class="layui-badge layui-bg-cyan">' + driver.useSpace_str + '</span><input type="text" class="changename" value="' + driver.driverName + '" disabled/></li>';


                }
            }



            $("#divall").html(divallHTML);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
