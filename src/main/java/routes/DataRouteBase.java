package routes;

import java.util.HashMap;
import java.util.Map;
import models.IDatabase;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class DataRouteBase implements Route {

    protected DataRouteBase(IDatabase database) {
        this.Database = database;
        this.authorizationRequired = true;
        this.keyRequired = true;
    }

    private final IDatabase Database;

    private boolean authorizationRequired;
    private boolean keyRequired;

    @Override
    public Object handle(Request rqst, Response rspns) throws Exception {
        String authorization = rqst.headers("Authorization");
        String key = rqst.headers("Key");
        String data = rqst.headers("Data");

        Map<String, String> jsonResult = new HashMap<>();
        if (this.IsKeyRequired() && (key == null || key.isEmpty())) {
            jsonResult.put("Exception", "Key required.");
        }
        if (this.IsAuthorizationRequired() && (authorization == null || authorization.isEmpty())) {
            jsonResult.put("Exception", "Authorization required.");
        }

        if (jsonResult.isEmpty()) {
            try {
                String result = this.Handle(this.Database, authorization, key, data);
                if (result == null) {
                    result = "";
                }
                jsonResult.put("Data", result);
            } catch (Exception ex) {
                jsonResult.put("Exception", ex.getMessage());
            }
        }

        rspns.type("application/json");
        return new JSONObject(jsonResult);
    }

    protected abstract String Handle(IDatabase database, String authorization, String key, String data);

    protected boolean IsAuthorizationRequired() {
        return this.authorizationRequired;
    }

    protected final void SetAuthorizationRequired(boolean authorizationRequired) {
        this.authorizationRequired = authorizationRequired;
    }

    protected boolean IsKeyRequired() {
        return this.keyRequired;
    }

    protected final void SetKeyRequired(boolean keyRequired) {
        this.keyRequired = keyRequired;
    }
}
