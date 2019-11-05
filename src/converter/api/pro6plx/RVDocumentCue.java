package converter.api.pro6plx;

import java.util.ArrayList;
import java.util.List;

public class RVDocumentCue {
	private String rvXMLIvarName;
	private List<Entry> entries = new ArrayList<Entry>();
	
	public RVDocumentCue() {}
	
	public RVDocumentCue(String rvXMLIvarName, Entry... entries) {
		this.rvXMLIvarName = rvXMLIvarName;
		for (Entry entry : entries)
			this.entries.add(entry);
	}
	
	public String getRvXMLIvarName() {
		return this.rvXMLIvarName;
	}
	
	public void setRvXMLIvarName(String rvXMLIvarName) {
		this.rvXMLIvarName = rvXMLIvarName;
	}
	
	public Entry[] getEntries() {
		return this.entries.toArray(new Entry[this.entries.size()]);
	}
	
	public RVDocumentCue addEntry(Entry entry) {
		this.entries.add(entry);
		return this;
	}
	
	public RVDocumentCue removeEntry(Entry entry) {
		this.entries.remove(entry);
		return this;
	}
	
	public RVDocumentCue removeEntry(int index) {
		this.entries.remove(index);
		return this;
	}
	
	public int getEntryCount() {
		return this.entries.size();
	}
	
	public void setEntries(Entry... entries) {
		this.entries.clear();
		for (Entry entry : entries)
			this.entries.add(entry);
	}
}
