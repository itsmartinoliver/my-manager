package com.mymanager.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.mymanager.util.CodeChallenge;
import com.mymanager.util.RandomString;

public class App {
    public static void main(String[] args) throws Exception {

        String code; // This is the code that is returned by Google upon explicit user permission
        String client_id = "507189714228-da7rij1t8vmik723s9qipi8ha2ls2nhh.apps.googleusercontent.com";
        // Google appears to misimplement PKCE and requires a client secret to be sent.
        // Revealing this secret to users should pose no actual security threat as clients cannot
        //      actually do anything with it unless they were given a code which requires explicit
        //      user permission anyway.
        // If I am misunderstanding this and revealing the secret is a genuine issue,
        //      please email itsmartinoliver@gmail.com to let me know.
        String client_secret = "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";
        String redirect_uri = "http://localhost:8080/";
        String code_verifier = new RandomString().randomString(48);
        String code_challenge = new CodeChallenge().hash(code_verifier);

        String userLink = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri="
            .concat(redirect_uri)
            .concat("&prompt=consent&response_type=code&client_id=")
            .concat(client_id)
            .concat("&scope=https://www.googleapis.com/auth/calendar&access_type=offline&code_challenge=")
            .concat(code_challenge)
            .concat("&code_challenge_method=S256");

        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Please visit the following link in your browser and paste the code below:");
        System.out.println(userLink);
        code = myScanner.nextLine();  // Read user input

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

        System.out.println(body);
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
