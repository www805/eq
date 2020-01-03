package com.avst.equipmentcontrol.web.service;

import com.avst.equipmentcontrol.common.cache.AppCache;
import com.avst.equipmentcontrol.common.cache.BaseEcCache;
import com.avst.equipmentcontrol.common.cache.param.AppCacheParam;
import com.avst.equipmentcontrol.common.conf.Constant;
import com.avst.equipmentcontrol.common.conf.NetTool;
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
import com.avst.equipmentcontrol.common.util.baseaction.BaseService;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.common.util.baseaction.ReqParam;
import com.avst.equipmentcontrol.common.util.properties.PropertiesListenerConfig;
import com.avst.equipmentcontrol.feignclient.base.vo.ControlInfoParamVO;
import com.avst.equipmentcontrol.feignclient.trm.TrmControl;
import com.avst.equipmentcontrol.feignclient.zk.ZkControl;
import com.avst.equipmentcontrol.web.req.GetBaseEcParam;
import com.avst.equipmentcontrol.web.req.LoginParam;
import com.avst.equipmentcontrol.web.vo.EcCountVO;
import com.avst.equipmentcontrol.web.vo.GetLoginCookieVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService extends BaseService {

    private Gson gson = new Gson();

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
    private TrmControl trmControl;

    @Autowired
    private Base_equipmentinfoMapper base_equipmentinfoMapper;

    @Autowired
    private Tts_etinfoMapper tts_etinfoMapper;

    public RResult logining(RResult result, HttpServletRequest request, HttpServletResponse response, LoginParam loginParam){

        String loginaccount = "";
        Boolean loginbool = false;

        if(StringUtils.isBlank(loginParam.getLoginaccount()) && StringUtils.isBlank(loginParam.getPassword())){

            ReqParam reqParam = new ReqParam();
            RResult userPwdResult = null;
            try {
                userPwdResult = trmControl.getUserPwd(reqParam);
            } catch (Exception e) {
                LogUtil.intoLog(4, this.getClass(), "远程请求trm获取账号密码失败。。。");
            }

            if(null != userPwdResult && "SUCCESS".equalsIgnoreCase(userPwdResult.getActioncode())){
                ControlInfoParamVO vo = gson.fromJson(gson.toJson(userPwdResult.getData()), ControlInfoParamVO.class);
                if(StringUtils.isNotBlank(vo.getLoginusername()) && StringUtils.isNotBlank(vo.getLoginpassword())){
                    loginaccount = vo.getLoginusername();
                    loginParam.setLoginaccount(loginaccount);
                    loginParam.setPassword(vo.getLoginpassword());
                    LogUtil.intoLog(1, this.getClass(), "获取trm账号密码成功！账号：" + loginParam.getLoginaccount() + " 密码：" + loginParam.getPassword());
                    loginbool = true;
                }
            }

        }

        if(!loginbool){
            AppCacheParam cacheParam = AppCache.getAppCacheParam();
            if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
                RResult rr = new RResult();
                this.getNavList(rr);
            }

            /**取出账号密码**/
            Map<String, Object> loginData = cacheParam.getData();

            loginaccount = (String) loginData.get("loginaccount");
            String password = (String) loginData.get("password");

            if(null == loginParam.getLoginaccount() || !loginParam.getLoginaccount().equals(loginaccount)){
                result.setMessage("用户不存在");
                return result;
            }

            if(null == loginParam.getPassword() || !loginParam.getPassword().equals(password)){
                result.setMessage("用户名或密码错误");
                return result;
            }
        }



        boolean rememberpassword=loginParam.isRememberpassword();
        if (rememberpassword){
            Cookie ecloginaccount=new Cookie("ECLOGINACCOUNT",loginaccount);
            ecloginaccount.setMaxAge(60*60*24*7);
            ecloginaccount.setPath("/");
            Cookie ecrememberme=new Cookie("ECREMEMBERME","YES");
            ecrememberme.setMaxAge(60*60*24*7);
            ecrememberme.setPath("/");
            response.addCookie(ecloginaccount);
            response.addCookie(ecrememberme);
        }else {
            Cookie ecloginaccount=new Cookie("ECLOGINACCOUNT",null);
            ecloginaccount.setMaxAge(0);
            ecloginaccount.setPath("/");
            Cookie ecrememberme=new Cookie("ECREMEMBERME",null);
            ecrememberme.setMaxAge(0);
            ecrememberme.setPath("/");
            response.addCookie(ecloginaccount);
            response.addCookie(ecrememberme);
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

        result.setData(cacheParam);
        result.changeToTrue();
    }

    public void getLoginCookie(RResult result,HttpServletRequest request){
        GetLoginCookieVO vo=new GetLoginCookieVO();
        String loginaccount = "";
        String password = "";

        //获取当前站点的所有Cookie
        String rememberme=null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
                if ("ECLOGINACCOUNT".equals(cookies[i].getName())) {
                    loginaccount = cookies[i].getValue();
                } else if ("ECREMEMBERME".equals(cookies[i].getName())) {
                    rememberme = cookies[i].getValue();
                }
            }
        }

        if (StringUtils.isNotEmpty(rememberme)&&rememberme.equals("YES")&&StringUtils.isNotEmpty(loginaccount)){
            AppCacheParam cacheParam = AppCache.getAppCacheParam();
            if (StringUtils.isBlank(cacheParam.getTitle()) || null == cacheParam.getData()) {
                RResult rr = new RResult();
                this.getNavList(rr);
            }
            Map<String, Object> loginData = cacheParam.getData();
            password = (String) loginData.get("password");
        }


        vo.setLoginaccount(loginaccount);
        vo.setPassword(password);
        result.setData(vo);
        result.changeToTrue();
        return;
    }

    public void getBaseEc(RResult result, GetBaseEcParam param) {

//        EntityWrapper<Base_equipmentinfo> ew = new EntityWrapper<>();

//        System.out.println(param.getEtnum());

//        if(StringUtils.isNotBlank(param.getEtnum())){
//            ew.like("etnum", param.getEtnum());
//        }

//        List<Base_equipmentinfo> equipmentinfos = base_equipmentinfoMapper.selectList(ew);

        List<Base_equipmentinfo> equipmentinfos = BaseEcCache.getBaseEcCache(param.getEtip());//从缓存里读基础设备信息

        result.changeToTrue(equipmentinfos);

    }
}
