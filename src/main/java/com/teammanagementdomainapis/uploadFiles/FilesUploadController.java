package com.teammanagementdomainapis.uploadFiles;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

@RestController
public class FilesUploadController {
	private AmazonClient amazonClient;

	@Autowired
	FilesUploadController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/files", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("Create file")
	public String uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
		return this.amazonClient.uploadFile(file);
	}
}
