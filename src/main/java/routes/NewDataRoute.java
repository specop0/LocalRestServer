package routes;

import models.IDatabase;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class NewDataRoute extends DataRouteBase {

    public NewDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected JSONObject Handle(IDatabase database, Request request, Response response) {
        return this.MakeSimpleJSON(database.CreateNewUser());
    }
}
