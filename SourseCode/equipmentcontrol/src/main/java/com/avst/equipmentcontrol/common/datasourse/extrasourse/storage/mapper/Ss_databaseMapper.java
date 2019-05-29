package com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper;

import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.Ss_database;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.entity.param.Ss_dataMessageParam;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
public interface Ss_databaseMapper extends BaseMapper<Ss_database> {

    @Select("select db.*,ds.iid iid,ds.datasavebasepath datasavebasepath,ds.saveinfossid saveinfossid" +
            " from ss_database db left join ss_datasave ds on ds.ssid=db.datasavessid" +
            " where 1=1 ${ew.sqlSegment}")
    public List<Ss_dataMessageParam> getSs_databaseByIid(@Param("ew")EntityWrapper ew);

}
