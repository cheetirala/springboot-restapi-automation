package hooks;

import clients.ItemsClient;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;
import support.TestContext;

public class CleanupHooks {
    private final ItemsClient client;
    private final TestContext testContext;

    @Autowired
    public CleanupHooks(ItemsClient client, TestContext state) {
        this.client = client;
        this.testContext = state;
    }

    @After
    public void cleanupCreateObjects() {
        for (String id : testContext.getCreatedIds()) {
            try {
                client.deleteItem(id);
            } catch (Exception exception) {
                // ignore
            }
        }
    }
}
