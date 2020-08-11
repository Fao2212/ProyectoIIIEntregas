package mrdelivery.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import mrdelivery.model.Const;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Out {

	private static Alert alert = new Alert(AlertType.ERROR);
	
	public static void pushNotification(String title, String message, String imgPath){
		TrayNotification notification = new TrayNotification();
		notification.setAnimationType(AnimationType.POPUP);
		notification.setTitle(title);
		notification.setMessage(message);
		notification.setImage(new Image(imgPath));
		notification.showAndDismiss(Duration.millis(Const.DURACION_NOTIFICACION_MLS));
	}

	public static void pushNotification(String title, String message, NotificationType type){
		TrayNotification notification = new TrayNotification();
		notification.setAnimationType(AnimationType.POPUP);
		notification.setTitle(title);
		notification.setMessage(message);
		notification.setNotificationType(type);
		notification.showAndDismiss(Duration.millis(Const.DURACION_NOTIFICACION_MLS));
	}

	public static ButtonType confirmationDialog(String title, String header, String question) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(question);
		
		alert.getButtonTypes().setAll(
				ButtonType.YES,
				ButtonType.NO,
				ButtonType.CANCEL
		);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get();
	}
	
	public static void msg(String title, String text, AlertType type) {
		alert.setAlertType(type);
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.setContentText("");
		alert.showAndWait();
	}
	
	public static void msg(String title, String text, String context, AlertType type) {
		alert.setAlertType(type);
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.setContentText(context);
		if (!alert.isShowing())
			alert.showAndWait();
	}
	
	public static void msg(String text) {
		alert.setAlertType(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText(text);
		if (!alert.isShowing())
			alert.showAndWait();
	}
	
	public static void msg(String title, String text) {
		alert.setAlertType(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(text);
		if (!alert.isShowing())
			alert.showAndWait();
	}

	public static void msg(String text, Exception ex) {
		alert.setAlertType(AlertType.ERROR);
		alert.setTitle("Mensaje de error");
		alert.setHeaderText("Ha habido un problema");
		alert.setContentText(text);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Informaci�n del error: ");
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		
		if (!alert.isShowing())
			alert.showAndWait();
	}

}
