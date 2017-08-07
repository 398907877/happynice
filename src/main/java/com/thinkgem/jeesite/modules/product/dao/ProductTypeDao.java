package com.thinkgem.jeesite.modules.product.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.entity.ProductType;
/**
 * 产品类别Dao
 * @author LFG
 *
 */
@MyBatisDao
public interface ProductTypeDao extends CrudDao<ProductType> {

}
