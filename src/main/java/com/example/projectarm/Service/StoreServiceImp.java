package com.example.projectarm.Service;

import com.example.projectarm.Exception.ArchiveStoreException;
import com.example.projectarm.Exception.ExceptionFileNotfound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import org.springframework.core.io.Resource;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StoreServiceImp implements StoreService {

    // ที่เก็บข้อมูลที่ตั้ง
    @Value("${storage.location}")
    private String storageLocation;

    // ใช้เพื่อระบุว่าเมธอดนี้จะถูกดำเนินการทุกครั้งที่พบอินสแตนซ์ใหม่ของคลาสนี้
    @PostConstruct
    @Override
    public void startArchiveStore() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException exception) {
            // ไม่สามารถเริ่มต้นตำแหน่งในที่เก็บไฟล์
            throw new ArchiveStoreException("Failed to initialize the location in the file store");
        }
    }

    @Override
    public String ArchiveStore(MultipartFile archive) {
        String nameArchive = archive.getOriginalFilename();
        if (archive.isEmpty()) {
            // ไม่สามารถจัดเก็บไฟล์เปล่าได้
            throw new ArchiveStoreException("Can't store an empty file");
        }
        try {
            InputStream inputStream = archive.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nameArchive),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) { // เกิดข้อผิดพลาดในการจัดเก็บไฟล์
            throw new ArchiveStoreException("Error storing file" + nameArchive, exception);
        }
        return nameArchive;
    }

    @Override
    public Path fileupload(String namearchive) {
        return Paths.get(storageLocation).resolve(namearchive);
    }

    @Override
    public Resource LoadResource(String namearchive) {
        try {
            Path archive = fileupload(namearchive);
            Resource resource = new UrlResource(archive.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                // ไม่พบไฟล์
                throw new ExceptionFileNotfound("The file could not be found" + namearchive);
            }
        } catch (MalformedURLException exception) {
            throw new ExceptionFileNotfound("The file could not be found" + namearchive, exception);
        }
    }

    @Override
    public void Deletefile(String namearchive) {
        Path archive = fileupload(namearchive);
        try {
            FileSystemUtils.deleteRecursively(archive);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
