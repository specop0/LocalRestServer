package routes;

import java.net.HttpURLConnection;
import java.util.HashMap;
import org.json.JSONException;
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
    public void TestGetInvalidJsonData() {
        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        String data = this.GetUniqueName();
        this.Database.SaveData(dataAuthorization, dataKey, data);

        try {
            JSONObject jsonObject = new JSONObject(this.Database.GetData(dataAuthorization, dataKey));
            Assert.fail("The following should not be valid JSON: " + jsonObject);
        } catch (JSONException ex) {
            Assert.assertNotNull(ex);
        }

        String url = this.GetUrl(dataAuthorization, dataKey);
        Response response = this.Do(url, "GET");

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());
        JSONObject actualData = response.GetBody();
        Assert.assertEquals(data, actualData.get(DataRouteBase.SINGLE_DATA_KEY));
        Assert.assertArrayEquals(new String[]{DataRouteBase.SINGLE_DATA_KEY}, JSONObject.getNames(actualData));
    }

    @Test
    public void TestGetValidJsonData() {
        HashMap<String, Object> expectedData = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            expectedData.put(this.GetUniqueName(), this.GetUniqueName());
        }

        String dataAuthorization = this.GetUniqueName();
        String dataKey = this.GetUniqueName();
        this.Database.SaveData(dataAuthorization, dataKey, new JSONObject(expectedData).toString());

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
