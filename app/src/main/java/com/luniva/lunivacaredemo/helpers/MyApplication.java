package com.luniva.lunivacaredemo.helpers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.luniva.lunivacaredemo.constants.AppConstants;
import com.luniva.lunivacaredemo.models.Fcm_User_Device_Token;
import com.luniva.lunivacaredemo.utils.Utilities;

/**
 * created by SAMIR SHRESTHA on 1/29/2020  at 11:32 AM
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static MyApplication myApplication;
    private static SharedPreferences sharedPreferences;
    private static boolean isActive;


    CollectionReference colUserDevice;
    //    db.collection("registered_user");
    DocumentReference docUserDevice;
    Fcm_User_Device_Token fcm_user_device_token;


    @Override
    public void onCreate() {
        super.onCreate();

        initiateFirebase();

        registerActivityLifecycleCallbacks(this);

        myApplication = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
    }

    public static Context getAppContext() {
        return myApplication.getApplicationContext();
    }

    public static SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

//    //    firebase
    public void initiateFirebase() {

        //  subscribe the topic, if not found the topic than it creates itself the given topic
        FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.FCM_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e("onComplete: ", "subscribed to luniva care");
                        } else {
                            Log.e("onComplete: ", "subscribed failed to luniva care");
                        }
                    }
                });


//        to get fcm token from firebase
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {

                        Log.e("onUnsuccessful: ", String.valueOf(task.getException()));
//                            fcm_token = Objects.requireNonNull(task.getResult()).getToken();

                    } else {

                        Log.e("device token: ", task.getResult().getToken());

                        Utilities.saveFcmToken(getApplicationContext(), task.getResult().getToken());
//
                        Log.e("onComplete: ", "token save to sharedpref" + Utilities.getSavedFcmToken(getAppContext()));

                        // retrieve the token to check whether it is registered or not
                        colUserDevice = Utilities.firebaseFirestore().collection("registered_user_devices");
                        docUserDevice = colUserDevice.document();

                        colUserDevice.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                if(queryDocumentSnapshots.isEmpty()){
                                    fcm_user_device_token = new Fcm_User_Device_Token(task.getResult().getId(), task.getResult().getToken());
                                    colUserDevice.add(fcm_user_device_token).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.e("onSuccess: ", "table was empty. added first token" );
                                        }
                                    });

                                } else {
                                    boolean found = false;
                                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                        Fcm_User_Device_Token fcm_user_device_token = documentSnapshot.toObject(Fcm_User_Device_Token.class);
                                        Log.e("onSuccess: ", "got from device: " + task.getResult().getId() + "\t" + task.getResult().getToken());

                                        if(fcm_user_device_token.getToken().equals(task.getResult().getToken())){

                                            found = true;
                                            break;
                                        }

                                    }

                                    if(found){
                                        Log.e("onSuccess: ", "user's device token exists in firestore :) \t" + task.getResult().getToken());
                                    }
                                    else {
//                                            Log.e( "onSuccess: ", "new device token inserted" );
//
//                                                Log.e("onSuccess: ", "new user's device token :) \t" + task.getResult().getId());

                                        Fcm_User_Device_Token fcm_user_device_token1 = new Fcm_User_Device_Token(task.getResult().getId(), task.getResult().getToken());

                                        colUserDevice.add(fcm_user_device_token1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.e("onSuccess: ", "user's device token inserted to firestore ::)");
                                            }
                                        });
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e( "onFailure: ", "couldn't retrieve data from firebase : " + e.getMessage());
                            }
                        });
                    }
                });
    }

}
