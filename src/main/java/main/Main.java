package main;

import models.IDatabase;
import models.LocalDatabase;
import routes.DeleteDataRoute;
import routes.GetDataRoute;
import routes.NewDataRoute;
import routes.SetDataRoute;

public class Main {

    public static void main(String[] args) {
        IDatabase database = new LocalDatabase();
        int port = 6491;
        spark.Spark.port(port);
        spark.Spark.get("/data/*/*", "application/json", new GetDataRoute(database));
        spark.Spark.put("/data/*/*", "application/json", new SetDataRoute(database));
        spark.Spark.delete("/data/*/*", "application/json", new DeleteDataRoute(database));
        spark.Spark.post("/data/new", "application/json", new NewDataRoute(database));

        // stop database at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                database.Stop();
            }
        });

        System.out.println(String.format("Endpoint listening at: localhost:%d", port));
    }
}
