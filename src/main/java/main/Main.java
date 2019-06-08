package main;

import java.io.IOException;
import models.IDatabase;
import models.LocalDatabase;
import routes.Routes;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 6491;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                // default port
            }
        }

        IDatabase database = new LocalDatabase();
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
