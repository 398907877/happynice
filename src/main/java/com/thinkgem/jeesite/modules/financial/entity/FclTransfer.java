/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 激活分转账Entity
 * @author 陈世杰
 * @version 2017-05-17
 */
public class FclTransfer extends DataEntity<FclTransfer> {
	
	private static final long serialVersionUID = 1L;
	private User user;				//转账用户
	private String outnumberId;		// 转出编号
	private String intonumberId;		// 转入编号
	private String username;		//转入登录名
	private String uname;			//转入姓名
	private String transMoney;		// 转账金额
	private String transType;		//转账类型
	private String loginName;		//转出登录名
	
	public FclTransfer() {
		super();
	}

	public FclTransfer(String id){
		super(id);
	}

	@Length(min=1, max=64, message="转出编号长度必须介于 1 和 64 之间")
	public String getOutnumberId() {
		return outnumberId;
	}

	public void setOutnumberId(String outnumberId) {
		this.outnumberId = outnumberId;
	}
	
	@Length(min=1, max=64, message="转入编号长度必须介于 1 和 64 之间")
	public String getIntonumberId() {
		return intonumberId;
	}

	public void setIntonumberId(String intonumberId) {
		this.intonumberId = intonumberId;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Length(min=1, max=64, message="转账金额长度必须介于 1 和 64 之间")
	public String getTransMoney() {
		return transMoney;
	}

	public void setTransMoney(String transMoney) {
		this.transMoney = transMoney;
	}
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}