package com.example.rssreader.db;

import com.example.rssreader.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * テスト自体が動くことを確認するためのテスト
 * Created by Hideyuki.Kikuma on 15/09/30.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AllGreenTest {
    @Test
    public void testGreen() {
        String src = "test";
        assertThat(src, is("test"));

    }


}
