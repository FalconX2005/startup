package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.startup.entity.Attachment;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.AttachmentDTO;
import uz.pdp.startup.repository.AttachmentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by: Umar
 * DateTime: 7/16/2025 1:15 PM
 */
@Service
@RequiredArgsConstructor
public class AttachmentService {


    @Value("${app.files.baseFolder}")
    private String baseFolder;

    private final AttachmentRepository attachmentRepository;


    public ApiResult<AttachmentDTO> upload(MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ApiResult.error("File is empty");
            }


            String contentType = file.getContentType();

            long size = file.getSize();

            if (size > 20 * 1024 * 1024) {
                return ApiResult.error("File is too big");
            }
            if (!Objects.requireNonNull(contentType).startsWith("image/")) {
                return ApiResult.error("Invalid file type");
            }


            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();


            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return ApiResult.error("Original file name is missing");
            }
            String name = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFilename);

            Path path = Paths.get(baseFolder)
                    .resolve(String.valueOf(year))
                    .resolve(String.valueOf(month))
                    .resolve(String.valueOf(day));

            Files.createDirectories(path);

            Attachment attachment = new Attachment();
            attachment.setFileName(name);
            attachment.setFilePath(path.toString());
            attachment.setFileType(contentType);
            attachment.setFileSize(size);

            Attachment save = attachmentRepository.save(attachment);


            return ApiResult.success(new AttachmentDTO(save));

        } catch (IOException e) {
            return ApiResult.error("Error while uploading file" + e.getMessage());
        }

    }

    public ApiResult<String> delete(Long id) {
        Optional<Attachment> attachmentById = attachmentRepository.findAttachmentById(id);

        if (attachmentById.isEmpty()) {
            return ApiResult.error("Attachment not found whith id " + id);
        }

        Attachment attachment = attachmentById.get();
        Path path = Paths.get(attachment.getFilePath()).resolve(attachment.getFileName());


        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            return ApiResult.error("Error during deleting file " + e.getMessage());
        }

        attachmentRepository.delete(attachment);
        return ApiResult.success("Attachment deleted successfully");
    }
}
