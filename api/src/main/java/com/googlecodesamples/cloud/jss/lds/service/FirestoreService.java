package com.googlecodesamples.cloud.jss.lds.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.googlecodesamples.cloud.jss.lds.model.BaseFile;
import com.googlecodesamples.cloud.jss.lds.model.FileMeta;
import com.googlecodesamples.cloud.jss.lds.util.LdsUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FirestoreService {
  private static final String TAGS = "tags";
  private static final String ORDER_NO = "orderNo";
  private final Firestore firestore;

  @Value("${firestore.collection.name}")
  private String collectionName;

  @Value("${resource.path}")
  private String basePath;

  public FirestoreService() {
    this.firestore = FirestoreOptions.getDefaultInstance().getService();
  }

  /**
   * Save metadata of a file to Firestore.
   *
   * @param fileMeta metadata of the file
   */
  public void save(FileMeta fileMeta) throws Exception {
    DocumentReference docRef = firestore.collection(collectionName).document(fileMeta.getId());
    docRef.set(fileMeta).get();
  }

  /**
   * Search a file with given fileId.
   *
   * @param fileId unique id of the file
   * @return file data
   */
  public BaseFile getFileById(String fileId) throws Exception {
    ApiFuture<QuerySnapshot> future =
        firestore.collection(collectionName).whereEqualTo(FieldPath.documentId(), fileId).get();
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    if (documents.isEmpty()) {
      return null;
    }
    return convertDoc2File(documents).get(0);
  }

  /**
   * Search files with given tags.
   *
   * @param tags list of tags label the files
   * @param orderNo application defined column for referencing order
   * @param size number of files return
   * @return list of files data
   */
  public List<BaseFile> getFilesByTag(List<String> tags, String orderNo, int size)
      throws Exception {
    ApiFuture<QuerySnapshot> future;
    Query query =
        firestore.collection(collectionName).orderBy(ORDER_NO, Query.Direction.DESCENDING);
    if (!CollectionUtils.isEmpty(tags)) {
      query = query.whereArrayContainsAny(TAGS, tags);
    }
    if (StringUtils.hasText(orderNo)) {
      query = query.startAfter(orderNo);
    }
    future = query.limit(size).get();
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    return convertDoc2File(documents);
  }

  /**
   * Delete a file from Firestore with given fileId.
   *
   * @param fileId unique id of the file
   */
  public void delete(String fileId) {
    firestore.collection(collectionName).document(fileId).delete();
  }

  /** Delete a collection in Firestore. */
  public void deleteCollection() {
    firestore.recursiveDelete(firestore.collection(collectionName));
  }

  /**
   * Convert documents retrieved from Firestore to BaseFile object.
   *
   * @param documents lis of documents retrieved from Firestore
   * @return list of files data
   */
  private List<BaseFile> convertDoc2File(List<QueryDocumentSnapshot> documents) {
    String resourceBasePath = LdsUtil.getResourceBasePath(basePath);
    return documents.stream()
        .map(doc -> new BaseFile(doc, resourceBasePath))
        .collect(Collectors.toList());
  }
}
