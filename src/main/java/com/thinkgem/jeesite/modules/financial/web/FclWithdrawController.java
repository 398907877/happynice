/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.financial.entity.FclWithdraw;
import com.thinkgem.jeesite.modules.financial.service.FclWithdrawService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 挖矿分提现Controller
 * @author 陈世杰
 * @version 2017-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/financial/fclWithdraw")
public class FclWithdrawController extends BaseController {

	@Autowired
	private FclWithdrawService fclWithdrawService;
	
	@ModelAttribute
	public FclWithdraw get(@RequestParam(required=false) String id) {
		FclWithdraw entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fclWithdrawService.get(id);
		}
		if (entity == null){
			entity = new FclWithdraw();
		}
		return entity;
	}
	
	@RequiresPermissions("financial:fclWithdraw:view")
	@RequestMapping(value = {"list", ""})
	public String list(FclWithdraw fclWithdraw, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		fclWithdraw.setOutnumberId(user.getId());
		Page<FclWithdraw> page = fclWithdrawService.findPage(new Page<FclWithdraw>(request, response), fclWithdraw); 
		model.addAttribute("page", page);
		return "modules/financial/fclWithdrawList";
	}

	@RequiresPermissions("financial:fclWithdraw:view")
	@RequestMapping(value = "form")
	public String form(FclWithdraw fclWithdraw, Model model) {
		model.addAttribute("fclWithdraw", fclWithdraw);
		return "modules/financial/fclWithdrawForm";
	}

	@RequiresPermissions("financial:fclWithdraw:edit")
	@RequestMapping(value = "save")
	public String save(FclWithdraw fclWithdraw, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fclWithdraw)){
			return form(fclWithdraw, model);
		}
		User user = UserUtils.getUser();
		fclWithdraw.setOutnumberId(user.getId());
		fclWithdrawService.save(fclWithdraw);
		//更新提现用户挖矿分
		fclWithdrawService.updateUserWkf(fclWithdraw);
		addMessage(redirectAttributes, "保存挖矿分提现成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclWithdraw/?repage";
	}
	
	@RequiresPermissions("financial:fclWithdraw:edit")
	@RequestMapping(value = "delete")
	public String delete(FclWithdraw fclWithdraw, RedirectAttributes redirectAttributes) {
		fclWithdrawService.delete(fclWithdraw);
		addMessage(redirectAttributes, "删除提现成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclWithdraw/?repage";
	}

	@RequiresPermissions("financial:fclWithdraw:edit")
	@RequestMapping(value = "getUserInfo")
	@ResponseBody
	public User getUserInfo() {
		User user = UserUtils.getUser();
		return fclWithdrawService.getUserInfo(user.getId());
	}
}