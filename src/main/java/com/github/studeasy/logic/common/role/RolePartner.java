package com.github.studeasy.logic.common.role;

import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Partner Role
 */
public class RolePartner extends Role{

    /**
     * Company name
     */
    private String company;

    /**
     * List of jobs provided by the company
     */
    private List<Job> jobs;

    /**
     * List of coupons provided by the company
     */
    private List<Coupon> coupons;

    public RolePartner(String company) {
        super();
        this.company = company;
        this.jobs = new ArrayList<Job>();
        this.coupons = new ArrayList<Coupon>();
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return this.getCompany();
    }
}