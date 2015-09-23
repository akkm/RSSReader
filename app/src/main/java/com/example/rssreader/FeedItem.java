package com.example.rssreader;

import android.net.Uri;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

/**
 * Created by atsuto.yamada on 2015/08/31.
 */
public class FeedItem {
    private String title;
    private String description;
    private String category;
    private String date;
    private Uri uri;

    public FeedItem(String title, String description, String category, String date, Uri uri) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.uri = uri;
    }

    public FeedItem(String title, String description, String category, String date) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public FeedItem(SyndEntry entry) {
        if (entry != null) {
            this.title = entry.getTitle();
            this.description = (entry.getDescription() != null) ? entry.getDescription().getValue() : null;
            this.date = (entry.getPublishedDate() != null) ? entry.getPublishedDate().toString() : null;
            this.uri = (entry.getLink() != null) ? Uri.parse(entry.getLink()) : null;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getUri() {
        return uri;
    }

    public String getUriString() {
        if (uri == null) return null;
        return uri.toString();
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
