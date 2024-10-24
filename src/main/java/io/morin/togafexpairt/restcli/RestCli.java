package io.morin.togafexpairt.restcli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * This Java main class provide CLI to send prompt to the TOGAF Expairt server and stream the response.
 */
@Slf4j
public class RestCli {

    @SuppressWarnings({ "java:S106" })
    public static void main(String[] args) {
        val scanner = new Scanner(System.in);
        try (val client = HttpClient.newHttpClient()) {
            while (true) {
                try {
                    log.info("Please input a line");
                    val prompt = scanner.nextLine();

                    if (prompt.equals("exit")) {
                        break;
                    }

                    val restCliSettings = RestCliSettings.builder().build();

                    val url = URI.create(restCliSettings.getPromptUrl());

                    val request = HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(prompt))
                        .header(
                            "Content-Type",
                            String.format("text/plain; charset=%s", Charset.defaultCharset().name())
                        )
                        .build();

                    val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                    val responseCode = response.statusCode();
                    if (responseCode == 200) {
                        try (val inputStream = response.body()) {
                            byte[] buffer = new byte[10];
                            int read;
                            while ((read = inputStream.read(buffer, 0, 10)) >= 0) {
                                System.out.write(buffer, 0, read);
                            }
                        }
                    } else {
                        log.error("Failed to send prompt with {}, try again! :)", responseCode);
                    }

                    System.out.println();
                    System.out.println();
                } catch (Exception e) {
                    log.error("Failed to send prompt, try again! :)", e);
                }
            }
        }
    }
}
