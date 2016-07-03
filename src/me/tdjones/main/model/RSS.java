package me.tdjones.main.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Tyler on 6/30/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rss")
public class RSS {
    @XmlElement(name = "channel")
    Feed feed;
}
