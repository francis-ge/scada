package com.sharpower.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionSupport;

public class AjaxSessionAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<String> sessionIds = new ArrayList<>();
	
	private SessionDAO sessionDAO;
	private Collection<Session> sessions = new ArrayList<>();
	private String result;
	
	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}
	public Collection<Session> getSessions() {
		return sessions;
	}
	public void setSessionIds(List<String> sessionIds) {
		this.sessionIds = sessionIds;
	}
	public String getResult() {
		return result;
	}
	public String currentUsers(){
		Collection<Session> sessions1  = sessionDAO.getActiveSessions();
		
		for (Session session : sessions1) {
			
			Object username = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			
			if (username!=null) {
				session.setAttribute("userName", session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
				sessions.add(session);
			}
		}
		
		return SUCCESS;
	}
	
	public String forceLogout(){
	
		try {
			for (Serializable sessionId : sessionIds) {
				Session session = sessionDAO.readSession(sessionId); 
				session.setTimeout(0);
				session.stop();
			}
			
			result = "强制退出成功！";
		}catch(Exception e){
			result = "操作失败！";
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

}
