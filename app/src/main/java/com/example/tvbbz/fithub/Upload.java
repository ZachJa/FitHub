package com.example.tvbbz.fithub;

public class Upload {
    private String mname;
    private String mimageurl;

    public Upload(){

    }

    public Upload(String name, String imageurl){

        mname = name;
        mimageurl = imageurl;

        if(name.trim().equals("")){
            name = "No name";
        }

    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMimageurl() {
        return mimageurl;
    }

    public void setMimageurl(String mimageurl) {
        this.mimageurl = mimageurl;
    }
}
