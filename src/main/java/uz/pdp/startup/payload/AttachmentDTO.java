package uz.pdp.startup.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.startup.entity.Attachment;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachmentDTO implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String fileName;
    @NotBlank
    private String fileType;
    @NotBlank
    private String filePath;
    @NotNull
    private Long fileSize;

    @NotBlank
    private String url;


    public AttachmentDTO(Attachment attachment) {
        this.id = attachment.getId();
        this.fileName = attachment.getFileName();
        this.fileType = attachment.getFileType();
        this.filePath = attachment.getFilePath();
        this.fileSize = attachment.getFileSize();
        this.url = "/api/attachment/download/" + attachment.getId();
    }
}