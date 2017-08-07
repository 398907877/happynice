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
import com.thinkgem.jeesite.modules.equity.entity.EquityGency;
import com.thinkgem.jeesite.modules.equity.dao.EquityBuyDao;
import com.thinkgem.jeesite.modules.equity.dao.EquityGencyDao;

/**
 * 欢乐豆提现应急通道Service
 * @author 陈世杰
 * @version 2017-05-29
 */
@Service
@Transactional(readOnly = true)
public class EquityGencyService extends CrudService<EquityGencyDao, EquityGency> {

	@Autowired
	private EquityGencyDao equityGencyDao;
	
	
	public EquityGency get(String id) {
		return super.get(id);
	}
	
	public List<EquityGency> findList(EquityGency equityGency) {
		return super.findList(equityGency);
	}
	
	public Page<EquityGency> findPage(Page<EquityGency> page, EquityGency equityGency) {
		return super.findPage(page, equityGency);
	}
	
	@Transactional(readOnly = false)
	public void save(EquityGency equityGency) {
		super.save(equityGency);
	}
	
	@Transactional(readOnly = false)
	public void delete(EquityGency equityGency) {
		super.delete(equityGency);
	}
	@Transactional(readOnly = false)
	public void updateUserInfo(EquityGency equityGency) {
		
		equityGencyDao.updateUserInfo(equityGency);
	}
	
}