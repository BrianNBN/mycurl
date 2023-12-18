package com.weekend.mycurl.controllers;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@RestController
public class MyCurlController {
    private boolean verbose = true;
    @PostMapping("/cccurl")
    @GetMapping("/cccurl")
    public String makeRequest(
            @RequestParam String url,
            @RequestParam(required = false, defaultValue = "GET") String method,
            @RequestParam(required = false) String data,
            @RequestParam(required = false) String contentType) {

        try {
            URI uri = new URI(url);

            System.out.println("connecting to " + uri.getHost() + ":" + uri.getPort());


//            if (method.equalsIgnoreCase("POST")) {
//                sendPostRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath(), data, contentType);
//            } else if (method.equalsIgnoreCase("GET")) {
//                sendGetRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath());
//            } else if (method.equalsIgnoreCase("DELETE")) {
//                sendDeleteRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath());
//            }

            if (method.equalsIgnoreCase("POST") || url.endsWith("/post")) {
                sendPostRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath(), data, contentType);
            } else if (method.equalsIgnoreCase("GET")) {
                sendGetRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath());
            } else if (method.equalsIgnoreCase("DELETE")) {
                sendDeleteRequestAndPrintResponse(uri.getHost(), uri.getPort(), uri.getPath());
            } else {
                return "Invalid Method!";

            }

            String protocol = uri.getScheme();
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();

            if (port == -1) {
                port = 80;
            }

            System.out.println("connecting to " + host + ":" + port);
            System.out.println("Sending Request Get " + path + "" + protocol + "/1.1");
            System.out.println("Host: " + host);
            System.out.println("Accept: */*");

            return "Request Sent Successfully!";
        } catch (URISyntaxException e) {
            return "Invalid URL!" + e.getMessage();
        }
    }



    private void sendGetRequestAndPrintResponse(String host, int port, String path){
        try {
            URL url = new URL("http", host, port, path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Add headers
            connection.setRequestProperty("Host", host);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "close");

            if (verbose) {
                System.out.println("> Request Headers:");
                connection.getRequestProperties().forEach((key, values) ->
                        values.forEach(value -> System.out.println("> " + key + ": " + value))
                );
                System.out.println();
            }

// Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("< HTTP/1.1 " + responseCode + " " + connection.getResponseMessage());

            // Print the response headers if verbose is enabled
            if (verbose) {
                System.out.println("< Response Headers:");
                connection.getHeaderFields().forEach((key, value) -> {
                    if (key != null) {
                        System.out.println("< " + key + ": " + value.get(0));
                    }
                });
                System.out.println();
            }

            // Read and print the response body
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("< " + inputLine);
                }
            }


        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDeleteRequestAndPrintResponse(String host, int port, String path) {
        try {
            URL url = new URL("http", host, port, path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            // Add headers
            connection.setRequestProperty("Host", host);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "close");

            if (verbose) {
                System.out.println("> Request Headers:");
                connection.getRequestProperties().forEach((key, values) ->
                        values.forEach(value -> System.out.println("> " + key + ": " + value))
                );
                System.out.println();
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("< HTTP/1.1 " + responseCode + " " + connection.getResponseMessage());

            // Print the response headers if verbose is enabled
            if (verbose) {
                System.out.println("< Response Headers:");
                connection.getHeaderFields().forEach((key, value) -> {
                    if (key != null) {
                        System.out.println("< " + key + ": " + value.get(0));
                    }
                });
                System.out.println();
            }

            // Read and print the response body
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("< " + inputLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPostRequestAndPrintResponse(String host, int port, String path, String data, String contentType) {
        try {
            URL url = new URL("http", host, port, path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Add headers
            connection.setRequestProperty("Host", host);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-Type", contentType);

            // Add body
            connection.setDoOutput(true);
            connection.setDoOutput(true);
            if (data != null) { // Check if the data is not null before calling getBytes
                connection.getOutputStream().write(data.getBytes());
            }

            if (verbose) {
                System.out.println("> Request Headers:");
                connection.getRequestProperties().forEach((key, values) ->
                        values.forEach(value -> System.out.println("> " + key + ": " + value))
                );
                System.out.println();
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("< HTTP/1.1 " + responseCode + " " + connection.getResponseMessage());

            // Print the response headers if verbose is enabled
            if (verbose) {
                System.out.println("< Response Headers:");
                connection.getHeaderFields().forEach((key, value) -> {
                    if (key != null) {
                        System.out.println("< " + key + ": " + value.get(0));
                    }
                });
                System.out.println();
            }

            // Read and print the response body
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("< " + inputLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
