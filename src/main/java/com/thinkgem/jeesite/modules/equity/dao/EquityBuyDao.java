/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.equity.entity.EquityBuy;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;

/**
 * 欢乐豆买DAO接口
 * @author 陈世杰
 * @version 2017-05-22
 */
@MyBatisDao
public interface EquityBuyDao extends CrudDao<EquityBuy> {

	public void updateSell(EquityBuy equityBuy);

	public void updateSellUserInfo(EquityBuy equityBuy);

	public void updateBuyUserInfo(EquityBuy equityBuy);

	
	
}