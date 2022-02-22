package base;

import java.util.Date;
import java.util.Objects;

public class Note {
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return this.title;
	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(title);
//	}

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
	
	
}
