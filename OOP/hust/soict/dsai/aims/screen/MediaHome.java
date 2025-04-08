package hust.soict.dsai.aims.screen;

import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.cart.Cart;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MediaHome extends JPanel {

    private Media media;
    private Cart cart;
    private JButton playButton;
    private JButton addToCartButton;

    public MediaHome(Media media) {
        this.media = media;

        // Create and add buttons
        playButton = new JButton("Play");
        addToCartButton = new JButton("Add to Cart");

        // Add action listeners to buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a JDialog for playing media
                JDialog dialog = new JDialog();
                dialog.setTitle("Playing " + media.getTitle());

                // Add a label to display media information (or a media player component)
                JLabel label = new JLabel("Playing: " + media.getTitle());
                dialog.add(label);

                dialog.setSize(300, 150);
                dialog.setVisible(true);
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart.addMedia(media); 
                JOptionPane.showMessageDialog(MediaHome.this, "Media added to cart!"); 
            }
        });

        add(playButton);
        add(addToCartButton);

    }

}