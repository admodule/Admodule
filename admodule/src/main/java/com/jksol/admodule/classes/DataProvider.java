package com.jksol.admodule.classes;

public class DataProvider {
    private String farm;
    private String for1;
    private String url;
    private String serverid;
    private String id;
    private String secret;
    private String packagename;
    private String appname;
    private String descrip;
    private String bannerurl;
    private String filename;

    public void setfarm(String farm) {
        this.farm = farm;
    }

    public void setserverid(String serverid) {
        this.serverid = serverid;
    }

    public void setsecret(String secret) {
        this.secret = secret;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getfarm() {
        return farm;
    }

    public String getserverid() {
        return serverid;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getid() {
        return id;
    }

    public String getsecret() {
        return secret;
    }

    public void setpackagename(String packagename) {
        this.packagename = packagename;
    }

    public void setappname(String appname) {
        this.appname = appname;
    }

    public String geturl() {
        return url;
    }

    public String getappname() {
        return appname;
    }

    public String getpackagename() {
        return packagename;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getFor1() {
        return for1;
    }

    public void setFor1(String for1) {
        this.for1 = for1;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}