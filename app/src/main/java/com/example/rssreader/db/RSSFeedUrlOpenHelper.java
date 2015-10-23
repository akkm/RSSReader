package com.example.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akkuma on 2015/10/24.
 */
public class RSSFeedUrlOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rss_feed_url.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "feeds";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_IS_PRIMARY = "is_primary";

    public RSSFeedUrlOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s (%s integer PRIMARY KEY AUTOINCREMENT, %s text, %s text not null, %s integer not null);",
                TABLE_NAME, COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_IS_PRIMARY));

        RSSFeedUrlEntity google = new RSSFeedUrlEntity(null,"Googleニュース テクノロジー",
                "http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&topic=p", true);
        insert(google, db);

        RSSFeedUrlEntity yahoo = new RSSFeedUrlEntity(null, "Yahoo!ニュース コンピューター",
                "http://news.yahoo.co.jp/pickup/computer/rss.xml", false);
        insert(yahoo, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }

    public List<RSSFeedUrlEntity> selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        List<RSSFeedUrlEntity> list = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_URL, COLUMN_NAME_IS_PRIMARY},
                null, null, null, null, COLUMN_NAME_ID);
        boolean hasNext = cursor.moveToFirst();
        while (hasNext) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_URL));
            boolean isPrimary = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_IS_PRIMARY)) == 1;

            RSSFeedUrlEntity entity = new RSSFeedUrlEntity(id, name ,url, isPrimary);
            list.add(entity);
            hasNext = cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return list;
    }


    public void insert(RSSFeedUrlEntity entity) {
        SQLiteDatabase db = getWritableDatabase();
        insert(entity, db);
        db.close();
    }

    public void insert(RSSFeedUrlEntity entity, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, entity.getId());
        values.put(COLUMN_NAME_NAME, entity.getName());
        values.put(COLUMN_NAME_URL, entity.getUrl());
        values.put(COLUMN_NAME_IS_PRIMARY, entity.isPrimary() ? 1 : 0);
        db.insert(TABLE_NAME, null, values);
    }

    public void update(RSSFeedUrlEntity entity) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, entity.getId());
        values.put(COLUMN_NAME_NAME, entity.getName());
        values.put(COLUMN_NAME_URL, entity.getUrl());
        values.put(COLUMN_NAME_IS_PRIMARY, entity.isPrimary() ? 1 : 0);
        db.update(TABLE_NAME, values, COLUMN_NAME_ID + "=?", new String[]{String.valueOf(entity.getId())});

        db.close();
    }

    public void delete(RSSFeedUrlEntity entity) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME_ID + "=?", new String[]{String.valueOf(entity.getId())});
        db.close();
    }
}
