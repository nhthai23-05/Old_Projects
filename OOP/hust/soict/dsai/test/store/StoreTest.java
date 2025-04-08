package hust.soict.dsai.test.store;
import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
public class StoreTest {
    public static void main(String[] args) {
        Store store = new Store();
        DigitalVideoDisc media1 = new DigitalVideoDisc("The Lion King");
        store.addMedia();
        store.removeMedia(media1);
    }
}