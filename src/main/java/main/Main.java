package main;

import java.io.IOException;
import models.IDatabase;
import models.LocalDatabase;
import routes.Routes;

public class Main {

    public static void main(String[] args) throws IOException {
        String ipAddress = "localrestserver";
        int port = 6491;
        String databaseFilename = "/mnt/localDatabase.json";
        if (args.length > 0) {
            ipAddress = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                // default port
            }
        }
        if (args.length > 2) {
            databaseFilename = args[2];
        }

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
