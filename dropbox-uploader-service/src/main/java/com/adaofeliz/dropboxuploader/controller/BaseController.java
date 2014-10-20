package com.adaofeliz.dropboxuploader.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 20/10/14.
 */
@RestController
@RequestMapping("/*")
public class BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public void baseUrl(HttpServletResponse httpServletResponse)
            throws IOException {

        // Just Redirecting to the Web Interface
        httpServletResponse.sendRedirect("web/");
    }
}
