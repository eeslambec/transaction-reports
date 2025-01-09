package uz.pdp.transactionreports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Attachment;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentDto {
    private UUID id;
    private String originalName;
    private String type;
    private Long size;
    private String path;

    public AttachmentDto(Attachment attachment) {
        this.id = attachment.getId();
        this.originalName = attachment.getOriginalName();
        this.type = attachment.getType();
        this.size = attachment.getSize();
        this.path = attachment.getPath();
    }
}
