package hooks;

import clients.ObjectsClient;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;
import support.TestContext;

public class CleanupHooks {
    private final ObjectsClient client;
    private final TestContext testContext;

    @Autowired
    public CleanupHooks(ObjectsClient client, TestContext state) {
        this.client = client;
        this.testContext = state;
    }

    @After
    public void cleanupCreateObjects() {
        for (String id : testContext.getCreatedIds()) {
            try {
                client.deleteObject(id);
            } catch (Exception exception) {
                // ignore
            }
        }
    }
}
