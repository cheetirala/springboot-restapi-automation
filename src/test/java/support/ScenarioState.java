package support;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScenarioState {
    @Setter
    private Response lastResponse;
    private String lastCreatedId;
    private final List<String> createdIds = new ArrayList<>();

    public void setLastCreatedId(String lastCreatedId) {
        this.lastCreatedId = lastCreatedId;
        if (lastCreatedId != null && !lastCreatedId.isBlank()) {
            this.createdIds.add(lastCreatedId);
        }
    }
}
