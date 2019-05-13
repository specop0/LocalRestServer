package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        spark.Spark.post("/get", "application/json", new GetDataRoute(database));
        spark.Spark.post("/set", "application/json", new SetDataRoute(database));
        spark.Spark.post("/delete", "application/json", new DeleteDataRoute(database));
        spark.Spark.post("/new", "application/json", new NewDataRoute(database));
        // TODO Path Groups
        // localhost:6491/{GUID}/{Key}/get
        // http://sparkjava.com/documentation#routes

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
