package com.adaofeliz.dropboxuploader.controller;

import com.adaofeliz.dropboxuploader.service.DropboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 19/10/14.
 */

@RestController
@RequestMapping("/dropbox/*")
public class DropboxController {

    private static final Logger LOG = LoggerFactory.getLogger(DropboxController.class);

    @Autowired
    private DropboxService dropboxService;

    @Value("${dropbox.app.sessionstore.key}")
    private String sessionStoreKey;


    @RequestMapping(value = "/allow", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public void allowDropboxAccessURL(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        // Generate Authorization URL
        String redirectUrl = dropboxService.generateAuthorizationURL(httpServletRequest.getSession());

        // Redirect Request
        LOG.info("Redirecting request to: " + redirectUrl);
        httpServletResponse.setHeader("Location", redirectUrl);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public void checkDropboxAccessURL(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        String redirectUrl =
                dropboxService.saveAccessDetails(httpServletRequest.getParameterMap(), httpServletRequest.getSession());

        LOG.info("Redirecting request to: " + redirectUrl);
        httpServletResponse.setHeader("Location", redirectUrl);
    }

}
