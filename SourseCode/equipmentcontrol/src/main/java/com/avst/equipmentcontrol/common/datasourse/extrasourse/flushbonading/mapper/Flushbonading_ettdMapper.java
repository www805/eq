package com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper;

import com.avst.equipmentcontrol.web.req.flushbonadingEttd.FlushbonadingEttd;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_ettd;
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
public interface Flushbonading_ettdMapper extends BaseMapper<Flushbonading_ettd> {

    //查询单个
    @Select("select e.* from flushbonading_etinfo f " +
            " left join flushbonading_ettd e on f.ssid = e.flushbonadingssid " +
            " where 1=1 ${ew.sqlSegment}")
    public FlushbonadingEttd getFlushbonadingEttd(@Param("ew") EntityWrapper ew);


    //查询总条数
    @Select("select count(e.id) from flushbonading_etinfo f " +
            " left join flushbonading_ettd e on f.ssid = e.flushbonadingssid " +
            " where 1=1 ${ew.sqlSegment}")
    public int getFlushbonadingEttdCount(@Param("ew") EntityWrapper ew);


    //查询列表+带分页
    @Select("select e.* from flushbonading_etinfo f " +
            " left join flushbonading_ettd e on f.ssid = e.flushbonadingssid " +
            " where 1=1 ${ew.sqlSegment}")
    public List<FlushbonadingEttd> getFlushbonadingEttdPage(Page page, @Param("ew") EntityWrapper ew);

    //查询列表
    @Select("select e.* from flushbonading_etinfo f " +
            " left join flushbonading_ettd e on f.ssid = e.flushbonadingssid " +
            " where 1=1 ${ew.sqlSegment}")
    public List<FlushbonadingEttd> getFlushbonadingEttdList(@Param("ew") EntityWrapper ew);


    //通过会议通道ssid查询指定的直播地址
    @Select("select ti.livingurl from flushbonading_ettd td " +
            " LEFT JOIN flushbonading_etinfo ti ON td.flushbonadingssid = ti.ssid " +
            " where 1=1 ${ew.sqlSegment}")
    public String getFlushbonadingEttdByMcSsid(@Param("ew") EntityWrapper ew);



}
