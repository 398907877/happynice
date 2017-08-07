package com.thinkgem.jeesite.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product.dao.ProductTypeDao;
import com.thinkgem.jeesite.modules.product.entity.ProductType;

/**
 * 产品类别Service
 * 
 * @author LFG
 *
 */
@Service
@Transactional(readOnly = true)
public class ProductTypeService extends CrudService<ProductTypeDao, ProductType> {
	@Autowired
	private ProductTypeDao productTypeDao;

	public List<ProductType> findAllList() {
		return productTypeDao.findAllList(new ProductType());
	}

}
