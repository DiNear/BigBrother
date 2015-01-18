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
import java.util.Random;

public class Pinger {

    public static int STATUS_OK = 0;
    public static int STATUS_DANGER = 1;
    public static int STATUS_WARNING = 2;

    private static Pinger instance;
    private Person person;
    private String timestamp;
    double longitude;
    double latitude;
    private int token;
    private int status;

    private Pinger() {
        this.status = Pinger.STATUS_OK;

        // generate token
        Random r = new Random();
        this.token = r.nextInt();
    }

    public void setPerson(Person person) { this.person = person; }

    public static synchronized Pinger getInstance(){
        if(instance == null){
            instance = new Pinger();
        }
        return instance;
    }

    public int getId() {
        return this.person.getId();
    }

    public int getStatus() {
        return this.status;
    }

    public HttpPost getPing() {
        try {
            // create JSON object
            JSONObject obj = new JSONObject();
            Date d = new Date();
            obj.put("uid", this.person.getId());
            obj.put("timestamp", new Timestamp(d.getTime()).toString());
            obj.put("token", this.person.getToken());
            obj.put("longitude", this.longitude);
            obj.put("latitude", this.latitude);
            obj.put("status", this.status);

            // create HTTP POST object
            HttpPost post = new HttpPost("https://bigbrother-backend.herokuapp.com:443/api/pings");
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

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLong(double longitude) {
        this.longitude = longitude;
    }

    public void setLat(double latitude) {
        this.latitude = latitude;
    }
}
