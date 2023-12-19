package main;

// C# like appsettings.json configuration approach
public class Configuration {

    public static String GetString(String path, String defaultValue) {
        path = "APPSETTINGS_" + path;
        path = path.replace(":", "__");
        path = path.toUpperCase();

        String value = System.getenv(path);
        if (null != value) {
            return value;
        }
        return defaultValue;
    }

    public static int GetInt(String path, int defaultValue) {
        String integerAsString = GetString(path, null);
        if (null == integerAsString) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(integerAsString);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

}
