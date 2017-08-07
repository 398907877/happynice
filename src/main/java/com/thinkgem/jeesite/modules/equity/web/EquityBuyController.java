/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.web;

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
import com.thinkgem.jeesite.modules.config.entity.EquityConfig;
import com.thinkgem.jeesite.modules.config.service.EquityConfigService;
import com.thinkgem.jeesite.modules.equity.entity.EquityBuy;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;
import com.thinkgem.jeesite.modules.equity.service.EquityBuyService;
import com.thinkgem.jeesite.modules.equity.service.EquitySellService;
import com.thinkgem.jeesite.modules.financial.service.FclWkJhService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 欢乐豆买Controller
 * 
 * @author 陈世杰
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/equity/equityBuy")
public class EquityBuyController extends BaseController {

	@Autowired
	private EquityBuyService equityBuyService;
	@Autowired
	private EquitySellService equitySellService;
	@Autowired
	private FclWkJhService fclWkJhService;
	@Autowired
	private EquityConfigService equityConfigService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public EquityBuy get(@RequestParam(required = false) String id) {
		EquityBuy entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = equityBuyService.get(id);
		}
		if (entity == null) {
			entity = new EquityBuy();
		}
		return entity;
	}

	@RequiresPermissions("equity:equityBuy:view")
	@RequestMapping(value = { "list", "" })
	public String list(EquityBuy equityBuy, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		equityBuy.setBuyUserid(user.getId());
		Page<EquityBuy> page = equityBuyService.findPage(new Page<EquityBuy>(request, response), equityBuy);
		model.addAttribute("page", page);
		return "modules/equity/equityBuyList";
	}

	@RequiresPermissions("equity:equityBuy:view")
	@RequestMapping(value = "form")
	public String form(EquityBuy equityBuy, Model model) {
		model.addAttribute("equityBuy", equityBuy);

		return "modules/equity/equityBuyForm";
	}

	@RequiresPermissions("equity:equityBuy:edit")
	@RequestMapping(value = "save")
	public String save(EquityBuy equityBuy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, equityBuy)) {
			return form(equityBuy, model);
		}
		EquityConfig equityConfig = new EquityConfig();
		equityConfig.setId("1");
		String openState = equityConfigService.getOpenState(equityConfig);// 开关状态
																			// 1
																			// 开启，0关闭
		if (null != openState && "0".equals(openState)) {// 交易已关闭
			addMessage(redirectAttributes, "交易已关闭,无法购买！");
		} else {
			User user = UserUtils.getUser();
			equityBuy.setBuyUserid(user.getId());

			User u = fclWkJhService.getUserInfo(user.getId());
			if (u.getGwf().equals("0")) {
				addMessage(redirectAttributes, "用户购物分为零，无法购买");
				return "redirect:" + Global.getAdminPath() + "/equity/equityBuy/form";
			} else {
				equityBuyService.save(equityBuy);
				equityBuyService.updateSell(equityBuy);
				EquitySell equitySell = new EquitySell();
				equitySell.setId(equityBuy.getEquitySellId());
				EquitySell sell = equitySellService.getEquitySellId(equitySell);
				equityBuy.setEquitySell(sell);
				equityBuyService.updateSellUserInfo(equityBuy);
				equityBuyService.updateBuyUserInfo(equityBuy);
				User sysUser = systemService.getUser(user.getId());
				UserUtils.getUser().setHappyfood(sysUser.getHappyfood());
				UserUtils.getUser().setHappyfoodsum(sysUser.getHappyfoodsum());
				addMessage(redirectAttributes, "保存欢乐豆买成功");
			}
		}
		return "redirect:" + Global.getAdminPath() + "/equity/equityBuy/?repage";
	}

	@RequiresPermissions("equity:equityBuy:edit")
	@RequestMapping(value = "delete")
	public String delete(EquityBuy equityBuy, RedirectAttributes redirectAttributes) {
		equityBuyService.delete(equityBuy);
		addMessage(redirectAttributes, "删除欢乐豆买成功");
		return "redirect:" + Global.getAdminPath() + "/equity/equityBuy/?repage";
	}

}