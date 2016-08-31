package me.tdjones.main.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Thumbnail {
    private String url;

    @XmlAttribute(name = "href")
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
