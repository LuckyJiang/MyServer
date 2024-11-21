package com.example.myserver.file.service;


import com.example.myserver.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Service
public class FileService {
    
    public ResponseEntity<InputStreamResource> download() {
        final ClassPathResource classPathResource = new ClassPathResource("templates/考试模板.xlsx");
        final Path tempFile = FileUtil.createTempFile(UUID.randomUUID().toString() + classPathResource.getFilename());
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
            FileUtil.writeInputStreamToFile(inputStream, tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileUtil.handleFileRequest(classPathResource.getFilename(), tempFile);

    }
}
