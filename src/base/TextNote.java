package base;

public class TextNote extends Note {
	String content;
	
	public TextNote(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	public TextNote(String title, String content) {
		this(title);
		this.content = content;
	}
	
}
