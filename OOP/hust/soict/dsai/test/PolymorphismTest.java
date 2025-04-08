package hust.soict.dsai.test;
import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Track;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.Media;

import java.util.List;
import java.util.ArrayList;

public class PolymorphismTest extends Media {
    public static void main(String[] args) {
        Book book = new Book("Dune", "Science Fiction", 20.0f, List.of("Frank Herbert"));
        DigitalVideoDisc dvd = new DigitalVideoDisc("The Lion King", "Animation", 19.95f);
        Track track1 = new Track("Track 1", 1);
        Track track2 = new Track("Track 2", 2);
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(track1);
        tracks.add(track2);
        CompactDisc cd = new CompactDisc("CD 1", "Music", "Artist 1", 15.0f, tracks);
        List<Media> mediae = new ArrayList<Media>();
        mediae.add(book);
        mediae.add(dvd);
        mediae.add(cd);
        for (Media media : mediae) {
            System.out.println(media.toString());
        }
    }
}
