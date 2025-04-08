package hust.soict.dsai.aims.screen.customer.controller;

import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.exception.PlayerException;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CartScreenController {
    private Cart cart;
    private Store store;
    public CartScreenController( Store store, Cart cart){
        this.cart = cart;
        this.store = store;
    }
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnRemove;
    @FXML
    private RadioButton radioBtnFilterId;

    @FXML
    private TextField tfFilter;

    @FXML
    private RadioButton radioBtnFilterTitle;

    @FXML
    private TableColumn<Media, String> colMediaCategory;

    @FXML
    private TableColumn<Media, Float> colMediaCost;

    @FXML
    private TableColumn<Media, Integer> colMediaID;

    @FXML
    private TableColumn<Media, String> colMediaTitle;

    @FXML
    private ToggleGroup filterCategory;

    @FXML
    private TableView<Media> tblMedia;

    @FXML
    private Label costLabel;

    @FXML
    void btnRemovePressed(ActionEvent event){
        Media media = tblMedia.getSelectionModel().getSelectedItem();
        cart.removeMedia(media);
        costLabel.setText(cart.totalCost()+"");
    }

    @FXML
    void btnPlayPressed(ActionEvent event) throws PlayerException {
        Media media = tblMedia.getSelectionModel().getSelectedItem();
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Play");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        try {
            dialog.setContentText( ((Playable)media).play());
        } catch (PlayerException e) {
            Dialog<String> errorDialog = new Dialog<String>();
            errorDialog.setTitle("Error");
            ButtonType typeError = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            errorDialog.setContentText("Error: Length is non-positive");
            errorDialog.getDialogPane().getButtonTypes().add(typeError);
            errorDialog.showAndWait();
            throw new PlayerException("ERROR: DVD length is non-positive!");
        }
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    @FXML
    void btnViewStorePressed(javafx.event.ActionEvent event) {
            final String STORE_FXML_FILE_PATH = "/hust/soict/dsai/aims/screen/customer/view/Store.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
            fxmlLoader.setController(new ViewStoreController(store, cart));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void btnPlaceOrderPressed(ActionEvent event) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Play");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText("You have already place an order with the total cost \n" +
                cart.totalCost() + "\nPress OK to confirm your request!" );
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }


    public void initialize(){
        colMediaID.setCellValueFactory(
                new PropertyValueFactory<Media, Integer>("id"));
        colMediaTitle.setCellValueFactory(
                new PropertyValueFactory<Media, String>("title"));
        colMediaCategory.setCellValueFactory(
                new PropertyValueFactory<Media, String>("category"));
        colMediaCost.setCellValueFactory(
                new PropertyValueFactory<Media, Float>("cost"));
        if (cart.getItemsOrdered() != null){
            tblMedia.setItems(FXCollections.observableArrayList(cart.getItemsOrdered()));
        }
        btnPlay.setVisible(false);
        btnRemove.setVisible(false);

        tblMedia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Media>() {
            @Override
            public void changed(ObservableValue<? extends Media> observableValue, Media oldValue, Media newValue) {
                updateButtonBar(newValue);
            }
            public void updateButtonBar(Media media){
                if (media == null) {
                    btnPlay.setVisible(false);
                    btnRemove.setVisible(false);
                } else {
                    btnRemove.setVisible(true);
                    btnPlay.setVisible(media instanceof Playable);
                }
            }
        });
        tfFilter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showFilteredMedia(newValue);
            }
            public void showFilteredMedia(String newValue) {
                ObservableList<Media> FilteredList = FXCollections.observableArrayList();
                if (radioBtnFilterTitle.isSelected()) {
                    for (Media media : cart.getItemsOrdered()) {
                        if (media.getTitle().contains(newValue)) {
                            FilteredList.add(media);
                        }
                    }
                } else if (radioBtnFilterId.isSelected()) {
                    if (newValue.equals("")) {
                        FilteredList = FXCollections.observableArrayList(cart.getItemsOrdered());
                    } else {
                        for (Media media : cart.getItemsOrdered()) {
                            if (media.getId() == parseInt(newValue)) {
                                FilteredList.add(media);
                            }
                        }
                    }
                }
                tblMedia.setItems(FilteredList);
            }
        });
        costLabel.setText(cart.totalCost()+"");
    }
}