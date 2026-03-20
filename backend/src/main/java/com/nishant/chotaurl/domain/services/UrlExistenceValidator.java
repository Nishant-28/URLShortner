package com.nishant.chotaurl.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlExistenceValidator {
    private static final Logger log = LoggerFactory.getLogger(UrlExistenceValidator.class);

    static boolean isUrlExists(String urlString) {
        try {
            log.debug("Checking if URL exists: {}", urlString);
            URL url = new URI(urlString).toURL();

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400);
        } catch (Exception e) {
            log.error("Error while checking URL: {}", urlString, e);
            return false;
        }
    }
}
