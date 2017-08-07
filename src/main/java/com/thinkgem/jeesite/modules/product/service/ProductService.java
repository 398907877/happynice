package com.thinkgem.jeesite.modules.product.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product.dao.ProductDao;
import com.thinkgem.jeesite.modules.product.entity.Product;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 产品信息Service
 * 
 * @author LFG
 *
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {

	@Autowired
	private ProductDao productDao;

	public Product getProduct(String id) {
		return productDao.get(id);
	}

	@Transactional(readOnly = false)
	public void updateProductState(String productState, String ids) {
		if (null != ids && ids.length() > 0) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				Product product = new Product();
				product.setId(id);
				product.setUpdateBy(UserUtils.getUser());
				product.setUpdateDate(new Date());
				product.setProductState(productState);
				productDao.updateProductState(product);
			}
		}
	}

}
