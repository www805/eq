package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.baseEquipmentinfo.AddOrUpBaseEquipmentinfoParam;
import com.avst.equipmentcontrol.web.req.baseEquipmentinfo.BaseEquipmentinfoParam;
import com.avst.equipmentcontrol.web.vo.baseEquipmentinfo.BaseEquipmentinfoVO;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseEquipmentinfoService extends BaseService {

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    //查询
    public void getEquipmentBasics(RResult result, ReqParam<BaseEquipmentinfoParam> param){
        BaseEquipmentinfoVO baseEquipmentinfoVO = new BaseEquipmentinfoVO();

        //请求参数转换
        BaseEquipmentinfoParam baseEquipmentinfoParam = param.getParam();
        if (null==baseEquipmentinfoParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(baseEquipmentinfoParam.getEtnum())){
            ew.like("etnum",baseEquipmentinfoParam.getEtnum());
        }

        int count = base_equipmentinfoMapper.getEquipmentBasicsCount(ew);
        baseEquipmentinfoParam.setRecordCount(count);

        ew.orderBy("id",false);
        Page<EquipmentBasicsVO> page=new Page<EquipmentBasicsVO>(baseEquipmentinfoParam.getCurrPage(),baseEquipmentinfoParam.getPageSize());

        List<EquipmentBasicsVO> baseEquipmentinfoList = base_equipmentinfoMapper.getEquipmentBasics(page,ew);

        EntityWrapper wrapper = new EntityWrapper();
        List<Base_ettype> ettypeList = base_ettypeMapper.selectList(wrapper);

        baseEquipmentinfoVO.setPagelist(baseEquipmentinfoList);
        baseEquipmentinfoVO.setPageparam(baseEquipmentinfoParam);
        baseEquipmentinfoVO.setEttypeList(ettypeList);

        result.setData(baseEquipmentinfoVO);
        changeResultToSuccess(result);
        return;
    }

    //查询单个
    public void getEquipmentBasicsById(RResult result, ReqParam<BaseEquipmentinfoParam> param){

        //请求参数转换
        BaseEquipmentinfoParam baseEquipmentinfoParam = param.getParam();
        if (null==baseEquipmentinfoParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(baseEquipmentinfoParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("e.ssid", baseEquipmentinfoParam.getSsid());

        EquipmentBasicsVO ettypeVO = base_equipmentinfoMapper.getEquipmentBasicsById(ew);

//        EntityWrapper wrapper = new EntityWrapper();
//        List<Base_ettype> ettypeList = base_ettypeMapper.selectList(wrapper);


        result.setData(ettypeVO);
        changeResultToSuccess(result);
        return;
    }

    //新增
    public void addEquipmentBasics(RResult result, ReqParam<AddOrUpBaseEquipmentinfoParam> param){

        //请求参数转换
        AddOrUpBaseEquipmentinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if(StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setSsid(OpenUtil.getUUID_32());
        base_equipmentinfo.setExplain(paramParam.getExplain());

        Integer insert = base_equipmentinfoMapper.insert(base_equipmentinfo);

        result.setData(insert);
        changeResultToSuccess(result);
        return;
    }



    //修改
    public void updateEquipmentBasics(RResult result, ReqParam<AddOrUpBaseEquipmentinfoParam> param){

        //请求参数转换
        AddOrUpBaseEquipmentinfoParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getEtnum())){
            result.setMessage("设备编号不能为空");
            return;
        }
        if(StringUtils.isBlank(paramParam.getEtip())){
            result.setMessage("设备IP不能为空");
            return;
        }

        Base_equipmentinfo base_equipmentinfo = new Base_equipmentinfo();
        base_equipmentinfo.setEtnum(paramParam.getEtnum());
        base_equipmentinfo.setEtip(paramParam.getEtip());
        base_equipmentinfo.setEtypessid(paramParam.getEtypessid());
        base_equipmentinfo.setExplain(paramParam.getExplain());

        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("ssid", paramParam.getSsid());

        Integer update = base_equipmentinfoMapper.update(base_equipmentinfo, ew);

        result.setData(update);
        changeResultToSuccess(result);
        return;
    }

    //删除
    public void delEquipmentBasics(RResult result, ReqParam<BaseEquipmentinfoParam> param){

        //请求参数转换
        BaseEquipmentinfoParam baseEquipmentinfoParam = param.getParam();
        if (null==baseEquipmentinfoParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(baseEquipmentinfoParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        EntityWrapper<Base_equipmentinfo> ew = new EntityWrapper<>();
        ew.eq("ssid",baseEquipmentinfoParam.getSsid() );

        Integer delete = base_equipmentinfoMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);
        return;
    }

}
