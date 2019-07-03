var ipath;


function setPath() {
    var hostport=document.location.host;
	ipath = 'http://'+hostport;
//    ipath = 'https://'+hostport;//HTTPS加密协议
};
function getPath() {
	
	if (ipath == "" || ipath == null || ipath == undefined || ipath == 'null' || ipath == 'Null' || ipath == 'NULL') {
		setPath();
	}
	
	return ipath;
}


function getUrl_manage() {

	var basepath="/ec/";

	return {
		//后台请求
		gotologin:getPath()+basepath+"main/gotologin",

		logining:getPath()+basepath+"main/logining",

		main:getPath()+basepath+"main/gotomain",
		logout:getPath()+basepath+"main/logout"
	};
}

function getUrl_manageZk() {

	return {
		//后台请求
		getFlushbonadingList:getPath()+"/Flushbonading/getFlushbonadingList",
		getFlushbonadingById:getPath()+"/Flushbonading/getFlushbonadingById",
		addFlushbonading:getPath()+"/Flushbonading/addFlushbonading",
		updateFlushbonading:getPath()+"/Flushbonading/updateFlushbonading",
		delFlushbonading:getPath()+"/Flushbonading/delFlushbonading",
		getFlushbonadingBaseEttype:getPath()+"/Flushbonading/getBaseEttype",

		getPolygraphList:getPath()+"/Polygraph/getPolygraphList",
		getPolygraphById:getPath()+"/Polygraph/getPolygraphById",
		addPolygraph:getPath()+"/Polygraph/addPolygraph",
		updatePolygraph:getPath()+"/Polygraph/updatePolygraph",
		delPolygraph:getPath()+"/Polygraph/delPolygraph",

		getAsrList:getPath()+"/Asr/getAsrList",
		getAsrById:getPath()+"/Asr/getAsrById",
		addAsr:getPath()+"/Asr/addAsr",
		updateAsr:getPath()+"/Asr/updateAsr",
		delAsr:getPath()+"/Asr/delAsr",

		getStorageList:getPath()+"/Storage/getStorageList",
		getStorageById:getPath()+"/Storage/getStorageById",
		addStorage:getPath()+"/Storage/addStorage",
		updateStorage:getPath()+"/Storage/updateStorage",
		delStorage:getPath()+"/Storage/delStorage",

		getFlushbonadingEttdList:getPath()+"/FlushbonadingEttd/getFlushbonadingEttdList",
		getFlushbonadingEttdById:getPath()+"/FlushbonadingEttd/getFlushbonadingEttdById",
		updateFlushbonadingEttd:getPath()+"/FlushbonadingEttd/updateFlushbonadingEttd",
		addFlushbonadingEttd:getPath()+"/FlushbonadingEttd/addFlushbonadingEttd",
		delFlushbonadingEttd:getPath()+"/FlushbonadingEttd/delFlushbonadingEttd",

		getEquipmentBasics:getPath()+"/BaseEquipmentinfo/getEquipmentBasics",
		getEquipmentBasicsById:getPath()+"/BaseEquipmentinfo/getEquipmentBasicsById",
		addEquipmentBasics:getPath()+"/BaseEquipmentinfo/addEquipmentBasics",
		updateEquipmentBasics:getPath()+"/BaseEquipmentinfo/updateEquipmentBasics",
		delEquipmentBasics:getPath()+"/BaseEquipmentinfo/delEquipmentBasics",

		getBaseEttype:getPath()+"/BaseEttype/getBaseEttype",
		getBaseEttypeById:getPath()+"/BaseEttype/getBaseEttypeById",
		addBaseEttype:getPath()+"/BaseEttype/addBaseEttype",
		updateBaseEttype:getPath()+"/BaseEttype/updateBaseEttype",
		delBaseEttype:getPath()+"/BaseEttype/delBaseEttype"


	};
}

