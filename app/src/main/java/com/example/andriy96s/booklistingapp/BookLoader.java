package com.example.andriy96s.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by andriy96s on 11.08.17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private final String TAG = BookLoader.class.getSimpleName();
    private String urlString;

    public BookLoader (Context context, String url) {
        super(context);
        urlString=url;
    }

    @Override
    public void onStartLoading () {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground () {
        if (urlString.isEmpty()) return null;
        URL url = QueryUtils.createURL(urlString);
        List<Book> books = null;
        try {
            books = QueryUtils.parseJSON(makeHttpRequest(url));
        } catch (IOException e) {
            Log.e(TAG,"Closing stream error: "+e.getMessage());
        }

        return books;
    }

    private String makeHttpRequest (URL url) throws IOException {
        if (url==null || url.equals("")) return null;
        String jsonResponse = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(20000);
            connection.connect();

            if (connection.getResponseCode()==200) {
                inputStream = connection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
        }
        catch (IOException e) {
            Log.e(TAG,"IO exception: "+e.getMessage());
        }
        finally {
            if(inputStream!=null) inputStream.close();
            if (connection!=null) connection.disconnect();
        }
        return jsonResponse;
    }

    private String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line!=null) {
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
}
