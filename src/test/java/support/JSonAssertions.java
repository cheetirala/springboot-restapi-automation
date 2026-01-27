package support;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class JSonAssertions {
    private JSonAssertions(){}

    public static void assertStatus(Response response, int expected){
        assertNotNull(response, "Response was null");
        assertEquals(expected, response.statusCode(), "Unexpected status. Body=" + response.asString());
    }

    public static void assertJsonEquals(Response response, String key, Object expected){
        Object actual = response.jsonPath().get(key);
        assertEquals(expected, actual, "JsonPath mismatch at: " + key + ". Body" + response.asString());
    }

    public static void assertJsonNotBlank(Response response, String key){
        String value = response.jsonPath().getString(key);
        assertNotNull(value, "Expected non-null at: " + key);
        assertFalse(value.isBlank(), "Expected non-blank at: " + key);
    }
}
