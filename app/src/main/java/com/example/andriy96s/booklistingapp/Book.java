package com.example.andriy96s.booklistingapp;


import java.util.List;

/**
 * Created by andriy96s on 08.08.17.
 */

public class Book {
    private String title;
    private List<String> authors;
    private String description;
    private String thumbnailLink;
    private String  previewLink;

    public Book (String _title, List<String> _authors, String _description, String _thumbnailLink, String _previewLink) {
        title=_title;
        authors=_authors;
        description=_description;
        thumbnailLink=_thumbnailLink;
        previewLink=_previewLink;
    }

    public String getTitle () {return title;}
    public String getAuthors() {
        String authorsString = null;
        if (authors.size()==1) {return authors.get(0);}
        ;for(int i = 0; i<authors.size()-1;i++) {
            if (authorsString==null) authorsString = authors.get(i) +", ";
            else authorsString +=  authors.get(i) +", ";
        }
        authorsString +=  authors.get(authors.size()-1);
        return authorsString;
    }
    public String getDescription() {return description;}
    public String  getThumbnailLink () {return thumbnailLink;}
    public String getPreviewLink () {return previewLink;}
}
