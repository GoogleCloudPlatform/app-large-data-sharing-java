package com.googlecodesamples.cloud.jss.lds.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class BaseFileTest {

    public static final String BASE_PATH = "192.168.0.1/resource";

    public static final String FILE_ID = "test";

    public static final String FULL_PATH = BASE_PATH + "/" + FILE_ID;

    @Test
    public void testGetThumbnailPath() {
        BaseFile file = getTestFile(1);
        Assert.assertEquals("test-path-1_small", file.getThumbnailPath());
    }

    @Test
    public void testIsFile() {
        BaseFile file = getTestFile(1);
        Assert.assertFalse(file.isImage());
    }

    @Test
    public void testIsImage() {
        BaseFile image = getTestImage(1);
        Assert.assertTrue(image.isImage());
    }

    public static BaseFile getTestFile(int serialNumber) {
        BaseFile file = new BaseFile();
        file.setId("test-" + serialNumber);
        file.setPath("test-path-" + serialNumber);
        file.setName("test-filename-" + serialNumber);
        file.setUrl("resources/test-url" + serialNumber);
        file.setTags(List.of("test-tag"));
        file.setCreateTime(new Date());
        file.setUpdateTime(file.getCreateTime());
        return file;
    }

    public static BaseFile getTestImage(int serialNumber) {
        BaseFile file = new BaseFile();
        file.setId("test-" + serialNumber);
        file.setPath("test-path-" + serialNumber);
        file.setName("test-filename-" + serialNumber + ".png");
        file.setUrl("resources/test-url" + serialNumber);
        file.setTags(List.of("test-tag"));
        file.setCreateTime(new Date());
        file.setUpdateTime(file.getCreateTime());
        return file;
    }
}
