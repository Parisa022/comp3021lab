package base;

import java.io.*;

public class TextNote extends Note implements java.io.Serializable{
	String content;
	
	public TextNote(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public TextNote(String title, String content) {
		this(title);
		this.content = content;
	}
	
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath) {
		// TODO Auto-generated method stub
		String result = "";
		BufferedReader objReader = null;
		try {
			objReader = new BufferedReader(new FileReader(absolutePath));
			while (objReader.ready()) {
				result += objReader.readLine();
			}
			objReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void exportTextToFile(String pathFolder) {
		BufferedWriter bw = null;
		FileWriter file = null;
		String pathname = "";
		if (!pathFolder.equals("")) pathname = pathFolder + File.separator;
		File f = new File(pathname + this.getTitle().replaceAll(" ","_") +".txt");
		try {
			if (!f.exists()) f.createNewFile();
			file = new FileWriter(f.getAbsolutePath());
			bw = new BufferedWriter(file);  
			bw.write(this.content);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
