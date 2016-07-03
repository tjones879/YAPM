package me.tdjones.main.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Tyler on 7/3/2016.
 */
public class Enclosure {
    @XmlAttribute
    private String url;

    public String getUrl(){
        return url;
    }
}
