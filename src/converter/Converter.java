package converter;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import converter.api.ConverterAPI;

public class Converter {

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			switch (args[0].toLowerCase()) {
				case "pro6plx":
					ConverterAPI.writeFile(new File(args[2]), ConverterAPI.convertColToPro6Plx(new File(args[1])));
					break;
				case "col":
					System.err.println("Please don't omit the color (r g b a at the end)!");
					break;
				default:
					System.err.println("Please only use pro6plx or col as targetformat!");
					break;
			}
		} else if (args.length == 4 || args.length == 6 || args.length == 7) {
			switch (args[0].toLowerCase()) {
				case "pro6plx":
					System.err.println("Please omit the color (r g b a at the end)!");
					break;
				case "col":
					Color color;
					if (args.length == 4)
						color = new Color(Integer.parseInt(args[3]));
					else if (args.length == 6)
						color = new Color(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
					else
						color = new Color(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
					ConverterAPI.writeFile(new File(args[2]), ConverterAPI.convertPro6PlxToCol(new File(args[1]), color));
					break;
				default:
					System.err.println("Please only use pro6plx or col as targetformat!");
					break;
			}
		} else {
			System.err.println("Usage: java -jar SNGConverter.jar targetformat(pro6plx/col) infile outfile (r g b a -> Only when targetformat is col)!");
		}
	}
}
