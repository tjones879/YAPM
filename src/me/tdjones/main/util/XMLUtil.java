package me.tdjones.main.util;

import me.tdjones.main.model.Episode;
import me.tdjones.main.model.Feed;
import me.tdjones.main.model.RSS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
    public static List<URL> parseOPML(File OPMLFile){
        List<URL> feedURLList = new ArrayList<>();
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(OPMLFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("outline");

            for (int index = 0; index < nList.getLength(); index++){
                Element outline = (Element) nList.item(index);
                URL xmlUrl = new URL(outline.getAttribute("xmlUrl"));

                feedURLList.add(xmlUrl);
            }
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }

        return feedURLList;
    }

    public static Feed parseRSS(URL feedURL){
        try {
            JAXBContext jc = JAXBContext.newInstance(RSS.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            RSS rss = (RSS) unmarshaller.unmarshal(feedURL.openStream());
            validateRSS(rss);
            return rss.getFeed();
        }catch (JAXBException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static void validateRSS(RSS rss){
        validateFeed(rss.getFeed());
        for (Episode episode : rss.getFeed().getEpisodeList()){
            episode.setFeed(rss.getFeed());
            validateEpisode(episode);
        }
    }

    private static void validateFeed(Feed feed){

    }

    private static void validateEpisode(Episode episode){
        try {
            if (episode.getThumbnail().getUrl() == null) {
                episode.setThumbnail(episode.getFeed().getThumbnail());
            } else {

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
