package com.admodule.classes;

public class DataProvider {
    private String appname;
    private String packagename;
    private String imageurl;
    private String description;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof DataProvider) {
            sameSame = this.getPackagename().equals(((DataProvider) object).getPackagename());
        }

        return sameSame;
    }
}