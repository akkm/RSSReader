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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.rssreader.db.FeedItemEntity;
import com.example.rssreader.db.RSSFeedUrlEntity;
import com.example.rssreader.db.RSSFeedUrlOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<FeedItemEntity>>, SwipeRefreshLayout.OnRefreshListener {

    private static final int FEED_LOADER_ID = 1;
    private ListView mListView;
    private MyAdapter mListAdapter;
    private List<FeedItemEntity> mItemList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<RSSFeedUrlEntity> mUrlEntities;
    private ArrayAdapter<RSSFeedUrlEntity> mUrlAdapter;
    private RSSFeedUrlEntity mLastSelectedUrlEntity;

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

        setupSpinner(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 設定を読み込む
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // デフォルト値をtrueとして設定する
        boolean cacheEnabled = sharedPreferences.getBoolean(getString(R.string.cache_enabled_key), true);

//        // キャッシュがあれば利用する
//        FeedCache cache = new FeedCache(this);
//        if (cache.exists() && cacheEnabled) {
//            mListAdapter.addAll(cache.read());
//        } else {
//            // なければインターネット上から取得する
//            Log.d("DEBUG", "------NO CACHE------");
//            fetch(null);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Settings を選択したらlSettingActivityを表示する
            Intent intent = new android.content.Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<FeedItemEntity>> onCreateLoader(int id, Bundle args) {
        return new FeedAsyncTaskLoader(this, args.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<List<FeedItemEntity>> loader, List<FeedItemEntity> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        mListAdapter.clear();
        mListAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<FeedItemEntity>> loader) {

    }

    @Override
    public void onRefresh() {
        if (mLastSelectedUrlEntity == null) return;
        fetch(mLastSelectedUrlEntity.getUrl());
    }

    private void fetch(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        if (getSupportLoaderManager().getLoader(FEED_LOADER_ID) == null) {
            getSupportLoaderManager().initLoader(FEED_LOADER_ID, bundle, this);
        } else {
            getSupportLoaderManager().restartLoader(FEED_LOADER_ID, bundle, this);
        }
    }

    private void setupSpinner(Toolbar toolbar) {
        View view = getLayoutInflater().inflate(R.layout.toolbar_spinner, null, false);
        toolbar.addView(view);

        RSSFeedUrlOpenHelper helper = new RSSFeedUrlOpenHelper(this);
        mUrlEntities = helper.selectAll();
        helper.close();

        mUrlAdapter = new ArrayAdapter<RSSFeedUrlEntity>(this, android.R.layout.simple_list_item_1, mUrlEntities);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(mUrlAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RSSFeedUrlEntity entity = mUrlEntities.get(position);
                mLastSelectedUrlEntity = entity;
                mListAdapter.clear();
                fetch(entity.getUrl());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
