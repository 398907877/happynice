package com.thinkgem.jeesite.modules.product.web;

import java.util.Date;

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
import com.thinkgem.jeesite.modules.product.entity.Product;
import com.thinkgem.jeesite.modules.product.service.ProductService;

/**
 * 产品类别Controller
 * 
 * @author LFG
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/product/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productService.getProduct(id);
		} else {
			return new Product();
		}
	}

	@RequiresPermissions({ "product:producty:view", "product:productyType:view" })
	@RequestMapping(value = { "list", "" })
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		return "modules/product/productList";
	}

	@RequiresPermissions("product:producty:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		model.addAttribute("product", product);
		return "modules/product/productForm";
	}

	@RequiresPermissions("product:producty:edit")
	@RequestMapping(value = "save")
	// @Valid
	public String save(Product product, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/product/?repage";
		}
		if (!beanValidator(model, product)) {
			return form(product, model);
		}
		product.setProductState("1");// 显示
		product.setPulishDate(new Date());
		productService.save(product);
		addMessage(redirectAttributes, "保存'" + product.getProductName() + "'成功");
		return "redirect:" + adminPath + "/product/product/?repage";
	}

	@RequiresPermissions("product:producty:edit")
	@RequestMapping(value = "updateProductState")
	public String updateProductState(String productState, String ids, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/product/?repage";
		}
		productService.updateProductState(productState, ids);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/product/product/?repage";
	}

	@RequiresPermissions("product:producty:edit")
	@RequestMapping(value = "delete")
	public String delete(Product product, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/product/?repage";
		}
		productService.delete(product);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/product/product/?repage";
	}

}
