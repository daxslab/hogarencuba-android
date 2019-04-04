package com.f4adaxs.apps.hogarencuba.models;

/**
 * Created by Edney on 19/07/2018.
 */

public class PublicacionModel {

    private String code;

    private String title;

    private String bedroomsCount;

    private String bathroomsCount;

    private String garageCount;

    private String areaCount;

    private String metadata;

    private String description;

    private String location;

    private String price;

    private String ranking;

    private String created_at;

    private String firstImageUrl;

    private String type;

    public String titulo;


    public PublicacionModel(String code, String title, String bedroomsCount, String bathroomsCount, String garageCount, String areaCount, String metadata, String description, String location, String price, String ranking, String created_at, String firstImageUrl, String type) {
        this.code = code;
        this.title = title;
        this.bedroomsCount = bedroomsCount;
        this.bathroomsCount = bathroomsCount;
        this.garageCount = garageCount;
        this.areaCount = areaCount;
        this.metadata = metadata;
        this.description = description;
        this.location = location;
        this.price = price;
        this.ranking = ranking;
        this.created_at = created_at;
        this.firstImageUrl = firstImageUrl;
        this.type = type;
        this.titulo = code + ": " + title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBedroomsCount() {
        return bedroomsCount;
    }

    public void setBedroomsCount(String bedroomsCount) {
        this.bedroomsCount = bedroomsCount;
    }

    public String getBathroomsCount() {
        return bathroomsCount;
    }

    public void setBathroomsCount(String bathroomsCount) {
        this.bathroomsCount = bathroomsCount;
    }

    public String getGarageCount() {
        return garageCount;
    }

    public void setGarageCount(String garageCount) {
        this.garageCount = garageCount;
    }

    public String getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(String areaCount) {
        this.areaCount = areaCount;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

