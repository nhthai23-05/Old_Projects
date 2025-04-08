package hust.soict.dsai.aims.screen.manager;
import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.Book;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Float.parseFloat;

public class AddBookToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfTitle;
    private JTextField tfCategory;
    private JTextField tfCost;
    private JTextField tfContent;

    public AddBookToStoreScreen(Store store){
        super(store);
        Container cp  = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createNorth(), BorderLayout.NORTH);
        cp.add(createCenter(), BorderLayout.CENTER);
        cp.add(createSouth(), BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Add Book");
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

        JButton btnSubmit = new JButton("Submit");
        south.add(btnSubmit);
        btnSubmit.addActionListener(btnListener);
        return south;
    }

    JPanel createCenter(){
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(4,2));

        JLabel title =  new JLabel("Book's title: ");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(title);
        tfTitle = new JTextField(10);
        center.add(tfTitle);

        JLabel category =  new JLabel("Book's category: ");
        category.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        category.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(category);
        tfCategory = new JTextField(10);
        center.add(tfCategory);


        JLabel cost = new JLabel("Book's cost");
        cost.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
        cost.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(cost);
        tfCost = new JTextField(0+"", 10);
        center.add(tfCost);

        JLabel content = new JLabel("Book's content");
        content.setFont(new Font(content.getFont().getName(), Font.PLAIN, 18));
        content.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(content);
        tfContent = new JTextField(10);
        center.add(tfContent);

        return center;
    }

    private class TFInputListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String button = evt.getActionCommand();
            if (button.equals("Reset")) {
                tfTitle.setText("");
                tfCategory.setText("");
                tfCost.setText("0");
                tfContent.setText("");
            } else if (button.equals("Submit")) {
                store.addMedia(new Book(tfTitle.getText(), tfCategory.getText()
                        , parseFloat(tfCost.getText()), tfContent.getText()));

                Frame f = new Frame();
                JDialog d = new JDialog(f, "Notification");
                JLabel l = new JLabel("Already add the Book to the Store");
                d.add(l);
                d.setSize(300, 80);
                d.setVisible(true);
                tfTitle.setText("");
                tfCategory.setText("");
                tfCost.setText("0");
                tfContent.setText("");
            }
        }
    }
}