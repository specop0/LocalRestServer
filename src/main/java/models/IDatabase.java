package models;

public interface IDatabase {
    
    String CreateNewUser();
    
    String GetData(String authorization, String key);
    
    void SaveData(String authorization, String key, String data);
    
    void DeleteData(String authorization, String key);
    
    void Stop();
}
