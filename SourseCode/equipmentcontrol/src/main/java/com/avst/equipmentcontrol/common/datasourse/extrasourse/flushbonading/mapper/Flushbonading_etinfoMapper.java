package com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.Flushbonading_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.entity.param.Flushbonadinginfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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

}
