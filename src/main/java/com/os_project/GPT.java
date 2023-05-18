package com.os_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

class GPT {
    // source code: https://gist.github.com/gantoin/190684c344bb70e5c5f9f2339c7be6ed
    public String chatGPT(String text) throws Exception {
        // using the openai api to chat with the user
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer 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");

        JSONObject data = new JSONObject();
        //model: text-davinci-003 is the best model for chat
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        // 600 tokes ~ 100 words
        data.put("max_tokens", 600); 
        data.put("temperature", 1.0);
        // settting to true will allow the model to send back a response before the max_tokens limit is reached
        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());
        // reading the response from the api
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        String output = response.toString();
        // returning the response from the api by converting the json to a string
        return (new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }

}
