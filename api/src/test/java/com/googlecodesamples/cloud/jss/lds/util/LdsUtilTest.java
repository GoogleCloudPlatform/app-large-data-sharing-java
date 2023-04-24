package com.googlecodesamples.cloud.jss.lds.util;

import com.googlecodesamples.cloud.jss.lds.model.BaseFileTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdsUtilTest {

    private static final Logger log = LoggerFactory.getLogger(LdsUtilTest.class);

    @Test
    public void testGetFileBucketPath() {
        String fileBucketPath = LdsUtil.getFileBucketPath(BaseFileTest.BASE_PATH, BaseFileTest.FILE_ID);
        Assert.assertEquals(BaseFileTest.FULL_PATH, fileBucketPath);
    }

    @Test
    public void testGetPathId() {
        String id = LdsUtil.getPathId(BaseFileTest.FULL_PATH);
        Assert.assertEquals(BaseFileTest.FILE_ID, id);
    }
}
