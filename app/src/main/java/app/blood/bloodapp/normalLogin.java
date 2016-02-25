package app.blood.bloodapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by babbi on 2/23/2016.
 */
public class normalLogin {
    String bloodGroup=null;
    public String selectBloodGroup(final Context context){

        final CharSequence bloodGroupArr[] = new CharSequence[] {"A+", "B+", "A-", "B-"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick your blood group");
        builder.setItems(bloodGroupArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bloodGroup = bloodGroupArr[which].toString();
                // the user clicked on colors[which]

                System.out.print(bloodGroup);
            }
        });
        builder.show();
        System.out.print(bloodGroup);
        return bloodGroup;

    }
}
