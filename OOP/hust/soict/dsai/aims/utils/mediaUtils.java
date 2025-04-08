package hust.soict.dsai.aims.utils;

import hust.soict.dsai.aims.media.Media;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class mediaUtils {

/* Comparing functions always return the media that has greater value.
   If 2 medias have the same value, return the first media */
    public static Media compareByCost (Media media1, Media media2){
        if (media1.getCost() > media2.getCost()) {
            return media1;
        } else if (media1.getCost() < media2.getCost()){
            return media2;
        } else {
            return media1;
        }
    }

    public static Media compareByTitle (Media media1, Media media2){
        int diff = media1.getTitle().compareToIgnoreCase(media2.getTitle());
        if (diff < 0){
            return media2;
        } else if (diff > 0){
            return media1;
        } else {
            return media1;
        }
    }

/*  Sorting function all sorting in ascending order by default,
    but if desc is true then it sort in descending order
    ------------------------------------------------------------
    In case they have the same title (or cost) the medias having the higher cost (or in alphabetical order) will be display first.*/
    public static ArrayList<Media> sortByCost (ArrayList<Media> mediaList){
        for (int i = 0; i < mediaList.size() - 1; i++){
            for (int j = i + 1; j < mediaList.size(); j++){
                if (mediaList.get(j).getCost() < mediaList.get(i).getCost()){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if  (mediaList.get(j).getCost() == mediaList.get(i).getCost()){
                    if (compareByTitle(mediaList.get(j), mediaList.get(i)).getTitle().equalsIgnoreCase(mediaList.get(j).getTitle())){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ArrayList<Media> sortByCost (ArrayList<Media> mediaList, boolean desc) {
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i + 1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getCost() > mediaList.get(i).getCost()) {
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getCost() == mediaList.get(i).getCost()) {
                    if (compareByTitle(mediaList.get(j), mediaList.get(i)).getTitle().equalsIgnoreCase(mediaList.get(j).getTitle())) {
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ArrayList<Media> sortByTitle (ArrayList<Media> mediaList){
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i+1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) < 0){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) == 0) {
                    if (compareByCost(mediaList.get(j), mediaList.get(i)).getCost() > mediaList.get(i).getCost()){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ArrayList<Media> sortByTitle (ArrayList<Media> mediaList, boolean desc){
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i+1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) > 0){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) == 0) {
                    if (compareByCost(mediaList.get(j), mediaList.get(i)).getCost() > mediaList.get(i).getCost()){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ObservableList<Media> sortByCost (ObservableList<Media> mediaList){
        for (int i = 0; i < mediaList.size() - 1; i++){
            for (int j = i + 1; j < mediaList.size(); j++){
                if (mediaList.get(j).getCost() < mediaList.get(i).getCost()){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if  (mediaList.get(j).getCost() == mediaList.get(i).getCost()){
                    if (compareByTitle(mediaList.get(j), mediaList.get(i)).getTitle().equalsIgnoreCase(mediaList.get(j).getTitle())){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ObservableList<Media> sortByCost (ObservableList<Media> mediaList, boolean desc) {
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i + 1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getCost() > mediaList.get(i).getCost()) {
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getCost() == mediaList.get(i).getCost()) {
                    if (compareByTitle(mediaList.get(j), mediaList.get(i)).getTitle().equalsIgnoreCase(mediaList.get(j).getTitle())) {
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ObservableList<Media> sortByTitle (ObservableList<Media> mediaList){
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i+1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) < 0){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) == 0) {
                    if (compareByCost(mediaList.get(j), mediaList.get(i)).getCost() > mediaList.get(i).getCost()){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }

    public static ObservableList<Media> sortByTitle (ObservableList<Media> mediaList, boolean desc){
        for (int i = 0; i < mediaList.size() - 1; i++) {
            for (int j = i+1; j < mediaList.size(); j++) {
                if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) > 0){
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(i));
                    mediaList.set(i, temp);
                } else if (mediaList.get(j).getTitle().compareToIgnoreCase(mediaList.get(i).getTitle()) == 0) {
                    if (compareByCost(mediaList.get(j), mediaList.get(i)).getCost() > mediaList.get(i).getCost()){
                        Media temp = mediaList.get(j);
                        mediaList.set(j, mediaList.get(i));
                        mediaList.set(i, temp);
                    }
                }
            }
        }
        return mediaList;
    }
}