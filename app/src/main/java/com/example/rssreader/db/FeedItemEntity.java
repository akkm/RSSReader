package com.example.rssreader.db;

import android.net.Uri;

import com.example.rssreader.FeedItem;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

/**
 * DBで記事を扱うためのクラス
 * Created by takafumi.nanao on 10/2/15.
 */
public class FeedItemEntity extends FeedItem {
    public static final long UNDEFINED = -1;
    private long id;

    public FeedItemEntity(long id, SyndEntry entry) {
        super(entry);
        this.id = id;
    }

    public FeedItemEntity(long id, String title, String description, String category, String date, Uri uri) {
        super(title, description, category, date, uri);
        this.id = id;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public static class Builder {
        private long id = UNDEFINED;

        private String title;
        private String description;
        private String category;
        private String date;
        private Uri uri;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }
        public FeedItemEntity build() {
            return new FeedItemEntity(id, title, description, category, date, uri);
        }
    }

}