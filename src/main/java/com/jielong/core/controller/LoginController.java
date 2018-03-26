package com.jielong.core.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	static Logger logger = Logger.getLogger(LoginController.class);
	
	
	@RequestMapping(value="tologin")
    public String tologin(){
        return "redirect:login.jsp";
    }
	
	 @RequestMapping(value={"/", "toHome"})
	    public String toHome(){
	    	return "home";
	    }
	
    @RequestMapping(value="session", method = RequestMethod.POST)
    public ModelAndView session(String username, String password){
        
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        
        Subject currentUser = SecurityUtils.getSubject();
        
        String viewname = "redirect:toHome";
        
        try {
            currentUser.login(token);
        } catch  ( UnknownAccountException uae ) {
            logger.warn("UnknownAccountException", uae);
        } catch  ( IncorrectCredentialsException ice ) {
            logger.warn("IncorrectCredentialsException", ice);
            viewname = "redirect:login.jsp";
        } catch  ( LockedAccountException lae ) {
            logger.warn("LockedAccountException", lae);
        } catch  ( ExcessiveAttemptsException eae ) {
            logger.warn("ExcessiveAttemptsException", eae);
        } catch ( AuthenticationException ae ) {
            logger.warn("AuthenticationException", ae);
        }
        currentUser.getSession().setAttribute("username", username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewname);
      
        return modelAndView;
    }
    
    @RequestMapping(value="logout")
    public String logout(){
        Subject currentUser = SecurityUtils.getSubject();
        
        currentUser.logout();
        
        return "redirect:login.jsp";
    }
}
