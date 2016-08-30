package me.tdjones.main.model;

import me.tdjones.main.Main;

import me.tdjones.main.util.LengthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Episode {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "description")
    private String description;
    private Thumbnail thumbnail;
    private Feed feed;
    @XmlElement(namespace = Main.itunesNamespace, name = "duration", required = true)
    @XmlJavaTypeAdapter(LengthAdapter.class)
    private Integer length;
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

    public int getLength(){
        return this.length;
    }

    @XmlElement(namespace = Main.itunesNamespace, name = "explicit")
    public void setIsExplicit(String isExplicit){
        if (isExplicit.equalsIgnoreCase("yes")){
            this.isExplicit = true;
        }else if (isExplicit.equalsIgnoreCase("no") || isExplicit.equalsIgnoreCase("clean")){
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
