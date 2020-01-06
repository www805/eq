package com.avst.equipmentcontrol.web.interceptor;

import com.avst.equipmentcontrol.common.conf.Constant;
import com.avst.equipmentcontrol.common.util.LogUtil;
import com.avst.equipmentcontrol.common.util.SpringUtil;
import com.avst.equipmentcontrol.common.util.baseaction.RResult;
import com.avst.equipmentcontrol.web.req.LoginParam;
import com.avst.equipmentcontrol.web.service.MainService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员拦截器
 */
public class ManagerInterceptor extends HandlerInterceptorAdapter {

    //在控制器执行前调用
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        LogUtil.intoLog(this.getClass(),"执行preHandle方法-->01");

        //获取session，判断用户
        HttpSession session=request.getSession();

        Object attribute = session.getAttribute(Constant.MANAGE_WEB);

        System.out.println(attribute);

        boolean disbool=true;
        String forstpageid="/ec/main/gotologin";//登录界面
        if(null==session.getAttribute(Constant.MANAGE_WEB)){//web客户端session
            disbool=false;
        }

        String url=request.getRequestURI();
        if(url.endsWith("/ec/main/gotologin") || url.endsWith("/ec/main/logining")){//跳过进入登录页面的拦截
            return true;
        }

        //如果跳转的是没路径的，就跳到设备home页
        if(url.endsWith("/")){
            response.sendRedirect("ec/main/gotomain");
            return false;
        }

        LoginParam loginParam = new LoginParam();
        loginParam.setLoginaccount("admin");
        loginParam.setPassword("admin");
        request.getSession().setAttribute(Constant.MANAGE_WEB, loginParam);
        disbool = true;  //暂时让他成功
        if (disbool) {
            return true;  //通过拦截器，继续执行请求
        } else {//跳转登录界面
//            RResult rresult = new RResult();
//            LoginParam loginParam = new LoginParam();
//            MainService mainService = SpringUtil.getBean(MainService.class);
//            rresult = mainService.logining(rresult, request, response, loginParam);
//            //登录成功就返回true
//            if ("SUCCESS".equalsIgnoreCase(rresult.getActioncode())) {
//                return true;
//            }

            request.getRequestDispatcher(forstpageid).forward(request, response);
            return false;  //没有通过拦截器，返回登录页面
        }
    }
    //在后端控制器执行后调用
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        LogUtil.intoLog(this.getClass(),"执行postHandle方法-->02");
        super.postHandle(request, response, handler, modelAndView);
    }
    //整个请求执行完成后调用
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LogUtil.intoLog(this.getClass(),"执行afterCompletion方法-->03");
        super.afterCompletion(request, response, handler, ex);
    }


}
