package com.example.rssreader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.rssreader.db.FeedItemEntity;

import java.util.List;

/**
 * フィードの取得を非同期で行うためのクラス
 * Created by Hideyuki.Kikuma on 15/09/02.
 */
public class FeedAsyncTaskLoader extends AsyncTaskLoader<List<FeedItemEntity>> {
    public FeedAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<FeedItemEntity> loadInBackground() {
        return new FeedFetcher(getContext()).fetch();
    }
}
