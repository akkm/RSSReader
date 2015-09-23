package com.example.rssreader;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanao on 9/23/15.
 * フィードの取得結果を保存したり読み出したりするクラス
 */
public class FeedCache {
    private Context context;

    private static final String CACHE_FILE_NAME = "cache.txt";

    public FeedCache(Context context) {
        this.context = context;
    }

    /*
    * キャッシュファイルが存在すれば、キャッシュがあるという判断をするメソッド
    * */
    public boolean exists() {
        String filepath = context.getCacheDir().getAbsolutePath() + "/" + CACHE_FILE_NAME;
        File file = new File(filepath);
        return file.exists();
    }

    public List<FeedItem> read() {
        List<FeedItem> items = new ArrayList<>();

        try {
            File file = new File(context.getCacheDir(), CACHE_FILE_NAME);
            FileInputStream inputStream = new FileInputStream(file);

            byte[] buffer = new byte[inputStream.available()];
            int result = inputStream.read(buffer);
            inputStream.close();
            if (result==-1) {
                // 最後まで読めなかったら例外を投げる
                throw new IOException("could not read input stream");
            }

            // 読み込めたらListView用のデータに変換して返す
            String text = new String(buffer);
            FeedFetcher fetcher = new FeedFetcher(context);
            items = fetcher.parseRss(text);
        } catch (Exception e) {
            Log.e("FeedCache.read", e.getMessage());
        }
        return items;
    }

    public void write(String feedString) {
        try {
            File file = new File(context.getCacheDir(), CACHE_FILE_NAME);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(feedString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("FeedCache.write", e.getMessage());
        }
    }
}
