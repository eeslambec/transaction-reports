package uz.pdp.transactionreports.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Page {
    private String object;
    private String id;
    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @JsonProperty("last_edited_time")
    private LocalDateTime lastEditedTime;
    private Boolean archived;
    private String url;
    private JsonNode properties;
}
