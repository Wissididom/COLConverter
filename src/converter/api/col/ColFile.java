package converter.api.col;

import java.util.ArrayList;
import java.util.List;

import converter.api.col.parser.ColTool;

public class ColFile {
	private List<ColFileEntry> entries = new ArrayList<ColFileEntry>();
	
	public ColFile() {}
	
	public ColFile(ColFileEntry... entries) {
		for (ColFileEntry entry : entries)
			this.entries.add(entry);
	}
	
	public ColFileEntry[] getEntries() {
		return this.entries.toArray(new ColFileEntry[this.entries.size()]);
	}
	
	public ColFileEntry getEntry(String caption) {
		for (ColFileEntry entry : this.entries)
			if (entry.getCaption().equalsIgnoreCase(caption))
				return entry;
		return null;
	}
	
	public ColFile addEntry(ColFileEntry entry) {
		this.entries.add(entry);
		return this;
	}
	
	public boolean containsEntry(String caption) {
		for (ColFileEntry entry : this.entries)
			if (entry.getCaption().equals(caption))
				return true;
		return false;
	}
	
	public boolean containsEntry(String caption, int color, String fileName) {
		return this.containsEntry(new ColFileEntry(caption, color, fileName));
	}
	
	public boolean containsEntry(ColFileEntry entry) {
		for (ColFileEntry e : this.entries)
			if (e.equals(entry))
				return true;
		return false;
	}
	
	public String getAsCol() {
		return ColTool.generateCol(this);
	}
	
	public void printAsSng() {
		System.out.println(this.getAsCol());
	}
	
	@Override
	public String toString() {
		return this.getAsCol();
	}
}
