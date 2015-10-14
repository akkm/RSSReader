package com.example.rssreader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rssreader.db.FeedItemEntity;

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

        mListView = (ListView) findViewById(R.id.listView);

        mItemList = new ArrayList<>();

        mListAdapter = new MyAdapter(this, mItemList);

        mListView.setAdapter(mListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + 1 + "番目がタップされました", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // キャッシュがあれば利用する
        FeedCache cache = new FeedCache(this);
        if (cache.exists()) {
            mListAdapter.addAll(cache.read());
        }
        else {
            // なければインターネット上から取得する
            getSupportLoaderManager().initLoader(FEED_LOADER_ID, null, this);
        }
//        mItemList.add(FeedItemEntity.getBuilder()
//                .setTitle("事件です")
//                .setDate("2015/10/13 00:00:00")
//                .build());
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
        getSupportLoaderManager().restartLoader(FEED_LOADER_ID,null,this);
    }
}
