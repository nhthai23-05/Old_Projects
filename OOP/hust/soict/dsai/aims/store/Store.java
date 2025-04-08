package hust.soict.dsai.aims.store;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;

import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
public class Store {
    private ArrayList<Media> itemsInStore;
    public Store() {
        itemsInStore = new ArrayList<Media>();
    }
    public Media generatingNewMedia(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter DVD information!");
        System.out.println("----");
        System.out.println("Please enter the DVD's title:");
        String title = sc.nextLine();
        System.out.println("Please enter the DVD's category:");
        String category = sc.nextLine();
        System.out.println("Please enter the DVD's director:");
        String director = sc.nextLine();
        System.out.println("Please enter the DVD's length:");
        int length = sc.nextInt();
        System.out.println("Please enter the DVD's cost");
        float cost = sc.nextFloat();
        Media media = new DigitalVideoDisc(title, category, director, length, cost );
        System.out.println("Created DVD with the following information:");
        System.out.println(media.toString());
        System.out.println("------------------------------------");
        return media;
    }

    public void addMedia(Media media){
        itemsInStore.add(media);
    }

    public void addMedia (Media... args){
        for (Media arg : args) {
            addMedia(arg);
        }
    }

    public void removeMedia (Media media) {
        if (itemsInStore.contains(media)) {
            itemsInStore.remove(media);
        } else {
            JOptionPane.showMessageDialog(null, "Media not found");
        }
    }
    public Media SearchByTitle(String title) {
        for (Media media : itemsInStore) {
            if (media.getTitle().equals(title)) {
                return media;
            }
        }
        return null;
    }
    public ArrayList<Media> getItemsInStore() {
        ArrayList<Media> items = new ArrayList<>();
        for (Media media : itemsInStore) {
            items.add(media);
        }
        return items;
    }
    public Media findMedia(int id) {
        for (Media media : itemsInStore) {
            if (media.getId() == id) {
                return media;
            }
        }
        return null;
    }
    public Media findMedia(String title) {
        for (Media media : itemsInStore) {
            if (media.getTitle().equals(title)) {
                return media;
            }
        }
        return null;
    }
    public String toString() {
        String result = "";
        for (Media media : itemsInStore) {
            result += media.toString() + "\n";
        }
        return result;
    }
    public void removeMedia(int id) {
        for (Media media : itemsInStore) {
            if (media.getId() == id) {
                itemsInStore.remove(media);
                return;
            }
        }
    }
    public void removeMedia(String title) {
        for (Media media : itemsInStore) {
            if (media.getTitle().equals(title)) {
                itemsInStore.remove(media);
                return;
            }
        }
    }
}