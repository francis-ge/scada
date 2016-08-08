package com.sharpower.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.sharpower.entity.Resource;
import com.sharpower.entity.User;
import com.sharpower.service.UserService;

public class MyHibernateReaml extends AuthorizingRealm{
	private UserService userService;
	private SessionDAO sessionDAO;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String)principals.fromRealm(getName()).iterator().next();
        
        String hql = "from User u where u.name=?";
        
        List<User> users = new ArrayList<>();
        
        try {
        	users = userService.findEntityByHQL(hql, userName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if (users.size()>0) {
        	User user = users.get(0);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addRole(user.getRole().getName());

			for (Resource res : user.getRole().getResources()) {
				if (res!=null) {
					info.addRole(res.getUrl());
				}
			}
			
			return info;
		}else{
			return null;
		}
        
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		Collection<Session> sessions = sessionDAO.getActiveSessions();

		for(Session session:sessions){
			if(username.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
				session.setTimeout(0);
				session.stop();//设置session立即失效，即将其踢出系统
				break;
		}}
			
		String hql = "from User u where u.name=?";
		
		List<User> users = new ArrayList<>();
		try {
			users = userService.findEntityByHQL(hql, username);
			
		} catch (Exception e) {
				e.printStackTrace();
		}

		if (users.size()>0) {
			User user = users.get(0);
			
			if (user.getDisabled()) {
				throw new DisabledAccountException("Account [" + user.getName() + "] is Disabled.");
			}
		      
			String password = user.getPassword();
			
			String realmName = getName();
			SimpleAuthenticationInfo info = 
					new SimpleAuthenticationInfo(username, password, new Md5Hash(username+username),realmName);
			return info;
		}else {
			throw new UnknownAccountException();
		}
		
	}

}
