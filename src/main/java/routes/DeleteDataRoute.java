package routes;

import models.IDatabase;
import org.json.JSONObject;

public class DeleteDataRoute extends DataRouteWithAuthBase {

    public DeleteDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected JSONObject Handle(IDatabase database, String authorization, String key, JSONObject data) {
        database.DeleteData(authorization, key);
        return null;
    }
}
