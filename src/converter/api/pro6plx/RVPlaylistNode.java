package converter.api.pro6plx;

import java.util.UUID;

import converter.api.ConverterAPI;

public class RVPlaylistNode {
	private boolean expanded;
	private String displayName;
	private String modifiedDate;
	private String smartDirectoryURL;
	private String rvXMLIvarName;
	private String uuid;
	private int type;
	private int hotFolderType;
	private RVPlaylistNode playlistNode;
	private RVDocumentCue documentCue;
	
	public RVPlaylistNode() {
		this(false, "default", ConverterAPI.getCurrentDateTime(), "", "", UUID.randomUUID().toString().toUpperCase(), 0, 2);
	}
	
	public RVPlaylistNode(boolean expanded, String displayName, String modifiedDate, String smartDirectoryURL, String rvXMLIvarName, String uuid, int type, int hotFolderType) {
		this.expanded = expanded;
		this.displayName = displayName;
		this.modifiedDate = modifiedDate;
		this.smartDirectoryURL = smartDirectoryURL;
		this.rvXMLIvarName = rvXMLIvarName;
		this.uuid = uuid;
		this.type = type;
		this.hotFolderType = hotFolderType;
		this.playlistNode = null;
		this.documentCue = null;
	}
	
	public boolean isExpanded() {
		return this.expanded;
	}
	
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getModifiedDate() {
		return this.modifiedDate;
	}
	
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getSmartDirectoryURL() {
		return this.smartDirectoryURL;
	}
	
	public void setSmartDirectoryURL(String smartDirectoryURL) {
		this.smartDirectoryURL = smartDirectoryURL;
	}
	
	public String getRvXMLIvarName() {
		return this.rvXMLIvarName;
	}
	
	public void setRvXMLIvarName(String rvXMLIvarName) {
		this.rvXMLIvarName = rvXMLIvarName;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getHotFolderType() {
		return this.hotFolderType;
	}
	
	public void setHotFolderType(int hotFolderType) {
		this.hotFolderType = hotFolderType;
	}
	
	public RVPlaylistNode getPlaylistNode() {
		return this.playlistNode;
	}
	
	public void setPlaylistNode(RVPlaylistNode playlistNode) {
		this.playlistNode = playlistNode;
	}
	
	public RVDocumentCue getDocumentCue() {
		return this.documentCue;
	}
	
	public void setDocumentCue(RVDocumentCue documentCue) {
		this.documentCue = documentCue;
	}
}
