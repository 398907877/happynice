/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.etd.web;

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
import com.thinkgem.jeesite.modules.etd.entity.EquityTrading;
import com.thinkgem.jeesite.modules.etd.service.EquityTradingService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 股权交易Controller
 * @author 陈世杰
 * @version 2017-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/etd/equityTrading")
public class EquityTradingController extends BaseController {

	@Autowired
	private EquityTradingService equityTradingService;
	
	@ModelAttribute
	public EquityTrading get(@RequestParam(required=false) String id) {
		EquityTrading entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = equityTradingService.get(id);
		}
		if (entity == null){
			entity = new EquityTrading();
		}
		return entity;
	}
	/**
	 * 股权卖列表
	 * @param equityTrading
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("etd:equityTrading:view")
	@RequestMapping(value = {"list", ""})
	public String list(EquityTrading equityTrading, HttpServletRequest request, HttpServletResponse response, Model model) {
		equityTrading.setTradingType("1");
		Page<EquityTrading> page = equityTradingService.findPage(new Page<EquityTrading>(request, response), equityTrading); 
		model.addAttribute("page", page);
		return "modules/etd/equityTradingList";
	}

	@RequiresPermissions("etd:equityTrading:view")
	@RequestMapping(value = "form")
	public String form(EquityTrading equityTrading, Model model) {
		model.addAttribute("equityTrading", equityTrading);
		return "modules/etd/equityTradingForm";
	}
	/**
	 * 股权买列表
	 * @param equityTrading
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("etd:equityTrading:view")
	@RequestMapping(value = {"equityBuylist", ""})
	public String equityBuylist(EquityTrading equityTrading, HttpServletRequest request, HttpServletResponse response, Model model) {
		equityTrading.setTradingType("2");
		Page<EquityTrading> page = equityTradingService.findPage(new Page<EquityTrading>(request, response), equityTrading); 
		model.addAttribute("page", page);
		return "modules/etd/equityBuyList";
	}

	@RequiresPermissions("etd:equityTrading:view")
	@RequestMapping(value = "equityBuyform")
	public String equityBuyform(EquityTrading equityTrading, Model model) {
		model.addAttribute("equityTrading", equityTrading);
		return "modules/etd/equityBuyForm";
	}

	@RequiresPermissions("etd:equityTrading:edit")
	@RequestMapping(value = "save")
	public String save(EquityTrading equityTrading, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, equityTrading)){
			return form(equityTrading, model);
		}
		User user = UserUtils.getUser();
		equityTrading.setTradingId(user.getId());
		//equityTrading.setTradingType("1");
		equityTradingService.save(equityTrading);
		addMessage(redirectAttributes, "保存股权买卖成功");
		list(equityTrading, request,response, model);
		return "modules/etd/equityTradingList";
	}
	
	@RequiresPermissions("etd:equityTrading:edit")
	@RequestMapping(value = "equityBuySave")
	public String equityBuySave(EquityTrading equityTrading, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, equityTrading)){
			return form(equityTrading, model);
		}
		User user = UserUtils.getUser();
		equityTrading.setTradingId(user.getId());
		equityTrading.setTradingType("2");
		equityTradingService.save(equityTrading);
		addMessage(redirectAttributes, "保存股权购买成功");
		equityBuylist(equityTrading, request, response, model);
		return "modules/etd/equityTradingList";
	}
	
	@RequiresPermissions("etd:equityTrading:edit")
	@RequestMapping(value = "delete")
	public String delete(EquityTrading equityTrading, RedirectAttributes redirectAttributes) {
		equityTradingService.delete(equityTrading);
		addMessage(redirectAttributes, "删除股权买卖成功");
		return "redirect:"+Global.getAdminPath()+"/etd/equityTrading/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("etd:equityTrading:view")
	@RequestMapping(value = "getMoney")
	public List<Map<String, Object>> getMoney(EquityTrading equityTrading) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<EquityTrading> list = equityTradingService.getMoney(equityTrading);
		for (int i=0; i<list.size(); i++){
			EquityTrading e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("tradingMoney", e.getTradingMoney());
			mapList.add(map);
		}
		return mapList;
	}
}