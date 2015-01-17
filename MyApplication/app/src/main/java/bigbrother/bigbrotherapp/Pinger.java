package bigbrother.bigbrotherapp;


import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;

public class Pinger {
    private int id;
    private String timestamp;
    private int token;
    private int status;

    Pinger(int id, int token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return this.id;
    }

    public int getStatus() {
        return this.status;
    }

    public HttpPost getPing() {
        try {
            // create JSON object
            JSONObject obj = new JSONObject();
            Date d = new Date();
            obj.put("uid", id);
            obj.put("timestamp", new Timestamp(d.getTime()).toString());
            obj.put("token", token);
            obj.put("status", 0);

            // create HTTP POST object
            HttpPost post = new HttpPost("https://bigbrother-backend.herokuapp.com");
            post.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            post.setEntity(new StringEntity(obj.toString()));

            return post;
        } catch (JSONException e) {
            System.err.println("JSONException in Pinger");
            return null;
        } catch (UnsupportedEncodingException e) {
            System.err.println("UnsupportedEncodingException in Pinger");
            return null;
        }
    }
}
