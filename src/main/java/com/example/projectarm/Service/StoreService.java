package com.example.projectarm.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface StoreService {

    public void startArchiveStore();

    // ที่เก็บเอกสาร
    public String ArchiveStore(MultipartFile archive); // คลังเก็บเอกสารสำคัญ

    public Path fileupload(String namearchive);

    public Resource LoadResource(String namearchive);// ทรัพยากร

    public void Deletefile(String namearchive);
}
