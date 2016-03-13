
public class ApplicationContext {
	private static ApplicationContext ap = new ApplicationContext();
	private Settings settings;
	private WordSimilarities ws;

	public WordSimilarities getWs() {
		return ws;
	}

	private ApplicationContext() {
		settings = new Settings();
	}

	public static ApplicationContext getInstance() {
		return ap;
	}

	public void init() {
		settings.load();
		ws = new WordSimilarities(settings.getAccuracy(), settings.getMaxSuggestions());
	}

	public void saveSettings() {
		settings.save();
	}
	
	public void setSettings(double delay, double accuracy, int maxSuggestions) {
		settings.setDelay(delay);
		settings.setAccuracy(accuracy);
		settings.setMaxSuggestions(maxSuggestions);
		ws.setAccuracy(accuracy);
		ws.setMaxSuggestions(maxSuggestions);
	}
	
	public int getMaxSuggestions() {
		return settings.getMaxSuggestions();
	}

	public double getDelay() {
		return settings.getDelay();
	}


	public double getAccuracy() {
		return settings.getAccuracy();
	}

}
