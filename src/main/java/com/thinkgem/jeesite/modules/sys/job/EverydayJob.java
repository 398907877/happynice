package com.thinkgem.jeesite.modules.sys.job;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("EverydayJob")
@Lazy(false)
public class EverydayJob {

    @Autowired
    private UserDao userDao;

    @Scheduled(cron = "1 1 0 1/1 * ?")//0/15 * * * * ?
    public void job() {//TODO 根据配置项加减
        System.out.println("每日扣除及奖励开始。。。");
        List<User> users = userDao.findAllList(new User());
        for (User user: users) {
            if ("100".equals(user.getLevel())){
                user .setZyy(new BigDecimal(user.getZyy()).subtract(new BigDecimal(0.5)).toString());
                user.setGwf(new BigDecimal(user.getGwf()).add(new BigDecimal("0.2")).toString());
            }else if("500".equals(user.getLevel())){
                user .setZyy(new BigDecimal(user.getZyy()).subtract(new BigDecimal(1.5)).toString());
                user.setGwf(new BigDecimal(user.getGwf()).add(new BigDecimal("1")).toString());
            }else if("1000".equals(user.getLevel())){
                user .setZyy(new BigDecimal(user.getZyy()).subtract(new BigDecimal(3)).toString());
                user.setGwf(new BigDecimal(user.getGwf()).add(new BigDecimal("2")).toString());
            }else if("2000".equals(user.getLevel())){
                user .setZyy(new BigDecimal(user.getZyy()).subtract(new BigDecimal(6)).toString());
                user.setGwf(new BigDecimal(user.getGwf()).add(new BigDecimal("4")).toString());
            }else{
                user .setZyy(new BigDecimal(user.getZyy()).subtract(new BigDecimal(12)).toString());
                user.setGwf(new BigDecimal(user.getGwf()).add(new BigDecimal("8")).toString());
            }
            userDao.update(user);
        }
        System.out.println("每日扣除及奖励结束。。。");
    }
}
