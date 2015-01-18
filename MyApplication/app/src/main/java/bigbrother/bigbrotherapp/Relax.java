package bigbrother.bigbrotherapp;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Relax extends AsyncTask<HttpPost, String, JSONObject> {

    // manage callback
    Object caller;

    Relax() {
        this.caller = null;
    }

    Relax(Object caller) {
        this.caller = caller;
    }

    protected JSONObject doInBackground(HttpPost... obj) {
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();

        // send request
        try {
            HttpResponse response = client.execute(obj[0]);

            // get response body
            String resp = handler.handleResponse(response);

            if (response.getStatusLine().getStatusCode() == 200) {
                return new JSONObject(resp);
            } else {
                return null;
            }
        } catch (IOException e) {
        } catch (JSONException e) {
        }

        return null;
    }

    protected void onPostExecute(String result) {
        System.out.println(result);

        try {
            JSONObject jobj = new JSONObject(result);

            if (this.caller != null) {
                ((Person) caller).setId(jobj.getInt("id"));
                System.out.println(((Person) caller).getId());
            }
        } catch (JSONException e) {
        }

    }
}
