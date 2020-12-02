package com.github.studeasy.logic.common.role;

import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Partner Role
 */
public class RoleStudent extends Role{
    private String pseudo;
    private List<Service> services;
    private List<CommandOfService> servicesbuy;

    public RoleStudent(String pseudo) {
        super();
        this.pseudo = pseudo;
        this.services = new ArrayList<Service>();
        this.servicesbuy = new ArrayList<CommandOfService>();

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<CommandOfService> getServicesbuy() {
        return servicesbuy;
    }

    public void setServicesbuy(List<CommandOfService> servicesbuy) {
        this.servicesbuy = servicesbuy;
    }
}
