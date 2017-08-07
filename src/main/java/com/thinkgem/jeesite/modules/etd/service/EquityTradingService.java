/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.etd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.etd.entity.EquityTrading;
import com.thinkgem.jeesite.modules.etd.dao.EquityTradingDao;
import com.thinkgem.jeesite.modules.financial.dao.FclTransferDao;

/**
 * 股权交易Service
 * @author 陈世杰
 * @version 2017-05-19
 */
@Service
@Transactional(readOnly = true)
public class EquityTradingService extends CrudService<EquityTradingDao, EquityTrading> {

	@Autowired
	private EquityTradingDao equityTradingDao;
	
	public EquityTrading get(String id) {
		return super.get(id);
	}
	
	public List<EquityTrading> findList(EquityTrading equityTrading) {
		return super.findList(equityTrading);
	}
	
	public Page<EquityTrading> findPage(Page<EquityTrading> page, EquityTrading equityTrading) {
		return super.findPage(page, equityTrading);
	}
	
	@Transactional(readOnly = false)
	public void save(EquityTrading equityTrading) {
		super.save(equityTrading);
	}
	
	@Transactional(readOnly = false)
	public void delete(EquityTrading equityTrading) {
		super.delete(equityTrading);
	}
	@SuppressWarnings("unchecked")
	public List<EquityTrading> getMoney(EquityTrading equityTrading) {
		// TODO Auto-generated method stub
		return equityTradingDao.getMoney(equityTrading);
	}
	
}