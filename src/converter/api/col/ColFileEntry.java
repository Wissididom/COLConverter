package converter.api.col;

public class ColFileEntry {
	private String caption;
	private int color;
	private String fileName;
	
	public ColFileEntry(String caption, int color, String fileName) {
		this.caption = caption;
		this.color = color;
		this.fileName = fileName;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ColFileEntry))
			return false;
		ColFileEntry cfe = (ColFileEntry) obj;
		if (this.caption.equals(cfe.caption) && this.color == cfe.color && this.fileName.equals(cfe.fileName))
			return true;
		return false;
	}
}
