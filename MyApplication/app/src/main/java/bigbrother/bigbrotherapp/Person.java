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

public class Person {
    private String firstname;
    private String lastname;
    private String last_check;
    private String token;
    private int check_freq;
    private int pin;
    private int id;

    Person(String firstname, String lastname, int check_freq, int pin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.last_check = "";
        this.token = "";
        this.check_freq = check_freq;
        this.pin = pin;
        this.id = 0;
    }

    Person(JSONObject obj) {
        try {
            firstname = obj.getString("firstname");
            lastname = obj.getString("lastname");
            last_check = obj.getString("last_check");
            token = obj.getString("token");
            check_freq = obj.getInt("check_freq");
            pin = obj.getInt("pin");
            id = obj.getInt("id");
        } catch (JSONException e) {
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLast_check() {
        return last_check;
    }

    public String token() {
        return token;
    }

    public int getCheck_freq() {
        return check_freq;
    }

    public int getPin() {
        return pin;
    }

    public int getId() {
        return id;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("firstname", firstname);
            obj.put("lastname", lastname);
            obj.put("last_check", last_check);
            obj.put("token", token);
            obj.put("check_freq", check_freq);
            obj.put("pin", pin);
            obj.put("id", id);
        } catch (JSONException e) {
        }

        return obj;
    }

    public void sendToServer() {
        try {
            // create JSON object
            JSONObject obj = this.toJSON();

            // create HTTP POST object
            HttpPost post = new HttpPost("https://bigbrother-backend.herokuapp.com:443/api/people");
            post.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            post.setEntity(new StringEntity(obj.toString()));

            new Relax(this).execute(post);
        } catch (UnsupportedEncodingException e) {
            System.err.println("UnsupportedEncodingException in Person.sendToServer");
        }
    }

    public void setId(int id) {
        // from callback
        this.id = id;
    }
}
