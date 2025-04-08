package hust.soict.dsai.aims.media;

public class Disc extends Media {
    public int length;
    public String director;
    public int getLength() {
        return length;
    }
    public String getDirector() {
        return director;
    }
    public Disc(String title) {
        super();
        setTitle(title);
    }
    public Disc(String title, String category, float cost) {
        super();
        setTitle(title);
        setCategory(category);
        setCost(cost);
    }
    public Disc(String title, String category, String director, int length, float cost) {
        super();
        setTitle(title);
        setCategory(category);
        setCost(cost);
        this.director = director;
        this.length = length;
    }
}
