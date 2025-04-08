package hust.soict.dsai.aims.cart;
import java.lang.Math;

import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Disc;
import hust.soict.dsai.aims.media.Media;
import java.util.Comparator;
import java.util.Collections;

import java.util.ArrayList;
public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20;
    private int qtyOrdered = 0;
    private ArrayList<Media> itemsOrdered = new ArrayList<Media>();
    public void addMedia(Media itemsOrdered) {
        qtyOrdered++;
        itemsOrdered.setId(qtyOrdered);
        if (itemsOrdered != null) {
            if (this.itemsOrdered.size() < MAX_NUMBERS_ORDERED) {
                this.itemsOrdered.add(itemsOrdered);
                System.out.println("The disc has been added");
            } else {
                System.out.println("The cart is almost full");
            }
        }
    }
    public void removeMedia(Media itemsOrdered) {
        if (itemsOrdered != null) {
            if (this.itemsOrdered.size() > 0) {
                this.itemsOrdered.remove(itemsOrdered);
                System.out.println("The disc has been removed");
            } else {
                System.out.println("The cart is empty");
            }
        }
    }
    public void removeMedia(int id) {
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered.get(i).getId() == id) {
                itemsOrdered.remove(i);
                System.out.println("The disc has been removed");
                return;
            }
        }
        System.out.println("The disc is not in the cart");
    }
    public void removeMedia(String title) {
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered.get(i).getTitle().equalsIgnoreCase(title)) {
                itemsOrdered.remove(i);
                System.out.println("The disc has been removed");
                return;
            }
        }
        System.out.println("The disc is not in the cart");
    }
    public float totalCost() {
        float total = 0;
        for (Media media : itemsOrdered) {
            total += media.getCost();
        }
        return Math.round(total * 100) / 100;
    }
    public String toString() {
        String result = "***********************CART***********************\n";
        result += "Ordered items:\n";
        for (int i = 0; i < qtyOrdered; i++) {
            Media mediaItem = itemsOrdered.get(i);
            if (mediaItem instanceof Disc) {
                result += (i + 1) + ". DVD - " + mediaItem.getTitle() + " - " + mediaItem.getCategory() + " - " + ((Disc) mediaItem).getDirector() + " - " + ((Disc) mediaItem).getLength() + ": " + mediaItem.getCost() + "$" + "\n";
                continue;
            } else if (mediaItem instanceof DigitalVideoDisc) {
                result += (i + 1) + ". DVD - " + mediaItem.getTitle() + " - " + mediaItem.getCategory() + " - " + ((DigitalVideoDisc) mediaItem).getDirector() + " - " + ((DigitalVideoDisc) mediaItem).getLength() + ": " + mediaItem.getCost() + "$" + "\n";
                continue;
            } else if (mediaItem instanceof Book) {
                result += (i + 1) + ". Book - " + mediaItem.getTitle() + " - " + mediaItem.getCategory() + " - " + ((Book) mediaItem).getAuthors() + ": " + mediaItem.getCost() + "$" + "\n";
                continue;
            }
        }
        result += "Total cost: " + totalCost() + "$" + "\n";
        result += "***************************************************";
        return result;
    }
    public String searchByTitle(String title) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        
        for (Media media : itemsOrdered) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                result.append(media.getTitle()).append("\n");
                found = true;
            }
        }

        if (found) {
            return result.toString();
        } else {
            return "No media found with title: " + title;
        }
    }
    public Media findMedia(String title) {
        for (Media media : itemsOrdered) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                return media;
            }
        }
        return null;
    }
    public String SearchByID(int id) {
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered.get(i).getId() == id) {
                return itemsOrdered.get(i).getTitle();
            }
        }
        return "Not found";
    }
    public void sortMediaByTitleCost() {
        Collections.sort(itemsOrdered, new Comparator<Media>() {
            public int compare(Media m1, Media m2) {
                int titleComparison = m1.getTitle().compareToIgnoreCase(m2.getTitle());
                if (titleComparison != 0) {
                    return titleComparison;
                }
                return Float.compare(m1.getCost(), m2.getCost());
            }
        });
    }

    public void sortMediaByCostTitle() {
        Collections.sort(itemsOrdered, new Comparator<Media>() {
            public int compare(Media m1, Media m2) {
                int costComparison = Float.compare(m1.getCost(), m2.getCost());
                if (costComparison != 0) {
                    return costComparison;
                }

                return m1.getTitle().compareToIgnoreCase(m2.getTitle());
            }
        });
    }
    public ArrayList<Media> getItemsOrdered() {
        return itemsOrdered;
    }
    public void clear() {
        itemsOrdered.clear();
    }
    public Media getrALuckyItem(){
        double rand = Math.random();
        int index = (int)Math.floor(rand * itemsOrdered.size());
        return this.itemsOrdered.get(index);
    }
    public void printWithLuckyItem() {
        System.out.println("********************** CART ***********************");
        float totalCost = totalCost(); // Calculate total before getting lucky item
        for (int i = 0; i < this.itemsOrdered.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(this.itemsOrdered.get(i).toString());
        }
        Media luckyMedia = getrALuckyItem();
        System.out.println("You got a lucky media");
        System.out.println(luckyMedia.toString());
        System.out.println("Total cost: " + totalCost); // Use pre-calculated total
        System.out.println("***************************************************");
    }
}