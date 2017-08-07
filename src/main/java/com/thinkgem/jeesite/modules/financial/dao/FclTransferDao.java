/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.financial.entity.FclTransfer;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 激活分转账DAO接口
 * @author 陈世杰
 * @version 2017-05-17
 */
@MyBatisDao
public interface FclTransferDao extends CrudDao<FclTransfer> {

	/**
	 * 更新转入用户激活分
	 * @param fclWkJh
	 */
	public void updateUserIntoJh(FclTransfer fclTransfer);
	/**
	 * 更新转出用户激活分
	 * @param fclJhTransfer
	 */
	public void updateUserOutJh(FclTransfer fclTransfer);
	/**
	 * 更新转入用户权证分
	 * @param fclWkJh
	 */
	public void updateUserIntoQz(FclTransfer fclTransfer);
	/**
	 * 更新转出用户权证分
	 * @param fclJhTransfer
	 */
	public void updateUserOutQz(FclTransfer fclTransfer);
	
	public User getUserInfo(String userid);
	
}