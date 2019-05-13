package routes;

import models.IDatabase;

public class DeleteDataRoute extends DataRouteBase {

    public DeleteDataRoute(IDatabase database) {
        super(database);
        this.SetAuthorizationRequired(false);
        this.SetKeyRequired(false);
    }

    @Override
    protected String Handle(IDatabase database, String authorization, String key, String data) {
        database.DeleteData(authorization, key);
        return null;
    }
}
