package support;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class JSonAssertions {
    private JSonAssertions(){}

    public static void assertStatus(Response response, int expected){
        assertNotNull(response, "Response was null");
        assertEquals(expected, response.statusCode(), "Unexpected status. Body=" + response.asString());
    }

    public static void assertJsonEquals(Response response, String jsonPath, Object expected){
        Object actual = response.jsonPath().get(jsonPath);
        assertEquals(expected, actual, "JsonPath mismatch at: " + jsonPath + ". Body" + response.asString());
    }

    public static void assertJsonNotBlank(Response response, String jsonPath){
        String value = response.jsonPath().getString(jsonPath);
        assertNotNull(value, "Expected non-null at: " + jsonPath);
        assertFalse(value.isBlank(), "Expected non-blank at: " + jsonPath);
    }
}
