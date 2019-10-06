package converter.api.pro6plx;

public class Entry {
	private double timeStamp;
	private int actionType;
	private String displayName;
	private String filePath;
	private double delayTime;
	private String selectedArrangementID;
	private String uuid;
	private boolean enabled;
	
	public Entry(double timeStamp, int actionType, String displayName, String filePath, double delayTime, String selectedArrangementID, String uuid, boolean enabled) {
		this.timeStamp = timeStamp;
		this.actionType = actionType;
		this.displayName = displayName;
		this.filePath = filePath;
		this.delayTime = delayTime;
		this.selectedArrangementID = selectedArrangementID;
		this.uuid = uuid;
		this.enabled = enabled;
	}
	
	public double getTimeStamp() {
		return this.timeStamp;
	}
	
	public void setTimeStamp(double timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public int getActionType() {
		return this.actionType;
	}
	
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public double getDelayTime() {
		return this.delayTime;
	}
	
	public void setDelayTime(double delayTime) {
		this.delayTime = delayTime;
	}
	
	public String getSelectedArrangementID() {
		return this.selectedArrangementID;
	}
	
	public void setSelectedArrangementID(String selectedArrangementID) {
		this.selectedArrangementID = selectedArrangementID;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
