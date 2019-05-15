package routes;

import models.IDatabase;

public class DeleteDataRoute extends DataRouteWithAuthBase {

    public DeleteDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, String authorization, String key, String data) {
        database.DeleteData(authorization, key);
        return null;
    }
}
