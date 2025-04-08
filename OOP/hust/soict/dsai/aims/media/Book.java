package hust.soict.dsai.aims.media;
import java.util.ArrayList;
import java.util.List;
public class Book extends Media {
    private List<String> authors = new ArrayList<String>();
    public List<String> getAuthors() {
        return authors;
    }
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public Book(String title) {
        super();
        setTitle(title);
    }
    public Book(String title, String category, float cost, List<String> authors) {
        super();
        setTitle(title);
        setCategory(category);
        setCost(cost);
        this.authors = authors;
    }
    public void addAuthor(String author) {
        authors.add(author);
    }
    public void removeAuthor(String author) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).equals(author)) {
                authors.remove(i);
                break;
            }
        }
    }
    public String toString() {
        return "Book - " + this.getTitle() + " - " + this.getCategory() + " - " + this.getAuthors() + " : " + this.getCost() + "$";
    }
}
