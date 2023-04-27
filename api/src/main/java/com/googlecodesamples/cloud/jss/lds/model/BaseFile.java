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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseFile extends FileMeta {
  private static final List<String> IMG_EXTENSIONS = List.of("png", "jpeg", "jpg", "gif");
  private static final String THUMBNAIL_EXTENSION = "_small";
  private String url;
  private String thumbUrl;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updateTime;

  public BaseFile() {}

  public BaseFile(QueryDocumentSnapshot document, String resourceBasePath) {
    BaseFile file = document.toObject(BaseFile.class);
    BeanUtils.copyProperties(file, this);
    this.setUrl(resourceBasePath + file.getPath());
    this.setThumbUrl(resourceBasePath + file.getThumbnailPath());
    this.setCreateTime(document.getCreateTime().toDate());
    this.setUpdateTime(document.getUpdateTime().toDate());
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getThumbUrl() {
    return thumbUrl;
  }

  public void setThumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @JsonIgnore
  public String getThumbnailPath() {
    return getPath() + THUMBNAIL_EXTENSION;
  }

  @JsonIgnore
  public boolean isImage() {
    return IMG_EXTENSIONS.stream().anyMatch(e -> getName().toLowerCase().endsWith(e));
  }
}
