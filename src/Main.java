import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private IntelliKeyController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			ApplicationContext.getInstance().init();
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(Constants.MAIN_WINDOW_FXML));
			BorderPane page = loader.load();
			controller = loader.getController();

			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle(Constants.APP_TITLE);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(Constants.ICON_PATH));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.out.println("stop");
		controller.stop();
		ApplicationContext.getInstance().saveSettings();
	}

	public static void main(String[] args) {
		Application.launch(Main.class, (java.lang.String[]) null);
	}

}
