/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.financial.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.equity.entity.EquitySell;
import com.thinkgem.jeesite.modules.financial.entity.FclWkJh;
import com.thinkgem.jeesite.modules.financial.service.FclWkJhService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 挖矿分转换Controller
 * @author 陈世杰
 * @version 2017-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/financial/fclWkJh")
public class FclWkJhController extends BaseController {

	@Autowired
	private FclWkJhService fclWkJhService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public FclWkJh get(@RequestParam(required=false) String id) {
		FclWkJh entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fclWkJhService.get(id);
		}
		if (entity == null){
			entity = new FclWkJh();
		}
		return entity;
	}
	
	@RequiresPermissions("financial:fclWkJh:view")
	@RequestMapping(value = {"list", ""})
	public String list(FclWkJh fclWkJh, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		fclWkJh.setOutnumberId(user.getId());
		Page<FclWkJh> page = fclWkJhService.findPage(new Page<FclWkJh>(request, response), fclWkJh); 
		model.addAttribute("page", page);
		return "modules/financial/fclWkJhList";
	}

	@RequiresPermissions("financial:fclWkJh:view")
	@RequestMapping(value = "form")
	public String form(FclWkJh fclWkJh, Model model) {
		model.addAttribute("fclWkJh", fclWkJh);
		return "modules/financial/fclWkJhForm";
	}

	@RequiresPermissions("financial:fclWkJh:edit")
	@RequestMapping(value = "save")
	public String save(FclWkJh fclWkJh, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		fclWkJh.setOutnumberId(user.getId());
		fclWkJh.setIntonumberId(user.getId());
		if (!beanValidator(model, fclWkJh)){
			return form(fclWkJh, model);
		}
		fclWkJhService.save(fclWkJh);
		if(fclWkJh.getChangetype().equals("1")){
			fclWkJhService.updateUserJh(fclWkJh);
		}else if(fclWkJh.getChangetype().equals("2")){
			fclWkJhService.updateUserZyy(fclWkJh);
		}
		
		addMessage(redirectAttributes, "保存积分转换成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclWkJh/?repage";
	}
	
	@RequiresPermissions("financial:fclWkJh:edit")
	@RequestMapping(value = "delete")
	public String delete(FclWkJh fclWkJh, RedirectAttributes redirectAttributes) {
		fclWkJhService.delete(fclWkJh);
		addMessage(redirectAttributes, "删除积分转换成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclWkJh/?repage";
	}

	@RequiresPermissions("financial:fclWkJh:edit")
	@RequestMapping(value = "getUserInfo")
	@ResponseBody
	public User getUserInfo() {
		User user = UserUtils.getUser();
		return fclWkJhService.getUserInfo(user.getId());
	}
	
}