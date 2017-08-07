package com.thinkgem.jeesite.modules.product.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品类别
 * @author LFG
 *
 */
public class ProductType extends DataEntity<ProductType> {
	private static final long serialVersionUID = 1L;
	/**
	 * 类别名称
	 */
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
