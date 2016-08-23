package com.sharpower.shiro;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import com.sharpower.entity.User;
import com.sharpower.entity.UserOperationRecode;
import com.sharpower.service.UserOperationRecodeService;
import com.sharpower.service.UserService;

public class OperationRecodeFilter extends PermissionsAuthorizationFilter {
	private UserService UserService;
	private UserOperationRecodeService userOperationRecodeService;
	
	public void setUserService(UserService userService) {
		UserService = userService;
	}
	public void setUserOperationRecodeService(UserOperationRecodeService userOperationRecodeService) {
		this.userOperationRecodeService = userOperationRecodeService;
	}
	
	@Override
	public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		Subject subject = getSubject(request, response);
		
		String userName = "anonymous";
		
		Object object = subject.getPrincipal();
		
        if (null != object ){  
           userName = (String)object;
           User user = (User) UserService.uniqueResult("from User u where u.name=?", userName);
           
           if (user!=null) {
               HttpServletRequest httpServletRequest = (HttpServletRequest)request;
               
               String url = httpServletRequest.getRequestURI();
               Map<String, String[]> params = httpServletRequest.getParameterMap();
               
               UserOperationRecode userOperationRecode = new UserOperationRecode();
               
               userOperationRecode.setDateTime(new Date());
               userOperationRecode.setUser(user);
               userOperationRecode.setUrl(url);
               userOperationRecode.setDescription(params.toString());
               
               userOperationRecodeService.saveEntity(userOperationRecode);
           }
        }
        
		super.doFilterInternal(request, response, chain);
	}
}
