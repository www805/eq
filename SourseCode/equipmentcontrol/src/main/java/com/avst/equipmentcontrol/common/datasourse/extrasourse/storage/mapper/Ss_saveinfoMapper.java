package com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_saveinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Storage_ettype;
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
 * InnoDB free: 37888 kB Mapper 接口
 * </p>
 *
 * @author Mht
 * @since 2019-05-28
 */
@Component
public interface Ss_saveinfoMapper extends BaseMapper<Ss_saveinfo> {

    //查询单个
    @Select("select ss.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from ss_saveinfo ss " +
            " left join base_equipmentinfo et on ss.mtssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public Storage_ettype getStorageinfo(@Param("ew") EntityWrapper ew);


    //查询总条数
    @Select("select count(ss.id) from ss_saveinfo ss " +
            " left join base_equipmentinfo et on ss.mtssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public int getStorageInfoCount(@Param("ew") EntityWrapper ew);


    //查询列表+带分页
    @Select("select ss.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from ss_saveinfo ss " +
            " left join base_equipmentinfo et on ss.mtssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Storage_ettype> getStorageInfoPage(Page page, @Param("ew") EntityWrapper ew);

    //存储服务是否重复
    @Select("SELECT " +
            " count(s.id) " +
            " FROM " +
            " ss_saveinfo s " +
            " INNER JOIN base_equipmentinfo b ON s.mtssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    int getRepetition(@Param("ew")EntityWrapper entityWrapper);


    //获取存储服务关联的设备基表
    @Select("SELECT " +
            " b.* " +
            " FROM " +
            " ss_saveinfo s " +
            " INNER JOIN base_equipmentinfo b ON s.mtssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Base_equipmentinfo> getBase_equipmentinfo(@Param("ew")EntityWrapper entityWrapper);



}
