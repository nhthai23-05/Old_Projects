package hust.soict.dsai.aims.media;
import hust.soict.dsai.aims.MediaComparatorByCostTitle;
import hust.soict.dsai.aims.MediaComparatorByTitleCost;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class Media extends Object {
    public String title;
    public String category;
    public float cost;
    public int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public boolean equals(Media media) {
        if (this.title == media.getTitle()) {
            return true;
        } else {
            return false;
        }
    }
    public static final Comparator<Media> COMPARE_BY_TITLE_COST = new MediaComparatorByTitleCost();
    
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new MediaComparatorByCostTitle();
    public static void sortMediaByTitleCost(ArrayList<Media> mediaList) {
        Collections.sort(mediaList, Media.COMPARE_BY_TITLE_COST);
    }
    public static void sortMediaByCostTitle(ArrayList<Media> mediaList) {
        Collections.sort(mediaList, Media.COMPARE_BY_COST_TITLE);
    }
}