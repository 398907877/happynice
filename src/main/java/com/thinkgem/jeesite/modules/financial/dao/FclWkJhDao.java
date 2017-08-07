/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.financial.entity.FclWkJh;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分转换DAO接口
 * @author 陈世杰
 * @version 2017-05-16
 */
@MyBatisDao
public interface FclWkJhDao extends CrudDao<FclWkJh> {
	
	/**
	 * 挖矿分转激活分
	 * @param fclWkJh
	 */
	public void updateUserJh(FclWkJh fclWkJh);
	
	/**
	 * 挖矿分转滋养液
	 * @param fclWkJh
	 */
	public void updateUserZyy(FclWkJh fclWkJh);

	public User getUserInfo(String userid);
}