package com.thinkgem.jeesite.modules.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.product.entity.ProductType;
import com.thinkgem.jeesite.modules.product.service.ProductTypeService;

/**
 * 产品类别Controller
 * 
 * @author LFG
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productType")
public class ProductTypeController extends BaseController {

	@Autowired
	private ProductTypeService productTypeService;

	@RequiresPermissions("product:productyType:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProductType productType, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<ProductType> page = productTypeService.findPage(new Page<ProductType>(request, response), productType);
		model.addAttribute("page", page);
		return "modules/product/productTypeList";
	}

	@ModelAttribute
	public ProductType get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productTypeService.get(id);
		} else {
			return new ProductType();
		}
	}

	@RequiresPermissions("product:productyType:view")
	@RequestMapping(value = "form")
	public String form(ProductType productType, Model model) {
		model.addAttribute("productType", productType);
		return "modules/product/productTypeForm";
	}

	@RequiresPermissions("product:productyType:edit")
	@RequestMapping(value = "save")
	// @Valid
	public String save(ProductType productType, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/productType/list?repage&typeName=" + productType.getTypeName();
		}
		if (!beanValidator(model, productType)) {
			return form(productType, model);
		}
		productTypeService.save(productType);
		addMessage(redirectAttributes, "保存产品类别'" + productType.getTypeName() + "'成功");
		return "redirect:" + adminPath + "/product/productType/list?repage";
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductType productType, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/productType/list?repage&typeName=" + productType.getTypeName();
		}
		productTypeService.delete(productType);
		addMessage(redirectAttributes, "删除产品类别成功");
		return "redirect:" + adminPath + "/product/productType/list?repage";
	}

}
