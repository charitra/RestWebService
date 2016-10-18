package hello;

import java.util.List;
import java.util.Map;

public class Response {

	private String speech;
	private String displayText;
	private List<Map<String, String>> data;
	private List<String> contextOut;
	private String source;
	
	public Response(String speech, String displayText, List<Map<String, String>> data,
			List<String> contextOut, String source) {
		this.speech = speech;
		this.displayText = displayText;
		this.data = data;
		this.contextOut = contextOut;
		this.source = source;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public List<Map<String, String>> getData() {
		return data;
	}
	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
	public List<String> getContextOut() {
		return contextOut;
	}
	public void setContextOut(List<String> contextOut) {
		this.contextOut = contextOut;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
}
