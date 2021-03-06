package com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_et_ettype;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.entity.Asr_etinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * InnoDB free: 38912 kB Mapper 接口
 * </p>
 *
 * @author Mht
 * @since 2019-05-14
 */
@Component
public interface Asr_etinfoMapper extends BaseMapper<Asr_etinfo> {

    //查询单个
    @Select("select aet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from asr_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public Asr_et_ettype getAsrinfo(@Param("ew") EntityWrapper ew);


    //查询总条数
    @Select("select count(aet.id) from asr_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public int getAsrInfoCount(@Param("ew") EntityWrapper ew);


    //查询列表+带分页
    @Select("select aet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from asr_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Asr_et_ettype> getAsrInfoPage(Page page, @Param("ew") EntityWrapper ew);


    //语音服务器是否重复
    @Select("SELECT " +
            " count(a.id) " +
            " FROM " +
            " asr_etinfo a " +
            " INNER JOIN base_equipmentinfo b ON a.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    int getRepetition(@Param("ew")EntityWrapper entityWrapper);


    //获取语音服务器关联的设备基表
    @Select("SELECT " +
            " b.* " +
            " FROM " +
            " asr_etinfo a " +
            " INNER JOIN base_equipmentinfo b ON a.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Base_equipmentinfo> getBase_equipmentinfo(@Param("ew")EntityWrapper entityWrapper);

}
