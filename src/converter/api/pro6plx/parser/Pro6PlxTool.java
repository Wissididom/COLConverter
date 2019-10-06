package converter.api.pro6plx.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import converter.api.ConverterAPI;
import converter.api.pro6plx.Entry;
import converter.api.pro6plx.Pro6PlxFile;
import converter.api.pro6plx.RVDocumentCue;
import converter.api.pro6plx.RVPlaylistNode;

public class Pro6PlxTool {
	
	private static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String inputLine = "", result = "";
		while ((inputLine = br.readLine()) != null) {
			if (result.length() < 1)
				result = inputLine;
			else
				result += "\n" + inputLine;
		}
		br.close();
		return result;
	}
	
	public static Pro6PlxFile parsePro6Plx(File pro6plxFile) throws IOException {
		String pro6plxContent = readFile(pro6plxFile).replace("\uFEFF", ""); // remove BOM
		return Pro6PlxTool.parsePro6Plx(pro6plxContent);
	}
	
	public static Pro6PlxFile parsePro6Plx(String pro6plxContent) {
		pro6plxContent = pro6plxContent.substring(pro6plxContent.indexOf("<RVPlaylistDocument"));
		pro6plxContent = pro6plxContent.substring(0, pro6plxContent.lastIndexOf("</RVPlaylistDocument>") + "</RVPlaylistDocument>".length());
		JSONObject obj = XML.toJSONObject(pro6plxContent).getJSONObject("RVPlaylistDocument");
		int os = 2, buildNumber = 16245, versionNumber = 600;
		if (obj.has("os"))
			os = obj.getInt("os");
		if (obj.has("buildNumber"))
			buildNumber = obj.getInt("buildNumber");
		if (obj.has("versionNumber"))
			versionNumber = obj.getInt("versionNumber");
		obj = obj.getJSONObject("RVPlaylistNode");
		RVPlaylistNode playlistNode = Pro6PlxTool.getPlaylistNode(obj, false, "root", ConverterAPI.getCurrentDateTime(), "", "rootNode", UUID.randomUUID().toString().toUpperCase(), 0, 2);
		JSONArray arr = obj.getJSONArray("array");
		RVPlaylistNode innerPlaylistNode = null;
		boolean hasInner = false;
		RVDocumentCue documentCue = new RVDocumentCue();
		for (int i = 0; i < arr.length(); i++) {
			obj = arr.getJSONObject(i);
			if (obj.has("RVPlaylistNode")) {
				if (obj.has("rvXMLIvarName"))
					playlistNode.setRvXMLIvarName(obj.getString("rvXMLIvarName"));
				obj = obj.getJSONObject("RVPlaylistNode");
				innerPlaylistNode = Pro6PlxTool.getPlaylistNode(obj, false, "default", ConverterAPI.getCurrentDateTime(), "", "", UUID.randomUUID().toString().toUpperCase(), 3, 2);
				hasInner = true;
			}
			if (hasInner) {
				if (obj.has("array")) {
					arr = obj.getJSONArray("array");
					for (int j = 0; j < arr.length(); j++) {
						obj = arr.getJSONObject(j);
						if (obj.has("RVDocumentCue")) {
							if (obj.has("rvXMLIvarName"))
								documentCue.setRvXMLIvarName(obj.getString("rvXMLIvarName"));
							JSONArray arr2 = obj.getJSONArray("RVDocumentCue");
							for (int k = 0; k < arr2.length(); k++) {
								obj = arr2.getJSONObject(k);
								double timeStamp = 0, delayTime = 0;
								int actionType = 0;
								String displayName = "", filePath = "", selectedArrangementID = "", uuid = UUID.randomUUID().toString().toUpperCase();
								boolean enabled = false;
								if (obj.has("timeStamp"))
									timeStamp = obj.getDouble("timeStamp");
								if (obj.has("actionType"))
									actionType = obj.getInt("actionType");
								if (obj.has("displayName"))
									displayName = obj.getString("displayName");
								if (obj.has("filePath"))
									filePath = obj.getString("filePath");
								if (obj.has("delayTime"))
									delayTime = obj.getDouble("delayTime");
								if (obj.has("selectedArrangementID"))
									selectedArrangementID = obj.getString("selectedArrangementID");
								if (obj.has("UUID"))
									uuid = obj.getString("UUID");
								else if (obj.has("uuid"))
									uuid = obj.getString("uuid");
								if (obj.has("enabled"))
									enabled = obj.getBoolean("enabled");
								Entry entry = new Entry(timeStamp, actionType, displayName, filePath, delayTime, selectedArrangementID, uuid, enabled);
								documentCue.addEntry(entry);
							}
						}
					}
				}
			} else {
				if (obj.has("RVDocumentCue")) {
					arr = obj.getJSONArray("RVDocumentCue");
					for (int k = 0; k < arr.length(); k++) {
						obj = arr.getJSONObject(k);
						double timeStamp = 0, delayTime = 0;
						int actionType = 0;
						String displayName = "", filePath = "", selectedArrangementID = "", uuid = UUID.randomUUID().toString().toUpperCase();
						boolean enabled = false;
						if (obj.has("timeStamp"))
							timeStamp = obj.getDouble("timeStamp");
						if (obj.has("actionType"))
							actionType = obj.getInt("actionType");
						if (obj.has("displayName"))
							displayName = obj.getString("displayName");
						if (obj.has("filePath"))
							filePath = obj.getString("filePath");
						if (obj.has("delayTime"))
							delayTime = obj.getDouble("delayTime");
						if (obj.has("selectedArrangementID"))
							selectedArrangementID = obj.getString("selectedArrangementID");
						if (obj.has("UUID"))
							selectedArrangementID = obj.getString("UUID");
						else if (obj.has("uuid"))
							selectedArrangementID = obj.getString("uuid");
						if (obj.has("enabled"))
							enabled = obj.getBoolean("enabled");
						Entry entry = new Entry(timeStamp, actionType, displayName, filePath, delayTime, selectedArrangementID, uuid, enabled);
						documentCue.addEntry(entry);
					}
				}
			}
		}
		if (hasInner) {
			innerPlaylistNode.setDocumentCue(documentCue);
			playlistNode.setPlaylistNode(innerPlaylistNode);
		} else {
			playlistNode.setDocumentCue(documentCue);
		}
		return new Pro6PlxFile(os, buildNumber, versionNumber, playlistNode);
	}
	
	private static RVPlaylistNode getPlaylistNode(JSONObject obj, boolean defaultExpanded, String defaultDisplayName, String defaultModifiedDate, String defaultSmartDirectoryURL, String defaultRvXMLIvarName, String defaultUUID, int defaultType, int defaultHotFolderType) {
		RVPlaylistNode playlistNode = new RVPlaylistNode();
		if (obj.has("isExpanded"))
			playlistNode.setExpanded(obj.getBoolean("isExpanded"));
		else
			playlistNode.setExpanded(defaultExpanded);
		if (obj.has("displayName"))
			playlistNode.setDisplayName(obj.getString("displayName"));
		else
			playlistNode.setDisplayName(defaultDisplayName);
		if (obj.has("modifiedDate"))
			playlistNode.setModifiedDate(obj.getString("modifiedDate"));
		else
			playlistNode.setModifiedDate(defaultModifiedDate);
		if (obj.has("smartDirectoryURL"))
			playlistNode.setSmartDirectoryURL(obj.getString("smartDirectoryURL"));
		else
			playlistNode.setSmartDirectoryURL(defaultSmartDirectoryURL);
		if (obj.has("rvXMLIvarName"))
			playlistNode.setRvXMLIvarName(obj.getString("rvXMLIvarName"));
		else
			playlistNode.setRvXMLIvarName(defaultRvXMLIvarName);
		if (obj.has("uuid"))
			playlistNode.setUUID(obj.getString("uuid"));
		else if (obj.has("UUID"))
			playlistNode.setUUID(obj.getString("UUID"));
		else
			playlistNode.setUUID(defaultUUID);
		if (obj.has("type"))
			playlistNode.setType(obj.getInt("type"));
		else
			playlistNode.setType(defaultType);
		if (obj.has("hotFolderType"))
			playlistNode.setHotFolderType(obj.getInt("hotFolderType"));
		else
			playlistNode.setHotFolderType(defaultHotFolderType);
		return playlistNode;
	}
	
	public static void generatePro6Plx(Pro6PlxFile entries, String pro6plxFilePath) throws IOException {
		Pro6PlxTool.generatePro6Plx(entries, new File(pro6plxFilePath));
	}
	
	public static void generatePro6Plx(Pro6PlxFile entries, File pro6plxFile) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pro6plxFile)));
		bw.write(Pro6PlxTool.generatePro6Plx(entries));
		bw.flush();
		bw.close();
	}
	
	public static String generatePro6Plx(Pro6PlxFile entries) {
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("<RVPlaylistDocument versionNumber=\"");
		sb.append(entries.getVersionNumber());
		sb.append("\" os=\"");
		sb.append(entries.getOS());
		sb.append("\" buildNumber\"");
		sb.append(entries.getBuildNumber());
		sb.append("\">\n");
		indent += 4;
		RVPlaylistNode playlistNode = entries.getPlaylistNode();
		sb.append(space(indent)).append("<RVPlaylistNode displayName=\"");
		sb.append(playlistNode.getDisplayName());
		sb.append("\" UUID=\"");
		sb.append(playlistNode.getUUID());
		sb.append("\" smartDirectoryURL=\"");
		sb.append(playlistNode.getSmartDirectoryURL());
		sb.append("\" modifiedDate=\"");
		sb.append(playlistNode.getModifiedDate());
		sb.append("\" type=\"");
		sb.append(playlistNode.getType());
		sb.append("\" isExpanded=\"");
		sb.append(playlistNode.isExpanded());
		sb.append("\" hotFolderType=\"");
		sb.append(playlistNode.getHotFolderType());
		sb.append("\" rvXMLIvarName=\"");
		sb.append(playlistNode.getRvXMLIvarName());
		sb.append("\">\n");
		indent += 4;
		sb.append(space(indent)).append("<array rvXMLIvarName=\"");
		if (playlistNode.getPlaylistNode() == null) {
			RVDocumentCue documentCue = playlistNode.getDocumentCue();
			sb.append(documentCue.getRvXMLIvarName());
			sb.append("\">\n");
			indent += 4;
			Entry[] e = documentCue.getEntries();
			for (Entry entry : e) {
				sb.append(space(indent)).append("<RVDocumentCue UUID=\"");
				sb.append(entry.getUUID());
				sb.append("\" displayName=\"");
				sb.append(entry.getDisplayName());
				sb.append("\" actionType=\"");
				sb.append(entry.getActionType());
				sb.append("\" enabled=\"");
				sb.append(entry.isEnabled());
				sb.append("\" timeStamp=\"");
				sb.append(entry.getTimeStamp());
				sb.append("\" delayTime=\"");
				sb.append(entry.getDelayTime());
				sb.append("\" filePath=\"");
				sb.append(entry.getFilePath());
				sb.append("\" selectedArrangementID=\"");
				sb.append(entry.getSelectedArrangementID());
				sb.append("\"/>\n");
			}
			indent -= 4;
			sb.append(space(indent)).append("</array>\n");
			sb.append(space(indent)).append("<array rvXMLIvarName=\"events\"/>\n");
		} else {
			sb.append(playlistNode.getRvXMLIvarName());
			playlistNode = playlistNode.getPlaylistNode();
			sb.append("\">\n");
			indent += 4;
			sb.append(space(indent)).append("<RVPlaylistNode displayName=\"");
			sb.append(playlistNode.getDisplayName());
			sb.append("\" UUID=\"");
			sb.append(playlistNode.getUUID());
			sb.append("\" smartDirectoryURL=\"");
			sb.append(playlistNode.getSmartDirectoryURL());
			sb.append("\" modifiedDate=\"");
			sb.append(playlistNode.getModifiedDate());
			sb.append("\" type=\"");
			sb.append(playlistNode.getType());
			sb.append("\" isExpanded=\"");
			sb.append(playlistNode.isExpanded());
			sb.append("\" hotFolderType=\"");
			sb.append(playlistNode.getHotFolderType());
			sb.append("\" rvXMLIvarName=\"");
			sb.append(playlistNode.getRvXMLIvarName());
			sb.append("\">\n");
			indent += 4;
			sb.append(space(indent)).append("<array rvXMLIvarName=\"");
			RVDocumentCue documentCue = playlistNode.getDocumentCue();
			sb.append(documentCue.getRvXMLIvarName());
			sb.append("\">\n");
			indent += 4;
			Entry[] e = documentCue.getEntries();
			for (Entry entry : e) {
				sb.append(space(indent)).append("<RVDocumentCue UUID=\"");
				sb.append(entry.getUUID());
				sb.append("\" displayName=\"");
				sb.append(entry.getDisplayName());
				sb.append("\" actionType=\"");
				sb.append(entry.getActionType());
				sb.append("\" enabled=\"");
				sb.append(entry.isEnabled());
				sb.append("\" timeStamp=\"");
				sb.append(entry.getTimeStamp());
				sb.append("\" delayTime=\"");
				sb.append(entry.getDelayTime());
				sb.append("\" filePath=\"");
				sb.append(entry.getFilePath());
				sb.append("\" selectedArrangementID=\"");
				sb.append(entry.getSelectedArrangementID());
				sb.append("\"/>\n");
			}
			indent -= 4;
			sb.append(space(indent)).append("</array>\n");
			sb.append(space(indent)).append("<array rvXMLIvarName=\"events\"/>\n");
			indent -= 4;
			sb.append(space(indent)).append("</RVPlaylistNode>\n");
			indent -= 4;
			sb.append(space(indent)).append("</array>\n");
			sb.append(space(indent)).append("<array rvXMLIvarName=\"events\"/>\n");
		}
		indent -= 4;
		sb.append(space(indent)).append("</RVPlaylistNode>\n");
		sb.append(space(indent)).append("<array rvXMLIvarName=\"deletions\"/>\n");
		sb.append(space(indent)).append("<array rvXMLIvarName=\"tags\"/>\n");
		indent -= 4;
		sb.append(space(indent)).append("</RVPlaylistDocument>\n");
		return sb.toString();
	}
	
	private static String space(int count) {
		String result = "";
		for (int i = 0; i < count; i++)
			result += " ";
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		Pro6PlxFile ppf = Pro6PlxTool.parsePro6Plx(new File("/home/dominik/Dokumente/default.pro6plx"));
		System.out.println(Pro6PlxTool.generatePro6Plx(ppf));
	}
}
