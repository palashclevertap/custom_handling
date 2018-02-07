package com.clevertap.customhandlingpushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
   // private TextView txtRegId, txtMessage;


   // Button upload, multivalue1, multivalue2, multivalue3;
    CleverTapAPI cleverTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        upload = (Button) findViewById(R.id.upload);
//        multivalue1 = (Button) findViewById(R.id.multivalue1);
//        multivalue2 = (Button) findViewById(R.id.multivalue2);
//        multivalue3 = (Button) findViewById(R.id.multivalue3);

        try {
            CleverTapAPI.setDebugLevel(3);
            cleverTap = CleverTapAPI.getInstance(getApplicationContext());

//            Location location = cleverTap.getLocation();
//            location.setLatitude(19.1667335);
//            location.setLongitude(72.8484032);
//            cleverTap.setLocation(location);


        } catch (CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
        } catch (CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
        }

       // txtRegId = (TextView) findViewById(R.id.txt_reg_id);
    //    txtMessage = (TextView) findViewById(R.id.txt_push_message);






        // each of the below mentioned fields are optional
// if set, these populate demographic information in the Dashboard
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Name", "Jack Montana");                  // String
        profileUpdate.put("Identity", 61026032);                    // String or number
        profileUpdate.put("Email", "jack@gmail.com");               // Email address of the user
        profileUpdate.put("Phone", "+14155551234");                 // Phone (with the country code, starting with +)
        profileUpdate.put("Gender", "M");                           // Can be either M or F
        profileUpdate.put("Employed", "Y");                         // Can be either Y or N
        profileUpdate.put("Education", "Graduate");                 // Can be either Graduate, College or School
        profileUpdate.put("Married", "Y");                          // Can be either Y or N
        profileUpdate.put("DOB", new Date());                       // Date of Birth. Set the Date object to the appropriate value first
        profileUpdate.put("Age", 28);                               // Not required if DOB is set
        profileUpdate.put("Tz", "Asia/Kolkata");                    //an abbreviation such as "PST", a full name such as "America/Los_Angeles",
        //or a custom ID such as "GMT-8:00"
        profileUpdate.put("Photo", "www.foobar.com/image.jpeg");    // URL to the Image

// optional fields. controls whether the user will be sent email, push etc.
        profileUpdate.put("MSG-email", false);                      // Disable email notifications
        profileUpdate.put("MSG-push", true);                        // Enable push notifications
        profileUpdate.put("MSG-sms", false);                        // Disable SMS notifications

        ArrayList<String> stuff = new ArrayList<String>();
        stuff.add("bag");
        stuff.add("shoes");
        profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings

        String[] otherStuff = {"Jeans","Perfume"};
        profileUpdate.put("MyStuff", otherStuff);                   //String Array

        cleverTap.profile.push(profileUpdate);





        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

         //           txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();







    //    clickListeners();
    }


  //  private void clickListeners() {


//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
//                profileUpdate.put("Name", "Jack Montana new");                   // String or number
//                profileUpdate.put("Email", "4tp4uswki112334567@yopmail.com");
//                profileUpdate.put("Identity", 123);             // Email address of the user
//                profileUpdate.put("Phone", "9920879978");                 // Phone (with the country code, starting with +)
//                profileUpdate.put("Gender", "M");                           // Can be either M or F
//                profileUpdate.put("Employed", "Y");                         // Can be either Y or N
//                profileUpdate.put("Education", "Graduate");                 // Can be either Graduate, College or School
//                profileUpdate.put("Married", "Y");                          // Can be either Y or N
//                profileUpdate.put("DOB", new Date());                       // Date of Birth. Set the Date object to the appropriate value first
//                profileUpdate.put("Age", 28);
//
//                cleverTap.onUserLogin(profileUpdate);
//
//
//            }
//        });
//
//
//        multivalue1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                ArrayList<String> stuff = new ArrayList<String>();
////                stuff.add("One");
////                stuff.add("Two");
////                cleverTap.profile.addMultiValuesForKey("TestNewLatest2", stuff);
////
////
////
////                stuff = new ArrayList<String>();
////                stuff.add("Three");
////
////                cleverTap.profile.addMultiValuesForKey("TestNewLatest2", stuff);
//
//
//
//
//
////                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
////                profileUpdate.put("Name", "Jack Montana");                   // String or number
////                profileUpdate.put("Email", "4tp4uswki1123@yopmail.com");
////                profileUpdate.put("Identity", 12345);             // Email address of the user
////                profileUpdate.put("Phone", "9920879978");                 // Phone (with the country code, starting with +)
////                profileUpdate.put("Gender", "M");                           // Can be either M or F
////                profileUpdate.put("Employed", "Y");                         // Can be either Y or N
////                profileUpdate.put("Education", "Graduate");                 // Can be either Graduate, College or School
////                profileUpdate.put("Married", "Y");                          // Can be either Y or N
////                profileUpdate.put("DOB", new Date());                       // Date of Birth. Set the Date object to the appropriate value first
////                profileUpdate.put("Age", 28);
////
////                cleverTap.onUserLogin(profileUpdate);
//
//
////                // To set a multi-value property
//                ArrayList<String> stuff = new ArrayList<String>();
//                stuff.add("bag new 1");
//                stuff.add("shoes new 1");
//                cleverTap.profile.setMultiValuesForKey("freshKeyNew", stuff);
//
//
//                // To set a multi-value property
//
//
//            }
//        });
//
//        multivalue2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                ArrayList<String> oldStuff = new ArrayList<String>();
//                oldStuff.add("bag new 1");
//                oldStuff.add("shoes new 1");
//                cleverTap.profile.removeMultiValuesForKey("freshKeyNew", oldStuff);
//
//
////                try {
////                    Thread.sleep(1000);
////                } catch (Exception e) {
////                    //
////                }
//                ArrayList<String> stuff = new ArrayList<String>();
//                stuff.add("bag new 2");
//                stuff.add("shoes new 2");
//                cleverTap.profile.addMultiValuesForKey("freshKeyNew", stuff);
//
//                cleverTap.event.push("second push");
//
//            }
//        });
//
//        multivalue3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ArrayList<String> oldStuff = new ArrayList<String>();
//                oldStuff.add("bag new 2");
//                oldStuff.add("shoes new 2");
//                cleverTap.profile.removeMultiValuesForKey("freshKeyNew", oldStuff);
//
//
//                ArrayList<String> stuff = new ArrayList<String>();
//                stuff.add("bag new 3");
//                stuff.add("shoes new 3");
//                cleverTap.profile.addMultiValuesForKey("freshKeyNew", stuff);
//
//                cleverTap.event.push("third push");
//
//            }
//        });
//    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

//        if (!TextUtils.isEmpty(regId))
//       //     txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//      //      txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}