package com.avst.equipmentcontrol;

import com.avst.equipmentcontrol.common.util.properties.PropertiesListener;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@MapperScan({"com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper",
        "com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper",
        "com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper",
        "com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper",
        "com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper",
        "com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper"})
@EnableScheduling
public class EquipmentcontrolApplication{

//    public class EquipmentcontrolApplication extends SpringBootServletInitializer {//外部Tomcat使用的处理
//@Override
//protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    application.application().addListeners(new PropertiesListener("application.properties","ec.properties"));
//    application.application().setBeanNameGenerator(new CustomGenerator());
//    return application.sources(EquipmentcontrolApplication.class);
//}

    /**
     *
     * 第四种方式：通过注册监听器(`Listeners`) + `PropertiesLoaderUtils`的方式
     *
     * 注册配置文件监听器
     */
    @RequestMapping("/listener")
    public Map<String, Object> listener() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(PropertiesListenerConfig.getAllProperty());
        return map;
    }




    public static class CustomGenerator implements BeanNameGenerator {

        @Override
        public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
            if(definition != null) {
                return definition.getBeanClassName();
            }
            return null;
        }
    }

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(EquipmentcontrolApplication.class);
        application.setBeanNameGenerator(new CustomGenerator());
        application.addListeners(new PropertiesListener("application.properties","ec.properties"));
        application.run( args);

    }

}
