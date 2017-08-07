/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.equity.entity.EquityGency;

/**
 * 欢乐豆提现应急通道DAO接口
 * @author 陈世杰
 * @version 2017-05-29
 */
@MyBatisDao
public interface EquityGencyDao extends CrudDao<EquityGency> {

	public void updateUserInfo(EquityGency equityGency);
	
}