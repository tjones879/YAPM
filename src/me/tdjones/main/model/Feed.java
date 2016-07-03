package me.tdjones.main.model;

import com.sun.xml.internal.txw2.annotation.XmlNamespace;
import me.tdjones.main.Main;

import javax.xml.bind.annotation.*;
import java.net.URL;
import java.util.List;

/**
 * Created by Tyler on 6/30/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "link")
    private String link;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "language")
    private String language;
    @XmlElement(name = "pubDate")
    private String pubDate;
    @XmlElement(namespace = Main.itunesNamespace, name = "author")
    private String author;
    @XmlElement(namespace = Main.itunesNamespace, name = "category")
    private List<String> categories;
    @XmlAttribute(namespace = Main.itunesNamespace, name = "href")
    private String thumbnail;
    @XmlElement(name = "item")
    private List<Episode> episodeList;



    public String getThumbnail(){
        return thumbnail;
    }

    public String getTitle(){
        return title;
    }
}
