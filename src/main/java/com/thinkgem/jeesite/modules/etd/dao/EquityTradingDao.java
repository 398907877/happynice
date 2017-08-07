/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.etd.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.etd.entity.EquityTrading;

/**
 * 股权交易DAO接口
 * @author 陈世杰
 * @version 2017-05-19
 */
@MyBatisDao
public interface EquityTradingDao extends CrudDao<EquityTrading> {

	public List<EquityTrading> getMoney(EquityTrading equityTrading);
	
}