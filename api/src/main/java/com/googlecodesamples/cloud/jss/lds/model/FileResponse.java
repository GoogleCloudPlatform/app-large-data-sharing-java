package com.googlecodesamples.cloud.jss.lds.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse {
  private List<BaseFile> files;
  private BaseFile file;

  public FileResponse(List<BaseFile> files) {
    this.files = files;
  }

  public FileResponse(BaseFile file) {
    this.file = file;
  }

  public List<BaseFile> getFiles() {
    return files;
  }

  public void setFiles(List<BaseFile> files) {
    this.files = files;
  }

  public BaseFile getFile() {
    return file;
  }

  public void setFile(BaseFile file) {
    this.file = file;
  }
}
