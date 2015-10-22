package com.example.rssreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rssreader.db.FeedItemEntity;
import com.example.rssreader.lesson25.Lesson25Activity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<FeedItemEntity>>, SwipeRefreshLayout.OnRefreshListener {

    private static final int FEED_LOADER_ID = 1;
    private ListView mListView;
    private MyAdapter mListAdapter;
    private List<FeedItemEntity> mItemList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_main);
        // menu選択時の動きを設定
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });

        mListView = (ListView) findViewById(R.id.listView);
        mItemList = new ArrayList<>();
        mListAdapter = new MyAdapter(this, mItemList);
        mListView.setAdapter(mListAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 設定を読み込む
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // デフォルト値をtrueとして設定する
        boolean cacheEnabled = sharedPreferences.getBoolean(getString(R.string.cache_enabled_key), true);

        // キャッシュがあれば利用する
        FeedCache cache = new FeedCache(this);
        if (cache.exists() && cacheEnabled) {
            mListAdapter.addAll(cache.read());
        } else {
            // なければインターネット上から取得する
            Log.d("DEBUG", "------NO CACHE------");
            getSupportLoaderManager().initLoader(FEED_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Settings を選択したらlSettingActivityを表示する
            Intent intent = new android.content.Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_lesson25) {
            Intent intent = new Intent(this, Lesson25Activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<FeedItemEntity>> onCreateLoader(int id, Bundle args) {
        return new FeedAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<FeedItemEntity>> loader, List<FeedItemEntity> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        mListAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<FeedItemEntity>> loader) {

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(FEED_LOADER_ID, null, this);
    }
}
