package routes;

import models.IDatabase;

public class Routes {

    public static void EstablishRoutes(IDatabase database, int port) {
        spark.Spark.port(port);
        spark.Spark.get("/data/*/*", "application/json", new GetDataRoute(database));
        spark.Spark.put("/data/*/*", "application/json", new SetDataRoute(database));
        spark.Spark.delete("/data/*/*", "application/json", new DeleteDataRoute(database));
        spark.Spark.post("/data/new", "application/json", new NewDataRoute(database));
    }
}
