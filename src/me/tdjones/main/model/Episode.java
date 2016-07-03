package me.tdjones.main.model;

import me.tdjones.main.Main;
import me.tdjones.main.util.TimeUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.net.MalformedURLException;
import java.net.URL;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Episode {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "description")
    private String description;
    private Thumbnail thumbnail;
    private Feed feed;
    private int length;
    private boolean isExplicit;
    @XmlElement(name = "enclosure")
    private Enclosure enclosure;
    @XmlElement(name = "pubDate")
    private String pubDate;

    public Thumbnail getThumbnail(){
        return thumbnail;
    }

    public Feed getFeed(){
        return feed;
    }

    public String getTitle(){
        return title;
    }

    @XmlElement(namespace = Main.itunesNamespace, name = "duration")
    public void setLength(String length){
        if (length.contains(":")){
            this.length = TimeUtil.parseTime(length);
        } else {
            this.length = Integer.parseInt(length);
        }
    }

    public int getLength(){
        return this.length;
    }

    @XmlElement(namespace = Main.itunesNamespace, name = "explicit")
    public void setIsExplicit(String isExplicit){
        if (isExplicit == "yes"){
            this.isExplicit = true;
        }else if (isExplicit == "no" || isExplicit == "clean"){
            this.isExplicit = false;
        }
    }

    public boolean getIsExplicit(){
        return isExplicit;
    }

    public String getUrl(){
        return enclosure.getUrl();
    }

    public void setFeed(Feed feed){
        this.feed = feed;
    }

    public void setThumbnail(Thumbnail thumbnail){
        this.thumbnail = thumbnail;
    }

}
