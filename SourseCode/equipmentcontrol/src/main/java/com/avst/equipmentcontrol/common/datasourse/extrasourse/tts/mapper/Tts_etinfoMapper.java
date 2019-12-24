package com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.Tts_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.entity.param.TTS_et_ettype;
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
 * @since 2019-07-04
 */
@Component
public interface Tts_etinfoMapper extends BaseMapper<Tts_etinfo> {


    //查询单个
    @Select("select aet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from tts_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public TTS_et_ettype getttsinfo(@Param("ew") EntityWrapper ew);


    @Select("select aet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from tts_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid")
    public List<TTS_et_ettype> getttsinfoList();

    //带分页查询
    @Select("select aet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from tts_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<TTS_et_ettype> getttsinfoListPage(Page page, @Param("ew") EntityWrapper ew);


    //查询总数量
    @Select("select count(aet.id) from tts_etinfo aet " +
            " left join base_equipmentinfo et on aet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public int getttsinfoCount(@Param("ew") EntityWrapper ew);


    //文字转语音服务是否重复
    @Select("SELECT " +
            " count(t.id) " +
            " FROM " +
            " tts_etinfo t " +
            " INNER JOIN base_equipmentinfo b ON t.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    int getRepetition(@Param("ew")EntityWrapper entityWrapper);

    //获取语音服务器关联的设备基表
    @Select("SELECT " +
            " b.* " +
            " FROM " +
            " tts_etinfo t " +
            " INNER JOIN base_equipmentinfo b ON t.equipmentssid = b.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Base_equipmentinfo> getBase_equipmentinfo(@Param("ew")EntityWrapper entityWrapper);

}
