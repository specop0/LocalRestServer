package routes;

import models.IDatabase;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public abstract class DataRouteWithAuthBase extends DataRouteBase {

    public DataRouteWithAuthBase(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, Request request, Response response) {
        String[] urlWildcards = request.splat();
        if (urlWildcards.length != 2) {
            return null;
        }

        // input must be JSON
        String data = request.body();
        if (data != null && !"".equals(data)) {
            data = new JSONObject(data).toString();
        }
        return this.Handle(database, urlWildcards[0], urlWildcards[1], data);
    }

    protected abstract String Handle(IDatabase database, String authorization, String key, String data);
}
