package mgo.controller;

import java.io.IOException;

import mgo.model.DirectoryListing;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileListController {

	private static final Logger LOG = Logger.getLogger(FileListController.class);
	
	@RequestMapping(value = "/list")
    public @ResponseBody DirectoryListing listDirectory(@RequestParam(value="dir", required=true) String directory) throws IOException {		
		LOG.info(String.format("Listing files in directory %s...", directory)); 
				
		return new DirectoryListing(directory);
	}

}
