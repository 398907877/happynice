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
import com.thinkgem.jeesite.modules.financial.entity.FclWkJh;
import com.thinkgem.jeesite.modules.financial.service.FclWkJhService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 挖矿分转换Controller
 * @author 陈世杰
 * @version 2017-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/financial/fileup")
public class FileUpController extends BaseController {

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
	

	@RequiresPermissions("financial:fileup:view")
	@RequestMapping(value = "form")
	public String form() {
		return "modules/financial/fileUpForm";
	}

}