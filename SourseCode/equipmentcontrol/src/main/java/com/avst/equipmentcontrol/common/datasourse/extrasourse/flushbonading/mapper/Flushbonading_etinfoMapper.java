package com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
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
public interface Flushbonading_etinfoMapper extends BaseMapper<Flushbonading_etinfo> {


    @Select("select fet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from flushbonading_etinfo fet " +
            " left join base_equipmentinfo et on fet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public Flushbonadinginfo getFlushbonadinginfo(@Param("ew") EntityWrapper ew);


    //带分页
    @Select("select fet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from flushbonading_etinfo fet " +
            " left join base_equipmentinfo et on fet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Flushbonadinginfo> getFlushbonadingList(Page page, @Param("ew") EntityWrapper ew);

    //总数量
    @Select("select count(fet.id) from flushbonading_etinfo fet " +
            " left join base_equipmentinfo et on fet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public int getFlushbonadingCount(@Param("ew") EntityWrapper ew);


    //审讯设备是否重复
    @Select("SELECT " +
            " count(f.id) " +
            " FROM " +
            " flushbonading_etinfo f " +
            " INNER JOIN base_equipmentinfo b ON f.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    int getRepetition(@Param("ew")EntityWrapper entityWrapper);


    //获取审讯设备关联的设备基表
    @Select("SELECT " +
            " b.* " +
            " FROM " +
            " flushbonading_etinfo f " +
            " INNER JOIN base_equipmentinfo b ON f.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Base_equipmentinfo> getBase_equipmentinfo(@Param("ew")EntityWrapper entityWrapper);
}
