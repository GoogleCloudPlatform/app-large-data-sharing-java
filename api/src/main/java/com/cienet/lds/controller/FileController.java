package com.cienet.lds.controller;

import com.cienet.lds.model.BaseFile;
import com.cienet.lds.model.FileResponse;
import com.cienet.lds.service.FileService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {
  private static final Logger log = LoggerFactory.getLogger(FileController.class);
  private static final String STRING_SEPARATOR = "\\s+";
  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/healthchecker")
  public ResponseEntity<?> healthCheck() {
    log.trace("entering healthCheck()");
    return ResponseEntity.ok().build();
  }

  @PostMapping("/files")
  public ResponseEntity<?> uploadFiles(
      @RequestParam List<MultipartFile> files, @RequestParam String tags) throws Exception {
    log.trace("entering uploadFiles()");
    List<String> tagList = getTagList(tags);
    List<BaseFile> fileList = fileService.uploadFiles(files, tagList);
    return ResponseEntity.status(HttpStatus.CREATED).body(new FileResponse(fileList));
  }

  @GetMapping("/files")
  public ResponseEntity<?> getFilesByTag(
      @RequestParam(required = false) String tags,
      @RequestParam(required = false) String orderNo,
      @RequestParam(required = false, defaultValue = "50") int size)
      throws Exception {
    log.trace("entering getFilesByTag()");
    List<String> tagList = getTagList(tags);
    List<BaseFile> fileList = fileService.getFilesByTag(tagList, orderNo, size);
    if (CollectionUtils.isEmpty(fileList)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(new FileResponse(fileList));
  }

  @PutMapping("/files/{id}")
  public ResponseEntity<?> updateFile(
      @PathVariable("id") String fileId,
      @RequestParam(required = false) MultipartFile file,
      @RequestParam String tags)
      throws Exception {
    log.trace("entering updateFile()");
    BaseFile oldFile = fileService.getFileById(fileId);
    if (oldFile == null) {
      return ResponseEntity.notFound().build();
    }
    List<String> tagList = getTagList(tags);
    BaseFile newFile = fileService.updateFile(file, tagList, oldFile);
    return ResponseEntity.ok().body(new FileResponse(newFile));
  }

  @DeleteMapping("/files/{id}")
  public ResponseEntity<?> deleteFile(@PathVariable("id") String fileId) throws Exception {
    log.trace("entering deleteFile()");
    BaseFile file = fileService.getFileById(fileId);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }
    fileService.deleteFile(file);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/reset")
  public ResponseEntity<?> resetFile() throws Exception {
    log.trace("entering resetFile()");
    fileService.resetFile();
    return ResponseEntity.noContent().build();
  }

  private List<String> getTagList(String tags) {
    if (!StringUtils.hasText(tags)) {
      return new ArrayList<>();
    }
    return Arrays.stream(tags.split(STRING_SEPARATOR))
        .map(String::trim)
        .collect(Collectors.toList());
  }
}
