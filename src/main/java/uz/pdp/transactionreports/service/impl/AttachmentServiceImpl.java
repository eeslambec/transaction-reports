package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.transactionreports.dto.AttachmentDto;
import uz.pdp.transactionreports.entity.Attachment;
import uz.pdp.transactionreports.exception.InvalidArgumentException;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.repository.AttachmentRepository;
import uz.pdp.transactionreports.service.AttachmentService;
import uz.pdp.transactionreports.utils.enums.AttachmentStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    Path BASE_PATH = Path.of(System.getProperty("user.home")+ File.separator + "transaction-reports"+ File.separator + "files" + File.separator);

    @Override
    public AttachmentDto upload(MultipartFile request) throws IOException {
        String originalFileName = request.getOriginalFilename();

        String fileName = UUID.randomUUID() + getExtension(request.getContentType());
        Path path = BASE_PATH.resolve(fileName);

        Attachment attachment = Attachment.builder()
                .originalName(originalFileName)
                .type(request.getContentType())
                .size(request.getSize())
                .path(path.toString())
                .attachmentStatus(AttachmentStatus.UPLOADED)
                .build();

        Files.createDirectories(BASE_PATH);
        attachmentRepository.save(attachment);
        try (var inputStream = request.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return new AttachmentDto(attachment);
    }


    @Override
    public AttachmentDto getById(UUID id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attachment"));
        return new AttachmentDto(attachment);
    }

    @Override
    public void deleteFile(UUID id) throws IOException {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attachment"));
        Path path = BASE_PATH.resolve(attachment.getOriginalName());

        Files.deleteIfExists(path);

        attachment.setAttachmentStatus(AttachmentStatus.DELETED);
        attachmentRepository.save(attachment);
    }

    private String getExtension(String contentType) {
        if (contentType != null) {
            return "." + contentType.substring(contentType.indexOf("/") + 1);
        }
        throw new InvalidArgumentException("Invalid file type");
    }
}
