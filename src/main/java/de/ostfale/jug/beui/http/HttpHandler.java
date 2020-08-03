package de.ostfale.jug.beui.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.net.http.HttpResponse.BodyHandlers;

/**
 * Provides services to retrieve data from backend
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class HttpHandler {

    private final String BACKEND_HOST = "http://localhost:8080/api/v1";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HttpHandler httpHandler = new HttpHandler();
        final CompletableFuture<String> completableFuture = httpHandler.getAsync("http://localhost:8080/api/v1/person/");
        System.out.println(completableFuture.get());
    }

    public CompletableFuture<String> getAsync(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        return client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
