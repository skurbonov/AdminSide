package com.example.sardorbek.adminside.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.sardorbek.adminside.Model.Request;
import com.example.sardorbek.adminside.Model.User;
import com.example.sardorbek.adminside.Remote.APIService;
import com.example.sardorbek.adminside.Remote.FCMRetrofitClient;
import com.example.sardorbek.adminside.Remote.IGeoCordinates;
import com.example.sardorbek.adminside.Remote.RetrofitClient;

/**
 * Created by sardorbek on 4/18/18.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static final String UPDATE="Update";
    public static final String DELETE="Delete";
    public static int PICK_IMAGE_REQUEST=71;

    public static final String baseUrl="https://maps.googleapis.com";
    public static final String fcmUrl="https://fcm.googleapis.com/";


    public static String convertCodeToStatus(String code)
    {
        if(code.equals("0"))
            return "Placed";
        else if(code.equals("1"))
            return "On My Way";
        else
            return "Ready to pick up";

    }


    public static APIService getFCMCClient()
    {
        return FCMRetrofitClient.getClient(fcmUrl).create(APIService.class);
    }
    public static IGeoCordinates getGeoCodeService()
    {
        return RetrofitClient.getClient(baseUrl).create(IGeoCordinates.class);
    }

    public  static Bitmap scaleBitmap(Bitmap bitmap, int newWidth,int newHeight)
    {
        Bitmap scaledBitmap=Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);
        float scaleX=newWidth/(float)bitmap.getWidth();
        float scaleY=newHeight/(float)bitmap.getHeight();
        float pivotX=0,pivotY=0;

        Matrix scaleMatrix=new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas=new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }




}
