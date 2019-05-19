package models;

import org.json.JSONObject;

public interface IDatabase {

    String CreateNewUser();

    JSONObject GetData(String authorization, String key);

    void SaveData(String authorization, String key, JSONObject data);

    void DeleteData(String authorization, String key);

    void Stop();
}
