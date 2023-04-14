package com.cienet.lds.service;

import com.cienet.lds.model.BaseFile;
import com.cienet.lds.model.FileMeta;
import com.cienet.lds.util.LdsUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
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

  public void save(FileMeta fileMeta) throws Exception {
    DocumentReference docRef = firestore.collection(collectionName).document(fileMeta.getId());
    docRef.set(fileMeta).get();
  }

  public BaseFile getFileById(String fileId) throws Exception {
    ApiFuture<QuerySnapshot> future =
        firestore.collection(collectionName).whereEqualTo(FieldPath.documentId(), fileId).get();
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    if (documents.isEmpty()) {
      return null;
    }
    return convertDoc2File(documents).get(0);
  }

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

  public void delete(String fileId) {
    firestore.collection(collectionName).document(fileId).delete();
  }

  public void deleteCollection() {
    firestore.recursiveDelete(firestore.collection(collectionName));
  }

  private List<BaseFile> convertDoc2File(List<QueryDocumentSnapshot> documents) {
    String resourceBasePath = LdsUtil.getResourceBasePath(basePath);
    return documents.stream()
        .map(doc -> new BaseFile(doc, resourceBasePath))
        .collect(Collectors.toList());
  }
}
