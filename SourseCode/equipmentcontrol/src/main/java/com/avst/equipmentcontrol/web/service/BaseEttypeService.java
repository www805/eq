package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.web.req.baseEttype.AddOrUpEttypeParam;
import com.avst.equipmentcontrol.web.req.baseEttype.BaseEttypeParam;
import com.avst.equipmentcontrol.web.vo.baseEttype.BaseEttypeVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseEttypeService extends BaseService {

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    //查询
    public void getBaseEttype(RResult result, ReqParam<BaseEttypeParam> param){
        BaseEttypeVO baseEttypeVO = new BaseEttypeVO();

        //请求参数转换
        BaseEttypeParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        EntityWrapper ew=new EntityWrapper();
        if (StringUtils.isNotBlank(paramParam.getEttypenum())){
            ew.like("ettypenum",paramParam.getEttypenum());
        }

        int count = base_ettypeMapper.selectCount(ew);
        paramParam.setRecordCount(count);

        ew.orderBy("id",false);
        Page<Base_ettype> page=new Page<Base_ettype>(paramParam.getCurrPage(),paramParam.getPageSize());

        List<Base_ettype> baseEquipmentinfoList = base_ettypeMapper.selectPage(page,ew);


        baseEttypeVO.setPagelist(baseEquipmentinfoList);
        baseEttypeVO.setPageparam(paramParam);

        result.setData(baseEttypeVO);
        changeResultToSuccess(result);
        return;
    }

    //查询单个
    public void getBaseEttypeById(RResult result, ReqParam<BaseEttypeParam> param){

        //请求参数转换
        BaseEttypeParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("查询的参数不能为空");
            return;
        }

        Base_ettype base_ettype = new Base_ettype();
        base_ettype.setSsid(paramParam.getSsid());

        Base_ettype ettype = base_ettypeMapper.selectOne(base_ettype);

        result.setData(ettype);
        changeResultToSuccess(result);
        return;
    }

    //新增
    public void addBaseEttype(RResult result, ReqParam<AddOrUpEttypeParam> param){

        //请求参数转换
        AddOrUpEttypeParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getEttypenum())){
            result.setMessage("设备类型标号不能为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getExplain())){
            result.setMessage("设备类型中文解释不能为空");
            return;
        }

        Base_ettype base_ettype = new Base_ettype();
        base_ettype.setEttypenum(paramParam.getEttypenum());
        base_ettype.setSsid(OpenUtil.getUUID_32());
        base_ettype.setExplain(paramParam.getExplain());

        Integer insert = base_ettypeMapper.insert(base_ettype);

        result.setData(insert);
        changeResultToSuccess(result);
        return;
    }



    //修改
    public void updateBaseEttype(RResult result, ReqParam<AddOrUpEttypeParam> param){

        //请求参数转换
        AddOrUpEttypeParam paramParam = param.getParam();
        if (null==paramParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(paramParam.getSsid())){
            result.setMessage("ssid不能为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getEttypenum())){
            result.setMessage("设备类型标号不能为空");
            return;
        }

        if(StringUtils.isBlank(paramParam.getExplain())){
            result.setMessage("设备类型中文解释不能为空");
            return;
        }

        Base_ettype base_equipmentinfo = new Base_ettype();
        base_equipmentinfo.setEttypenum(paramParam.getEttypenum());
        base_equipmentinfo.setExplain(paramParam.getExplain());

        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("ssid", paramParam.getSsid());

        Integer update = base_ettypeMapper.update(base_equipmentinfo, ew);
        result.setData(update);

        if (update == 1) {
            changeResultToSuccess(result);
        }else{
            result.setMessage("ssid不存在或请求失败!");
        }
        return;
    }

    //删除
    public void delBaseEttype(RResult result, ReqParam<BaseEttypeParam> param){

        //请求参数转换
        BaseEttypeParam baseEttypeParam = param.getParam();
        if (null==baseEttypeParam){
            result.setMessage("参数为空");
            return;
        }

        if (StringUtils.isBlank(baseEttypeParam.getSsid())){
            result.setMessage("ssid不能为空");
            return;
        }

        EntityWrapper<Base_ettype> ew = new EntityWrapper<>();
        ew.eq("ssid",baseEttypeParam.getSsid() );

        Integer delete = base_ettypeMapper.delete(ew);

        result.setData(delete);
        changeResultToSuccess(result);
        return;
    }


}
