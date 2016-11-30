package model;

public class ImageInfo {

	String path, filename;
	float similarity;
	
	public ImageInfo(String path, String filename, float similarity){
		this.path = path;
		this.filename = filename;
		this.similarity = similarity;
	}

	public String getPath() {
		return path;
	}

	public String getFilename() {
		return filename;
	}

	public float getSimilarity() {
		return similarity;
	}
	
}
