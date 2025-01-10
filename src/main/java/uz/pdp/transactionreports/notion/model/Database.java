package uz.pdp.transactionreports.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Database {
    private String object;
    @JsonProperty("results")
    private List<Page> pages = new ArrayList<>();
    @JsonProperty("next_cursor")
    private Boolean nextCursor;
    @JsonProperty("has_more")
    private Boolean hasMore;
}
