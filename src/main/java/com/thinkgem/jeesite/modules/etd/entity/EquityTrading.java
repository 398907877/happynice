/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.etd.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 股权交易Entity
 * @author 陈世杰
 * @version 2017-05-19
 */
public class EquityTrading extends DataEntity<EquityTrading> {
	
	private static final long serialVersionUID = 1L;
	private User user;				//交易用户
	private String tradingId;		// 交易人id
	private String tradingType;		// 交易类型1买欢乐豆2卖欢乐豆
	private String tradingNum;		// 交易数量
	private String tradingMoney;		// 交易金额
	private String startDate;		//开始时间
	private String endDate;			//结束时间
	
	public EquityTrading() {
		super();
	}

	public EquityTrading(String id){
		super(id);
	}

	@Length(min=1, max=64, message="交易人id长度必须介于 1 和 64 之间")
	public String getTradingId() {
		return tradingId;
	}

	public void setTradingId(String tradingId) {
		this.tradingId = tradingId;
	}
	
	@Length(min=0, max=100, message="交易类型1买欢乐豆2卖欢乐豆长度必须介于 0 和 100 之间")
	public String getTradingType() {
		return tradingType;
	}

	public void setTradingType(String tradingType) {
		this.tradingType = tradingType;
	}
	
	@Length(min=0, max=100, message="交易数量长度必须介于 0 和 100 之间")
	public String getTradingNum() {
		return tradingNum;
	}

	public void setTradingNum(String tradingNum) {
		this.tradingNum = tradingNum;
	}
	
	@Length(min=1, max=100, message="交易金额长度必须介于 1 和 100 之间")
	public String getTradingMoney() {
		return tradingMoney;
	}

	public void setTradingMoney(String tradingMoney) {
		this.tradingMoney = tradingMoney;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}