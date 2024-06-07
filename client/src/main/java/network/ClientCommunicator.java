package network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {

    private void read(InputStream responseBody, String response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));
        String line;

        while ((line = reader.readLine()) != null) {
            response += line;
        }

        reader.close();
    }

    private String handleResponse(HttpURLConnection connection) throws IOException {
        String response = "";

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream responseBody = connection.getInputStream()) {
                read(responseBody, response);
            }
        }
        else {
            try (InputStream responseBody = connection.getErrorStream()) {
                read(responseBody, response);
            }
        }
        return response;
    }

    private String registerOrLogin(String urlString, String reqBody) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream()) {
            requestBody.write(reqBody.getBytes());
        }

        return handleResponse(connection);
    }

    public String register(String urlString, String reqBody) throws IOException {
        return registerOrLogin(urlString, reqBody);
    }

    public String listGames(String urlString, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Authorization", authToken);

        connection.connect();

        return handleResponse(connection);
    }

    public String createGame(String urlString, String reqBody, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.addRequestProperty("Authorization", authToken);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream()) {
            requestBody.write(reqBody.getBytes());
        }

        return handleResponse(connection);
    }

    public String login(String urlString, String reqBody) throws IOException {
        return registerOrLogin(urlString, reqBody);
    }

    public String logout(String urlString, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.addRequestProperty("Authorization", authToken);

        connection.connect();

        return handleResponse(connection);
    }

    public String joinGame(String urlString, String reqBody, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.addRequestProperty("Authorization", authToken);
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream()) {
            requestBody.write(reqBody.getBytes());
        }

        return handleResponse(connection);
    }


}
