package app.blood.bloodapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    String serverName = "192.168.2.11";
    int port = 10121;
    EditText id;
    EditText dateOfBirth;
    EditText phone;
    Button send;
    EditText bloodGroup;
    EditText genderId;
    int year_x,month_x,day_x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.user_id);
        dateOfBirth = (EditText) findViewById(R.id.date_Of_Birth);
        phone = (EditText) findViewById(R.id.user_phone);
        bloodGroup=(EditText) findViewById(R.id.blood_group);
        genderId=(EditText) findViewById(R.id.gender);
        send = (Button) findViewById(R.id.send);
        final Calendar currentDate= Calendar.getInstance();
        year_x=currentDate.get(Calendar.YEAR);
        month_x=currentDate.get(Calendar.MONTH);
        day_x=currentDate.get(Calendar.DAY_OF_MONTH);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = id.getText().toString();
                String dateOfbirth1 = dateOfBirth.getText().toString();
                String user_phone = phone.getText().toString();
                sendToServer(user_id, dateOfbirth1,user_phone);


            }
        });
        dateOfBirth.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        showDialog(0);
                    }
                }
        );
        bloodGroup.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        normalLogin obj= new normalLogin();
                        //this code need to be moved to normal login
                        // obj.selectBloodGroup(MainActivity.this);
                        final CharSequence bloodGroupArr[] = new CharSequence[] {"A+", "B+", "A-", "B-"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Pick your blood group");
                        builder.setItems(bloodGroupArr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bloodGroup.setText(bloodGroupArr[which]);
                                // the user clicked on colors[which]


                            }
                        });
                        builder.show();
                        //bloodGroup.setText(obj.selectBloodGroup(MainActivity.this));
                    }
                }
        );

        genderId.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        normalLogin obj= new normalLogin();
                        //this code need to be moved to normal login
                        // obj.selectBloodGroup(MainActivity.this);
                        final CharSequence genderArr[] = new CharSequence[] {"Male", "Female", "Others"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Pick your blood group");
                        builder.setItems(genderArr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                genderId.setText(genderArr[which]);
                                // the user clicked on colors[which]


                            }
                        });
                        builder.show();
                        //bloodGroup.setText(obj.selectBloodGroup(MainActivity.this));
                    }
                }
        );

    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==0) {
            return new DatePickerDialog(this, listener, year_x, month_x, day_x);
        }
        else
            return null;
    }



    private void sendToServer(final String user_id, final String user_name, final String user_phone) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try
                {
                    System.out.println("Connecting to " + serverName +
                            " on port " + port);
                    Socket client = new Socket(serverName, port);
                    System.out.println("Just connected to "
                            + client.getRemoteSocketAddress());
                    OutputStream outToServer = client.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF(user_id);
                    out.writeUTF(user_name);
                    out.writeUTF(user_phone);
                    out.writeUTF("Hello from "
                            + client.getLocalSocketAddress());
                    InputStream inFromServer = client.getInputStream();
                    DataInputStream in =
                            new DataInputStream(inFromServer);
                    System.out.println("Server says " + in.readUTF());
                    client.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        ).start();
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

    protected Dialog createDatePicker() {
        System.out.println("in side date picker");
        return new DatePickerDialog(this,listener,year_x,month_x,day_x);

    }
    private DatePickerDialog.OnDateSetListener listener
            =new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view,int year,int monthOfyear,int dayOfmonth){

            year_x=year;
            month_x=monthOfyear;
            day_x=dayOfmonth;
            GregorianCalendar  dob=new GregorianCalendar(year_x,month_x,day_x);
            android.text.format.DateFormat.format("dd-MMM-yyyy", dob);
            dateOfBirth.setText(android.text.format.DateFormat.format("dd-MMM-yyyy", dob));

        }

    };



}
