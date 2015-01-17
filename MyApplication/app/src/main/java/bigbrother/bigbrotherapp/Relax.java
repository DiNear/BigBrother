package bigbrother.bigbrotherapp;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Relax extends AsyncTask<HttpPost, String, String> {
    protected String doInBackground(HttpPost... obj) {
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();

        // send request
        try {
            HttpResponse response = client.execute(obj[0]);

            // get response body
            String resp = handler.handleResponse(response);

            if (response.getStatusLine().getStatusCode() == 200) {
                return resp;
            } else {
                return ":(";
            }
        } catch (IOException e) {
        }

        return ":(";
    }

    protected void onPostExecute(String result) {
        System.out.println(result);
    }
}
