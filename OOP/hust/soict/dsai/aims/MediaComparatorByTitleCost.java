package hust.soict.dsai.aims;
import hust.soict.dsai.aims.media.Media;
import java.util.Comparator;

public class MediaComparatorByTitleCost implements Comparator<Media> {
    public int compare(Media media1, Media media2) {
        return Comparator.comparing(Media::getTitle).thenComparing(Media::getCost).compare(media1, media2);
    }
}
