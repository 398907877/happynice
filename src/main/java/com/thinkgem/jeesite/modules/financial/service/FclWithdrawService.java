/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.financial.entity.FclWithdraw;
import com.thinkgem.jeesite.modules.financial.dao.FclTransferDao;
import com.thinkgem.jeesite.modules.financial.dao.FclWithdrawDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分提现Service
 * @author 陈世杰
 * @version 2017-05-18
 */
@Service
@Transactional(readOnly = true)
public class FclWithdrawService extends CrudService<FclWithdrawDao, FclWithdraw> {

	@Autowired
	private FclWithdrawDao fclWithdrawDao;
	
	public FclWithdraw get(String id) {
		return super.get(id);
	}
	
	public List<FclWithdraw> findList(FclWithdraw fclWithdraw) {
		return super.findList(fclWithdraw);
	}
	
	public Page<FclWithdraw> findPage(Page<FclWithdraw> page, FclWithdraw fclWithdraw) {
		return super.findPage(page, fclWithdraw);
	}
	
	@Transactional(readOnly = false)
	public void save(FclWithdraw fclWithdraw) {
		super.save(fclWithdraw);
	}
	
	@Transactional(readOnly = false)
	public void delete(FclWithdraw fclWithdraw) {
		super.delete(fclWithdraw);
	}
	
	/**
	 * 更新提现用户挖矿分
	 * @param fclWithdraw
	 */
	@Transactional(readOnly = false)
	public void updateUserWkf(FclWithdraw fclWithdraw) {
		// TODO Auto-generated method stub
		fclWithdrawDao.updateUserWkf(fclWithdraw);
	}

	public User getUserInfo(String userid) {
		// TODO Auto-generated method stub
		return fclWithdrawDao.getUserInfo(userid);
	}
	
}