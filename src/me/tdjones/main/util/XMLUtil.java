package me.tdjones.main.util;

import me.tdjones.main.model.Episode;
import me.tdjones.main.model.Feed;
import me.tdjones.main.model.RSS;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class XMLUtil {
    public static Feed parseRSS(URL feedURL){
        try {
            JAXBContext jc = JAXBContext.newInstance(RSS.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            RSS rss = (RSS) unmarshaller.unmarshal(feedURL.openStream());
            validateRss(rss);
            return rss.getFeed();
        }catch (JAXBException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static void validateRss(RSS rss){
        validateFeed(rss.getFeed());
        for (Episode episode : rss.getFeed().getEpisodeList()){
            episode.setFeed(rss.getFeed());
            validateEpisode(episode);
        }
    }

    private static void validateFeed(Feed feed){

    }

    private static void validateEpisode(Episode episode){
        if (episode.getThumbnail().getUrl() == null){
            episode.setThumbnail(episode.getFeed().getThumbnail());
        }
    }
}
