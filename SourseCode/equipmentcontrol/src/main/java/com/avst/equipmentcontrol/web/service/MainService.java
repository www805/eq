package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.cache.AppCache;
import com.avst.equipmentcontrol.common.cache.param.AppCacheParam;
import com.avst.equipmentcontrol.common.conf.Constant;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.asr.mapper.Asr_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.flushbonading.mapper.Flushbonading_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.polygraph.mapper.Polygraph_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.storage.mapper.Ss_saveinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.extrasourse.tts.mapper.Tts_etinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_equipmentinfo;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.entity.Base_ettype;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_equipmentinfoMapper;
import com.avst.equipmentcontrol.common.datasourse.publicsourse.mapper.Base_ettypeMapper;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.OpenUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.web.req.LoginParam;
import com.avst.equipmentcontrol.web.vo.EcCountVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    private Flushbonading_etinfoMapper flushbonading_etinfoMapper;

    @Autowired
    private Asr_etinfoMapper asr_etinfoMapper;

    @Autowired
    private Polygraph_etinfoMapper polygraph_etinfoMapper;

    @Autowired
    private Ss_saveinfoMapper ss_saveinfoMapper;

    @Autowired
    private Base_ettypeMapper base_ettypeMapper;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    public RResult logining(RResult result, HttpServletRequest request, LoginParam loginParam){

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
            RResult rr = new RResult();
            this.getNavList(rr);
        }

        /**取出账号密码**/
        Map<String, Object> loginData = cacheParam.getData();

        String loginaccount = (String) loginData.get("loginaccount");
        String password = (String) loginData.get("password");

        if(!loginParam.getLoginaccount().equals(loginaccount)){
            result.setMessage("用户不存在");
            return result;
        }

        if(!loginParam.getPassword().equals(password)){
            result.setMessage("用户名或密码错误");
            return result;
        }

        result.changeToTrue();
        request.getSession().setAttribute(Constant.MANAGE_WEB, loginParam);

//        if(loginParam.getLoginaccount().equals("admin")&&loginParam.getPassword().equals("admin123")){
//            result.changeToTrue();
//
//            request.getSession().setAttribute(Constant.MANAGE_WEB,loginParam);
//
//        }else{
//            result.setMessage("登录失败");
//        }
        return result;
    }

    /**首页统计**/
    public EcCountVO homeCount(){

        EntityWrapper ew = new EntityWrapper();

        /**设备总数**/
        Integer flushbonadingCount = flushbonading_etinfoMapper.selectCount(ew);
        Integer asrCount = asr_etinfoMapper.selectCount(ew);
        Integer polygraphCount = polygraph_etinfoMapper.selectCount(ew);
        Integer ssCount = ss_saveinfoMapper.selectCount(ew);
        /**设备类型总数**/
        Integer ettypeCount = base_ettypeMapper.selectCount(ew);
        Integer ttsCount = tts_etinfoMapper.selectCount(ew);

        EcCountVO ecCountVO = new EcCountVO();
        ecCountVO.setFlushbonadingCount(flushbonadingCount);
        ecCountVO.setAsrCount(asrCount);
        ecCountVO.setPolygraphCount(polygraphCount);
        ecCountVO.setSsCount(ssCount);
        ecCountVO.setEttypeCount(ettypeCount);
        ecCountVO.setTtsCount(ttsCount);

        return ecCountVO;
    }

    public void getNavList(RResult result) {

        AppCacheParam cacheParam = AppCache.getAppCacheParam();
        if(null == cacheParam.getData()){
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

                avstYml.put("bottom", map.get("bottom"));
                if (null != map && map.size() > 0) {
                    cacheParam.setTitle((String) avstYml.get("title"));
                }
                cacheParam.setData(avstYml);

            } catch (IOException e) {
                LogUtil.intoLog(4, this.getClass(), "没找到外部配置文件：" + path);
            }finally {
                if(null != fis){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        result.setData(cacheParam);
        result.changeToTrue();
    }
}
