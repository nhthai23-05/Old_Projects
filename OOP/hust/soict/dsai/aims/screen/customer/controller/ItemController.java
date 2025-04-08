package hust.soict.dsai.aims.screen.customer.controller;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.exception.PlayerException;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ItemController {
    private Media media;
    private Cart cart = new Cart();
    private Store store = new Store();
    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblTitle;

    @FXML
    void btnAddToCartClicked(ActionEvent event) {
        this.cart.addMedia(media);
    }

    @FXML
    void btnPlayClicked(ActionEvent event) throws PlayerException {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Play");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        try {
            dialog.setContentText( ((Playable)media).play());
            //Adding buttons to the dialog pane
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        } catch (PlayerException e) {
            Dialog<String> errorDialog = new Dialog<String>();
            errorDialog.setTitle("Error");
            ButtonType typeError = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            errorDialog.setContentText("Error: Length is non-positive");
            errorDialog.getDialogPane().getButtonTypes().add(typeError);
            errorDialog.showAndWait();
            throw new PlayerException("ERROR: DVD length is non-positive!");
        }
    }
    public void setData(Media media,Cart cart,Store store){
        this.media = media;
        this.store = store;
        this.cart = cart;
        lblTitle.setText(media.getTitle());
        lblCost.setText(media.getCost()+"$");
        if (media instanceof Playable){
            btnPlay.setVisible(true);
        } else {
            btnPlay.setVisible(false);
            HBox.setMargin(btnAddToCart, new Insets(0, 0, 0, 108));
        }
    }
}