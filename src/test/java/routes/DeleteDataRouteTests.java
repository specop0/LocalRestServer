package routes;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DeleteDataRouteTests extends RouteTestsBase {

    public DeleteDataRouteTests(boolean isKeyPresent) {
        this.IsKeyPresent = isKeyPresent;
    }

    protected boolean IsKeyPresent;

    @Parameterized.Parameters
    public static Collection DeleteCases() {
        Object[] flags = new Object[]{true, false};
        return Arrays.asList(flags);
    }

    @Test
    public void TestDelete() {
        String authorization = this.GetUniqueName();
        String key = this.GetUniqueName();
        if (IsKeyPresent) {
            String data = this.GetUniqueName();
            this.Database.SaveData(authorization, key, data);
            Assert.assertEquals(data, this.Database.GetData(authorization, key));
        } else {
            Assert.assertNull(this.Database.GetData(authorization, key));
        }

        String url = this.GetUrl(authorization, key);
        Response response = this.Do(url, "DELETE");

        Assert.assertEquals(HttpURLConnection.HTTP_OK, response.GetStatus());
        Assert.assertTrue(response.GetBody().isEmpty());
        Assert.assertNull(this.Database.GetData(authorization, key));
    }
}
