package routes;

import models.IDatabase;
import org.json.JSONObject;

public class GetDataRoute extends DataRouteWithAuthBase {

    public GetDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected JSONObject Handle(IDatabase database, String authorization, String key, JSONObject data) {
        return database.GetData(authorization, key);
    }
}
