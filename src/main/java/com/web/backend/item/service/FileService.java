package com.web.backend.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileService {
    /**
     * 1. 파일을 만드는 일
     * 2. 파일을 삭제하는 일
     */

    public String uploadFile(String uploadPath, String oriFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = oriFileName.substring(oriFileName.lastIndexOf(".")); // 마지막 .글자 기준으로 가져온다. 확장자
        String savedFileName = uuid.toString() + extension;
        String fileUploadUrl = uploadPath + "/" + savedFileName; // 저장되는 경로
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close(); //자원 반납
        return savedFileName;
    }

    public void deleteFile(String filePath) {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("Deleted file={} 파일을 삭제하였습니다.", filePath);
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
