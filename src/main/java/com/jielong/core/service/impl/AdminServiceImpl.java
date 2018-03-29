package com.jielong.core.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jielong.core.dao.AdminMapper;
import com.jielong.core.domain.Admin;
import com.jielong.core.service.AdminService;



@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminMapper adminMapper;
	
	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Admin admin=adminMapper.queryAdminByUserName(userName);
		if (admin==null) {
			throw new UsernameNotFoundException("User"+userName+"not found!");
		}else {
		
			//return new org.springframework.security.core.userdetails.User(admin.getUsername(),admin.getPassword(),authorities);
			return User.withDefaultPasswordEncoder().username(admin.getUsername()).password(admin.getPassword()).roles("ADMIN").build(); 
		}
        
	}

	
}
