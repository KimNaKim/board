package com.example.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload-dir}") // application.properties에서 설정한 ./uploads/값이 주입됨
    private String uploadDir;

    /**
     * 
     * 
     * 파일을 저장하고, 저장된 파일명(UUID)을 반환합니다.
     * 파일이 없으면 null을 반환합니다.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 1. 파일이 비어있으면 null 반환
        if (file == null || file.isEmpty()) {
            return null;
        }
        // 2. 원본 파일명에서 확장자 추출
        String originalFileName = file.getOriginalFilename();
        // "photo.jpg"
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".")); // ".jpg"
        // 3. UUID로 저장용 파일명 생성 (중복 방지) - 사용자가 같은 이름의 파일을 올릴 시 덮어쓰기가 되기 때문
        String savedFileName = UUID.randomUUID() + ext; // "a1b2c3d4-....jpg"
        // 4. 저장 폴더가 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더가 존재하지 않을 시 자동 생성 - 중간 경로까지 전부 만들어줌
        }
        // 5. 파일 저장
        File savedFile = new File(dir, savedFileName);
        file.transferTo(savedFile); // MultipartFile의 내용을 실제 파일로 저장
        // 6. 저장된 파일명 반환
        return savedFileName;
    }

    /**
     * 
     * 파일의 전체 경로를 반환합니다.
     */
    public String getFullPath(String fileName) {
        return uploadDir + fileName;
    }
}