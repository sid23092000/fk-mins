package com.customer.experience.Utils;

import com.customer.experience.controller.ProductController;
import com.customer.experience.model.Items;
import com.customer.experience.model.Products;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class VernacWrapper {


    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static String usage = "";


    private static void disableSSLVerification() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public boolean checkNameMatching(String itemName1, String itemName2)
    {


        int retries = 0;

        int maxRetries = 5;



        ProductController productController=new ProductController();



       StringBuilder productNames = new StringBuilder("Here are the list of items that are present in my database: ");




            try {
                disableSSLVerification();
                // Set up the HTTP connection
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create JSON body
                JSONObject messageObject = new JSONObject();
                messageObject.put("role", "user");

                messageObject.put("content", "Answer True or False. Is the hindi word "+ itemName1 + " same as " + itemName2 + ". If True, type True. If False, type False. 1 word answer. No explanation needed.");

                JSONArray messagesArray = new JSONArray();
                messagesArray.put(messageObject);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("model", "llama3-8b-8192");
                jsonBody.put("messages", messagesArray);

                // Write JSON body to the request
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Read the response
                if (conn.getResponseCode() == 429) {

                    retries++;
                    return cleanResponse("Rate limit exceeded. Retrying in " + (2 * retries) + " seconds...");

                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line.trim());
                }
                in.close();

                // Parse and print the response
                JSONObject jsonResponse = new JSONObject(response.toString());
                String reply = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                return cleanResponse(reply);

            } catch (Exception e) {
                e.printStackTrace();

            }

        return cleanResponse("Unexpected error occurred.");

    }

    public List<Products> directMatcher(List<Items> itemList, List<Products> productList)
    {


        int retries = 0;

        int maxRetries = 5;



        ProductController productController=new ProductController();



        StringBuilder productNames = new StringBuilder("Here are the list of items that are present in my database: ");

        for(Products products:productList)
        {
            productNames.append(products.getName());
            productNames.append(", ");
        }

        productNames.append("\n");

        productNames.append("\n");

        productNames.append("Here are the list of items that are present in the hand written list: ");

        for(Items items:itemList)
        {
            productNames.append(items.getName());
            productNames.append(", ");
        }

        productNames.append("\n");

        productNames.append("\n");

        productNames.append("If an item is present in both the lists, give me a table(use '|' to create the table) that contains only the common items with actual name in the database. Don't include items that are not present in both the lists to the table you are providing.");




        try {
            disableSSLVerification();
            // Set up the HTTP connection
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON body
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");

            messageObject.put("content", productNames);

            JSONArray messagesArray = new JSONArray();
            messagesArray.put(messageObject);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "llama3-8b-8192");
            jsonBody.put("messages", messagesArray);

            // Write JSON body to the request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            if (conn.getResponseCode() == 429) {

                retries++;
                return extractProducts("Rate limit exceeded. Retrying in " + (2 * retries) + " seconds...", productList);

            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line.trim());
            }
            in.close();

            // Parse and print the response
            JSONObject jsonResponse = new JSONObject(response.toString());
            String reply = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            return extractProducts(reply, productList);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public boolean cleanResponse(String response)
    {
        if (response == null) {
            return false;
        }
        response = response.toLowerCase();
        return response.contains("true");
    }

    public List<Products> extractProducts(String response, List<Products> prodDB)
    {
        System.out.println(response);
        response = response.substring(response.indexOf('|'), response.lastIndexOf('|') + 1).trim();

        List<Products> matchedProducts = new ArrayList<>();

        for (Products product : prodDB) {
            if (response.toLowerCase().contains(product.getName().toLowerCase())) {
                matchedProducts.add(product);
                break;
            }
        }
        return matchedProducts;
    }
}

