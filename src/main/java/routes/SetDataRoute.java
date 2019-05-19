package routes;

import models.IDatabase;
import org.json.JSONObject;

public class SetDataRoute extends DataRouteWithAuthBase {

    public SetDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected JSONObject Handle(IDatabase database, String authorization, String key, JSONObject data) {
        database.SaveData(authorization, key, data);
        return null;
    }

}
