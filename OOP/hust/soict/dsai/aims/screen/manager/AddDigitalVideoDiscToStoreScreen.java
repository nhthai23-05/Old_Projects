package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.DigitalVideoDisc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AddDigitalVideoDiscToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfTitle;
    private JTextField tfCategory;
    private JTextField tfDirector;
    private JTextField tfLength;
    private JTextField tfCost;

    public AddDigitalVideoDiscToStoreScreen(Store store){
        super(store);
        Container cp  = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createNorth(), BorderLayout.NORTH);
        cp.add(createCenter(), BorderLayout.CENTER);
        cp.add(createSouth(), BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Add DVD");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel createSouth(){
        TFInputListener btnListener = new TFInputListener();
        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1,2));

        JButton btnDelete = new JButton("Reset");
        btnDelete.setPreferredSize(new Dimension(5, 10));
        south.add(btnDelete);
        btnDelete.addActionListener(btnListener);

        JButton btnSubmit = new JButton("Submit");
        south.add(btnSubmit);
        btnSubmit.addActionListener(btnListener);
        return south;
    }

    JPanel createCenter(){
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(5,2));

        JLabel title =  new JLabel("DVD's title: ");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(title);
        tfTitle = new JTextField(8);
        center.add(tfTitle);

        JLabel category =  new JLabel("DVD's category: ");
        category.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        category.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(category);
        tfCategory = new JTextField(8);
        center.add(tfCategory);



        JLabel director = new JLabel("DVD's director: ");
        director.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        director.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(director);
        tfDirector = new JTextField(8);
        center.add(tfDirector);


        JLabel length = new JLabel("DVD's length");
        length.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        length.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(length);
        tfLength = new JTextField(0+"",8);
        center.add(tfLength);


        JLabel cost = new JLabel("DVD's cost");
        cost.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        cost.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(cost);
        tfCost = new JTextField(0+"",8);
        center.add(tfCost);

        return center;
    }

    private class TFInputListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            String button =  evt.getActionCommand();
            if (button.equals("Reset")){
                tfTitle.setText("");
                tfCategory.setText("");
                tfDirector.setText("");
                tfCost.setText("0");
                tfLength.setText("0");

            } else if (button.equals("Submit")){
                store.addMedia(new DigitalVideoDisc(tfTitle.getText(), tfCategory.getText(), tfDirector.getText()
                        ,parseInt(tfLength.getText()), parseFloat(tfCost.getText())));

                Frame f = new Frame();
                JDialog d = new JDialog(f, "Notification");
                JLabel l = new JLabel("Already add the DVD to the Store");
                d.add(l);
                d.setSize(300, 80);
                d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tfTitle.setText("");
                tfCategory.setText("");
                tfDirector.setText("");
                tfCost.setText("0");
                tfLength.setText("0");
                d.setVisible(true);
            }
        }
    }

}