package com.example.andriy96s.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriy96s on 08.08.17.
 */

public class QueryUtils {
    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Book> parseJSON(String jsonResponse) {
        List<Book> books = new ArrayList<>();
        if (jsonResponse != null) {
            try {
                JSONObject object = new JSONObject(jsonResponse);
                JSONArray items = object.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject volumeInfo = (items.getJSONObject(i)).getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");

                    List<String> authors = new ArrayList<>();
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    for (int j = 0; j < authorsArray.length(); j++) {
                        authors.add(authorsArray.getString(j));
                    }

                    String description = volumeInfo.getString("description");

                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    String thumbnailLink = imageLinks.getString("thumbnail");

                    String previewLink = volumeInfo.getString("previewLink");

                    books.add(new Book(title, authors, description, thumbnailLink, previewLink));

                }
            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }
        }

        return books;
    }

    public static URL createURL(String urlString) {
        URL url = null;
        if (urlString != null) {
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Creating URL error: " + e.getMessage());
            }
        }

        return url;
    }
}
