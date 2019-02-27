package com.example.tvbbz.fithub;

public class Upload {
    private String mname;
    private String mdesc;
    private String mimageurl;


    public Upload(){

    }

    public Upload(String name, String desc, String imageurl){

        mname = name;
        mdesc = desc;
        mimageurl = imageurl;

        if(name.trim().equals("")){
            name = "No name";
        }
        if(desc.trim().equals("")){
            desc = "No description";
        }

    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMdesc() {
        return mdesc;
    }

    public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

    public String getMimageurl() {
        return mimageurl;
    }

    public void setMimageurl(String mimageurl) {
        this.mimageurl = mimageurl;
    }
}
