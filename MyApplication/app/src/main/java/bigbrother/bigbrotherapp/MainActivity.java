package bigbrother.bigbrotherapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Relax extends AsyncTask<JSONObject, String, String> {
        protected String doInBackground(JSONObject... obj) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://bigbrother-backend.herokuapp.com");
            post.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            try {
                post.setEntity(new StringEntity(obj.toString()));
            } catch (UnsupportedEncodingException e) {
            }

            // send request
            try {
                HttpResponse response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == 200) {
                    return ":)";
                } else {
                    return ":(";
                }
            } catch (IOException e) {
            }

            return ":(";
        }

        protected void onPostExecute() {

        }
    }
}
