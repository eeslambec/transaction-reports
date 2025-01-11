package uz.pdp.transactionreports.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.transactionreports.dto.AttachmentDto;
import uz.pdp.transactionreports.entity.Attachment;

@Component
public class AttachmentMapper {
    public Attachment toEntity(AttachmentDto dto){
        return Attachment.builder()
                .id(dto.getId())
                .attachmentStatus(dto.getAttachmentStatus())
                .size(dto.getSize())
                .type(dto.getType())
                .originalName(dto.getOriginalName())
                .build();
    }
}
