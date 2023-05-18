package com.os_project;

import org.junit.Test;

import static org.junit.Assert.*;

public class GPTTest {

    @Test
    public void testChatGPT() {
        GPT gpt = new GPT();
        String text = "Hello my name is Karl!";

        try {
            // chatGPT() returns a response from the GPT API
            String response = gpt.chatGPT(text);
            // response should not be null and should not be empty
            assertNotNull(response);
            assertFalse(response.isEmpty());
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testChatGPTResponseLength() {
        GPT gpt = new GPT();
        String text = "Hello, GPT!";
        
        try {
            String response = gpt.chatGPT(text);
            int wordCount = countWords(response);
            // response should be less than 100 words
            assertTrue("Response length should be less than 100 words", wordCount < 100);
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }
    // helper method to count the number of words in a string
    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
}
