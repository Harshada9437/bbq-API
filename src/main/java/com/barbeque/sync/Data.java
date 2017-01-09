package com.barbeque.sync;

import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Data {
    private List<Cluster> clusters;
    private List<Regions> regions;
    private List<Company> companies;
    private List<Group> groups;
    private List<Outlet> outlets;

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public List<Regions> getRegions() {
        return regions;
    }

    public void setRegions(List<Regions> regions) {
        this.regions = regions;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Outlet> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<Outlet> outlets) {
        this.outlets = outlets;
    }

    @Override
    public String toString() {
        return "Data{" +
                "clusters=" + clusters +
                ", regions=" + regions +
                ", companies=" + companies +
                ", groups=" + groups +
                ", outlets=" + outlets +
                '}';
    }
}
