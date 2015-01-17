package bigbrother.bigbrotherapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

    private Toolbar toolbar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button_test);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               new Relax().execute();

            }
        });
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
            HttpPost post = new HttpPost("https://bigbrother-backend.herokuapp.com:443/api/people");
            post.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            Person p = new Person("Tony", "Hwuang", 4, 1996);

            try {
                post.setEntity(new StringEntity(p.toJSON().toString()));
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
