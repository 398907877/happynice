/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.equity.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.EquityConfig;
import com.thinkgem.jeesite.modules.config.service.EquityConfigService;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;
import com.thinkgem.jeesite.modules.equity.service.EquitySellService;
import com.thinkgem.jeesite.modules.financial.service.FclWkJhService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 欢乐豆卖Controller
 * 
 * @author 陈世杰
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/equity/equitySell")
public class EquitySellController extends BaseController {

	@Autowired
	private EquitySellService equitySellService;
	@Autowired
	private FclWkJhService fclWkJhService;
	@Autowired
	private EquityConfigService equityConfigService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public EquitySell get(@RequestParam(required = false) String id) {
		EquitySell entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = equitySellService.get(id);
		}
		if (entity == null) {
			entity = new EquitySell();
		}
		return entity;
	}

	@RequiresPermissions("equity:equitySell:view")
	@RequestMapping(value = { "list", "" })
	public String list(EquitySell equitySell, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		equitySell.setTradingId(user.getId());
		Page<EquitySell> page = equitySellService.findPage(new Page<EquitySell>(request, response), equitySell);

		model.addAttribute("page", page);
		return "modules/equity/equitySellList";
	}

	@RequiresPermissions("equity:equitySell:view")
	@RequestMapping(value = "form")
	public String form(EquitySell equitySell, Model model) {
		model.addAttribute("equitySell", equitySell);
		return "modules/equity/equitySellForm";
	}

	@RequiresPermissions("equity:equitySell:edit")
	@RequestMapping(value = "save")
	public String save(EquitySell equitySell, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, equitySell)) {
			return form(equitySell, model);
		}
		EquityConfig equityConfig = new EquityConfig();
		equityConfig.setId("1");
		String openState = equityConfigService.getOpenState(equityConfig);//开关状态 1 开启，0关闭
		if (null != openState && "0".equals(openState)) {//交易已关闭
			addMessage(redirectAttributes, "交易已关闭,无法出售!");
		} else {
			User user = UserUtils.getUser();
			equitySell.setTradingId(user.getId());
			equitySellService.save(equitySell);
			User sysUser = systemService.getUser(user.getId());
			UserUtils.getUser().setHappyfood(sysUser.getHappyfood());
			UserUtils.getUser().setHappyfoodsum(sysUser.getHappyfoodsum());
			addMessage(redirectAttributes, "保存欢乐豆卖成功");
		}
		return "redirect:" + Global.getAdminPath() + "/equity/equitySell?repage";
	}

	@RequiresPermissions("equity:equitySell:edit")
	@RequestMapping(value = "delete")
	public String delete(EquitySell equitySell, RedirectAttributes redirectAttributes) {
		equitySellService.delete(equitySell);
		addMessage(redirectAttributes, "删除欢乐豆卖成功");
		return "redirect:" + Global.getAdminPath() + "/equity/equitySell/list?repage";
	}

	@ResponseBody
	@RequiresPermissions("equity:equitySell:view")
	@RequestMapping(value = "getMoney")
	public EquitySell getMoney(EquitySell equitySell, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		User u = fclWkJhService.getUserInfo(user.getId());
		if (u.getGwf().equals("0")) {
			addMessage(redirectAttributes, "用户购物分为零，无法购买");
		}
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setGwf(u.getGwf());
		EquitySell es = equitySellService.getMoney(equitySell);
		es.setUser(newUser);
		return es;
	}

	@RequiresPermissions("equity:equitySell:edit")
	@RequestMapping(value = "repealSell")
	public String repealSell(EquitySell equitySell, RedirectAttributes redirectAttributes) {
		List<EquitySell> list = equitySellService.getSellList(equitySell);
		if (list.size() > 0) {
			addMessage(redirectAttributes, "该欢乐豆已有人购买不能撤销");
		} else {
			equitySellService.repealSell(equitySell);
			addMessage(redirectAttributes, "撤销欢乐豆成功");
		}

		return "redirect:" + Global.getAdminPath() + "/equity/equitySell/list?repage";
	}

	@ResponseBody
	@RequiresPermissions("equity:equitySell:view")
	@RequestMapping(value = "getMinTradingMoney")
	public String getMinTradingMoney(EquitySell equitySell, RedirectAttributes redirectAttributes) {
		equitySell.setRepealFlag("0");// 未撤销
		return equitySellService.getMinTradingMoney(equitySell);
	}

}