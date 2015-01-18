package bigbrother.bigbrotherapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EnterPinActivity extends ActionBarActivity {

    private Button enter_pin;
    private EditText pin_check;
    private int test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_pin);

        enter_pin = (Button) findViewById(R.id.submit_btn2);
        pin_check = (EditText) findViewById(R.id.pin_text_check);

        SharedPreferences prefs = getSharedPreferences("saved",MODE_PRIVATE);
        test = prefs.getInt("pin",0000);
        System.out.println(test);
        enter_pin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (Integer.parseInt(pin_check.getText().toString()) == test){
                    System.out.println("lol");
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_pin, menu);
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
