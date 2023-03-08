package pruebaapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PruebaAPI {
    public static void APIConsumer(){
        String url = "http://10.10.10.100:8080/api/distritos";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            System.out.println(connection.getResponseMessage());
            
            in.close();
            System.out.println("Response body: " + response.toString());            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void HTTPClientNativo() throws URISyntaxException{
        HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_2)
        .followRedirects(Redirect.NORMAL)
        .build();
  
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://10.10.10.100:8080/api/distritos"))
        .GET()
        .timeout(Duration.ofSeconds(10))
        .build();  
  
        client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println)
        .join();
    }
    
    public static void main(String[] args) {
        System.out.println("Chat GPT");
        APIConsumer();
        System.out.println();
        try {
            System.out.println("HTTP Client Nativo");
            HTTPClientNativo();
        } catch (URISyntaxException ex) {
            Logger.getLogger(PruebaAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
