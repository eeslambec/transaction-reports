package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.transactionreports.dto.AttachmentDto;
import uz.pdp.transactionreports.service.AttachmentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AttachmentDto> upload(@RequestParam("file") MultipartFile file) {
        try {
            AttachmentDto attachmentDto = attachmentService.upload(file);
            return new ResponseEntity<>(attachmentDto, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(attachmentService.getById(id));
    }
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) throws IOException {
        attachmentService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully!");
    }
}
