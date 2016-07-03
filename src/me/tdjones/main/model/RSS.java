package me.tdjones.main.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rss")
public class RSS {
    @XmlElement(name = "channel")
    Feed feed;

    public Feed getFeed(){
        return feed;
    }
}
