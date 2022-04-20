package base;

import java.util.Date;
import java.util.Objects;

public class Note implements Comparable<Note>, java.io.Serializable {
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return this.title;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)     
			return true;
		if (obj == null)
			return false;
//		System.out.println(obj.getClass());
//		if (getClass() != obj.getClass())
//			return false;
		Note other = (Note) obj;
//		System.out.println(obj.getTitle()+" " +other.getTitle());
		return Objects.equals(title, other.title);
	}

	@Override
	public int compareTo(Note o) {
		// TODO Auto-generated method stub
//		System.out.println("Note's compareTo");
		if (this.date.compareTo(o.date) == 0) return 0;
		else if (this.date.compareTo(o.date) > 0) return -1;
		else return 1;
	}
	
	public String toString() {
		return date.toString() + "\t" + this.title;
	}
	
}
