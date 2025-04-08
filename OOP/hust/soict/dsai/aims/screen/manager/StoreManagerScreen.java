package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StoreManagerScreen extends JFrame {
    private Store store;

    public StoreManagerScreen(Store store){
        this.store = store;
        Container cp  = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createNorth(), BorderLayout.NORTH);
        cp.add(createCenter(), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Store");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel createNorth(){
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(createMenuBar());
        north.add(createHeader());
        return north;
    }

    JMenuBar createMenuBar() {
        JMenu menu = new JMenu("Options");

        ButtonListener btnListener = new ButtonListener();
        JMenuItem btnMenu = new JMenuItem("View store");
        menu.add(btnMenu);
        btnMenu.addActionListener(btnListener);

        JMenu smUpdateStore = new JMenu("Update Store");

//      ----- Add book ----
        JMenuItem btnAddBook = new JMenuItem("AddBook");
        smUpdateStore.add(btnAddBook);
        btnAddBook.addActionListener(btnListener);
//      ----- Add CD -----
        JMenuItem btnAddCD = new JMenuItem("AddCD");
        smUpdateStore.add(btnAddCD);
        btnAddCD.addActionListener(btnListener);
//      ----- Add DVD ----
        JMenuItem btnAddDVD = new JMenuItem("AddDVD");
        smUpdateStore.add(btnAddDVD);
        btnAddDVD.addActionListener(btnListener);
//      ---- Add sub menu to the menu ---
        menu.add(smUpdateStore);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuBar.add(menu);

        return menuBar;
    }

    JPanel createHeader(){
        JPanel header  = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JLabel title = new JLabel("AIMS");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 50));
        title.setForeground(Color.CYAN);

        header.add(Box.createRigidArea(new Dimension(10, 10)));
        header.add(title);
        header.add(Box.createHorizontalGlue());
        header.add(Box.createRigidArea(new Dimension(10, 10)));

        return header;
    }

    JPanel createCenter(){
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3,3,2,2));
        ArrayList<Media> mediaInStore = store.getItemsInStore();
        for (int i = 0; i < store.getItemsInStore().size(); i++){
            MediaStore cell = new MediaStore(mediaInStore.get(i));
            center.add(cell);
        }
        return center;
    }

    public class MediaStore extends JPanel{
        public MediaStore(Media media){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JLabel title = new JLabel(media.getTitle());
            title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 15));
            title.setAlignmentX(CENTER_ALIGNMENT);

            JLabel cost = new JLabel(""+ media.getCost() + "$");
            cost.setAlignmentX(CENTER_ALIGNMENT);

            JPanel container = new JPanel();
            container.setLayout(new FlowLayout(FlowLayout.CENTER));

            if (media instanceof Playable){
                JButton playButton = new JButton("Play");
                container.add(playButton);
                playButton.addActionListener(new ButtonListener(){
                    public void actionPerformed( ActionEvent e ) {
                        JFrame f = new JFrame();
                        JDialog d = new JDialog(f, "Playing");
                        if (media instanceof DigitalVideoDisc) {
                            d.setLayout(new GridLayout(2, 1));
                            JLabel play = new JLabel("Playing DVD: " + ((Disc) media).getTitle());
                            play.setHorizontalAlignment(0);
                            JLabel len = new JLabel("Length: " + ((Disc) media).getLength() + "");
                            len.setHorizontalAlignment(0);
                            d.add(play);
                            d.add(len);
                        } else {
                            d.setLayout(new GridLayout(2, 1));
                            JLabel trackTitle = new JLabel( "Playing track: " + ((CompactDisc)media).getTracks().get(0).getTitle());
                            trackTitle.setHorizontalAlignment(0);
                            JLabel trackLength = new JLabel( "Length: " + ((CompactDisc)media).getTracks().get(0).getLength() );
                            trackLength.setHorizontalAlignment(0);
                            d.add(trackTitle);
                            d.add(trackLength);
                        }
                        d.setSize(300, 100);
                        d.setVisible(true);
                    }
                });
            }

            this.add(Box.createVerticalGlue());
            this.add(title);
            this.add(cost);
            this.add(Box.createVerticalGlue());
            this.add(container);

            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String button = e.getActionCommand();
            switch (button) {
                case "View store" -> {
                    dispose();
                    new StoreManagerScreen(store);
                }
                case "AddDVD" -> {
                    dispose();
                    new AddDigitalVideoDiscToStoreScreen(store);
                }
                case "AddCD" -> {
                    dispose();
                    new AddCompactDiscToStoreScreen(store);
                }
                case "AddBook" -> {
                    dispose();
                    new AddBookToStoreScreen(store);
                }
            }
        }
    }

    public static void main(String[] args) {
        Store aStore = new Store();
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("Harry Potter and the Philosopher's Stone (2001)",
                "Animation", "Roger Allers", 87, 3.0f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Harry Potter and the Chamber of Secrets (2002)",
                "Science Fiction", "Geogre Lucas", 87, 3.5f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Harry Potter and the Prisoner of Azkaban (2004)",
                "Animation", 5.0f);
        DigitalVideoDisc dvd4 = new DigitalVideoDisc("Harry Potter and the Goblet of Fire (2005)",
                "Animaton", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd5 = new DigitalVideoDisc("Fetch the Bolt Cutters",
                "Science Fiction", "Geogre Lucas", 87, 24.95f);
        DigitalVideoDisc dvd6 = new DigitalVideoDisc("Future Nostalgia",
                "Animation", 18.99f);
}
}