package com.github.studeasy.logic.common.role;

import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Partner Role
 */
public class RolePartner extends Role{
    private String company;
    private List<Job> jobs;
    private List<Coupon> coupons;

    public RolePartner(String company) {
        super();
        this.company = company;
        this.jobs = new ArrayList<Job>();
        this.coupons = new ArrayList<Coupon>();
    }
}
