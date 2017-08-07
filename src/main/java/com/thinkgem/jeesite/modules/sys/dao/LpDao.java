package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Lp;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * Created by Administrator on 2017/5/28.
 */
@MyBatisDao
public interface LpDao extends CrudDao<Lp> {
    public Lp getCount(User user);
}
