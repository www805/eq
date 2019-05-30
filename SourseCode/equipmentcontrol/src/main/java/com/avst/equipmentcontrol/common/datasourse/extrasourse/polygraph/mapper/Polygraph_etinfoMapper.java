package com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.Polygraph_etinfo;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.entity.param.PolygraphInfo;
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
public interface Polygraph_etinfoMapper extends BaseMapper<Polygraph_etinfo> {


    @Select("select pet.*,et.etnum,et.etip,et.etypessid,ett.ettypenum from polygraph_etinfo pet " +
            " left join base_equipmentinfo et on pet.equipmentssid=et.ssid" +
            " left join base_ettype ett on et.etypessid=ett.ssid" +
            " where 1=1 ${ew.sqlSegment}")
    public PolygraphInfo getPolygraphInfo(@Param("ew")EntityWrapper entityWrapper);

}
