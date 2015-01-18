package bigbrother.bigbrotherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EnterPinActivity extends ActionBarActivity {

    private Button enter_pin;
    private TextView message;
    private EditText pin_check;
    private int savedPin;
    private int activeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        enter_pin = (Button) findViewById(R.id.submit_btn2);
        message = (TextView) findViewById(R.id.enter_pin_msg);
        pin_check = (EditText) findViewById(R.id.pin_text_check);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);

        SharedPreferences prefs = getSharedPreferences("saved",MODE_PRIVATE);

        savedPin = prefs.getInt("pin", 0000);
        activeTime = prefs.getInt("frequency", 60);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("result", Pinger.STATUS_DANGER);
                setResult(MapActivity.PIN_RESULT_ID, intent);
                finish();
            }
        }, activeTime * 1000);

        enter_pin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (Integer.parseInt(pin_check.getText().toString()) == savedPin){
                    Intent in = getIntent();
                    Intent intent = new Intent();

                    if(in.getBooleanExtra("confirm_arrival", false)) {
                        intent.putExtra("result", Pinger.STATUS_ARRIVED);
                        setResult(MapActivity.CONFIRM_RESULT_ID, intent);
                    } else {
                        intent.putExtra("result", Pinger.STATUS_OK);
                        setResult(MapActivity.PIN_RESULT_ID, intent);
                    }
                    finish();
                } else {
                    message.setText("Incorrect Pin, please try again");
                    message.setTextColor(Color.RED);
                    pin_check.setText("");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    public void closeActivityWithError() {

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
