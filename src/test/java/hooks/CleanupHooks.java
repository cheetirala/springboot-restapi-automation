package hooks;

import clients.ObjectsClient;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;
import support.ScenarioState;

public class CleanupHooks {
    private final ObjectsClient client;
    private final ScenarioState state;

    @Autowired
    public CleanupHooks(ObjectsClient client, ScenarioState state) {
        this.client = client;
        this.state = state;
    }

    @After
    public void cleanupCreateObjects() {
        for (String id : state.getCreatedIds()) {
            try {
                client.deleteObject(id);
            } catch (Exception exception) {
                // ignore
            }
        }
    }
}
