package com.thinkgem.jeesite.modules.config.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.config.entity.EquityConfig;
import com.thinkgem.jeesite.modules.config.service.EquityConfigService;

/**
 * 配置Controller
 * 
 * @author LFG
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/config/equityConfig")
public class EquityConfigController extends BaseController {

	@Autowired
	private EquityConfigService equityConfigService;

	@RequiresPermissions("config:equityConfig:view")
	@RequestMapping(value = "form")
	public String form(EquityConfig equityConfig, Model model) {
		equityConfig = equityConfigService.get("1");
		model.addAttribute("equityConfig", equityConfig);
		return "modules/config/equityConfigForm";
	}

	@RequiresPermissions("config:equityConfig:edit")
	@RequestMapping(value = "save")
	public String save(EquityConfig equityConfig, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/product/form?repage";
		}
		if (!beanValidator(model, equityConfig)) {
			return form(equityConfig, model);
		}
		equityConfigService.save(equityConfig);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/config/equityConfig/form?repage";
	}

	@ResponseBody
	@RequiresPermissions("config:equityConfig:edit")
	@RequestMapping(value = "resetAllSell")
	public String resetAllSell(Model model, RedirectAttributes redirectAttributes) {
		String result = "";
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/product/product/form?repage";
		}
		try {
			equityConfigService.resetAllSell();
			addMessage(redirectAttributes, "成功撤销挂卖");
			result = "成功撤销挂卖";
		} catch (Exception e) {
			e.printStackTrace();
			result = "成功撤销挂卖";
		}
		return result;
	}
}
