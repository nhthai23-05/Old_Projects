package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.Track;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static java.lang.Integer.parseInt;

public class AddTrackToCompactDiscScreen extends JFrame {

    private JTextField tfTitle;
    private JTextField tfLength;
    private CompactDisc cd;
    Store store;

    public AddTrackToCompactDiscScreen(CompactDisc cd, Store store) {
        this.cd = cd;
        this.store = store;
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createCenter(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Store");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JPanel createCenter() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 2));

        JLabel title = new JLabel("Track's title");
        jPanel.add(title);

        tfTitle = new JTextField();
        jPanel.add(tfTitle);

        JLabel length = new JLabel("Track's length");
        jPanel.add(length);

        tfLength = new JTextField();
        jPanel.add(tfLength);

        TFInputListener buttonListener = new TFInputListener();
        JButton add = new JButton("Add Track");
        jPanel.add(add);
        add.addActionListener(buttonListener);

        JButton done = new JButton("Done");
        jPanel.add(done);
        done.addActionListener(buttonListener);

        return jPanel;
    }

    private class TFInputListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String button = evt.getActionCommand();
            if (button.equalsIgnoreCase("Add Track")) {
                Track track = new Track(tfTitle.getText(), parseInt(tfLength.getText()));
                tfTitle.setText("");
                tfLength.setText("");
                cd.addTrack(track);
            } else if (button.equalsIgnoreCase("Done")) {
                store.addMedia(cd);
                JFrame f = new JFrame();
                JDialog d = new JDialog(f, "Notification");
                JLabel l = new JLabel("Already add the CD to the Store");
                d.add(l);
                d.setSize(300, 80);
                d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                d.setVisible(true);
                dispose();
                new AddCompactDiscToStoreScreen(store);
            }
        }
    }
}