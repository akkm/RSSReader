package com.example.rssreader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Hideyuki.Kikuma on 15/09/02.
 */
public class FeedAsyncTaskLoader extends AsyncTaskLoader<List<FeedItem>> {
    public FeedAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<FeedItem> loadInBackground() {
        List<FeedItem> result = new FeedFetcher(getContext()).fetch();
        return result;
    }
}
