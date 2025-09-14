package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.AttachmentDTO;
import uz.pdp.startup.service.AttachmentService;

/**
 * Created by: Umar
 * DateTime: 7/16/2025 4:22 PM
 */
@RestController
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;



    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<AttachmentDTO> uploadAttachment(@RequestParam("/file") MultipartFile file) {
        ApiResult<AttachmentDTO> upload = attachmentService.upload(file);

        return ApiResult.success(upload.getData());
    }


    @DeleteMapping("/{id}")
    public ApiResult<String> deleteAttachment(@PathVariable Long id) {
        ApiResult<String> delete = attachmentService.delete(id);
        return ApiResult.success(delete.getData());
    }

}
