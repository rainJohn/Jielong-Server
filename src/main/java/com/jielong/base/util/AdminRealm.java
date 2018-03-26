package com.jielong.base.util;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.jielong.core.dao.AdminMapper;
import com.jielong.core.domain.Admin;

public class AdminRealm extends AuthorizingRealm{

	static Logger logger = Logger.getLogger(AdminRealm.class);
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token=(UsernamePasswordToken) authcToken;
        String username = (String) token.getPrincipal();
        
        Admin admin = (this.adminMapper).queryAdminByUsername(username);
        
        if(admin == null){
        	 logger.warn("无此用户信息,用户名："+username);
             throw new UnknownAccountException("无此用户信息,用户名："+username);
        }
        logger.info("token: "+JSON.toJSONString(token));
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("user", admin);
        return new SimpleAuthenticationInfo(username, admin.getPassword(),getName());
	}

}
