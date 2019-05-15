package routes;

import java.util.HashMap;
import java.util.Map;
import models.IDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class DataRouteBase implements Route {

    protected DataRouteBase(IDatabase database) {
        this.Database = database;
    }

    private final IDatabase Database;

    @Override
    public Object handle(Request rqst, Response rspns) throws Exception {
        JSONObject jsonObject = new JSONObject();

        String result = this.Handle(this.Database, rqst, rspns);
        if (result != null) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException ex) {
                jsonObject.put("$data", result);
            }
        }

        rspns.type("application/json");
        return jsonObject;
    }

    protected abstract String Handle(IDatabase database, Request request, Response response);
}
