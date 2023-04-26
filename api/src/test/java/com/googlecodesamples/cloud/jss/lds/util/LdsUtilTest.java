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
