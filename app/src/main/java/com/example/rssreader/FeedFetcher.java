package com.example.rssreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.rssreader.db.FeedItemEntity;
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
import java.util.Collections;
import java.util.List;

/**
 * 実際にフィードを取得する処理をまとめたクラス
 * Created by Hideyuki.Kikuma on 15/09/02.
 */
public class FeedFetcher {
    private Context context;

    private static final String RSS_URL = "http://news.yahoo.co.jp/pickup/computer/rss.xml";
    private String mUrl;

    public FeedFetcher(Context context) {
        this.context = context;
        mUrl = RSS_URL;

    }

    public FeedFetcher(Context context, String url) {
        this.context = context;
        mUrl = url;
        if (mUrl == null) {
            mUrl = RSS_URL;
        }
    }

    /**
     * 記事一覧を取得する
     *
     * @return 記事リスト
     */
    public List<FeedItemEntity> fetch() {
        Request request = new Request.Builder()
                .url(mUrl)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            // デフォルト値をtrueとして設定する
            boolean cacheEnabled = sharedPreferences.getBoolean(context.getString(R.string.cache_enabled_key), true);

            if (cacheEnabled==true) {
                // 取得したらキャッシュに書き込む
//                FeedCache cache = new FeedCache(context);
//                cache.write(result);
            }

            return parseRss(result);
        } catch (IOException e) {
            Log.e("FeedFetcher", "something went wrong on ");
            return Collections.emptyList();
        }
    }

    public List<FeedItemEntity> parseRss(String rss) {
        List<FeedItemEntity> items = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();

        try {
            SyndFeed feed = input.build(new StringReader(rss));
            for (Object obj : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) obj;
                FeedItemEntity item = new FeedItemEntity(FeedItemEntity.UNDEFINED, entry);
                items.add(item);
            }
        } catch (FeedException ex) {
            Log.e("FeedFetcher", "something wrong on feed");
        }
        return items;
    }
}
