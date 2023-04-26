package com.googlecodesamples.cloud.jss.lds.util;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class LdsUtilTest {

    private static final String BASE_PATH = "192.168.0.1/resource";
    private static final String FILE_ID = "test";
    private static final String FULL_PATH = BASE_PATH + "/" + FILE_ID;

    @Test
    public void testGetFileBucketPath() {
        String fileBucketPath = LdsUtil.getFileBucketPath(BASE_PATH, FILE_ID);
        assertThat(fileBucketPath).isEqualTo(FULL_PATH);
    }

    @Test
    public void testGetPathId() {
        String id = LdsUtil.getPathId(FULL_PATH);
        assertThat(id).isEqualTo(FILE_ID);
    }
}
