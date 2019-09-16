package routes;

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

    public static final String SINGLE_DATA_KEY = "$data";

    protected final IDatabase Database;

    @Override
    public Object handle(Request rqst, Response rspns) throws Exception {
        rspns.type("application/json");
        JSONObject result = this.Handle(this.Database, rqst, rspns);
        if (result != null) {
            return result;
        }
        return this.MakeEmptyJSON();
    }

    protected abstract JSONObject Handle(IDatabase database, Request request, Response response);

    protected JSONObject MakeSimpleJSON(String data) {
        JSONObject jsonData = new JSONObject();
        jsonData.put(SINGLE_DATA_KEY, data);
        return jsonData;
    }

    protected JSONObject MakeEmptyJSON() {
        return new JSONObject();
    }
}
