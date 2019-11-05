package converter;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import converter.api.ConverterAPI;

public class Converter {
	private JFrame converterFrame = null;
	private JTextField txtColToPro6PlxCol = null;
	private JTextField txtColToPro6PlxPro6Plx = null;
	private JTextField txtPro6PlxToColCol = null;
	private JTextField txtPro6PlxToColPro6Plx = null;

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			SwingUtilities.invokeLater(() -> {
				new Converter().converterFrame.setVisible(true);
			});
		} else if (args.length == 3) {
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
	
	public Converter() {
		this.converterFrame = new JFrame("COLConverter");
		this.converterFrame.setBounds(100, 100, 700, 500);
		this.converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.converterFrame.setIconImage(image);
		this.converterFrame.setLayout(null);
		this.converterFrame.setLocationRelativeTo(null);
		this.converterFrame.setResizable(false);
		
		JLabel lblColToPro6PlxCol = new JLabel("<html>SongBeamer-Ablaufplan -> ProPresenter-Ablaufplan (funktioniert nicht richtig):<br>SongBeamer-Ablaufplan:</html>");
		lblColToPro6PlxCol.setBounds(10, 5, 600, 35);
		this.converterFrame.add(lblColToPro6PlxCol);
		
		this.txtColToPro6PlxCol = new JTextField();
		this.txtColToPro6PlxCol.setBounds(10, 40, 640, 25);
		this.txtColToPro6PlxCol.setEditable(false);
		this.converterFrame.add(this.txtColToPro6PlxCol);
		
		JButton btnColToPro6PlxCol = new JButton("...");
		btnColToPro6PlxCol.addActionListener((e) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "SongBeamer-Ablaufplan";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".col");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Text-Dateien";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Dateien";
				}
				@Override
				public boolean accept(File f) {
					return true;
				}
			});
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setDialogTitle("SongBeamer-Ablaufplan öffnen");
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(this.converterFrame) == JFileChooser.APPROVE_OPTION) {
				this.txtColToPro6PlxCol.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
		btnColToPro6PlxCol.setBounds(660, 40, 30, 25);
		this.converterFrame.add(btnColToPro6PlxCol);
		
		JLabel lblColToPro6PlxPro6Plx = new JLabel("ProPresenter-Ablaufplan:");
		lblColToPro6PlxPro6Plx.setBounds(10, 60, 500, 35);
		this.converterFrame.add(lblColToPro6PlxPro6Plx);
		
		this.txtColToPro6PlxPro6Plx = new JTextField();
		this.txtColToPro6PlxPro6Plx.setBounds(10, 90, 640, 25);
		this.txtColToPro6PlxPro6Plx.setEditable(false);
		this.converterFrame.add(this.txtColToPro6PlxPro6Plx);
		
		JButton btnColToPro6PlxPro6Plx = new JButton("...");
		btnColToPro6PlxPro6Plx.addActionListener((e) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "ProPresenter-Ablaufplan";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".pro6plx");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Text-Dateien";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Dateien";
				}
				@Override
				public boolean accept(File f) {
					return true;
				}
			});
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setDialogTitle("ProPresenter-Ablaufplan öffnen");
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(this.converterFrame) == JFileChooser.APPROVE_OPTION) {
				this.txtColToPro6PlxPro6Plx.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
		btnColToPro6PlxPro6Plx.setBounds(660, 90, 30, 25);
		this.converterFrame.add(btnColToPro6PlxPro6Plx);
		
		JButton convertColToPro6Plx = new JButton("SongBeamer-Ablaufplan zu ProPresenter-Ablaufplan konvertieren");
		convertColToPro6Plx.addActionListener((e) -> {
			File destinationFile = new File(this.txtColToPro6PlxPro6Plx.getText());
			File sourceFile = new File(this.txtColToPro6PlxCol.getText());
			try {
				ConverterAPI.writeFile(destinationFile, ConverterAPI.convertColToPro6Plx(sourceFile));
			} catch (IOException ex) {
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
				JOptionPane.showMessageDialog(this.converterFrame, sw.toString(), "Fehler bei der Konvertierung", JOptionPane.ERROR_MESSAGE);
			}
		});
		convertColToPro6Plx.setBounds(10, 130, 680, 25);
		this.converterFrame.add(convertColToPro6Plx);
		
		JLabel lblPro6PlxToColPro6Plx = new JLabel("<html>ProPresenter-Ablaufplan -> SongBeamer-Ablaufplan:<br>ProPresenter-Ablaufplan:</html>");
		lblPro6PlxToColPro6Plx.setBounds(10, 250, 500, 35);
		this.converterFrame.add(lblPro6PlxToColPro6Plx);
		
		this.txtPro6PlxToColPro6Plx = new JTextField();
		this.txtPro6PlxToColPro6Plx.setBounds(10, 290, 640, 25);
		this.txtPro6PlxToColPro6Plx.setEditable(false);
		this.converterFrame.add(this.txtPro6PlxToColPro6Plx);
		
		JButton btnPro6PlxToColPro6Plx = new JButton("...");
		btnPro6PlxToColPro6Plx.addActionListener((e) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "ProPresenter-Ablaufplan";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".pro6plx");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Text-Dateien";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Dateien";
				}
				@Override
				public boolean accept(File f) {
					return true;
				}
			});
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setDialogTitle("ProPresenter-Ablaufplan öffnen");
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(this.converterFrame) == JFileChooser.APPROVE_OPTION) {
				this.txtPro6PlxToColPro6Plx.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
		btnPro6PlxToColPro6Plx.setBounds(660, 290, 30, 25);
		this.converterFrame.add(btnPro6PlxToColPro6Plx);
		
		JLabel lblPro6PlxToColCol = new JLabel("SongBeamer-Ablaufplan:");
		lblPro6PlxToColCol.setBounds(10, 310, 500, 35);
		this.converterFrame.add(lblPro6PlxToColCol);
		
		this.txtPro6PlxToColCol = new JTextField();
		this.txtPro6PlxToColCol.setBounds(10, 340, 640, 25);
		this.txtPro6PlxToColCol.setEditable(false);
		this.converterFrame.add(this.txtPro6PlxToColCol);
		
		JButton btnPro6PlxToColCol = new JButton("...");
		btnPro6PlxToColCol.addActionListener((e) -> {
			JFileChooser jfc = new JFileChooser();
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "SongBeamer-Ablaufplan";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".col");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Text-Dateien";
				}
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
				}
			});
			jfc.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Alle Dateien";
				}
				@Override
				public boolean accept(File f) {
					return true;
				}
			});
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setDialogTitle("ProPresenter-Datei öffnen");
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(this.converterFrame) == JFileChooser.APPROVE_OPTION) {
				this.txtPro6PlxToColCol.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
		btnPro6PlxToColCol.setBounds(660, 340, 30, 25);
		this.converterFrame.add(btnPro6PlxToColCol);
		
		JButton convertPro6PlxToCol = new JButton("ProPresenter-Ablaufplan zu SongBeamer-Ablaufplan konvertieren");
		convertPro6PlxToCol.addActionListener((e) -> {
			File sourceFile = new File(this.txtPro6PlxToColPro6Plx.getText());
			File destinationFile = new File(this.txtPro6PlxToColCol.getText());
			Color c = JColorChooser.showDialog(this.converterFrame, "Hintergrundfarbe auswählen", new Color(35, 35, 38));
			try {
				if (c != null)
					ConverterAPI.writeFile(destinationFile, ConverterAPI.convertPro6PlxToCol(sourceFile, c));
			} catch (IOException ex) {
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
				JOptionPane.showMessageDialog(this.converterFrame, sw.toString(), "Fehler bei der Konvertierung", JOptionPane.ERROR_MESSAGE);
			}
		});
		convertPro6PlxToCol.setBounds(10, 380, 680, 25);
		this.converterFrame.add(convertPro6PlxToCol);
	}
}
