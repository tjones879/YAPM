package me.tdjones.main.model;

import me.tdjones.main.Main;

import javax.xml.bind.annotation.*;
import java.util.List;

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
    @XmlElement(namespace = Main.itunesNamespace, name = "image")
    private Thumbnail thumbnail;
    @XmlElement(name = "item")
    private List<Episode> episodeList;



    public Thumbnail getThumbnail(){
        return thumbnail;
    }

    public String getTitle(){
        return title;
    }

    public List<Episode> getEpisodeList(){
        return episodeList;
    }
}
