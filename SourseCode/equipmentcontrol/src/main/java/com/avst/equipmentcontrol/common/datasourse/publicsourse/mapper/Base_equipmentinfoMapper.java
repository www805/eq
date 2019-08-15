package com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper;

import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.web.vo.baseEttype.EquipmentBasicsVO;
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
public interface Base_equipmentinfoMapper extends BaseMapper<Base_equipmentinfo> {

    /**
     * 带分页批量查询
     * @param page
     * @param ew
     * @return
     */
    @Select("SELECT e.*, t.EXPLAIN typename FROM " +
            " base_equipmentinfo e " +
            " LEFT JOIN base_ettype t ON e.etypessid = t.ssid " +
            " where 1=1 ${ew.sqlSegment}")
    List<EquipmentBasicsVO> getEquipmentBasics(Page<EquipmentBasicsVO> page, @Param("ew") EntityWrapper ew);

    /**
     * 无分页
     * @param ew
     * @return
     */
    @Select("SELECT e.*, t.EXPLAIN typename FROM " +
            " base_equipmentinfo e " +
            " LEFT JOIN base_ettype t ON e.etypessid = t.ssid " +
            " where 1=1 ${ew.sqlSegment}")
    List<EquipmentBasicsVO> getEquipmentBasicsAll(@Param("ew") EntityWrapper ew);

    /**
     * 查询数量
     * @param ew
     * @return
     */
    @Select("SELECT count(e.id) FROM " +
            " base_equipmentinfo e " +
            " LEFT JOIN base_ettype t ON e.etypessid = t.ssid " +
            " where 1=1 ${ew.sqlSegment}")
    int getEquipmentBasicsCount(@Param("ew") EntityWrapper ew);

    /**
     * 查询单个
     * @param ew
     * @return
     */
    @Select("SELECT e.*, t.EXPLAIN typename FROM " +
            " base_equipmentinfo e " +
            " LEFT JOIN base_ettype t ON e.etypessid = t.ssid " +
            " where 1=1 ${ew.sqlSegment}")
    EquipmentBasicsVO getEquipmentBasicsById(@Param("ew") EntityWrapper ew);

    /**
     * 获取设备IP
     * @param ew
     * @return
     */
    @Select("SELECT eq.* FROM " +
            " base_ettype et " +
            " LEFT JOIN base_equipmentinfo eq ON et.ssid = eq.etypessid  " +
            " where 1=1 ${ew.sqlSegment}")
    EquipmentBasicsVO getEquipmentBasicsByIp(@Param("ew") EntityWrapper ew);
}
