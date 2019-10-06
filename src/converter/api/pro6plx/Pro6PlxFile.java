package converter.api.pro6plx;

import converter.api.pro6plx.parser.Pro6PlxTool;

public class Pro6PlxFile {
	private int os;
	private int buildNumber;
	private int versionNumber;
	private RVPlaylistNode playlistNode;
	
	public Pro6PlxFile(int os, int buildNumber, int versionNumber) {
		this(os, buildNumber, versionNumber, null);
	}
	
	public Pro6PlxFile(int os, int buildNumber, int versionNumber, RVPlaylistNode playlistNode) {
		this.os = os;
		this.buildNumber = buildNumber;
		this.versionNumber = versionNumber;
		this.playlistNode = playlistNode;
	}
	
	public int getOS() {
		return this.os;
	}
	
	public void setOS(int os) {
		this.os = os;
	}
	
	public int getBuildNumber() {
		return this.buildNumber;
	}
	
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	public int getVersionNumber() {
		return this.versionNumber;
	}
	
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	public RVPlaylistNode getPlaylistNode() {
		return this.playlistNode;
	}
	
	public void setPlaylistNode(RVPlaylistNode playlistNode) {
		this.playlistNode = playlistNode;
	}
	
	public String getAsPro6Plx() {
		return Pro6PlxTool.generatePro6Plx(this);
	}
	
	public void printAsPro6Plx() {
		System.out.println(this.getAsPro6Plx());
	}
	
	@Override
	public String toString() {
		return this.getAsPro6Plx();
	}
}
