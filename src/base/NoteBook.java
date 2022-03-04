package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		this.folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders() {
		return this.folders;
	}
	
	public boolean insertNote(String folderName, Note note) {
		Folder f = null;
		for(Folder f1:folders) {
			String name = f1.getName();
			if (name.equals(folderName)) {
				f = f1;
				break;
			}
		}
		if (f == null) {
			f = new Folder(folderName);
			this.folders.add(f);
		}
		for(Note n:f.getNotes()) {
			if (note.equals(n)) {
				System.out.println("Creating note " + note.getTitle() + " under folder " +
						folderName + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
	}
	
	// Overloading method createTextNote
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	
	public void sortFolders() {
		// To Do
		for (Folder folder : this.folders) {
			folder.sortNotes();
		}
		
		Collections.sort(this.folders);
	}
	
	@SuppressWarnings("null")
	public List<Note> searchNotes(String keywords) {
		List<Note> result = new ArrayList<Note>();
		for (Folder i : folders) {
			if (i.searchNotes(keywords) != null)
				result.addAll(i.searchNotes(keywords));
		}
		return result;
	}
}
