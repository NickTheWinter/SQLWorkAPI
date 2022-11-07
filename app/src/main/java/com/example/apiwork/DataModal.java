package com.example.apiwork;

public class DataModal {
    private String name;
    private String webSite;
    private String EncryptedIMG;
    public DataModal(String name,String webSite,String EncryptedIMG){
        this.name = name;
        this.webSite = webSite;
        this.EncryptedIMG = EncryptedIMG;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public String getEncryptedIMG(){
        return EncryptedIMG;
    }
    public void setEncryptedIMG(String EncryptedIMG){
        this.EncryptedIMG = EncryptedIMG;
    }
}
