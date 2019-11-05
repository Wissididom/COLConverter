package converter.api;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import org.mozilla.universalchardet.ReaderFactory;

import converter.api.col.ColFile;
import converter.api.col.ColFileEntry;
import converter.api.col.parser.ColTool;
import converter.api.pro6plx.Entry;
import converter.api.pro6plx.Pro6PlxFile;
import converter.api.pro6plx.RVDocumentCue;
import converter.api.pro6plx.RVPlaylistNode;
import converter.api.pro6plx.parser.Pro6PlxTool;

public class ConverterAPI {
	
	public static String getCurrentDateTime() {
		// String time = Instant.now().atZone(ZoneId.of("Europe/Berlin")).toString();
		 String time = Instant.now().atZone(ZoneId.systemDefault()).toString();
		return time.substring(0, time.indexOf('['));
	}
	
	public static String readFile(File file) throws IOException {
		// BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br = ReaderFactory.createBufferedReader(file);
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
	
	public static void writeFile(File file, String content) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		bw.write(content);
		bw.flush();
		bw.close();
	}
	
	public static String convertPro6PlxToCol(File pro6plxFile, Color color) throws IOException {
		return ConverterAPI.convertPro6PlxToCol(ConverterAPI.readFile(pro6plxFile), color);
	}
	
	public static String convertPro6PlxToCol(String pro6plxXml, Color color) throws IOException {
		return ConverterAPI.convertPro6PlxToCol(Pro6PlxTool.parsePro6Plx(pro6plxXml), color).getAsCol();
	}
	
	public static ColFile convertPro6PlxToCol(Pro6PlxFile pro6plxFile, Color color) throws IOException {
		ColFile cf = new ColFile();
		RVPlaylistNode playlistNode = pro6plxFile.getPlaylistNode();
		if (playlistNode.getPlaylistNode() != null)
			playlistNode = playlistNode.getPlaylistNode();
		RVDocumentCue documentCue = playlistNode.getDocumentCue();
		for (Entry entry : documentCue.getEntries())
			cf.addEntry(new ColFileEntry(entry.getDisplayName(), color.getRGB(), entry.getFilePath()));
		return cf;
	}
	
	public static String convertColToPro6Plx(File colFile) throws IOException {
		return ConverterAPI.convertColToPro6Plx(ConverterAPI.readFile(colFile), ConverterAPI.last(colFile.getAbsolutePath().split("(?:\\/|\\\\)")));
	}
	
	private static String last(String... arr) {
		return arr[arr.length - 1];
	}
	
	public static String convertColToPro6Plx(String colContent, String title) throws IOException {
		return ConverterAPI.convertColToPro6Plx(ColTool.parseCol(colContent), title).getAsPro6Plx();
	}
	
	public static Pro6PlxFile convertColToPro6Plx(ColFile colFile, String title) throws IOException {
		Pro6PlxFile ppf = new Pro6PlxFile(2, 16245, 600);
		RVPlaylistNode playlistNode1 = new RVPlaylistNode(false, "root", ConverterAPI.getCurrentDateTime(), "", "children", UUID.randomUUID().toString().toUpperCase(), 0, 2);
		RVPlaylistNode playlistNode2 = new RVPlaylistNode(false, title, ConverterAPI.getCurrentDateTime(), "", "", UUID.randomUUID().toString().toUpperCase(), 3, 2);
		RVDocumentCue documentCue = new RVDocumentCue("children");
		for (ColFileEntry entry : colFile.getEntries())
			documentCue.addEntry(new Entry(0, 0, entry.getCaption(), entry.getFileName(), 0, "", UUID.randomUUID().toString().toUpperCase(), false));
		playlistNode2.setDocumentCue(documentCue);
		playlistNode1.setPlaylistNode(playlistNode2);
		ppf.setPlaylistNode(playlistNode1);
		return ppf;
	}
}
