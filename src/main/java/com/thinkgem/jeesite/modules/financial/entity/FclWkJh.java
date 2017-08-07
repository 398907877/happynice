/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分转换Entity
 * @author 陈世杰
 * @version 2017-05-16
 */
public class FclWkJh extends DataEntity<FclWkJh> {
	
	private static final long serialVersionUID = 1L;
	private User user;				//归属用户
	private String outnumberId;		// 转出编号
	private String intonumberId;		// 转入编号
	private String changetype;		// 转账类型
	private String money;		// 转账金额
	private String loginName;		//转出登录名
	private String intoName;			//转出登录名
	
	public FclWkJh() {
		super();
	}

	public FclWkJh(String id){
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
	
	@Length(min=1, max=100, message="转账类型长度必须介于 1 和 100 之间")
	public String getChangetype() {
		return changetype;
	}

	public void setChangetype(String changetype) {
		this.changetype = changetype;
	}
	
	@Length(min=1, max=64, message="转账金额长度必须介于 1 和 64 之间")
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getIntoName() {
		return intoName;
	}

	public void setIntoName(String intoName) {
		this.intoName = intoName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}