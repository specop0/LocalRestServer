package routes;

import java.net.HttpURLConnection;
import java.util.HashMap;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class SetDataRouteTests extends RouteTestsBase {

    @Test
    public void TestSetValidJsonData() {
        HashMap<String, Object> expectedData = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            expectedData.put(this.GetUniqueName(), this.GetUniqueName());
        }

        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        Assert.assertNull(this.Database.GetData(dataAuthorization, dataKey));

        String url = this.GetUrl(dataAuthorization, dataKey);
        JSONObject json = new JSONObject(expectedData);
        Response response = this.Do(url, "PUT", json);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());

        Assert.assertTrue(response.GetBody().isEmpty());
        JSONObject actualData = this.Database.GetData(dataAuthorization, dataKey);
        Assert.assertArrayEquals(expectedData.keySet().toArray(), JSONObject.getNames(actualData));
        for (String key : expectedData.keySet()) {
            Assert.assertEquals(expectedData.get(key), actualData.get(key));
        }
    }

    @Test
    public void TestSetInvalidJsonData() {
        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        Assert.assertNull(this.Database.GetData(dataAuthorization, dataKey));

        String url = this.GetUrl(dataAuthorization, dataKey);
        Response response = this.Do(url, "PUT", this.GetUniqueName());

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, response.GetStatus());
        Assert.assertNull(response.GetBodyRaw());
        Assert.assertNull(this.Database.GetData(dataAuthorization, dataKey));
    }

    @Test
    public void TestSetNullJsonData() {
        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        this.Database.SaveData(dataAuthorization, dataKey, new JSONObject("{0:1}"));
        Assert.assertNotNull(this.Database.GetData(dataAuthorization, dataKey));
        Assert.assertFalse(this.Database.GetData(dataAuthorization, dataKey).isEmpty());

        String url = this.GetUrl(dataAuthorization, dataKey);
        Response response = this.Do(url, "PUT", null);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());
        Assert.assertTrue(response.GetBody().isEmpty());
        Assert.assertNull(this.Database.GetData(dataAuthorization, dataKey));
    }
}
