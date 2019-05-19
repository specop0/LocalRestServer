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
    protected JSONObject Handle(IDatabase database, Request request, Response response) {
        String[] urlWildcards = request.splat();
        if (urlWildcards.length != 2) {
            return null;
        }

        // input must be JSON
        JSONObject data = new JSONObject();
        String dataRaw = request.body();
        if (dataRaw != null && !"".equals(dataRaw)) {
            data = new JSONObject(dataRaw);
        }
        return this.Handle(database, urlWildcards[0], urlWildcards[1], data);
    }

    protected abstract JSONObject Handle(IDatabase database, String authorization, String key, JSONObject data);
}
