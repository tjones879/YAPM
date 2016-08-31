package me.tdjones.main.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Enclosure {
    @XmlAttribute
    private String url;

    public String getUrl(){
        return url;
    }
}
