package routes;

import java.net.HttpURLConnection;
import java.util.HashMap;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class GetDataRouteTests extends RouteTestsBase {

    @Test
    public void TestGetEmptyData() {
        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        Assert.assertNull(this.Database.GetData(dataAuthorization, dataKey));

        String url = this.GetUrl(dataAuthorization, dataKey);
        Response response = this.Do(url, "GET");

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());
        Assert.assertTrue(response.GetBody().isEmpty());
    }

    @Test
    public void TestGetValidJsonData() {
        HashMap<String, Object> expectedData = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            expectedData.put(this.GetUniqueName(), this.GetUniqueName());
        }

        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        this.Database.SaveData(dataAuthorization, dataKey, new JSONObject(expectedData));

        String url = this.GetUrl(dataAuthorization, dataKey);
        Response response = this.Do(url, "GET");

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());

        JSONObject actualData = response.GetBody();
        Assert.assertArrayEquals(expectedData.keySet().toArray(), JSONObject.getNames(actualData));
        for (String key : expectedData.keySet()) {
            Assert.assertEquals(expectedData.get(key), actualData.get(key));
        }
    }
}
