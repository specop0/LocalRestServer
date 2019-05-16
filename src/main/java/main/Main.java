package main;

import models.IDatabase;
import models.LocalDatabase;
import routes.Routes;

public class Main {

    public static void main(String[] args) {
        IDatabase database = new LocalDatabase();
        int port = 6491;
        Routes.EstablishRoutes(database, port);

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
