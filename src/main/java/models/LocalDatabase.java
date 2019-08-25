package models;

import java.util.HashMap;
import org.json.JSONObject;

public class LocalDatabase implements IDatabase {

    public final String DatabaseFilename;

    public LocalDatabase(String databaseFilename) {
        this.DatabaseFilename = databaseFilename;
        this.Start();
    }

    protected HashMap<String, HashMap<String, JSONObject>> Data = new HashMap<>();
    protected RandomString Generator = new RandomString();

    @Override
    public String CreateNewUser() {
        return this.Generator.nextString();
    }

    @Override
    public JSONObject GetData(String authorization, String key) {
        HashMap<String, JSONObject> appData = this.Data.get(authorization);
        if (appData == null) {
            return null;
        }

        return appData.get(key);
    }

    @Override
    public void SaveData(String authorization, String key, JSONObject data) {
        HashMap<String, JSONObject> appData = this.Data.get(authorization);
        if (appData == null) {
            appData = new HashMap<>();
            this.Data.put(authorization, appData);
        }

        if (data.isEmpty()) {
            appData.remove(key);
        } else {
            appData.put(key, data);
        }
    }

    @Override
    public void DeleteData(String authorization, String key) {
        HashMap<String, JSONObject> appData = this.Data.get(authorization);

        if (appData != null) {
            appData.remove(key);

            if (appData.isEmpty()) {
                this.Data.remove(authorization);
            }
        }
    }

    private void Start() {
        String content = File.ReadAllText(this.DatabaseFilename);
        if (content != null) {
            JSONObject serializedData = new JSONObject(content);
            for (String authorization : serializedData.keySet()) {
                JSONObject serializedEntry = serializedData.getJSONObject(authorization);
                for (String key : serializedEntry.keySet()) {
                    this.SaveData(authorization, key, serializedEntry.getJSONObject(key));
                }
            }
        }
    }

    @Override
    public void Stop() {
        JSONObject serializedData = new JSONObject(this.Data);
        File.WriteAllText(this.DatabaseFilename, serializedData.toString());
    }
}
