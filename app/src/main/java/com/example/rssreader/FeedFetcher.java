package com.example.rssreader;

import android.content.Context;

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

    public FeedFetcher(Context context) {
        this.context = context;
    }

    /**
     * ダミー実装　
     * @return
     */
    public List<FeedItem> fetch() {
        String result = null;
        Request request = new Request.Builder()
                .url("http://news.google.co.jp/news?cf=all&hl=ja&pz=1&ned=jp&topic=h&num=3&output=rss")
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<FeedItem> items = new ArrayList<>();
        items.add(new FeedItem("最初の大ニュースです！", "これはすごい", "スクープ", "2015/9/1"));
        items.add(new FeedItem("次の大ニュースです！", "これはすごいこれはすごい", "スクープ", "2015/9/1"));
        return items;
    }
}
