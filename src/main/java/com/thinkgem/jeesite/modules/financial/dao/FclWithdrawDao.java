/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.financial.entity.FclWithdraw;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分提现DAO接口
 * @author 陈世杰
 * @version 2017-05-18
 */
@MyBatisDao
public interface FclWithdrawDao extends CrudDao<FclWithdraw> {

	/**
	 * 更新提现用户挖矿分
	 * @param fclWithdraw
	 */
	public void updateUserWkf(FclWithdraw fclWithdraw);

	public User getUserInfo(String userid);
	
}