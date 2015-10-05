package com.example.rssreader;

import android.content.Context;
import android.util.Log;

import com.example.rssreader.db.FeedItemEntity;
import com.example.rssreader.db.FeedItemOpenHelper;

import java.util.List;

/**
 * Created by nanao on 9/23/15.
 * フィードの取得結果を保存したり読み出したりするクラス
 */
public class FeedCache {
    private Context context;
    private FeedItemOpenHelper mOpenHelper;

    public FeedCache(Context context) {
        this.context = context;
        mOpenHelper = new FeedItemOpenHelper(context);
    }

    /*
    * キャッシュファイルが存在すれば、キャッシュがあるという判断をするメソッド
    * */
    public boolean exists() {
        return (mOpenHelper.selectAllCount() > 0);
    }

    public List<FeedItemEntity> read() {
        return mOpenHelper.selectAll();
    }

    public void write(String feedString) {
        FeedFetcher fetcher = new FeedFetcher(context);
        boolean result = mOpenHelper.insertList(fetcher.parseRss(feedString));
        if (!result) {
           Log.e("FeedCache.write", "failed to write cache!");
        }
    }
}
