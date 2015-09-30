package com.example.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hideyuki.Kikuma on 15/09/28.
 */
public class PersonOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "person";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "person";
    private static final String COLUMN_NAME_ID = "_id";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_COMMENT = "comment";
    private static final String CREATE_PERSON_TABLE = "create table " + TABLE_NAME + "(" +
            " _id integer PRIMARY KEY AUTOINCREMENT," +
            " name text not null," +
            " age integer not null," +
            " comment text" +
            ");";

    private static final String TAG = PersonOpenHelper.class.getSimpleName();

    public PersonOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 今日はこれは使いません
        // 興味がある人は自分で調べてみましょう
    }

    public List<PersonEntity> selectAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<PersonEntity> list = new ArrayList<>();
        try {
            db = getReadableDatabase();
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT},
                    null, null, null, null, COLUMN_NAME_ID
            );
            boolean hasNext = cursor.moveToFirst();
            while (hasNext) {
                PersonEntity person = PersonEntity.getBuilder()
                        .setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)))
                        .setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)))
                        .setAge(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_AGE)))
                        .setComment(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMMENT)))
                        .build();
                list.add(person);
                hasNext = cursor.moveToNext();
            }


        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return list;
    }

    public PersonEntity selectById(long id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // TODO SQLiteDatabaseを取得
            // TODO query実行
            boolean hasNext = cursor.moveToFirst();
            if (!hasNext) {
                return null;
            }
            return PersonEntity.getBuilder()
                    .setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)))
                    .setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)))
                    .setAge(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_AGE)))
                    .setComment(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMMENT)))
                    .build();

        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    /**
     * 1行insertする
     *
     * @param person
     * @return
     */
    public long insert(PersonEntity person) {
        SQLiteDatabase db = null;
        try {
            // TODO SQLiteDatabaseを取得
            ContentValues values = new ContentValues();
            // TODO ContentValuesにpersonの値を入れる
            // TODO insert実行
            return 0;
        } finally {
            if (db != null) db.close();
        }
    }

    /**
     * 1行updateする
     *
     * @param person
     * @return updateに成功したらtrue それ以外はfalse
     */
    public boolean update(PersonEntity person) {
        SQLiteDatabase db = null;
        try {
            // TODO SQLiteDatabaseを取得
            ContentValues values = new ContentValues();
            // TODO ContentValuesにpersonの値を入れる
            // TODO update実行
            return false;
        } finally {
            if (db != null) db.close();
        }
    }
}
