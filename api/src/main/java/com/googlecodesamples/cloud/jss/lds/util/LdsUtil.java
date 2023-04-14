package com.googlecodesamples.cloud.jss.lds.util;

import java.util.UUID;
import org.springframework.util.StringUtils;

public class LdsUtil {
  private static final char URL_SLASH = '/';

  /** Get base path of a file. */
  public static String getResourceBasePath(String basePath) {
    String bucketBasePath = StringUtils.trimLeadingCharacter(basePath, URL_SLASH);
    return basePath.substring(0, basePath.length() - bucketBasePath.length());
  }

  /** Get relative path of a file from the base. */
  public static String getFileBucketPath(String basePath, String fileId) {
    String bucketBasePath = StringUtils.trimLeadingCharacter(basePath, URL_SLASH);
    return StringUtils.trimTrailingCharacter(bucketBasePath, URL_SLASH) + URL_SLASH + fileId;
  }

  /** Generate UUID. */
  public static String generateUuid() {
    return UUID.randomUUID().toString();
  }

  /** Get id from the path. */
  public static String getPathId(String path) {
    String[] pathArr = path.split(String.valueOf(URL_SLASH));
    return pathArr[pathArr.length - 1];
  }
}
