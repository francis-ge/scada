package com.sharpower.filter.springsecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

public class myFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return null;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource()  {
		return null;
	}

}