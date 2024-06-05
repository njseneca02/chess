package network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {

    public String register(String urlString, String reqBody) throws IOException {
        URL url = new URL(urlString);

        String response = "";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream()) {
            requestBody.write(reqBody.getBytes());
        }

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
}
