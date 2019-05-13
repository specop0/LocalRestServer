package routes;

import models.IDatabase;

public class SetDataRoute extends DataRouteBase {

    public SetDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, String authorization, String key, String data) {
        database.SaveData(authorization, key, data);
        return null;
    }

}
