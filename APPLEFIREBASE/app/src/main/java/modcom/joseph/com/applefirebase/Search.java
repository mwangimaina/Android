package modcom.joseph.com.applefirebase;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {

    //Create this 2 objects
    DatabaseReference ref;
    FirebaseDatabase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//Initialize/start your firebase service
        FirebaseApp.initializeApp(this);
//Create a firebase instance
        fdb = FirebaseDatabase.getInstance();
//database reference pointing to demo node
        ref = fdb.getReference().child("sacco");
        final EditText tvID = findViewById(R.id.tvID);
        final TextView tvData = findViewById(R.id.tvData);
        Button save = findViewById(R.id.btnSubmit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvData.setText("Please Wait....");//display this on Text view
                final String nombre = tvID.getText().toString().trim().toUpperCase();
                //query your 'sacco' table using client ID
               Query q =  ref.orderByChild("Client_ID").equalTo(nombre);
                //listen/check if there is data
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if there is data
                        if (dataSnapshot.exists()) {
                            //show the data found in the Textview
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                String id = (String) singleSnapshot.child("Client_ID").getValue(String.class);
                                String phone = (String) singleSnapshot.child("Client_Phone").getValue(String.class);
                                String amount = (String) singleSnapshot.child("Client_Amount").getValue(String.class);
                                //show data in Tetxview
                                tvData.setText("ID: " + id + "\n\n Phone: " + phone + "\n\n Amount: " + amount);

                            }
                        }
                        else {
                            //display Toast, No data
                            tvData.setText("No data! Try Again");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Search.this, "Database has an Error!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }
}
