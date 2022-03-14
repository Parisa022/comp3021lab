package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>, java.io.Serializable{
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote (Note note) {
		notes.add(note);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Note> getNotes() {
		return this.notes;
	}
	
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for(Note note : notes) {
			if (note instanceof ImageNote) nImage++;
			else if (note instanceof TextNote) nText++;
		}
		
		return this.name + ":" + nText + ":" + nImage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int compareTo(Folder o) {
		// TODO Auto-generated method stub
//		System.out.println("Folder's compareTo");
		if(this.name.compareTo(o.name) == 0) return 0;
		else if (this.name.compareTo(o.name) < 0) return -1;
		else return 1;
	}
	
	public void sortNotes() {
		//To Do
		Collections.sort(this.notes);
	}
	
	public List<Note> searchNotes(String keywords) {
		List<Note> result = new ArrayList<Note>();
		String[] target = keywords.toLowerCase().split("or|OR");
		for(Note i :notes) {
			String title = i.getTitle().toLowerCase();
			for(String j :target) {
				if (title.contains(j)) {
					result.add(i);
				}
				else if (i instanceof TextNote) {
					if (((TextNote) i).content.toLowerCase().contains(j)) {
						result.add(i);
					}
				}
			}
			
		}
		return result;
	}
}
