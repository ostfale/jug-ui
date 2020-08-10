package de.ostfale.jug.beui.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpResponse.BodyHandlers;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Provides services to retrieve data from backend
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class HttpHandler {

    private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

    private static final String BACKEND_HOST = "http://localhost:8080/api/v1/";
    public static final String PERSON_BASE = BACKEND_HOST + "person/";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public CompletableFuture<HttpResponse<String>> getAsync(String uri) {
        log.debug("Async GET for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return httpClient.sendAsync(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> getSync(String uri) throws IOException, InterruptedException {
        log.debug("Sync GET for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(3, SECONDS))
                .GET()
                .build();
        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> postSync(String uri, String requestBody) throws IOException, InterruptedException {
        log.debug("Async POST for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> deleteSync(String uri) throws IOException, InterruptedException {
        log.debug("Async DELETE for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .DELETE()
                .build();
        return httpClient.send(request, BodyHandlers.ofString());
    }

    public CompletableFuture<HttpResponse<String>> putAsync(String uri, String requestBody) {
        log.debug("Async PUT for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return httpClient.sendAsync(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> putSync(String uri, String requestBody) throws IOException, InterruptedException {
        log.debug("Sync PUT for URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return httpClient.send(request, BodyHandlers.ofString());
    }
}
