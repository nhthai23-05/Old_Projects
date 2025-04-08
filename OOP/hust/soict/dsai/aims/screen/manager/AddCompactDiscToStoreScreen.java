package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.CompactDisc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AddCompactDiscToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfTitle;
    private JTextField tfDirector;
    private JTextField tfArtist;
    private JTextField tfLength;
    private JTextField tfCategory;
    private JTextField tfCost;

    public AddCompactDiscToStoreScreen(Store store){
        super(store);
        Container cp  = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createNorth(), BorderLayout.NORTH);
        cp.add(createCenter(), BorderLayout.CENTER);
        cp.add(createSouth(), BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Store");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel createSouth(){
        TFInputListener btnListener = new TFInputListener();
        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1,2));

        JButton btnDelete = new JButton("Reset");
        south.add(btnDelete);
        btnDelete.addActionListener(btnListener);

        JButton btnAddTrack = new JButton("Add Track");
        south.add(btnAddTrack);
        btnAddTrack.addActionListener(btnListener);

        return south;
    }

    JPanel createCenter(){
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(6,2));

        JLabel title =  new JLabel("CD's title: ");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(title);
        tfTitle = new JTextField(10);
        center.add(tfTitle);

        JLabel category =  new JLabel("CD's category: ");
        category.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        category.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(category);
        tfCategory = new JTextField(10);
        center.add(tfCategory);


        JLabel director = new JLabel("CD's director: ");
        director.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        director.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(director);
        tfDirector = new JTextField(10);
        center.add(tfDirector);

        JLabel artist = new JLabel("CD's artist: ");
        artist.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        artist.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(artist);
        tfArtist = new JTextField(10);
        center.add(tfArtist);

        JLabel length = new JLabel("CD's length");
        length.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        length.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(length);
        tfLength = new JTextField(0+"", 10);
        center.add(tfLength);

        JLabel cost = new JLabel("CD's cost");
        cost.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        cost.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(cost);
        tfCost = new JTextField(0+"", 10);
        center.add(tfCost);

        return center;
    }

    private class TFInputListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            String button =  evt.getActionCommand();
            if (button.equals("Reset")){
                tfTitle.setText("");
                tfCategory.setText("");
                tfDirector.setText("");
                tfCost.setText("");
                tfLength.setText("");
                tfArtist.setText("");

            } else if (button.equals("Add Track")) {
                CompactDisc cd = new CompactDisc(tfTitle.getText(), tfCategory.getText(), tfDirector.getText(),
                        parseFloat(tfCost.getText()), parseInt(tfLength.getText()), tfArtist.getText(), new ArrayList<>());
                new AddTrackToCompactDiscScreen(cd, store);
                dispose();
            }
        }
    }
}