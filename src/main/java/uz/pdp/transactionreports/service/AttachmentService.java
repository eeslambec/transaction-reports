package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.transactionreports.dto.AttachmentDto;

import java.io.IOException;
import java.util.UUID;

@Service
public interface AttachmentService {
    AttachmentDto upload(MultipartFile request) throws IOException;
    AttachmentDto getById(UUID id);
    void deleteFile(UUID id) throws IOException;
}
