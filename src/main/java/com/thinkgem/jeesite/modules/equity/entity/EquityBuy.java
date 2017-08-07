/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 欢乐豆买Entity
 * @author 陈世杰
 * @version 2017-05-22
 */
public class EquityBuy extends DataEntity<EquityBuy> {
	
	private static final long serialVersionUID = 1L;
	private EquitySell equitySell;
	private String equitySellId;		// equity_sell表id
	private String buyNum;				// 购买数量
	private String buyMoney;			// 购买金额
	private String buyUserid;			//购买人id
	private User user;
	private String loginName;			//购买人登录名
	
	
	public EquityBuy() {
		super();
	}

	public EquityBuy(String id){
		super(id);
	}

	public EquitySell getEquitySell() {
		return equitySell;
	}

	public void setEquitySell(EquitySell equitySell) {
		this.equitySell = equitySell;
	}

	@Length(min=1, max=64, message="equity_sell表id长度必须介于 1 和 64 之间")
	public String getEquitySellId() {
		return equitySellId;
	}

	public void setEquitySellId(String equitySellId) {
		this.equitySellId = equitySellId;
	}
	
	@Length(min=0, max=100, message="购买数量长度必须介于 0 和 100 之间")
	public String getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	
	@Length(min=1, max=100, message="购买金额长度必须介于 1 和 100 之间")
	public String getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(String buyMoney) {
		this.buyMoney = buyMoney;
	}

	public String getBuyUserid() {
		return buyUserid;
	}

	public void setBuyUserid(String buyUserid) {
		this.buyUserid = buyUserid;
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