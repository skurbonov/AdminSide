package com.example.sardorbek.adminside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sardorbek.adminside.Common.Common;
import com.example.sardorbek.adminside.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class LogIn extends AppCompatActivity {

    EditText edtId, edtPassword;
    Button btnLogIn;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtId=(MaterialEditText)findViewById(R.id.edtIdNumber);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btnLogIn=(FButton)findViewById(R.id.btn_login);

        db=FirebaseDatabase.getInstance();
        users=db.getReference("User");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser(edtId.getText().toString(),edtPassword.getText().toString());
            }
        });

    }

    private void logInUser(String id, String password) {
        final ProgressDialog mDialog= new ProgressDialog(LogIn.this);
        mDialog.setMessage("Please wait..");

        final String localId=id;
        final String localPassword=password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.child(localId).exists())
               {
                   mDialog.dismiss();
                   User user=dataSnapshot.child(localId).getValue(User.class);
                   user.setId(localId);
                   if (Boolean.parseBoolean(user.getIsStaff())) //if isStaff true
                   {
                       if(user.getPassword().equals(localPassword))
                       {
                           //Login ok
                           Intent login=new Intent(LogIn.this,Home.class);
                           Common.currentUser=user;
                           startActivity(login);
                           finish();
                       }
                       else
                           Toast.makeText(LogIn.this, "Wrong password", Toast.LENGTH_SHORT).show();
                   }
                   else
                       Toast.makeText(LogIn.this, "Please Log In with Staff account", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   mDialog.dismiss();
                   Toast.makeText(LogIn.this, "User does not exist in Database", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
