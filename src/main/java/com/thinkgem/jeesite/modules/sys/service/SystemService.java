/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.dao.LpDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RewardDetailDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Lp;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.RewardDetail;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserTreeNode;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private LpDao lpDao;
	@Autowired
	private RewardDetailDao rewardDetailDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;

	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private IdentityService identityService;

	// -- User Service --//

	/**
	 * 获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}

	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}

	/**
	 * 分页查询奖励明细
	 * 
	 * @param page
	 * @param rewardDetail
	 * @return
	 */
	public Page<RewardDetail> findRewardDetail(Page<RewardDetail> page, RewardDetail rewardDetail) {
		// 设置分页参数
		rewardDetail.setPage(page);
		// 执行分页查询
		page.setList(rewardDetailDao.findList(rewardDetail));
		return page;
	}

	/**
	 * 分页查询量碰表
	 * 
	 * @param page
	 * @param lp
	 * @return
	 */
	public Page<Lp> findLpList(Page<Lp> page, Lp lp) {
		// 设置分页参数
		lp.setPage(page);
		// 执行分页查询
		page.setList(lpDao.findList(lp));
		return page;
	}

	/**
	 * 无分页查询人员列表
	 * 
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_
				+ officeId);
		if (list == null) {
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
		} else {
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null) {
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_
						+ oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())) {
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userDao.insertUserRole(user);
			} else {
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
			// // 清除权限缓存
			// systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	@Transactional(readOnly = false)
	public void saveRegistUser(User user) {
		if (StringUtils.isBlank(user.getId())) {
			String addedGwf = "0.48";// 购物分
			if (user.getLevel().equals("100")) {// 一级
				addedGwf = "0.48";
			} else if (user.getLevel().equals("500")) {// 二级
				addedGwf = "0.50";
			} else if (user.getLevel().equals("1000")) {// 三级
				addedGwf = "0.52";
			} else if (user.getLevel().equals("2000")) {// 四级
				addedGwf = "0.54";
			} else if (user.getLevel().equals("5000")) {// 五级
				addedGwf = "0.56";
			}
			user.setGwf(new BigDecimal(user.getLevel()).multiply(new BigDecimal(addedGwf)).toString());
			user.setZyy(new BigDecimal(user.getLevel()).multiply(new BigDecimal("0.1")).toString());
			user.preInsert();
			user.setJhf("0");
			user.setQzf("0");
			user.setYxf("0");
			user.setWkf("0");
			user.setHappyfood("0");
			user.setHappyfoodsum("0");
			userDao.insert(user);
			String reReward = "0.08";
			String reLdReward = "0.05";
			if (Integer.valueOf(user.getLevel()) > 500) {// 相应级别才有对碰
				reReward = "0.1";
			}
			// 推荐奖励
			countReward(UserUtils.getUser(), reReward, user.getLevel(), "tj");
			dealLp(user);// 处理量碰表
			countDp(user);
		} else {
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null) {
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_
						+ oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())) {
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userDao.insertUserRole(user);
			} else {
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
			// // 清除权限缓存
			// systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	/**
	 * 计算对碰
	 * 
	 * @param user
	 */
	private void countDp(User user) {
		String[] linkPersons = user.getLinkPersons().split(",");
		for (int i = 1; i < linkPersons.length; i++) {
			User dpUser = UserUtils.getByLoginName(linkPersons[i]);
			Lp lp = new Lp(dpUser);
			List<Lp> lpList = lpDao.findList(lp);
			for (Lp l1 : lpList) {
				Integer p = 0;
				Integer lwp = Integer.valueOf(l1.getLtotal()) - Integer.valueOf(l1.getLp());
				if (lwp == 0) {
					continue;
				}
				if (Integer.valueOf(l1.getLp()) == 30000) {
					continue;
				}
				for (Lp l2 : lpList) {
					Integer rwp = Integer.valueOf(l2.getRtotal()) - Integer.valueOf(l2.getRp());
					if (rwp == 0) {
						continue;
					}
					if (Integer.valueOf(l2.getLp()) == 30000) {
						continue;
					}
					// 产生碰撞
					p = lwp - rwp;
					if (p <= 0) {
						p = lwp;
					} else {
						p = rwp;
					}
					Integer newRp = Integer.valueOf(l2.getRp()) + p;
					l2.setRp(newRp.toString());
					l2.preUpdate();
					lpDao.update(l2);
					if (p == lwp) {
						break;
					} else {
						continue;
					}
				}
				Integer newLp = Integer.valueOf(l1.getLp()) + p;
				l1.setLp(newLp.toString());
				l1.preUpdate();
				lpDao.update(l1);
				String[] repersons = dpUser.getRePersons().split(",");
				if (p > 0) {
					countReward(dpUser, "0.05", p.toString(), "dp");// TODO
																	// 处理级别配置
					int countLd = 0;
					while (countLd <= 5 && countLd < repersons.length - 1) {
						System.out.println(countLd);
						User ldUser = UserUtils.getByLoginName(repersons[repersons.length - 1 - countLd]);
						// TODO 处理级别配置
						String ldPoint = new BigDecimal(p).multiply(new BigDecimal("0.05")).toString();
						countReward(ldUser, "0.05", ldPoint, "ld");
						countLd++;
					}
				}

				break;
			}
		}
	}

	/**
	 * 处理量碰表 单数 新增或者修改
	 * 
	 * @param user
	 */
	private void dealLp(User user) {
		Integer level = Integer.valueOf(user.getLinkLevel());
		for (int i = level - 1; i > 0; i--) {
			String[] linkPersons = user.getLinkPersons().split(",");
			Integer lpLevel = level - i;
			Lp lp = new Lp(UserUtils.getByLoginName(linkPersons[i]), "0", "0", "0", "0", lpLevel.toString());
			if (lpDao.get(lp) != null) {
				lp = lpDao.get(lp);
				if (i == level - 1) {
					Integer newRtotal = Integer.valueOf(lp.getRtotal()) + Integer.valueOf(user.getLevel());
					lp.setRtotal(newRtotal.toString());
				} else {
					User sideUser = UserUtils.getByLoginName(linkPersons[i + 1]);
					if ("0".equals(sideUser.getLinkSide())) {
						Integer newLtotal = Integer.valueOf(lp.getLtotal()) + Integer.valueOf(user.getLevel());
						lp.setLtotal(newLtotal.toString());
					} else {
						Integer newRtotal = Integer.valueOf(lp.getRtotal()) + Integer.valueOf(user.getLevel());
						lp.setRtotal(newRtotal.toString());
					}
				}
				lp.preUpdate();
				lpDao.update(lp);
			} else {
				lp.setLtotal(user.getLevel());
				lp.preInsert();
				lpDao.insert(lp);
			}
		}
	}

	/**
	 *
	 * @param rewardUser
	 *            奖励对象
	 * @param percent
	 *            百分比
	 * @param point
	 *            分数
	 */
	private void countReward(User rewardUser, String percent, String point, String type) {
		BigDecimal rewardPoint = new BigDecimal(percent).multiply(new BigDecimal(point));
		BigDecimal wkf = rewardPoint.multiply(new BigDecimal("0.9"));
		BigDecimal yxf = rewardPoint.multiply(new BigDecimal("0.05"));
		BigDecimal gwf = rewardPoint.multiply(new BigDecimal("0.05"));
		rewardUser.setWkf(new BigDecimal(rewardUser.getWkf()).add(wkf).toString());
		rewardUser.setYxf(new BigDecimal(rewardUser.getYxf()).add(yxf).toString());
		rewardUser.setGwf(new BigDecimal(rewardUser.getGwf()).add(gwf).toString());

		RewardDetail rewardDetail = new RewardDetail();
		rewardDetail.setUser(rewardUser);
		rewardDetail.setGwf(gwf.toString());
		rewardDetail.setWkf(wkf.toString());
		rewardDetail.setYxf(yxf.toString());
		if ("ld".equals(type)) {
			rewardDetail.setLd(rewardPoint.toString());
		}
		if ("dp".equals(type)) {
			rewardDetail.setDp(rewardPoint.toString());
		}
		if ("tj".equals(type)) {
			rewardDetail.setTj(rewardPoint.toString());
		}
		rewardDetail.preInsert();
		rewardDetailDao.insert(rewardDetail);
		rewardUser.preUpdate();
		userDao.update(rewardUser);
	}

	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 同步到Activiti
		deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	/**
	 * 获得活动会话
	 * 
	 * @return
	 */
	public Collection<Session> getActiveSessions() {
		return sessionDao.getActiveSessions(false);
	}

	// -- Role Service --//

	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}

	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}

	public List<Role> findRole(Role role) {
		return roleDao.findList(role);
	}

	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}

	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())) {
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		} else {
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0) {
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0) {
			roleDao.insertRoleOffice(role);
		}
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles) {
			if (e.getId().equals(role.getId())) {
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null) {
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	// -- Menu Service --//

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {

		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())) {
			menu.preInsert();
			menuDao.insert(menu);
		} else {
			menu.preUpdate();
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 " + Global.getConfig("productName") + "  - Powered By http://wujiajun-team.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	// /////////////// Synchronized to the Activiti //////////////////

	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;

	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (isSynActivitiIndetity) {
			isSynActivitiIndetity = false;
			// 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0) {
				Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
				while (roles.hasNext()) {
					Role role = roles.next();
					saveActivitiGroup(role);
				}
			}
			// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0) {
				Iterator<User> users = userDao.findAllList(new User()).iterator();
				while (users.hasNext()) {
					saveActivitiUser(users.next());
				}
			}
		}
	}

	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String groupId = role.getEnname();

		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())) {
			identityService.deleteGroup(role.getOldEnname());
		}

		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);

		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery()
				.memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList) {
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList) {
			String userId = e.getLoginName();// ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
					.singleResult();
			if (activitiUser == null) {
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (role != null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
				.singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);

		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
			String groupId = role.getEnname();
			// 如果该用户组不存在，则创建一个
			Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
			if (group == null) {
				group = identityService.newGroup(groupId);
				group.setName(role.getName());
				group.setType(role.getRoleType());
				identityService.saveGroup(group);
			}
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (user != null) {
			String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}

	// /////////////// Synchronized to the Activiti end //////////////////

	/**
	 * 获取用户树数据
	 * 
	 * @param
	 * @return
	 */
	public UserTreeNode getUserTreeData(String userId, String fromUserId) {
		UserTreeNode userTree = new UserTreeNode();
		User curUser = StringUtils.isEmpty(userId) ? UserUtils.getUser().getCurrentUser() : UserUtils
				.getByLoginName(userId);
		userTree.setName(curUser.getLoginName());
		userTree.setId(curUser.getLoginName());
		userTree.setParentId(curUser.getLinkperson());
		userTree.setSide(curUser.getLinkSide());
		userTree.setClassName(StringUtils.isEmpty(userId) ? "root-node" : "drill-up");
		userTree.setChildren(new ArrayList<UserTreeNode>());
		Lp curLp = lpDao.getCount(curUser);
		if (curLp == null) {
			curLp = new Lp();
			curLp.setLtotal("0");
			curLp.setRtotal("0");
		}
		userTree.setTitle(userTree.getName() + "-" + DictUtils.getDictLabel(curUser.getLevel(), "USER_LEVEL", "管理员")
				+ "<br >|" + curLp.getLtotal() + "|总|" + curLp.getRtotal() + "|");
		List<User> userChilds = userDao.getChildren(curUser.getLoginName(), curUser.getDbName());
		List<UserTreeNode> childs = new ArrayList<UserTreeNode>();
		for (User user : userChilds) {
			Lp newLp = lpDao.getCount(user);
			UserTreeNode newUserTree = new UserTreeNode();
			newUserTree.setName(user.getLoginName());
			if (newLp != null) {
				newUserTree.setTitle(user.getName() + "-" + user.getLevel() + "<br >|" + newLp.getLtotal() + "|总|"
						+ newLp.getRtotal() + "|");
			} else {
				newUserTree.setTitle(user.getName() + "-" + user.getLevel());
			}
			newUserTree.setParentId(user.getLinkperson());
			newUserTree.setId(user.getLoginName());
			newUserTree.setChildren(new ArrayList<UserTreeNode>());
			newUserTree.setSide(user.getLinkSide());
			childs.add(newUserTree);
			if (curUser.getLoginName().equals(user.getLinkperson())) {
				userTree.getChildren().add(newUserTree);
			}
		}
		Set<UserTreeNode> noChildNodes = new HashSet<UserTreeNode>();
		Set<UserTreeNode> oneChildNodes = new HashSet<UserTreeNode>();
		for (UserTreeNode node1 : childs) {
			int childCount = 0;
			for (UserTreeNode node2 : childs) {
				if (node2.getParentId() != null && node2.getParentId().equals(node1.getId())) {
					if (node1.getChildren() == null)
						node1.setChildren(new ArrayList<UserTreeNode>());
					node1.getChildren().add(node2);
					childCount++;
					// break;
				}
			}
			if (childCount == 0) {
				noChildNodes.add(node1);
			} else if (childCount == 1) {
				oneChildNodes.add(node1);
				node1.setClassName("drill-down");
			} else {
				node1.setClassName("drill-down");
			}
		}
		for (UserTreeNode noChildNode : noChildNodes) {
			noChildNode.getChildren().add(new UserTreeNode("点击注册", "点击注册", "", noChildNode.getId(), "null", "0"));
		}
		for (UserTreeNode oneChildNode : oneChildNodes) {
			oneChildNode.getChildren().add(new UserTreeNode("点击注册", "点击注册", "", oneChildNode.getId(), "null", "1"));
		}
		if (userTree.getChildren().size() == 1) {
			userTree.getChildren().add(new UserTreeNode("点击注册", "点击注册", "", userTree.getId(), "null", "1"));
		}
		if (userTree.getChildren().size() == 0) {
			userTree.getChildren().add(new UserTreeNode("点击注册", "点击注册", "", userTree.getId(), "null", "0"));
		}
		return userTree;
	}

	/**
	 * 获取用户树数据
	 * 
	 * @param
	 * @return
	 */
	public UserTreeNode getUserReTreeData(String userId) {
		UserTreeNode userTree = new UserTreeNode();
		User curUser = StringUtils.isEmpty(userId) ? UserUtils.getUser().getCurrentUser() : UserUtils
				.getByLoginName(userId);
		userTree.setId(curUser.getLoginName());
		userTree.setName(curUser.getLoginName());
		userTree.setParentId(curUser.getRePerson());
		userTree.setClassName(StringUtils.isEmpty(userId) ? "root-node" : "drill-up");
		userTree.setTitle(curUser.getName() + "-" + DictUtils.getDictLabel(curUser.getLevel(), "USER_LEVEL", "管理员"));
		userTree.setChildren(new ArrayList<UserTreeNode>());
		List<User> userChilds = userDao.getReChildren(curUser.getLoginName(), curUser.getDbName());
		List<UserTreeNode> childs = new ArrayList<UserTreeNode>();
		for (User user : userChilds) {
			UserTreeNode newUserTree = new UserTreeNode();
			newUserTree.setName(user.getLoginName());
			newUserTree.setTitle(user.getName() + "-" + user.getLevel());
			newUserTree.setParentId(user.getRePerson());
			newUserTree.setId(user.getLoginName());
			newUserTree.setChildren(new ArrayList<UserTreeNode>());
			childs.add(newUserTree);
			if (curUser.getLoginName().equals(user.getRePerson())) {
				userTree.getChildren().add(newUserTree);
			}
		}
		for (UserTreeNode node1 : childs) {
			for (UserTreeNode node2 : childs) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					if (node2.getChildren() == null)
						node2.setChildren(new ArrayList<UserTreeNode>());
					node2.getChildren().add(node1);
					node2.setClassName("drill-down");
					break;
				}
			}
		}
		return userTree;
	}

	public User getUserData(String id) {
		User user = userDao.getUserData(id);
		return user;
	}
}
