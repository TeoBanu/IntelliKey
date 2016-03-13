import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	private volatile int maxSuggestions;
	private volatile double delay;
	private volatile double accuracy;

	public void save() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			File file = new File(Constants.SETTINGS_PATH);
			if(!file.exists()) {
				file.createNewFile();
			}
			output = new FileOutputStream(file);

			// set the properties value
			prop.setProperty("sliderDelay", delay + "");
			prop.setProperty("sliderAccuracy", accuracy + "");
			prop.setProperty("txtMaxSuggestions", maxSuggestions + "");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void readFromFile(File file) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("sliderDelay"));
			delay = Double.parseDouble(prop.getProperty("sliderDelay"));
			System.out.println(prop.getProperty("sliderAccuracy"));
			accuracy = Double.parseDouble(prop.getProperty("sliderAccuracy"));
			System.out.println(prop.getProperty("txtMaxSuggestions"));
			maxSuggestions = Integer.parseInt(prop.getProperty("txtMaxSuggestions"));
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void load() {
		boolean couldNotLoadFromFile = true;
		File file = new File(Constants.SETTINGS_PATH);
		if (file.exists()) {
			try {
				readFromFile(file);
				couldNotLoadFromFile = false;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (couldNotLoadFromFile == true) {
			delay = Constants.DEFAULT_DELAY;
			accuracy = Constants.DEFAULT_ACCURACY;
			maxSuggestions = Constants.DEFAULT_MAX_SUGGESTIONS;
		}
	}

	public int getMaxSuggestions() {
		return maxSuggestions;
	}

	public void setMaxSuggestions(int maxSuggestions) {
		this.maxSuggestions = maxSuggestions;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

}
