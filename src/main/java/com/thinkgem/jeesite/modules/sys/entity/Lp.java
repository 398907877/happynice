package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Administrator on 2017/6/8.
 */
public class Lp extends DataEntity<Lp> {
    private static final long serialVersionUID = 1L;

    private User user;
    private String lp;
    private String rp;
    private String ltotal;
    private String rtotal;
    private String level;

    public Lp(){
        super();
    }
    public Lp(User user){
        super();
        this.user = user;
    }
    public Lp(User user,String lp,String rp, String ltotal,String rtotal,String level){
        super();
        this.user = user;
        this.lp = lp;
        this.rp = rp;
        this.ltotal = ltotal;
        this.rtotal = rtotal;
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min=1, max=100)
    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    @Length(min=1, max=100)
    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    @Length(min=1, max=100)
    public String getLtotal() {
        return ltotal;
    }

    public void setLtotal(String ltotal) {
        this.ltotal = ltotal;
    }

    @Length(min=1, max=100)
    public String getRtotal() {
        return rtotal;
    }

    public void setRtotal(String rtotal) {
        this.rtotal = rtotal;
    }

    @Length(min=1, max=100)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
