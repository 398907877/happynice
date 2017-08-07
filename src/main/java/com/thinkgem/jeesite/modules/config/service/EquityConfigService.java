package com.thinkgem.jeesite.modules.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.config.dao.EquityConfigDao;
import com.thinkgem.jeesite.modules.config.entity.EquityConfig;
import com.thinkgem.jeesite.modules.equity.dao.EquitySellDao;

@Service
@Transactional(readOnly = true)
public class EquityConfigService extends CrudService<EquityConfigDao, EquityConfig> {
	@Autowired
	EquitySellDao equitySellDao;
	@Autowired
	EquityConfigDao equityConfigDao;

	@Transactional(readOnly = false)
	public void resetAllSell() {
		equitySellDao.resetAllSell();
	}

	public String getOpenState(EquityConfig equityConfig) {
		return equityConfigDao.getOpenState(equityConfig);
	}

}
