package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品信息
 * 
 * @author LFG
 *
 */
public class Product extends DataEntity<Product> {

	private static final long serialVersionUID = 1L;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品编号
	 */
	private String productNo;
	/**
	 * 产品价格
	 */
	private Double productPrice;
	/**
	 * 图片路径
	 */
	private String picPath;
	/**
	 * 产品内容
	 */
	private String productContent;
	/**
	 * 发布时间
	 */
	private Date pulishDate;
	/**
	 * 产品状态 0：屏蔽，1：显示
	 */
	private String productState;
	/**
	 * 产品类别
	 */
	private String productTypeId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPulishDate() {
		return pulishDate;
	}

	public void setPulishDate(Date pulishDate) {
		this.pulishDate = pulishDate;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

}
