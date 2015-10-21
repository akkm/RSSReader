package com.example.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class FeedContentActivity extends AppCompatActivity {
    private static final String TAG = FeedContentActivity.class.getSimpleName();
    public static final String URI_PARAM = "url";
    private String mUrl;
    private ProgressBar mProgressBar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_content);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mActionBar = getSupportActionBar();

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO: ActionBar(mActionBar) に title を設定しよう
                mActionBar.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO: ProgressBar(mProgressBar) に newProgress の値を設定しよう
                mProgressBar.setProgress(newProgress);
            }
        });

        // TODO: ProgressBar の最大値を 100 に設定しよう
        mProgressBar.setMax(100);

        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(URI_PARAM);
            webView.loadUrl(mUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_content, menu);
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
        if (id == R.id.share) {
            Intent intent = new Intent();

            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mUrl);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
