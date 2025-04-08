package hust.soict.dsai.aims.media;
public class DigitalVideoDisc extends Disc implements Playable {

    public DigitalVideoDisc(String title) {
        super(title);
        setTitle(title);
    }
    public DigitalVideoDisc(String title, String category, float cost) {
        super(title, category, cost);
        setTitle(title);
        setCategory(category);
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super(title, category, director, length, cost);
        this.director = director;
        this.length = length;
        setTitle(title);
        setCategory(category);
        setCost(cost);
    }

    public boolean isMatchTitle(String title) {
        return this.title.equals(title);
    }
    public String play() {
        return "Playing DVD: " + this.getTitle() + "\n" + "DVD length: " + this.getLength();
    }
    public String toString() {
        return "DVD - " + this.getTitle() + " - " + this.getCategory() + " - " + this.getDirector() + " - " + this.getLength() + " : " + this.getCost() + "$";
    }
}