package com.avst.equipmentcontrol.common.cache;

import com.avst.equipmentcontrol.common.cache.param.AppCacheParam;
import com.avst.equipmentcontrol.common.conf.NetTool;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppCache {

    private static AppCacheParam appCacheParam;

    public static synchronized AppCacheParam getAppCacheParam() {

        if(null == appCacheParam || null == appCacheParam.getData()){
            appCacheParam = init();
        }
        return appCacheParam;
    }

    public static synchronized void setAppCacheParam(AppCacheParam appCacheParam) {
        AppCache.appCacheParam = appCacheParam;
    }

    public static synchronized void delAppCacheParam(){
        appCacheParam = null;
    }

    private static synchronized AppCacheParam init(){

        if(null == appCacheParam){
            appCacheParam = new AppCacheParam();
        }

        String application_name = PropertiesListenerConfig.getProperty("spring.application.name");
        String nav_file_name = PropertiesListenerConfig.getProperty("nav.file.name");

        String path = OpenUtil.getXMSoursePath() + "\\" + nav_file_name + ".yml";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);

            Yaml yaml = new Yaml();
            Map<String,Object> map = yaml.load(fis);
            Map<String,Object> avstYml = (Map<String, Object>) map.get(application_name);

            /**从数据库读取全部类型ip**/
            EntityWrapper<Base_ettype> ew = new EntityWrapper();
            Base_ettypeMapper base_ettypeMapper = SpringUtil.getBean(Base_ettypeMapper.class);
            List<Base_ettype> base_ettypes = base_ettypeMapper.selectList(ew);

            ArrayList<Map<String,String>> navList = (ArrayList<Map<String, String>>) avstYml.get("nav");
            for (Map<String, String> item : navList) {
                for (Base_ettype base_ettype : base_ettypes) {
                    if (item.get("name").equals(base_ettype.getExplain())) {
                        String url = item.get("url") + "?etypessid=" + base_ettype.getSsid();
                        item.put("url", url);
                    }
                }
            }

            Map<String,Object> zkYml = (Map<String, Object>) map.get("zk");
            Map<String,Object> guidepage = (Map<String, Object>) zkYml.get("guidepage");
            Map<String,Object> client_button = (Map<String, Object>) guidepage.get("client_button");
            String guidepageUrl = (String) client_button.get("url");
            String myIP = NetTool.getMyIP();
            avstYml.put("guidepageUrl" , "http://" + myIP + guidepageUrl);

            avstYml.put("bottom", map.get("bottom"));
            if (null != map && map.size() > 0) {
                appCacheParam.setTitle((String) avstYml.get("title"));
            }
            appCacheParam.setData(avstYml);

        } catch (IOException e) {
            LogUtil.intoLog(4, AppCache.class, "没找到外部配置文件：" + path);
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return appCacheParam;
    }

}
