package com.avst.equipmentcontrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan({"com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper","com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper","com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper","com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper"})
public class EquipmentcontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentcontrolApplication.class, args);
    }

}
