package com.example.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * RSSのデータを保存するDBを管理するためのクラス
 * Created by takafumi.nanao on 10/2/15.
 */
public class FeedItemOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rss_reader";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "feed";
    private static final String COLUMN_NAME_ID = "_id";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_DESCRIPTION = "description";
    private static final String COLUMN_NAME_CATEGORY = "category";
    private static final String COLUMN_NAME_DATE = "date";
    private static final String COLUMN_NAME_URI = "uri";

    private static final String CREATE_FEED_TABLE = "create table " + TABLE_NAME + " (" +
            COLUMN_NAME_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_TITLE + " text not null," +
            COLUMN_NAME_DESCRIPTION + " text," +
            COLUMN_NAME_CATEGORY + " text," +
            COLUMN_NAME_DATE + " text," +
            COLUMN_NAME_URI + " text not null" +
            ");";

    public FeedItemOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 今日はこれは使いません
        // 興味がある人は自分で調べてみましょう
    }

    public List<FeedItemEntity> selectAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<FeedItemEntity> list = new ArrayList<>();
        try {
            db = getReadableDatabase();
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{COLUMN_NAME_ID, COLUMN_NAME_TITLE, COLUMN_NAME_DESCRIPTION, COLUMN_NAME_CATEGORY, COLUMN_NAME_DATE, COLUMN_NAME_URI},
                    null, null, null, null, COLUMN_NAME_ID
            );
            boolean hasNext = cursor.moveToFirst();
            while (hasNext) {
                FeedItemEntity feedItem = FeedItemEntity.getBuilder()
                        .setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)))
                        .setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)))
                        .setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)))
                        .setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORY)))
                        .setDate(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE)))
                        .setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_URI))))
                        .build();
                list.add(feedItem);
                hasNext = cursor.moveToNext();
            }


        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return list;
    }

    public boolean insertList(List<FeedItemEntity> list) {
        boolean result = true;
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            for (FeedItemEntity feedItem: list) {
                values.clear();
                values.put(COLUMN_NAME_TITLE, feedItem.getTitle());
                values.put(COLUMN_NAME_DESCRIPTION, feedItem.getDescription());
                values.put(COLUMN_NAME_CATEGORY, feedItem.getCategory());
                values.put(COLUMN_NAME_DATE, feedItem.getDate());
                values.put(COLUMN_NAME_URI, feedItem.getUri().toString());
                db.insert(TABLE_NAME, "", values);
            }
        }catch (Exception e) {
            result = false;
        } finally {
            if (db != null) db.close();
        }
        return result;
    }

    public int selectAllCount() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            String sql = "SELECT COUNT(*) FROM feed";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            count= cursor.getInt(0);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return count;
    }
}
