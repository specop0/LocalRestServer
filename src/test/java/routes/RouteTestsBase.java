package routes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import models.IDatabase;
import models.LocalDatabase;
import models.RandomString;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import spark.Spark;
import spark.utils.IOUtils;

public abstract class RouteTestsBase {

    protected IDatabase Database;
    protected int Port;

    @Before
    public void SetUp() throws Exception {
        this.Database = new LocalDatabase();
        this.Port = 12345;
        Routes.EstablishRoutes(this.Database, this.Port);
        Spark.awaitInitialization();
    }

    @After
    public void TearDown() throws Exception {
        Spark.stop();
        Spark.awaitStop();
    }

    protected String GetUrl(String operation) {
        return String.format("http://localhost:%d/data/%s", this.Port, operation);
    }

    protected String GetUrl(String authorization, String key) {
        return String.format("http://localhost:%d/data/%s/%s", this.Port, authorization, key);
    }

    protected Response Do(String url, String httpMethod) {
        return this.Do(url, httpMethod, null);
    }

    protected Response Do(String url, String httpMethod, Object input) {
        try {
            URL actualUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) actualUrl.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setDoOutput(true);
            if (input != null) {
                connection.getOutputStream().write(input.toString().getBytes());
            }

            connection.connect();

            String body;
            try {
                body = IOUtils.toString(connection.getInputStream());
            } catch (IOException e) {
                body = null;
            }
            Response response = new Response(
                    connection.getResponseCode(),
                    body);
            return response;
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
            return null;
        }
    }

    protected String GetUniqueName() {
        return new RandomString(16).nextString();
    }
}
