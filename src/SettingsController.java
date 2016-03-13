import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController implements Initializable{
	@FXML
	private Slider sliderDelay;
	@FXML
	private Slider sliderAccuracy;
	@FXML
	private TextField txtMaxSuggestions;
	@FXML 
	private Button saveBtn;
	
	private ApplicationContext appCtx = ApplicationContext.getInstance();

	public void onSaveClick() {
		double accuracy = sliderAccuracy.getValue();
		double delay = sliderDelay.getValue();
		int maxSuggestions = Integer.parseInt(txtMaxSuggestions.getText());
		appCtx.setSettings(delay, accuracy, maxSuggestions);
		Stage stage = (Stage) saveBtn.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sliderDelay.setValue(appCtx.getDelay());
		//System.out.println(sliderDelay.getValue());
		sliderAccuracy.setValue(appCtx.getAccuracy());
		//System.out.println(sliderAccuracy.getValue());
		txtMaxSuggestions.setText(appCtx.getMaxSuggestions() + "");
		//System.out.println(txtMaxSuggestions.getText());
	}
}
