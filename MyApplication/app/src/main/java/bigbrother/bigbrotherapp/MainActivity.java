package bigbrother.bigbrotherapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText name;
    private EditText frequency;
    private EditText pin;
    private EditText pin_check;
    private Button submit_button;

    private Person person;

    private Pinger pinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create pinger
        pinger = new Pinger(1, 3453564);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button_test);
        submit_button = (Button) findViewById(R.id.btnLogin);
        pin = (EditText) findViewById(R.id.pin_text);
        pin_check = (EditText) findViewById(R.id.pin_check_text);
        name = (EditText) findViewById(R.id.name_text);
        frequency = (EditText) findViewById(R.id.frequeny_text);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               new Relax().execute(pinger.getPing());

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin.length() != 4){
                    pin.setError("Pin should be 4 numbers");
                    pin.requestFocus();
                    if (pin.getText() != pin_check.getText()){
                        pin_check.setError("Pin does not match");
                        pin.requestFocus();
                        pin_check.requestFocus();
                    }
                }else {
                    String[] flname = name.getText().toString().split(" ", 2);
                    person = new Person(flname[0], flname[1], 5, Integer.parseInt(pin.getText().toString()));

                    HttpPost post = new HttpPost();

                    System.out.println(person.toJSON().toString());
                    }
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
}
