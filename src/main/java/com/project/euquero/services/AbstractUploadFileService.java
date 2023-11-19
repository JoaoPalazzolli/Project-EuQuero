package com.project.euquero.services;

import com.cloudinary.Cloudinary;
import com.project.euquero.services.exceptions.FileException;
import com.project.euquero.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AbstractUploadFileService implements UploadFileService {

    private static final Logger LOGGER = Logger.getLogger(AbstractUploadFileService.class.getName());

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {

        LOGGER.info("Fazendo Upload do Arquivo");

        if (file == null){
            throw new FileException(ErrorMessages.FILE_NOT_NULL);
        }

        try {
            return cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("folder", "euquero-images"))
                    .get("url").toString();
        } catch (IOException e){
            throw new FileException(e.getMessage());
        }
    }
}
