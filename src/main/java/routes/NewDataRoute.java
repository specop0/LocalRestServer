package routes;

import models.IDatabase;

public class NewDataRoute extends DataRouteBase {

    public NewDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, String authorization, String key, String data) {
        return database.CreateNewUser();
    }
}
