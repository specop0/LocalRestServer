package routes;

import models.IDatabase;
import spark.Request;
import spark.Response;

public class NewDataRoute extends DataRouteBase {

    public NewDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected String Handle(IDatabase database, Request request, Response response) {
        return database.CreateNewUser();
    }
}
