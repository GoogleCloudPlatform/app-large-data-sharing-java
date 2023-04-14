package com.googlecodesamples.cloud.jss.lds.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageBatch;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
  private final Storage storage;

  @Value("${storage.bucket.name}")
  private String bucketName;

  public StorageService() {
    this.storage = StorageOptions.getDefaultInstance().getService();
  }

  /**
   * Save a file to Cloud Storage.
   *
   * @param fileId unique id of the file
   * @param contentType content type of the file
   * @param content content of the file
   */
  public void save(String fileId, String contentType, byte[] content) {
    BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileId).setContentType(contentType).build();
    storage.create(blobInfo, content);
  }

  /**
   * Delete a file with given fileId.
   *
   * @param fileId unique id of a file
   */
  public void delete(String fileId) {
    storage.delete(bucketName, fileId);
  }

  /** Delete all files in the bucket. */
  public void batchDelete() {
    Page<Blob> blobs = storage.list(bucketName);
    if (!blobs.getValues().iterator().hasNext()) {
      return;
    }
    StorageBatch batchRequest = storage.batch();
    for (Blob blob : blobs.iterateAll()) {
      batchRequest.delete(blob.getBlobId());
    }
    batchRequest.submit();
  }
}
