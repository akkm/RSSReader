package com.example.rssreader;

import android.content.Context;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hideyuki.Kikuma on 15/09/02.
 */
public class FeedFetcher {
    private Context context;

    private static final String RSS_URL = "http://news.yahoo.co.jp/pickup/computer/rss.xml";

    public FeedFetcher(Context context) {
        this.context = context;
    }

    /**
     * 記事一覧を取得する
     *
     * @return 記事リスト
     */
    public List<FeedItem> fetch() {
        String result = null;
        Request request = new Request.Builder()
                .url(RSS_URL)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        List<FeedItem> items = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            items = parseRss(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private List<FeedItem> parseRss(String rss) {
        List<FeedItem> items = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();

        try {
            SyndFeed feed = input.build(new StringReader(rss));
            for (Object obj : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) obj;
                FeedItem item = new FeedItem(entry);
                items.add(item);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (FeedException ex) {
            ex.printStackTrace();
        }
        return items;
    }
}
