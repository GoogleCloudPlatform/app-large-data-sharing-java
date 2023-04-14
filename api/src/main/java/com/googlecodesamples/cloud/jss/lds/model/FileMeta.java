package com.googlecodesamples.cloud.jss.lds.model;

import com.googlecodesamples.cloud.jss.lds.util.LdsUtil;
import java.util.List;

public class FileMeta {
  private String id;
  private String path;
  private String name;
  private List<String> tags;
  private String orderNo;
  private long size;

  public FileMeta() {}

  public FileMeta(String id, String path, String name, List<String> tags, long size) {
    this.id = id;
    this.path = path;
    this.name = name;
    this.tags = tags;
    this.orderNo = System.currentTimeMillis() + "-" + LdsUtil.getPathId(path);
    this.size = size;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}
