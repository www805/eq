package com.avst.equipmentcontrol.web.action;

import com.avst.equipmentcontrol.common.cache.AppCache;
import com.avst.equipmentcontrol.common.cache.NavCache;
import com.avst.equipmentcontrol.common.cache.param.Base_ettypeCacheParam;
import com.avst.equipmentcontrol.common.conf.Constant;
import com.avst.equipmentcontrol.common.util.DateUtil;
import com.avst.equipmentcontrol.common.util.baseaction.BaseAction;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.web.req.LoginParam;
import com.avst.equipmentcontrol.web.service.MainService;
import com.avst.equipmentcontrol.web.vo.EcCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/ec/main")
@Controller
public class MainAction extends BaseAction {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/{pageid}")
    public ModelAndView gotomain(Model model, @PathVariable("pageid")String pageid) {

        return new ModelAndView(pageid);
    }


    @RequestMapping(value = "/gotomain")
    public ModelAndView gotomain(Model model) {
        List<Base_ettypeCacheParam> navCache = NavCache.getNavCache();
        model.addAttribute("navCache", navCache);
        model.addAttribute("title", "设备管理系统");
        return new ModelAndView("sweb/main", "main", model);
    }

    @RequestMapping(value = "/home")
    public ModelAndView gotohome(Model model) {

        //获取统计数据信息
        EcCountVO ecCountVO = mainService.homeCount();

        model.addAttribute("ecCountVO", ecCountVO);
        model.addAttribute("title", "设备管理系统");
        return new ModelAndView("sweb/home", "main", model);
    }


    /**
     * 进入用户登录页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/gotologin")
    public ModelAndView gotologin(Model model, HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();

        model.addAttribute("result", rResult);
        model.addAttribute("title", "欢迎进入设备管理系统");

//        request.getSession().setAttribute(Constant.MANAGE_WEB,null);

        return new ModelAndView("sweb/login", "login", model);
    }

    @PostMapping(value = "/logining")
    @ResponseBody
    public RResult checklogin(Model model, HttpServletRequest request, HttpServletResponse response, LoginParam loginParam) {
        RResult result=createNewResultOfFail();
        mainService.logining(result,request,response,loginParam);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public RResult logout(Model model,HttpServletRequest request) {
        RResult rResult=createNewResultOfFail();
        this.changeResultToSuccess(rResult);
        rResult.setMessage("退出成功");
        request.getSession().setAttribute(Constant.MANAGE_WEB,null);
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return rResult;
    }

    /**
     * 获取导航栏目
     * @return
     */
    @RequestMapping("/getNavList")
    @ResponseBody
    public  RResult getNavList(){
        RResult result=this.createNewResultOfFail();
        mainService.getNavList(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping("/getLoginCookie")
    @ResponseBody
    public  RResult getLoginCookie(HttpServletRequest request) {
        RResult result = this.createNewResultOfFail();
        try {
            mainService.getLoginCookie(result,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    @RequestMapping(value = "/404")
    public ModelAndView getError(Model model) {
        model.addAttribute("title", "错误页面404");
        return new ModelAndView("error/404", "error", model);
    }


}
