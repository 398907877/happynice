/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 欢乐豆提现应急通道Entity
 * @author 陈世杰
 * @version 2017-05-29
 */
public class EquityGency extends DataEntity<EquityGency> {
	
	private static final long serialVersionUID = 1L;
	private String outnumberId;		// 转出编号
	private String happyfood;		// 欢乐豆数量
	private String money;		// 提现金额
	private User user;
	private String loginName;		//提现登录名
	
	public EquityGency() {
		super();
	}

	public EquityGency(String id){
		super(id);
	}

	@Length(min=1, max=64, message="转出编号长度必须介于 1 和 64 之间")
	public String getOutnumberId() {
		return outnumberId;
	}

	public void setOutnumberId(String outnumberId) {
		this.outnumberId = outnumberId;
	}
	
	@Length(min=0, max=255, message="欢乐豆数量长度必须介于 0 和 255 之间")
	public String getHappyfood() {
		return happyfood;
	}

	public void setHappyfood(String happyfood) {
		this.happyfood = happyfood;
	}
	
	@Length(min=1, max=100, message="提现金额长度必须介于 1 和 100 之间")
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}