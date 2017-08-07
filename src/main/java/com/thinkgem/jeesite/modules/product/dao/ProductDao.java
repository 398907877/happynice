package com.thinkgem.jeesite.modules.product.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.entity.Product;

/**
 * 产品信息Dao
 * 
 * @author LFG
 *
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {

	void updateProductState(Product product);

}
