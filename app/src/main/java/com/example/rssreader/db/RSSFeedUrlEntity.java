package com.example.rssreader.db;

/**
 * Created by akkuma on 2015/10/24.
 */
public class RSSFeedUrlEntity {
    private Integer id;
    private String name;
    private String url;
    private Boolean isPrimary;

    public RSSFeedUrlEntity(Integer id, String name, String url, Boolean isPrimary) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.isPrimary = isPrimary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return id + ":" + name;
    }
}
