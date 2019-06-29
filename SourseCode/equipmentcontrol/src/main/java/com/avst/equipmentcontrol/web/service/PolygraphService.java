package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.Polygraph_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.polygraph.PolygraphParam;
import com.avst.equipmentcontrol.web.req.polygraph.UpdatePolygraphParam;
import com.avst.equipmentcontrol.web.vo.polygraph.PolygraphVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolygraphService extends BaseService {

    @Autowired
    private Polygraph_etinfoMapper polygraph_etinfoMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    //查询
    public void getPolygraphList(RResult result, ReqParam<PolygraphParam> param){

        PolygraphVO polygraphVO = new PolygraphVO();

        //请求参数转换
        PolygraphParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(paramParam.getPort())){
            ew.like("pet.port",paramParam.getPort());
        }

        int count = polygraph_etinfoMapper.getPolygraphInfoCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("pet.id",false);
        Page<PolygraphInfo> page=new Page<PolygraphInfo>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<PolygraphInfo> polygraphInfoPage = polygraph_etinfoMapper.getPolygraphInfoPage(page,ew);

        if (null != polygraphInfoPage && polygraphInfoPage.size() > 0) {

            for (PolygraphInfo polygraphInfo : polygraphInfoPage) {
                Base_ettype base_ettype = new Base_ettype();
                base_ettype.setSsid(polygraphInfo.getEtypessid());
                Base_ettype ettype = base_ettypeMapper.selectOne(base_ettype);
                polygraphInfo.setEtypessid(ettype.getExplain());
            }
        }

        polygraphVO.setPagelist(polygraphInfoPage);
        polygraphVO.setPageparam(paramParam);


        result.setData(polygraphVO);
        changeResultToSuccess(result);
    }


    //查询单次
    public void getPolygraphById(RResult result, ReqParam<PolygraphParam> param){

        //请求参数转换
        PolygraphParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        ew.eq("pet.ssid",paramParam.getSsid());

        PolygraphInfo polygraphInfo = polygraph_etinfoMapper.getPolygraphInfo(ew);

        EntityWrapper wrapper = new EntityWrapper();
        List<Base_ettype> ettypeList = base_ettypeMapper.selectList(wrapper);
        polygraphInfo.setEttypeList(ettypeList);

        result.setData(polygraphInfo);
        changeResultToSuccess(result);
    }


    //新增
    public void addPolygraph(RResult result, ReqParam<UpdatePolygraphParam> param){

        //请求参数转换
        UpdatePolygraphParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPolygraphtype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPolygraphkey())){
            result.setMessage("验证密匙不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }


        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());

        base_equipmentinfoMapper.insert(base_equipmentinfo);

        Polygraph_etinfo polygraph_etinfo = new Polygraph_etinfo();
        polygraph_etinfo.setPort(paramParam.getPort());
        polygraph_etinfo.setPolygraphtype(paramParam.getPolygraphtype());
        polygraph_etinfo.setPolygraphkey(paramParam.getPolygraphkey());
        polygraph_etinfo.setExplain(paramParam.getExplain());
        polygraph_etinfo.setEquipmentssid(base_equipmentinfo.getSsid());
        polygraph_etinfo.setSsid(OpenUtil.getUUID_32());

        Integer insert = polygraph_etinfoMapper.insert(polygraph_etinfo);
        System.out.println("add_boot：" + insert);

        result.setData(insert);
        changeResultToSuccess(result);
    }

    //修改
    public void updatePolygraph(RResult result, ReqParam<UpdatePolygraphParam> param){

        //请求参数转换
        UpdatePolygraphParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }
        if (null == paramParam.getPort()) {
            result.setMessage("开放接口的端口不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPolygraphtype())){
            result.setMessage("服务类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getPolygraphkey())){
            result.setMessage("验证密匙不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtypessid())){
            result.setMessage("设备类型不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if (StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        PolygraphInfo flushbonadinginfo = new PolygraphInfo();
        flushbonadinginfo.setSsid(paramParam.getSsid());
        Polygraph_etinfo polygraph_etinfo = polygraph_etinfoMapper.selectOne(flushbonadinginfo);


        //删除设备再新增，不然就是修改那个设备
        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", polygraph_etinfo.getEquipmentssid());

        Base_equipmentinfo equipmentinfo = new Base_equipmentinfo();
        equipmentinfo.setEtnum(paramParam.getEtnum());
        equipmentinfo.setEtip(paramParam.getEtip());
        equipmentinfo.setEtypessid(paramParam.getEtypessid());

        base_equipmentinfoMapper.update(equipmentinfo, ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());

        Polygraph_etinfo polygraphEtinfo = new Polygraph_etinfo();
        polygraphEtinfo.setPort(paramParam.getPort());
        polygraphEtinfo.setPolygraphtype(paramParam.getPolygraphtype());
        polygraphEtinfo.setPolygraphkey(paramParam.getPolygraphkey());
        polygraphEtinfo.setExplain(paramParam.getExplain());
        polygraphEtinfo.setEquipmentssid(equipmentinfo.getSsid());

        Integer update = polygraph_etinfoMapper.update(polygraphEtinfo, ew);
        System.out.println("update_boot：" + update);

        result.setData(update);
        changeResultToSuccess(result);
    }

    //删除
    public void delPolygraph(RResult result, ReqParam<PolygraphParam> param){

        //请求参数转换
        PolygraphParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("删除的ssid不能为空");
            return;
        }

        //查询审讯主机从里面拿到他的设备ssid
        PolygraphInfo flushbonadinginfo = new PolygraphInfo();
        flushbonadinginfo.setSsid(paramParam.getSsid());
        Polygraph_etinfo polygraph_etinfo = polygraph_etinfoMapper.selectOne(flushbonadinginfo);

        EntityWrapper ew2 = new EntityWrapper();
        ew2.eq("ssid", polygraph_etinfo.getEquipmentssid());
        base_equipmentinfoMapper.delete(ew2);

        EntityWrapper ew = new EntityWrapper();
        ew.eq("ssid", paramParam.getSsid());
        Integer delete = polygraph_etinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);

    }
}
