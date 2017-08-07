package com.thinkgem.jeesite.modules.config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.config.entity.EquityConfig;

@MyBatisDao
public interface EquityConfigDao extends CrudDao<EquityConfig> {

	String getOpenState(EquityConfig equityConfig);

}
