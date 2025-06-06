package GUIProject.hust.dsai.swing.GUIProject.src.javafx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;

public class Painter extends Application{
	@Override
	public void start(Stage stage) throws Exception{
		try {
			Parent root = FXMLLoader.load(getClass()
                    .getResource("/hust/soict/dsai/javafx/Painter.fxml"));
			stage.setTitle("Painter");
			stage.setScene(new Scene(root));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}