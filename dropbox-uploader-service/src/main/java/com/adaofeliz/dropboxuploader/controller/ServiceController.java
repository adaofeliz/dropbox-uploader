package com.adaofeliz.dropboxuploader.controller;

import com.adaofeliz.dropboxuploader.service.DropboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 18/10/14.
 */

@RestController
@RequestMapping("/service/*")
public class ServiceController {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private DropboxService dropboxService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleFileUpload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file)
            throws Exception {
        if (!file.isEmpty()) {
            LOG.debug(file.getOriginalFilename() + " is not empty, it will proceed to upload method.");
            dropboxService.uploadFile(file, httpServletRequest.getSession());
        } else {
            LOG.error("Upload failed because the file was empty.");
            throw new IllegalArgumentException("Upload failed because the file was empty.");
        }
    }


}
