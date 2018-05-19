package com.example.sardorbek.adminside.Service;

import com.example.sardorbek.adminside.Common.Common;
import com.example.sardorbek.adminside.Model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sardorbek on 4/25/18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken=FirebaseInstanceId.getInstance().getToken();
        updateToAdmin(refreshedToken);
    }

    private void updateToAdmin(String refreshedToken) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Tokens");
        Token token=new Token(refreshedToken,true);
        tokens.child(Common.currentUser.getPassword()).setValue(token);
    }


}
