package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemToStoreScreen extends JFrame {
    Store store;
    public AddItemToStoreScreen(Store store){
        this.store = store;
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
    }
}