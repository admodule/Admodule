package com.admodule.classes;

/**
 * Created by pratik on 28-04-18.
 */

public class AdItem {


    /**
     * name : Whatsapp1
     * id : com.whatsapp
     * star : 4.6
     * rating : 32,508
     * icon : https://raw.githubusercontent.com/appsjk/apps/master/com.whatsapp.whatsapp/Whatsapp-icon.png
     * banner : https://raw.githubusercontent.com/appsjk/apps/master/com.whatsapp.whatsapp/whatsapp_banner.jpg
     * desc : WhatsApp Messenger is a FREE messaging app available for Android and other smartphones.
     */

    private String name;
    private String id;
    private String star;
    private String rating;
    private String icon;
    private String banner;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
