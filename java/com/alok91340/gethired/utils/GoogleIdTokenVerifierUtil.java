/**
 * 
 */
package com.alok91340.gethired.utils;

/**
 * @author aloksingh
 *
 */
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleIdTokenVerifierUtil {

    public static String verifyAndExtractEmail(String googleIdTokenString) {
        // Replace with your own Google API Client ID
        String googleClientId = "YOUR_GOOGLE_CLIENT_ID";

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList(googleClientId))
            .build();

        try {
            GoogleIdToken idToken = verifier.verify(googleIdTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                return payload.getEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ""; // Return null in case of failure or invalid token
    }
}
