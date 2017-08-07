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
import com.thinkgem.jeesite.modules.financial.entity.FclWkJh;
import com.thinkgem.jeesite.modules.financial.dao.FclWkJhDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 挖矿分转换Service
 * @author 陈世杰
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class FclWkJhService extends CrudService<FclWkJhDao, FclWkJh> {
	@Autowired
	private FclWkJhDao fclWkJhDao;
	
	public FclWkJh get(String id) {
		return super.get(id);
	}
	
	public List<FclWkJh> findList(FclWkJh fclWkJh) {
		return super.findList(fclWkJh);
	}
	
	public Page<FclWkJh> findPage(Page<FclWkJh> page, FclWkJh fclWkJh) {
		return super.findPage(page, fclWkJh);
	}
	
	@Transactional(readOnly = false)
	public void save(FclWkJh fclWkJh) {
		super.save(fclWkJh);
	}
	
	@Transactional(readOnly = false)
	public void delete(FclWkJh fclWkJh) {
		super.delete(fclWkJh);
	}
	/**
	 * 更新用户激活分
	 * @param fclWkJh
	 */
	@Transactional(readOnly = false)
	public void updateUserJh(FclWkJh fclWkJh) {
		fclWkJhDao.updateUserJh(fclWkJh);
	}
	/**
	 * 更新用户滋养液
	 * @param fclWkJh
	 */
	@Transactional(readOnly = false)
	public void updateUserZyy(FclWkJh fclWkJh) {
		fclWkJhDao.updateUserZyy(fclWkJh);
	}

	public User getUserInfo(String userid) {
		// TODO Auto-generated method stub
		return fclWkJhDao.getUserInfo(userid);
	}
}