package com.example.rssreader;

import android.content.Context;

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
        try {
            // ネットワーク遅延をエミュレート
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        List<FeedItem> result = new ArrayList<>();
        result.add(new FeedItem("最初の大ニュースです！", "これはすごい", "スクープ", "2015/9/1"));
        result.add(new FeedItem("次の大ニュースです！", "これはすごいこれはすごい", "スクープ", "2015/9/1"));
        return result;
    }
}
