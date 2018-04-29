package modcom.joseph.com.applefirebase;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Contribute extends AppCompatActivity {

    //Create this 2 objects
    DatabaseReference ref;
    FirebaseDatabase fdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

//Initialize/start your firebase service
        FirebaseApp.initializeApp(this);
//Create a firebase instance
        fdb = FirebaseDatabase.getInstance();
//database reference pointing to demo node
        ref = fdb.getReference().child("sacco");
        final EditText tvID = findViewById(R.id.tvID);
        final EditText tvPhone = findViewById(R.id.tvPhone);
        final EditText tvAmount = findViewById(R.id.tvAmount);
        final ProgressBar bar = findViewById(R.id.progressBar2);
        bar.setVisibility(View.GONE);
        Button save = findViewById(R.id.btnSubmit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Save your data using a hash map

                if (tvID.getText().toString().length()>0) {
                    bar.setVisibility(View.VISIBLE);
                    Map<String, String> userData = new HashMap<String, String>();

                    userData.put("Client_ID", tvID.getText().toString());
                    userData.put("Client_Phone", tvPhone.getText().toString());
                    userData.put("Client_Amount", tvAmount.getText().toString());
                    userData.put("Current Date", Calendar.getInstance().getTime().toString());
                    fdb.getReference().child("sacco").child(tvPhone.getText().toString()).setValue(userData);
                    Toast.makeText(Contribute.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
// ref.push().setValue(userData);
                    bar.setVisibility(View.GONE);
                }

                else {
                    Toast.makeText(Contribute.this, "Please Enter An ID", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}