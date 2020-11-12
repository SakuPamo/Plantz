package com.saku.plantz.Model;

public class Plant {

    private String id;
    private String plantName;
    private String scientificName;
    private String family;
    private String genus;
    private String height;
    private String spred;
    private String floweringPeriod;
    private String plantImageUrl;

    public Plant(String id, String plantName, String scientificName, String family, String genus, String height, String spred, String floweringPeriod, String plantImageUrl) {
        this.id = id;
        this.plantName = plantName;
        this.scientificName = scientificName;
        this.family = family;
        this.genus = genus;
        this.height = height;
        this.spred = spred;
        this.floweringPeriod = floweringPeriod;
        this.plantImageUrl = plantImageUrl;
    }

    public Plant() {
    }

    public String getId() {
        return id;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getScientificName() {
        return scientificName;
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

    public String getSpred() {
        return spred;
    }

    public String getFloweringPeriod() {
        return floweringPeriod;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
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

    public void setSpred(String spred) {
        this.spred = spred;
    }

    public void setFloweringPeriod(String floweringPeriod) {
        this.floweringPeriod = floweringPeriod;
    }

    public String getPlantImageUrl() {
        return plantImageUrl;
    }

    public void setPlantImageUrl(String plantImageUrl) {
        this.plantImageUrl = plantImageUrl;
    }
}
