package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import mgo.model.DirectoryListing;

import org.junit.Test;

public class DirectoryListingTest {

	@Test
	public void testDirectoryListing() {
		DirectoryListing dList = new DirectoryListing("abc");
		assertNotNull(dList);
	}

	@Test
	public void testGetDir() {
		DirectoryListing dList = new DirectoryListing("/");
		assertEquals("/", dList.getDir());
	}

	@Test
	public void testGetFiles() {
		DirectoryListing dList = new DirectoryListing("/");
		assertTrue(dList.getFiles().length > 1);
	}

}
