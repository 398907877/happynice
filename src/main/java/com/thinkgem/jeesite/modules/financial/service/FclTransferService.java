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
import com.thinkgem.jeesite.modules.financial.entity.FclTransfer;
import com.thinkgem.jeesite.modules.financial.dao.FclTransferDao;
import com.thinkgem.jeesite.modules.financial.dao.FclWkJhDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 激活分转账Service
 * @author 陈世杰
 * @version 2017-05-17
 */
@Service
@Transactional(readOnly = true)
public class FclTransferService extends CrudService<FclTransferDao, FclTransfer> {

	@Autowired
	private FclTransferDao fclTransferDao;
	
	public FclTransfer get(String id) {
		return super.get(id);
	}
	
	public List<FclTransfer> findList(FclTransfer fclJhTransfer) {
		return super.findList(fclJhTransfer);
	}
	
	public Page<FclTransfer> findPage(Page<FclTransfer> page, FclTransfer fclJhTransfer) {
		return super.findPage(page, fclJhTransfer);
	}
	
	@Transactional(readOnly = false)
	public void save(FclTransfer fclJhTransfer) {
		super.save(fclJhTransfer);
	}
	
	@Transactional(readOnly = false)
	public void delete(FclTransfer fclJhTransfer) {
		super.delete(fclJhTransfer);
	}

	/**
	 * 更新转入用户激活分
	 * @param fclJhTransfer
	 */
	@Transactional(readOnly = false)
	public void updateUserIntoJh(FclTransfer fclJhTransfer) {
		// TODO Auto-generated method stub
		fclTransferDao.updateUserIntoJh(fclJhTransfer);
	}
	/**
	 * 更新转出用户激活分
	 * @param fclJhTransfer
	 */
	@Transactional(readOnly = false)
	public void updateUserOutJh(FclTransfer fclJhTransfer) {
		// TODO Auto-generated method stub
		fclTransferDao.updateUserOutJh(fclJhTransfer);
	}
	
	/**
	 * 更新转入用户权证分
	 * @param fclJhTransfer
	 */
	@Transactional(readOnly = false)
	public void updateUserIntoQz(FclTransfer fclJhTransfer) {
		// TODO Auto-generated method stub
		fclTransferDao.updateUserIntoQz(fclJhTransfer);
	}
	/**
	 * 更新转出用户权证分
	 * @param fclJhTransfer
	 */
	@Transactional(readOnly = false)
	public void updateUserOutQz(FclTransfer fclJhTransfer) {
		// TODO Auto-generated method stub
		fclTransferDao.updateUserOutQz(fclJhTransfer);
	}

	public User getUserInfo(String userid) {
		// TODO Auto-generated method stub
		return fclTransferDao.getUserInfo(userid);
	}
}