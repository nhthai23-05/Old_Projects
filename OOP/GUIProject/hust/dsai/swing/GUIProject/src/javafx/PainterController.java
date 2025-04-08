package GUIProject.hust.dsai.swing.GUIProject.src.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

import java.awt.Color;

import javafx.scene.input.MouseEvent;

public class PainterController {
    @FXML
    private Pane drawingAreaPane;
    private String decision = "";
    private String tool = "";
    @FXML
    void clearButtonPressed(ActionEvent event){
        drawingAreaPane.getChildren().clear();
    }
    @FXML
	void menuButtonPressed(ActionEvent event) {
		tool = ((RadioButton)event.getSource()).getText();
	}
    @FXML
    void drawingAreaMouseDragged(MouseEvent event){
        Circle newCircle = new Circle();
        if (decision.equals("Pen")){
        newCircle = new Circle(event.getX(), event.getY(), 4, javafx.scene.paint.Color.BLACK);
        }
        else if (decision.equals("Eraser")){
            newCircle = new Circle(event.getX(), event.getY(), 4, javafx.scene.paint.Color.WHITE);
        }
        drawingAreaPane.getChildren().add(newCircle);
    }
}
