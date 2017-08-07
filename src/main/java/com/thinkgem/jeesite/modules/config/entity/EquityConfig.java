package com.thinkgem.jeesite.modules.config.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 参数配置对象
 * 
 * @author LFG
 * @version 2017-07-25
 */
public class EquityConfig extends DataEntity<EquityConfig> {

	private static final long serialVersionUID = 1L;
	/**
	 * 价格
	 */
	private String price;
	/**
	 * 交易手续费
	 */
	private String charge;
	/**
	 * 交易分账:奖金分
	 */
	private String splitBonus;
	/**
	 * 交易分账:注册分
	 */
	private String splitReg;
	/**
	 * 交易分账:倍数
	 */
	private String splitTimes;
	/**
	 * 比率
	 */
	private String ratio;
	/**
	 * 涨价额度
	 */
	private String riseLimit;
	/**
	 * 涨价比重
	 */
	private String riseRate;
	/**
	 * 开关状态 1 开启，0关闭
	 */
	private String openState;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getSplitBonus() {
		return splitBonus;
	}

	public void setSplitBonus(String splitBonus) {
		this.splitBonus = splitBonus;
	}

	public String getSplitReg() {
		return splitReg;
	}

	public void setSplitReg(String splitReg) {
		this.splitReg = splitReg;
	}

	public String getSplitTimes() {
		return splitTimes;
	}

	public void setSplitTimes(String splitTimes) {
		this.splitTimes = splitTimes;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getRiseLimit() {
		return riseLimit;
	}

	public void setRiseLimit(String riseLimit) {
		this.riseLimit = riseLimit;
	}

	public String getRiseRate() {
		return riseRate;
	}

	public void setRiseRate(String riseRate) {
		this.riseRate = riseRate;
	}

	public String getOpenState() {
		return openState;
	}

	public void setOpenState(String openState) {
		this.openState = openState;
	}

}
