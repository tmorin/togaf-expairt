package io.morin.togafexpairt.cli;

import io.morin.togafexpairt.fwk.SettingReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * A command line interface to interact with the REST Server.
 */
@Slf4j
public class RestClientCli {

    @SuppressWarnings({ "java:S106" })
    public static void main(String[] args) {
        val scanner = new Scanner(System.in);
        try (val client = HttpClient.newHttpClient()) {
            while (scanner.hasNextLine()) {
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

    /**
     * This class represents the settings of the REST CLI.
     */
    @Value
    @Builder(toBuilder = true)
    static class RestCliSettings {

        /**
         * The host of the REST CLI.
         */
        @NonNull
        @Builder.Default
        String promptUrl = SettingReader.readString("togafexpairt.rest_cli.prompt_url", "http://localhost:9090/prompt");
    }
}
