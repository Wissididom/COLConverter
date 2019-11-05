package converter.api.col.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.api.ConverterAPI;
import converter.api.col.ColFile;
import converter.api.col.ColFileEntry;

public class ColTool {
	
	public static ColFile parseCol(File colFile) throws IOException {
		String colContent = ConverterAPI.readFile(colFile).replace("\uFEFF", ""); // remove BOM
		return ColTool.parseCol(colContent);
	}
	
	public static ColFile parseCol(String colContent) {
		colContent = colContent.replace("\r\n", "\n").replace('\r', '\n');
		Pattern p = Pattern.compile("item\\n((?:.|[\\n])+?)end");
		Matcher m = p.matcher(colContent);
		ColFile cf = new ColFile();
		while (m.find()) {
			String group = m.group(1);
			String[] lines = group.split("\n");
			String caption = "";
			int color = 0;
			String fileName = "";
			for (String line : lines) {
				line = line.trim();
				if (line.toLowerCase().startsWith("caption") && line.contains("=")) {
					if (line.contains("'"))
						caption = line.substring(line.indexOf('\'') + 1).trim();
					else
						caption = line.substring(line.indexOf('=') + 1).trim();
					if (caption.endsWith("'"))
						caption = caption.substring(0, caption.lastIndexOf('\''));
				}
				if (line.toLowerCase().startsWith("color") && line.contains("="))
					color = ColTool.getColor(line.substring(line.indexOf('=') + 1).trim());
				if (line.toLowerCase().startsWith("filename") && line.contains("=")) {
					if (line.contains("'"))
						fileName = line.substring(line.indexOf('\'') + 1).trim();
					else
						fileName = line.substring(line.indexOf('=') + 1).trim();
					if (fileName.endsWith("'"))
						fileName = fileName.substring(0, fileName.lastIndexOf('\''));
				}
			}
			cf.addEntry(new ColFileEntry(caption, color, fileName));
		}
		return cf;
	}
	
	private static int getColor(String color) {
		color = color.trim();
		if (color.equalsIgnoreCase("clBlack"))
			return Integer.parseInt("000000", 16);
		if (color.equalsIgnoreCase("clMaroon"))
			return Integer.parseInt("000080", 16);
		if (color.equalsIgnoreCase("clGreen"))
			return Integer.parseInt("008000", 16);
		if (color.equalsIgnoreCase("clOlive"))
			return Integer.parseInt("008080", 16);
		if (color.equalsIgnoreCase("clNavy"))
			return Integer.parseInt("800000", 16);
		if (color.equalsIgnoreCase("clPurple"))
			return Integer.parseInt("800080", 16);
		if (color.equalsIgnoreCase("clTeal"))
			return Integer.parseInt("808000", 16);
		if (color.equalsIgnoreCase("clGray"))
			return Integer.parseInt("808080", 16);
		if (color.equalsIgnoreCase("clSilver"))
			return Integer.parseInt("C0C0C0", 16);
		if (color.equalsIgnoreCase("clRed"))
			return Integer.parseInt("0000FF", 16);
		if (color.equalsIgnoreCase("clLime"))
			return Integer.parseInt("00FF00", 16);
		if (color.equalsIgnoreCase("clYellow"))
			return Integer.parseInt("00FFFF", 16);
		if (color.equalsIgnoreCase("clBlue"))
			return Integer.parseInt("FF0000", 16);
		if (color.equalsIgnoreCase("clFuchsia"))
			return Integer.parseInt("FF00FF", 16);
		if (color.equalsIgnoreCase("clAqua"))
			return Integer.parseInt("FFFF00", 16);
		if (color.equalsIgnoreCase("clLtGray") || color.equalsIgnoreCase("clLightGray"))
			return Integer.parseInt("C0C0C0", 16);
		if (color.equalsIgnoreCase("clDkGray") || color.equalsIgnoreCase("clDarkGray"))
			return Integer.parseInt("808080", 16);
		if (color.equalsIgnoreCase("clWhite"))
			return Integer.parseInt("FFFFFF", 16);
		if (color.equalsIgnoreCase("clCream"))
			return Integer.parseInt("F0FBFF", 16);
		if (color.equalsIgnoreCase("clMedGray") || color.equalsIgnoreCase("clMediumGray"))
			return Integer.parseInt("A4A0A0", 16);
		if (color.equalsIgnoreCase("clMoneyGreen"))
			return Integer.parseInt("C0DCC0", 16);
		if (color.equalsIgnoreCase("clSkyBlue"))
			return Integer.parseInt("F0CAA6", 16);
		return Integer.parseInt(color);
	}
	
