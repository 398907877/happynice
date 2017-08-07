/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;

/**
 * 欢乐豆卖DAO接口
 * @author 陈世杰
 * @version 2017-05-22
 */
@MyBatisDao
public interface EquitySellDao extends CrudDao<EquitySell> {
	
	public EquitySell getMoney(EquitySell equitySell);

	public void repealSell(EquitySell equitySell);

	public List<EquitySell> getSellList(EquitySell equitySell);
	
	public EquitySell getEquitySellId(EquitySell equitySell);

	public String getMinTradingMoney(EquitySell equitySell);

	public void resetAllSell();

}