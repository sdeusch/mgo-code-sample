package sun.model;

import java.io.File;

public class DirectoryListing {

	private String dir;
	private String[] files;

	public DirectoryListing(String dir) {
		super();
		this.dir = dir;

		File dFile = new File(dir);
		this.files =  dFile.list();
	}

	public String getDir() {
		return dir;
	}

	public String[] getFiles() {
		return files;
	}

}
