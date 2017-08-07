/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分提现Entity
 * @author 陈世杰
 * @version 2017-05-18
 */
public class FclWithdraw extends DataEntity<FclWithdraw> {
	
	private static final long serialVersionUID = 1L;
	private User user;				//提现用户
	private String outnumberId;		// 转出编号
	private String money;		// 提现金额
	private String loginName;		//转出登录名
	
	public FclWithdraw() {
		super();
	}

	public FclWithdraw(String id){
		super(id);
	}

	@Length(min=1, max=64, message="转出编号长度必须介于 1 和 64 之间")
	public String getOutnumberId() {
		return outnumberId;
	}

	public void setOutnumberId(String outnumberId) {
		this.outnumberId = outnumberId;
	}
	
	@Length(min=1, max=100, message="提现金额长度必须介于 1 和 100 之间")
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}