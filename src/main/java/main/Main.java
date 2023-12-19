package main;

import java.io.IOException;
import models.IDatabase;
import models.LocalDatabase;
import routes.Routes;

public class Main {

    public static void main(String[] args) throws IOException {
        String ipAddress = Configuration.GetString("ipAddress", "localhost");
        int port = Configuration.GetInt("port", 6491);
        String databaseFilename = Configuration.GetString("databaseFilename", "/mnt/localDatabase.json");

        IDatabase database = new LocalDatabase(databaseFilename);
        Routes.EstablishRoutes(database, ipAddress, port);

        // stop database at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                database.Save();
            }
        });

        System.out.println(String.format("Endpoint listening at: %s:%d", ipAddress, port));

        spark.Spark.awaitStop();
    }
}
