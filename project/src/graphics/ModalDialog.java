package graphics;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ModalDialog {
	Button btn;

	public ModalDialog(final Stage stg, String text) {
		btn = new Button();

		final Stage stage = new Stage();
		// Initialize the Stage with type of modal
		stage.initModality(Modality.APPLICATION_MODAL);
		// Set the owner of the Stage
		stage.initOwner(stg);
		stage.setTitle("Top Stage With Modality");
		Group root = new Group();
		Scene scene = new Scene(root, 360, 90, Color.LIGHTCORAL);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				stage.hide();

			}
		});

		btn.setPrefSize(120, 28);
		
		btn.setLayoutX((scene.getWidth() - btn.getPrefWidth()) / 2);
		btn.setLayoutY(56);
		btn.setText("Ok");

		Label label = new Label(text);
		label.setWrapText(true);
		label.setMaxWidth(scene.getWidth() - 8);
		label.setLayoutY(8);
		
		root.getChildren().addAll(btn, label);
		stage.setScene(scene);
		stage.setResizable(false);
		
		stage.show();
		

		label.setLayoutX((scene.getWidth() - label.getWidth()) / 2);

	}

}