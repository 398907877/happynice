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
import com.thinkgem.jeesite.modules.equity.entity.EquityGency;
import com.thinkgem.jeesite.modules.equity.service.EquityGencyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 欢乐豆提现应急通道Controller
 * @author 陈世杰
 * @version 2017-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/equity/equityGency")
public class EquityGencyController extends BaseController {

	@Autowired
	private EquityGencyService equityGencyService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public EquityGency get(@RequestParam(required=false) String id) {
		EquityGency entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = equityGencyService.get(id);
		}
		if (entity == null){
			entity = new EquityGency();
		}
		return entity;
	}
	
	@RequiresPermissions("equity:equityGency:view")
	@RequestMapping(value = {"list", ""})
	public String list(EquityGency equityGency, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		equityGency.setOutnumberId(user.getId());
		Page<EquityGency> page = equityGencyService.findPage(new Page<EquityGency>(request, response), equityGency); 
		model.addAttribute("page", page);
		return "modules/equity/equityGencyList";
	}

	@RequiresPermissions("equity:equityGency:view")
	@RequestMapping(value = "form")
	public String form(EquityGency equityGency, Model model) {
		model.addAttribute("equityGency", equityGency);
		return "modules/equity/equityGencyForm";
	}

	@RequiresPermissions("equity:equityGency:edit")
	@RequestMapping(value = "save")
	public String save(EquityGency equityGency, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, equityGency)){
			return form(equityGency, model);
		}
		User user = UserUtils.getUser();
		equityGency.setOutnumberId(user.getId());
		equityGencyService.save(equityGency);
		equityGencyService.updateUserInfo(equityGency);
		User sysUser = systemService.getUser(user.getId());
		UserUtils.getUser().setHappyfood(sysUser.getHappyfood());
		UserUtils.getUser().setHappyfoodsum(sysUser.getHappyfoodsum());
		addMessage(redirectAttributes, "保存欢乐豆提现成功");
		return "redirect:"+Global.getAdminPath()+"/equity/equityGency/?repage";
	}
	
	@RequiresPermissions("equity:equityGency:edit")
	@RequestMapping(value = "delete")
	public String delete(EquityGency equityGency, RedirectAttributes redirectAttributes) {
		equityGencyService.delete(equityGency);
		addMessage(redirectAttributes, "删除欢乐豆提现成功");
		return "redirect:"+Global.getAdminPath()+"/equity/equityGency/?repage";
	}

}