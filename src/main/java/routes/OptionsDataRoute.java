package routes;

import models.IDatabase;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class OptionsDataRoute extends DataRouteBase {

    public OptionsDataRoute(IDatabase database) {
        super(database);
    }

    @Override
    protected JSONObject Handle(IDatabase database, Request request, Response response) {
        String[] urlWildcards = request.splat();
        if (urlWildcards.length != 1) {
            return null;
        }

        String option = urlWildcards[0].toLowerCase();
        switch (option) {
            case "save": {
                this.Database.Save();
                break;
            }
        }

        return null;
    }

}
