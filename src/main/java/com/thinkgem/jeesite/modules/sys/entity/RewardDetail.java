package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Administrator on 2017/5/28.
 */
public class RewardDetail extends DataEntity<RewardDetail> {
    private static final long serialVersionUID = 1L;

    private User user;
    private String tj;
    private String ld;
    private String dp;
    private String wkf;
    private String gwf;
    private String yxf;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Length(min=1, max=100)
    public String getTj() {
        return tj;
    }

    public void setTj(String tj) {
        this.tj = tj;
    }
    @Length(min=1, max=100)
    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }
    @Length(min=1, max=100)
    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
    @Length(min=1, max=100)
    public String getWkf() {
        return wkf;
    }

    public void setWkf(String wkf) {
        this.wkf = wkf;
    }
    @Length(min=1, max=100)
    public String getGwf() {
        return gwf;
    }

    public void setGwf(String gwf) {
        this.gwf = gwf;
    }
    @Length(min=1, max=100)
    public String getYxf() {
        return yxf;
    }

    public void setYxf(String yxf) {
        this.yxf = yxf;
    }
}
