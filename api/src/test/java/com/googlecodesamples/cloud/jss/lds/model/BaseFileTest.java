/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecodesamples.cloud.jss.lds.model;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class BaseFileTest {

    @Test
    public void testGetThumbnailPath() {
        BaseFile file = getTestFile(1, true);
        String expected = "test-path-1_small";
        assertThat(file.getThumbnailPath()).isEqualTo(expected);
    }

    @Test
    public void testIsFile() {
        BaseFile file = getTestFile(1, false);
        assertThat(file.isImage()).isFalse();
    }

    @Test
    public void testIsImage() {
        BaseFile file = getTestFile(1, true);
        assertThat(file.isImage()).isTrue();
    }

    public static BaseFile getTestFile(int serialNumber, boolean isImage) {
        BaseFile file = new BaseFile();
        file.setId("test-" + serialNumber);
        file.setPath("test-path-" + serialNumber);
        if (isImage) {
            file.setName("test-filename-" + serialNumber + ".png");
        } else {
            file.setName("test-filename-" + serialNumber);
        }

        file.setUrl("resources/test-url-" + serialNumber);
        file.setTags(List.of("test-tag"));
        file.setCreateTime(new Date());
        file.setUpdateTime(file.getCreateTime());
        return file;
    }
}
