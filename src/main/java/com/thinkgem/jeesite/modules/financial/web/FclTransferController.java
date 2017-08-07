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
import com.thinkgem.jeesite.modules.financial.entity.FclTransfer;
import com.thinkgem.jeesite.modules.financial.service.FclTransferService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 激活分转账Controller
 * @author 陈世杰
 * @version 2017-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/financial/fclTransfer")
public class FclTransferController extends BaseController {

	@Autowired
	private FclTransferService fclTransferService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public FclTransfer get(@RequestParam(required=false) String id) {
		FclTransfer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fclTransferService.get(id);
		}
		if (entity == null){
			entity = new FclTransfer();
		}
		return entity;
	}
	
	@RequiresPermissions("financial:fclTransfer:view")
	@RequestMapping(value = {"list", ""})
	public String list(FclTransfer fclTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		fclTransfer.setOutnumberId(user.getId());
		Page<FclTransfer> page = fclTransferService.findPage(new Page<FclTransfer>(request, response), fclTransfer); 
		model.addAttribute("page", page);
		return "modules/financial/fclTransferList";
	}

	@RequiresPermissions("financial:fclTransfer:view")
	@RequestMapping(value = "form")
	public String form(FclTransfer fclTransfer, Model model) {
		model.addAttribute("fclTransfer", fclTransfer);
		return "modules/financial/fclTransferForm";
	}

	@RequiresPermissions("financial:fclTransfer:edit")
	@RequestMapping(value = "save")
	public String save(FclTransfer fclTransfer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fclTransfer)){
			return form(fclTransfer, model);
		}
		User user = UserUtils.getUser();
		fclTransfer.setOutnumberId(user.getId());
		//根据转入编号查询用户
		User intoUser = systemService.getUserByLoginName(fclTransfer.getIntonumberId());
		fclTransfer.setIntonumberId(intoUser.getId());
		//保存转账记录
		fclTransferService.save(fclTransfer);
		if(fclTransfer.getTransType().equals("1")){
			//更新转入编号用户激活分
			fclTransferService.updateUserIntoJh(fclTransfer);
			//更新转出编号用户激活分
			fclTransferService.updateUserOutJh(fclTransfer);
		}else if(fclTransfer.getTransType().equals("2")){
			//更新转入编号用户权证分
			fclTransferService.updateUserIntoQz(fclTransfer);
			//更新转出编号用户权证分
			fclTransferService.updateUserOutQz(fclTransfer);
		}
		
		addMessage(redirectAttributes, "保存积分转账成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclTransfer/?repage";
	}
	
	@RequiresPermissions("financial:fclTransfer:edit")
	@RequestMapping(value = "delete")
	public String delete(FclTransfer fclTransfer, RedirectAttributes redirectAttributes) {
		fclTransferService.delete(fclTransfer);
		addMessage(redirectAttributes, "删除激活分转账成功");
		return "redirect:"+Global.getAdminPath()+"/financial/fclTransfer/?repage";
	}

	/**
	 * 验证转入编号是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("financial:fclTransfer:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String intonumberId) {
		if (intonumberId !=null && intonumberId.equals(oldLoginName)) {
			return "false";
		} else if (intonumberId !=null && systemService.getUserByLoginName(intonumberId) == null) {
			return "false";
		}
		return "true";
	}
	
	@RequiresPermissions("financial:fclTransfer:edit")
	@RequestMapping(value = "getUserInfo")
	@ResponseBody
	public User getUserInfo() {
		User user = UserUtils.getUser();
		return fclTransferService.getUserInfo(user.getId());
	}
}