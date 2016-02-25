package app.blood.bloodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.blood.bloodapp.Utility.Util;

/**
 * Created by Adil on 09-Feb-16.
 */
public class bloodSplash extends Activity {
    String ip="fds";
    String port="fds";
    EditText IP;
    EditText PORT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

       setContentView(R.layout.splash);
        Button btn = (Button)findViewById(R.id.ckeck);

        IP = (EditText) findViewById(R.id.SplashIp);
         PORT= (EditText) findViewById(R.id.SplashPort);

       // new Util().setIpPort(ip.getText().toString(),port.getText().toString());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip=IP.getText().toString();
                if(ip=="" || ip.equalsIgnoreCase("") || ip==null)
                    ip="192.68.2.7";
                port=PORT.getText().toString();
                if(port=="" || port.equalsIgnoreCase("") || port==null)
                    port="10131";
                Log.d("port is " , port);
                Log.d("port is " , ip);
                if (new Util().checkConnection(getApplicationContext())) {
                    Toast.makeText(bloodSplash.this, "Connected", Toast.LENGTH_LONG).show();
                    proceedToLogin();

                } else { //network not connected
                    Toast.makeText(bloodSplash.this, " Not Connected", Toast.LENGTH_LONG).show();
                    proceedAsGuest();
                }

            }
        });


        Log.d("Tag","TAg11");
    }




    private void proceedAsGuest() {
        Intent homeScreen = new Intent(bloodSplash.this, GuestActivity.class);
        homeScreen.putExtra("ip",ip);
        homeScreen.putExtra("port",port);
        startActivity(homeScreen);
    }

    private void proceedToLogin() {
        Intent mainScreen = new Intent(bloodSplash.this, MainActivity.class);
        mainScreen.putExtra("ip",ip);
        mainScreen.putExtra("port",port);
        startActivity(mainScreen);
    }
}
