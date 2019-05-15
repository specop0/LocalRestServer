package routes;

import models.IDatabase;

public class GetDataRoute extends DataRouteWithAuthBase {

    public GetDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, String authorization, String key, String data) {
        return database.GetData(authorization, key);
    }
}
