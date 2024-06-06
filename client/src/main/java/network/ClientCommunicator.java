package network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {

    private String handleResponse(HttpURLConnection connection) throws IOException{
        String response = "";

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
            try(InputStream responseBody = connection.getInputStream()){
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));
                String line;

                while ((line = reader.readLine()) != null) {
                    response += line;
                }

                reader.close();
            }

        }
        else {
            try(InputStream responseBody = connection.getErrorStream()){
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));
                String line;

                while ((line = reader.readLine()) != null) {
                    response += line;
                }

                reader.close();
            }
        }
        return response;
    }


    public String register(String urlString, String reqBody) throws IOException {
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
}
