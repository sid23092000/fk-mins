package com.customer.experience.Utils;

import com.customer.experience.controller.ProductController;
import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.*;


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

    public List<Products> directMatcher(List<ItemsDetailsDto> itemList, List<Products> productList)
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

        for(ItemsDetailsDto items:itemList)
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



    public List<Items> generateUsageBasedList(String usage, int listId)
    {


        int retries = 0;
        int maxRetries = 5;


        ProductController productController=new ProductController();



        StringBuilder productNames = new StringBuilder("Here is the usage:");


        productNames.append("\n");

        productNames.append(usage);

        productNames.append("I want you to return me the list of ingredients needed and the quantity required by a single person in an enumerated format. Exclude the items that are not generally ordered from an online platform.Give in a json format please with key as \"this_item\" and \"this_quantity\". Both key and value should be of type String. Also everytime the object name should be result.");




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
                return extractProductsFromUsage("Rate limit exceeded. Retrying in " + (2 * retries) + " seconds...",listId);

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

            return extractProductsFromUsage(reply,listId);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<Items> extractProductsFromUsage(String response,int listId){
        System.out.println(response);
        System.out.println("------------------");
       return extractAndProcess(response,listId);

    }



    public List<Items> extractAndProcess(String input,int listId) {
        try {
            // Extract JSON array from the input string
            int startIndex = input.indexOf("{");
            int endIndex = input.lastIndexOf("}") + 1;

            if (startIndex == -1 || endIndex == 0) {
                System.out.println("No valid JSON array found in input.");
                return null;
            }

            System.out.println(startIndex);
            System.out.println(endIndex);

            String jsonArrayString = "[" + input.substring(startIndex, endIndex) + "]";
            System.out.println(jsonArrayString);


            JSONArray jsonArray = new JSONArray(jsonArrayString);
            JSONObject firstObject = jsonArray.getJSONObject(0);
            JSONArray resultArray = firstObject.getJSONArray("result");

            List<String> items = new ArrayList<>();
            List<String> quantities = new ArrayList<>();

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject itemObject = resultArray.getJSONObject(i);
                items.add(itemObject.getString("this_item"));
                quantities.add(itemObject.getString("this_quantity"));
            }

            System.out.println("Items: " + items);
            System.out.println("Quantities: " + quantities);



            List<Items> finalList=new ArrayList<>();

            for(int i=0;i<items.size();i++)
            {
                Items item = new Items();
                item.setName(items.get(i));
                item.setQuantity(convertToInteger(quantities.get(i)));
                item.setListId(listId);
                System.out.println(item.getName());
                System.out.println(item.getQuantity());
                System.out.println(item.getListId());
                finalList.add(item);
            }

            return finalList;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in parsing the JSON input.");
        }
        return null;
    }


    public int convertToInteger(String quantity) {

        StringBuilder number = new StringBuilder();
        number.append(0);
        for (char c : quantity.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                break;
            }
        }
        double value = Double.parseDouble(number.toString());
        return (int) Math.ceil(value);


    }


    public List<Items> getListFromText(int userId, int listId, String listTextDto){
        int retries = 0;
        int maxRetries = 5;


        ProductController productController=new ProductController();



        StringBuilder productNames = new StringBuilder("Here is a grocery list a person has prepared:");


        productNames.append("\n");

        productNames.append(listTextDto);

        productNames.append("\n");

        productNames.append("I want you to return me the complete list of items that are there in the grocery list and the quantity mentioned as it is. Give in a json format please with key as \"this_item\" and \"this_quantity\". Both key and value should be of type String. Also everytime the object name should be result.");

        System.out.println(productNames);


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
                return extractProductsFromUsage("Rate limit exceeded. Retrying in " + (2 * retries) + " seconds...",listId);

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

            return extractProductsFromUsage(reply,listId);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }
}

