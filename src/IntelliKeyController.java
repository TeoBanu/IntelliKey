import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntelliKeyController implements Initializable {

	@FXML
	private VBox keybox;
	@FXML
	private TextArea textArea;
	@FXML
	private ListView<String> suggestionList;

	private volatile long touchStart;
	private volatile String selected;
	private volatile boolean isPressed;
	private StringBuilder sb = new StringBuilder();
	private ScheduledExecutorService sv;
	private ApplicationContext appCtx = ApplicationContext.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		keybox.addEventFilter(MouseEvent.MOUSE_DRAGGED, new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				PickResult pr = event.getPickResult();
				Node no = pr.getIntersectedNode();
				String current = getText(no);
				if (!current.equals(selected)) {
					selected = current;
					touchStart = System.currentTimeMillis();
				}
				event.consume();
			}
		});
		keybox.addEventFilter(MouseEvent.MOUSE_PRESSED, new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				isPressed = true;
				PickResult pr = event.getPickResult();
				Node no = pr.getIntersectedNode();
				String current = getText(no);
				if (!current.equals(selected)) {
					selected = current;
					touchStart = System.currentTimeMillis();
				}

				event.consume();
			}

		});
		keybox.addEventFilter(MouseEvent.MOUSE_RELEASED, new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				isPressed = false;
				sb.setLength(0);
				event.consume();
			}
		});

		final ObservableList<String> list = FXCollections.observableArrayList();
		suggestionList.setItems(list);

		sv = Executors.newSingleThreadScheduledExecutor();
		sv.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (isPressed) {
					long now = System.currentTimeMillis();
					System.out.println(appCtx.getDelay());
					if (now - touchStart >= appCtx.getDelay()) {
						touchStart = now;
						sb.append(selected);
						textArea.appendText(selected);

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								list.clear();
								list.addAll(appCtx.getWs().findSimilarities(sb.toString()));
							}
						});
					}
				}
			}
		}, 0, 50, TimeUnit.MILLISECONDS);

	}

	private String getText(Node no) {
		if (no instanceof Button) {
			return ((Button) no).getText();
		} else if (no != null) {
			return getText(no.getParent());
		} else {
			return "";
		}
	}
	
	public void onSettingsClick() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(Constants.SETTINGS_FXML));
		Parent page;
		try {
			page = loader.load();
			Scene scene = new Scene(page);

			stage.setScene(scene);
			stage.setTitle("Settings");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void stop() {
		sv.shutdown();
	}
}
