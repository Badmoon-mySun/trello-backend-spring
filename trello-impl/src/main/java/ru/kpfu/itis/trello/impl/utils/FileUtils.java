package ru.kpfu.itis.trello.impl.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Component
public class FileUtils {

    @Value("${file.save.path}")
    private String fileSavePath;

    public String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

                FileOutputStream stream = new FileOutputStream(fileSavePath + File.separator + fileName);

                stream.write(file.getBytes());

                stream.close();

                return fileName;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return null;
    }
}
