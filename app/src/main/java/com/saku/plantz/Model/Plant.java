package com.saku.plantz.Model;

public class Plant {

    private String add_Id;
    private String plantName;
    private String sciName;
    private String family;
    private String genus;
    private String height;
    private String spread;
    private String flow_period;
    private String plantImageUrl;

    public Plant(String add_Id, String plantName, String sciName, String family, String genus, String height, String spread, String flow_period, String plantImageUrl) {
        this.add_Id = add_Id;
        this.plantName = plantName;
        this.sciName = sciName;
        this.family = family;
        this.genus = genus;
        this.height = height;
        this.spread = spread;
        this.flow_period = flow_period;
        this.plantImageUrl = plantImageUrl;
    }

    public Plant() {
    }

    public String getAdd_Id() {
        return add_Id;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getSciName() {
        return sciName;
    }

    public String getFamily() {
        return family;
    }

    public String getGenus() {
        return genus;
    }

    public String getHeight() {
        return height;
    }

    public String getSpread() {
        return spread;
    }

    public String getFlow_period() {
        return flow_period;
    }

    public String getPlantImageUrl() {
        return plantImageUrl;
    }

    public void setAdd_Id(String add_Id) {
        this.add_Id = add_Id;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public void setFlow_period(String flow_period) {
        this.flow_period = flow_period;
    }

    public void setPlantImageUrl(String plantImageUrl) {
        this.plantImageUrl = plantImageUrl;
    }
}
