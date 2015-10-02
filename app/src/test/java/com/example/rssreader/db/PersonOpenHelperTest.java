package com.example.rssreader.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rssreader.BuildConfig;
import com.example.rssreader.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by Hideyuki.Kikuma on 15/09/29.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PersonOpenHelperTest {

    private static final String TABLE_NAME = "person";
    private static final String COLUMN_NAME_ID = "_id";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_AGE = "age";
    private static final String COLUMN_NAME_COMMENT = "comment";

    private PersonOpenHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PersonOpenHelper(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {
        helper.close();
    }

    /**
     * テーブルが作成できたことを確認する
     */
    @Test
    public void existsPersonTable() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT}, null, null, null, null, null);
        assertThat(cursor.getCount(), is(0));
        cursor.close();
        db.close();
    }

    /**
     * 空のデータベースから全件取得するけど、空なので空のリストが返ってくる
     */
    @Test
    public void testSelectAllCaseEmpty() {
        List<PersonEntity> result = helper.selectAll();
        assertThat(result.isEmpty(), is(true));
    }

    /**
     * 適当に何件か入れた状態でselect
     */
    @Test
    public void testSelectAll() {
        // 準備で何件かレコードを作成
        SQLiteDatabase db = helper.getWritableDatabase();
        Person[] srcData = {
                new Person("Keishin Yokomaku", 26, "comment K"),
                new Person("Takafumi Nanao", 28, "comment T"),
                new Person("Atsuto Yamada", 22, "comment A"),
                new Person("Hideyuki Kikuma", 27, "comment H"),

        };
        Map<Long, Person> map = new HashMap<>();
        for (Person data : srcData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, data.getName());
            values.put(COLUMN_NAME_AGE, data.getAge());
            values.put(COLUMN_NAME_COMMENT, data.getComment());
            long id = db.insert(TABLE_NAME, "", values);
            map.put(id, data);
        }
        db.close();


        // selectしてみる
        List<PersonEntity> results = helper.selectAll();

        // 結果を確認
        // 数が想定通りか確認
        assertThat(results.size(), is(srcData.length));
        for (int i = 0; i < results.size(); i++) {
            Person actual = results.get(i);
            Person expected = map.get((long) (i + 1));

            assertThat(actual, notNullValue());
            // 中身が正しいかを確認
            assertThat(actual.getName(), is(expected.getName()));
            assertThat(actual.getAge(), is(expected.getAge()));
            assertThat(actual.getComment(), is(expected.getComment()));
        }
    }

    /**
     * 適当に何件か入れた状態でidをキーにselect
     */
    @Test
    public void testSelectByIdCaseNotFound() {
        // 準備で何件かレコードを作成
        SQLiteDatabase db = helper.getWritableDatabase();
        Person[] srcData = {
                new Person("Keishin Yokomaku", 26, "comment K"),
                new Person("Takafumi Nanao", 28, "comment T"),
                new Person("Atsuto Yamada", 22, "comment A"),
                new Person("Hideyuki Kikuma", 27, "comment H"),

        };
        Map<Long, Person> map = new HashMap<>();
        for (Person data : srcData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, data.getName());
            values.put(COLUMN_NAME_AGE, data.getAge());
            values.put(COLUMN_NAME_COMMENT, data.getComment());
            long id = db.insert(TABLE_NAME, "", values);
            map.put(id, data);
        }
        db.close();

        long targetKey = 1;
        while (map.containsKey(targetKey)) {
            targetKey++;
        }
        // selectする
        Person result = helper.selectById(targetKey);
        assertThat(result, nullValue());

    }

    /**
     * 適当に何件か入れた状態でidをキーにselect
     */
    @Test
    public void testSelectById() {
        // 準備で何件かレコードを作成
        SQLiteDatabase db = helper.getWritableDatabase();
        Person[] srcData = {
                new Person("Keishin Yokomaku", 26, "comment K"),
                new Person("Takafumi Nanao", 28, "comment T"),
                new Person("Atsuto Yamada", 22, "comment A"),
                new Person("Hideyuki Kikuma", 27, "comment H"),

        };
        Map<Long, Person> map = new HashMap<>();
        for (Person data : srcData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_NAME, data.getName());
            values.put(COLUMN_NAME_AGE, data.getAge());
            values.put(COLUMN_NAME_COMMENT, data.getComment());
            long id = db.insert(TABLE_NAME, "", values);
            map.put(id, data);
        }
        db.close();

        // 存在してるはずのキー全部をチェック
        for (Long key : map.keySet()) {
            // selectする
            Person result = helper.selectById(key);
            Person expected = map.get(key);
            assertThat(result.getName(), is(expected.getName()));
            assertThat(result.getAge(), is(expected.getAge()));
            assertThat(result.getComment(), is(expected.getComment()));

        }
    }

    /**
     * 空の状態にデータを入れる
     */
    @Test
    public void testInsertWithBeforeEmpty() {

        PersonEntity src = PersonEntity.getBuilder()
                .setName("Keishin Yokomaku")
                .setAge(26)
                .setComment("comment K")
                .build();

        // insert
        long id = helper.insert(src);

        // 確認のためのデータを取得
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT}, "_id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)), is(src.getName()));
        assertThat(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_AGE)), is(src.getAge()));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMMENT)), is(src.getComment()));
    }

    /**
     * データがある状態からinsert
     */
    @Test
    public void testInsert() {
        SQLiteDatabase wdb = helper.getWritableDatabase();
        Person data = new Person("Takafumi Nanao", 28, "comment T");
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, data.getName());
        values.put(COLUMN_NAME_AGE, data.getAge());
        values.put(COLUMN_NAME_COMMENT, data.getComment());
        long beforeId = wdb.insert(TABLE_NAME, "", values);
        PersonEntity src = PersonEntity.getBuilder()
                .setName("Keishin Yokomaku")
                .setAge(26)
                .setComment("comment K")
                .build();

        // insert
        long id = helper.insert(src);

        // 確認のためのデータを取得
        SQLiteDatabase rdb = helper.getReadableDatabase();
        Cursor cursor = rdb.query(TABLE_NAME, new String[]{COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT}, "_id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        assertThat(id, not(beforeId));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)), is(src.getName()));
        assertThat(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_AGE)), is(src.getAge()));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMMENT)), is(src.getComment()));
    }

    /**
     * 存在しないデータをupdate
     */
    @Test
    public void testUpdateWithDataEmpty() {
        PersonEntity data = PersonEntity.getBuilder()
                .setName("Keishin Yokomaku")
                .setAge(26)
                .setComment("sampleComment")
                .build();

        boolean result = helper.update(data);
        assertThat(result, is(false));

        // 正しくアップデートされたか確認
        SQLiteDatabase rdb = helper.getReadableDatabase();
        Cursor cursor = rdb.query(TABLE_NAME, new String[]{COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT}, null, null, null, null, null, null);
        assertThat(cursor.getCount(), is(0));

    }

    /**
     * 正常にupdate
     */
    @Test
    public void testUpdate() {
        // 最初に入れておくデータを作成
        SQLiteDatabase wdb = helper.getWritableDatabase();
        Person src = new Person("Takafumi Nanao", 28, "comment T");
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, src.getName());
        values.put(COLUMN_NAME_AGE, src.getAge());
        values.put(COLUMN_NAME_COMMENT, src.getComment());
        long id = wdb.insert(TABLE_NAME, "", values);

        PersonEntity data = PersonEntity.getBuilder()
                .setId(id)
                .setName("Keishin Yokomaku")
                .setAge(26)
                .setComment("sampleComment")
                .build();

        boolean result = helper.update(data);
        assertThat(result, is(true));

        // 正しくアップデートされたか確認
        SQLiteDatabase rdb = helper.getReadableDatabase();
        Cursor cursor = rdb.query(TABLE_NAME, new String[]{COLUMN_NAME_NAME, COLUMN_NAME_AGE, COLUMN_NAME_COMMENT}, COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(data.getId())}, null, null, null, null);
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME)), is(data.getName()));
        assertThat(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_AGE)), is(data.getAge()));
        assertThat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMMENT)), is(data.getComment()));
    }
}