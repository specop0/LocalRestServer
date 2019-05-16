package routes;

import java.net.HttpURLConnection;
import models.RandomString;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class NewDataRouteTests extends RouteTestsBase {

    @Test
    public void TestNewAuthorization() {
        Response response = this.Do(this.GetUrl("new"), "POST");
        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());

        JSONObject actualData = response.GetBody();
        Assert.assertArrayEquals(new String[]{DataRouteBase.SINGLE_DATA_KEY}, JSONObject.getNames(actualData));
        String authorization = actualData.getString(DataRouteBase.SINGLE_DATA_KEY);
        Assert.assertEquals(64, authorization.length());

        RandomString authorizationGenerator = new RandomString();
        for (int i = 0; i < 1E6; i++) {
            String otherAuthorization = authorizationGenerator.nextString();
            Assert.assertEquals(authorization.length(), otherAuthorization.length());
            Assert.assertNotEquals(authorization, otherAuthorization);
        }
    }

}
