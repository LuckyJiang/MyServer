package com.example.myserver.util;

import com.rzon.exam.exception.InternalServerException;
import com.rzon.exam.exception.ResourceNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtil {
    
    public static final Path TEMP_FOLDER = Paths.get(System.getProperty("user.dir"), "tempFile");

    static {
        try {
            if (Files.notExists(TEMP_FOLDER)) {
                Files.createDirectories(TEMP_FOLDER);
            }
        } catch (IOException e) {
            throw new InternalServerException("初始化默认目录失败");
        }
    }

    private static final Integer COUNT = 1024;

    private FileUtil() {
    }

    public static Path createTempFile(final String fileName) {
        final Path resolve = TEMP_FOLDER.resolve(fileName);
        delete(resolve);
        return resolve;
    }

    private static void exist(final Path resolve) {
        if (Files.exists(resolve)) {
            try {
                Files.delete(resolve);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void multipartFileToFile(final MultipartFile file, final Path dest) {
        try {
            if (!Files.exists(dest.getParent())) {
                Files.createDirectories(dest.getParent());
            }
            file.transferTo(dest.toFile());
        } catch (IllegalStateException | IOException e) {
            throw new InternalServerException("transferTo失败");
        }
    }

    public static void delete(final Path tempFile) {
        try {
            if (Files.exists(tempFile)) {
                Files.delete(tempFile);
            }            
        } catch (IOException e) {
            throw new InternalServerException("文件删除失败");
        }
    }

    public static ResponseEntity<InputStreamResource> handleFileRequest(final String fileName, final Path path) {
        Assert.notNull(fileName, "fileName need non-null");
        Assert.notNull(path, "path need non-null");

        try {
            final InputStream is = Files.newInputStream(path);
            final InputStreamResource isr = new InputStreamResource(is);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, getDisposition(fileName))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(path.toFile().length()).body(isr);
        } catch (IOException e) {
            throw new ResourceNotFoundException("文件丢失", path);
        }
    }

    public static ResponseEntity<byte[]> handleFileRequest(final String fileName, final byte[] bytes) {
        Assert.notNull(fileName, "fileName need non-null");
        Assert.notNull(bytes, "bytes need non-null");

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, getDisposition(fileName))
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytes.length).body(bytes);
    }

    private static String getDisposition(final String fileName) {
        Assert.notNull(fileName, "fileName need non-null");

        try {
            final String encodeFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            return "attachment;filename=" + encodeFileName + ";filename*=UTF-8''" + encodeFileName;
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerException("设置Disposition失败", fileName);
        }
    }

    public static void writeInputStreamToFile(final InputStream inputStream, final Path filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
            final byte[] buffer = new byte[COUNT];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write InputStream to file", e);
        }
    }
}
