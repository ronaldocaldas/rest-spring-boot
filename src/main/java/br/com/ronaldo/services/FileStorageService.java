package br.com.ronaldo.services;

import br.com.ronaldo.config.FileStorageConfig;
import br.com.ronaldo.controllers.FileController;
import br.com.ronaldo.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    public static final String COULD_NOT_CREATE_THE_DIRECTORY_WHERE_FILES_WILL_BE_STORED = "Could not create the directory where files will be stored!";
    public static final String FILENAME_CONTAINS_A_INVALID_PATH_SEQUENCE = "Filename contains a invalid path sequence ";

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageConfig fileStorageConfig) {

        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            logger.info("Creating directories!");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error(COULD_NOT_CREATE_THE_DIRECTORY_WHERE_FILES_WILL_BE_STORED);
            throw new FileStorageException(COULD_NOT_CREATE_THE_DIRECTORY_WHERE_FILES_WILL_BE_STORED, e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                String messageInvalidFileName = FILENAME_CONTAINS_A_INVALID_PATH_SEQUENCE + fileName;
                logger.error(messageInvalidFileName);
                throw new FileStorageException(messageInvalidFileName);

            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Saving file in disc!");
            return fileName;
        } catch (Exception e) {
            String messageNotStorageError = "Could not store file " + fileName + ". Please try again!";
            logger.error(messageNotStorageError);
            throw new FileStorageException(messageNotStorageError);
        }
    }
}
