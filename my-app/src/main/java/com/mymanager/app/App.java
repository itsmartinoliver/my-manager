package com.mymanager.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class App {
    public static void main(String[] args) throws Exception {

        String code = ""; // This is the code that is returned by Google upon explicit user permission
        String client_id = "507189714228-7egic49gmbp51gj9j0u6buemkttdmtp3.apps.googleusercontent.com";
        String redirect_uri = "http://localhost:8080/";
        String code_verifier = ""; // This is the code verifier that generates the code_challenge via SHA256
        
        // Google appears to misimplement PKCE and requires a client secret to be sent.
        // Revealing this secret to users should pose no actual security threat as clients cannot
        //      actually do anything with it unless they were given a code which requires explicit
        //      user permission anyway.
        // If I am misunderstanding this and revealing the secret is a genuine issue,
        //      please email itsmartinoliver@gmail.com to let me know.
        String client_secret = "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";

        String body = "code="
            .concat(code)
            .concat("&client_id=")
            .concat(client_id)
            .concat("&redirect_uri=")
            .concat(redirect_uri)
            .concat("&grant_type=authorization_code&code_verifier=")
            .concat(code_verifier)
            .concat("&client_secret=")
            .concat(client_secret);

        new App().post("https://oauth2.googleapis.com/token", body);
    }

    public void get(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .build();

        HttpResponse<String> response =
            client.send(request, BodyHandlers.ofString());

        System.out.println(response.body());
    }

    // Post request with Content-Type assumption of application/x-www-form-urlencoded
    public void post(String uri, String body) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response =
            client.send(request, BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println(response.body());
    }
}
