package com.adaofeliz.dropboxuploader.service;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxWriteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * Created on 18/10/14.
 */

@Service
public class DropboxService {

    private static final Logger LOG = LoggerFactory.getLogger(DropboxService.class);

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    @Value("${dropbox.app.sessionstore.key}")
    private String sessionStoreKey;

    @Value("${app.base.url}")
    private String appBaseURL;

    public void uploadFile(MultipartFile file, HttpSession session) throws Exception {

        if (session.getAttribute(sessionStoreKey) == null) {
            throw new IllegalAccessException("Please allow access first: " + generateAppRedirectAuthorizationURL());
        }

        String name = file.getOriginalFilename();

        try {

            DbxSessionStore dbxSessionStore = new DbxStandardSessionStore(session, sessionStoreKey);

            DbxRequestConfig config = new DbxRequestConfig("SaCO", Locale.getDefault().toString());
            DbxClient client = new DbxClient(config, dbxSessionStore.get());

            InputStream inputStream = file.getInputStream();
            try {
                DbxEntry.File uploadedFile =
                        client.uploadFile(
                                "/" + name,
                                DbxWriteMode.add(),
                                file.getSize(),
                                inputStream);
                LOG.info("Uploaded: " + uploadedFile.toString());
            } finally {
                inputStream.close();
            }

            LOG.info("Uploaded successfully " + name);
        } catch (Exception e) {
            LOG.error("Upload failed " + name + " => " + e.getMessage());
            throw e;
        }
    }

    public String generateAppRedirectAuthorizationURL() {
        return appBaseURL + "/dropbox/allow";
    }

    public String generateAuthorizationURL(HttpSession session) {
        DbxWebAuth webAuth = createDbxWebAuth(session);

        String authorizeUrl = webAuth.start();
        LOG.info("Authorization URL generated: " + authorizeUrl);
        return authorizeUrl;
    }

    public String saveAccessDetails(Map<String, String[]> parameterMap, HttpSession session) throws Exception {

        if (parameterMap.containsKey("error")) {
            throw new Exception(Arrays.toString(parameterMap.get("error_description")));
        }

        DbxWebAuth webAuth = createDbxWebAuth(session);
        DbxAuthFinish dbxAuthFinish = webAuth.finish(parameterMap);

        DbxSessionStore dbxSessionStore = new DbxStandardSessionStore(session, sessionStoreKey);
        dbxSessionStore.set(dbxAuthFinish.accessToken);

        return appBaseURL;
    }

    private DbxWebAuth createDbxWebAuth(HttpSession session) {
        // Application Information
        DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);
        DbxRequestConfig config = new DbxRequestConfig("SaCO/1.0", Locale.getDefault().toString());

        // Request Information
        DbxSessionStore dbxSessionStore = new DbxStandardSessionStore(session, sessionStoreKey);
        return new DbxWebAuth(config, appInfo, appBaseURL + "/dropbox/check", dbxSessionStore);
    }
}
