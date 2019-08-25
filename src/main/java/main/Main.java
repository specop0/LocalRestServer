package main;

import java.io.IOException;
import models.IDatabase;
import models.LocalDatabase;
import routes.Routes;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 6491;
        String databaseFilename = "localDatabase.json";
        if (args.length > 0) {
            databaseFilename = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                // default port
            }
        }

        IDatabase database = new LocalDatabase(databaseFilename);
        Routes.EstablishRoutes(database, port);

        // stop database at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                database.Stop();
            }
        });

        System.out.println(String.format("Endpoint listening at: localhost:%d", port));

        System.in.read();
        spark.Spark.stop();
        spark.Spark.awaitStop();
    }
}
