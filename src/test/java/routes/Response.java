package routes;

import org.json.JSONObject;

public class Response {

    public Response(int status, String body) {
        this.status = status;
        this.body = body;
    }

    private final String body;
    private final int status;

    public int GetStatus() {
        return this.status;
    }

    public JSONObject GetBody() {
        return new JSONObject(this.body);
    }

    public String GetBodyRaw() {
        return this.body;
    }
}
