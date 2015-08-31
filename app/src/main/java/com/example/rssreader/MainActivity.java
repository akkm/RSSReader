package com.example.rssreader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private MyAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);

        // TODO List<Integer> を List<Person> に変更しよう
        List<Person> numberList = new ArrayList<>();

        // TODO List<Person> に好きな Person を追加しよう
        numberList.add(new Person("Alice", 24, "foo"));
        numberList.add(new Person("Bob", 23, "bar"));
        numberList.add(new Person("Charlie", 20, "baz"));
        numberList.add(new Person("Dave", 18, "qux"));
        numberList.add(new Person("Ellen", 30, "quux"));
        numberList.add(new Person("Frank", 33, "foobar"));
        numberList.add(new Person("Isaac", 29, "hoge"));
        numberList.add(new Person("Justin", 22, "fuga"));
        numberList.add(new Person("Mallory", 21, "piyo"));
        numberList.add(new Person("Oscar", 25, "hogehoge"));
        numberList.add(new Person("Pat", 26, "fugafuga"));
        numberList.add(new Person("Steve", 21, "piyopiyo"));

        // TODO 自作の Adapter を使いように変更しよう
        mListAdapter = new MyAdapter(this, numberList);

        mListView.setAdapter(mListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "番目がタップされました", Toast.LENGTH_SHORT)
                        .show();
            }
        });
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
}
