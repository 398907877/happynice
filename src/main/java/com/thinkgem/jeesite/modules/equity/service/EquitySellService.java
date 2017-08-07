/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.equity.entity.EquityBuy;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;
import com.thinkgem.jeesite.modules.equity.dao.EquitySellDao;
import com.thinkgem.jeesite.modules.etd.dao.EquityTradingDao;

/**
 * 欢乐豆卖Service
 * 
 * @author 陈世杰
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class EquitySellService extends CrudService<EquitySellDao, EquitySell> {

	@Autowired
	private EquitySellDao equitySellDao;

	public EquitySell get(String id) {
		return super.get(id);
	}

	public List<EquitySell> findList(EquitySell equitySell) {
		return super.findList(equitySell);
	}

	public Page<EquitySell> findPage(Page<EquitySell> page, EquitySell equitySell) {
		return super.findPage(page, equitySell);
	}

	@Transactional(readOnly = false)
	public void save(EquitySell equitySell) {
		super.save(equitySell);
	}

	@Transactional(readOnly = false)
	public void delete(EquitySell equitySell) {
		super.delete(equitySell);
	}

	@Transactional(readOnly = false)
	public EquitySell getMoney(EquitySell equitySell) {
		return equitySellDao.getMoney(equitySell);
	}

	@Transactional(readOnly = false)
	public void repealSell(EquitySell equitySell) {
		equitySellDao.repealSell(equitySell);
	}

	@Transactional(readOnly = false)
	public List<EquitySell> getSellList(EquitySell equitySell) {
		// TODO Auto-generated method stub
		return equitySellDao.getSellList(equitySell);
	}

	@Transactional(readOnly = false)
	public EquitySell getEquitySellId(EquitySell equitySell) {

		return equitySellDao.getEquitySellId(equitySell);

	}

	public String getMinTradingMoney(EquitySell equitySell) {
		return equitySellDao.getMinTradingMoney(equitySell);
	}

}