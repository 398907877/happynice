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
import com.thinkgem.jeesite.modules.equity.dao.EquityBuyDao;
import com.thinkgem.jeesite.modules.equity.dao.EquitySellDao;
import com.thinkgem.jeesite.modules.etd.entity.EquityTrading;

/**
 * 欢乐豆买Service
 * @author 陈世杰
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class EquityBuyService extends CrudService<EquityBuyDao, EquityBuy> {

	@Autowired
	private EquityBuyDao equityBuyDao;
	
	public EquityBuy get(String id) {
		return super.get(id);
	}
	
	public List<EquityBuy> findList(EquityBuy equityBuy) {
		return super.findList(equityBuy);
	}
	
	public Page<EquityBuy> findPage(Page<EquityBuy> page, EquityBuy equityBuy) {
		return super.findPage(page, equityBuy);
	}
	
	@Transactional(readOnly = false)
	public void save(EquityBuy equityBuy) {
		super.save(equityBuy);
	}
	
	@Transactional(readOnly = false)
	public void delete(EquityBuy equityBuy) {
		super.delete(equityBuy);
	}
	@Transactional(readOnly = false)
	public void updateSell(EquityBuy equityBuy) {
		equityBuyDao.updateSell(equityBuy);
	}
	@Transactional(readOnly = false)
	public void updateSellUserInfo(EquityBuy equityBuy) {
		equityBuyDao.updateSellUserInfo(equityBuy);
	}
	@Transactional(readOnly = false)
	public void updateBuyUserInfo(EquityBuy equityBuy) {
		equityBuyDao.updateBuyUserInfo(equityBuy);
	}
	
	@Transactional(readOnly = false)
	public void buySave(EquityBuy equityBuy) {
		equityBuyDao.insert(equityBuy);
	}
}