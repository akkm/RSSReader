package com.example.rssreader;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
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
        Request request = new Request.Builder()
                .url(RSS_URL)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string(); // TODO この result を整形して、List<FeedItem> を生成しよう
            Log.d("FeedFetcher", result);
        } catch (IOException e) {
            Log.e("FeedFetcher", "something went wrong on ");
        }

        List<FeedItem> result = new ArrayList<>(); // TODO このダミーデータは必要なくなるので消す
        result.add(new FeedItem("最初の大ニュースです！", "これはすごい", "スクープ", "2015/9/1"));
        result.add(new FeedItem("次の大ニュースです！", "これはすごいこれはすごい", "スクープ", "2015/9/1"));
        return result;
    }
}