	public static void generateCol(ColFile entries, String colFilePath) throws IOException {
		ColTool.generateCol(entries, new File(colFilePath));
	}
	
	public static void generateCol(ColFile entries, File colFile) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(colFile)));
		bw.write(ColTool.generateCol(entries));
		bw.flush();
		bw.close();
	}
	
	public static String generateCol(ColFile entries) {
		StringBuilder sb = new StringBuilder("object AblaufPlanItems: TAblaufPlanItems\n");
		int indent = 2;
		sb.append(space(indent)).append("items = <");
		indent += 2;
		for (ColFileEntry entry : entries.getEntries()) {
			sb.append("\n").append(space(indent)).append("item\n");
			indent += 2;
			sb.append(space(indent)).append("Caption = '");
			sb.append(entry.getCaption()).append("'\n");
			sb.append(space(indent)).append("Color = ");
			sb.append(ColTool.getColor(entry.getColor())).append("\n");
			sb.append(space(indent)).append("FileName = '");
			sb.append(entry.getFileName()).append("'\n");
			indent -= 2;
			sb.append(space(indent)).append("end");
		}
		sb.append(">\n");
		indent -= 2;
		sb.append(space(indent)).append("end");
		return sb.toString();
	}
	
	private static String space(int count) {
		String result = "";
		for (int i = 0; i < count; i++)
			result += " ";
		return result;
	}
	
	private static String getColor(int colorCode) {
		String hex = Integer.toHexString(colorCode).toUpperCase();
		System.out.println(hex);
		if (hex.equalsIgnoreCase("000000"))
			return "clBlack";
		if (hex.equalsIgnoreCase("000080"))
			return "clMaroon";
		if (hex.equalsIgnoreCase("008000"))
			return "clGreen";
		if (hex.equalsIgnoreCase("008080"))
			return "clOlive";
		if (hex.equalsIgnoreCase("800000"))
			return "clNavy";
		if (hex.equalsIgnoreCase("800080"))
			return "clPurple";
		if (hex.equalsIgnoreCase("808000"))
			return "clTeal";
		if (hex.equalsIgnoreCase("808080"))
			return "clGray";
		if (hex.equalsIgnoreCase("C0C0C0"))
			return "clSilver";
		if (hex.equalsIgnoreCase("0000FF"))
			return "clRed";
		if (hex.equalsIgnoreCase("00FF00"))
			return "clLime";
		if (hex.equalsIgnoreCase("00FFFF"))
			return "clYellow";
		if (hex.equalsIgnoreCase("FF0000"))
			return "clBlue";
		if (hex.equalsIgnoreCase("FF00FF"))
			return "clFuchsia";
		if (hex.equalsIgnoreCase("FFFF00"))
			return "clAqua";
		if (hex.equalsIgnoreCase("C0C0C0"))
			return "clLtGray";
		if (hex.equalsIgnoreCase("808080"))
			return "clDkGray";
		if (hex.equalsIgnoreCase("FFFFFF"))
			return "clWhite";
		if (hex.equalsIgnoreCase("F0FBFF"))
			return "clCream";
		if (hex.equalsIgnoreCase("A4A0A0"))
			return "clMedGray";
		if (hex.equalsIgnoreCase("C0DCC0"))
			return "clMoneyGreen";
		if (hex.equalsIgnoreCase("F0CAA6"))
			return "clSkyBlue";
		return Integer.toString(colorCode);
	}
	
	public static String generateCol(ColFileEntry... entries) {
		return new ColFile(entries).getAsCol();
	}
	
	public static void main(String[] args) throws IOException {
		ColFile cf = ColTool.parseCol(new File("/home/dominik/Dokumente/Standard-Ablaufplan.col"));
		System.out.println(ColTool.generateCol(cf));
		System.out.println("--------------------------------------");
		System.out.println(ColTool.generateCol(cf.getEntries()));
	}
}
