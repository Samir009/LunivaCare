package com.luniva.lunivacaredemo.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.apiservices.APIClient;
import com.luniva.lunivacaredemo.apiservices.PatientListByIdInterface;
import com.luniva.lunivacaredemo.models.PatientListByIdModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientListByIdPresenter {
    private WeakReference<View> view;

    public PatientListByIdPresenter(PatientListByIdPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private PatientListByIdPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onPatientListByIdResponseSuccess(PatientListByIdModel body);

        void onPatientListByIdResponseFailure(String something_went_wrong);
    }

    public void getPatientListById(String p_id){
        Log.e("patient: ","patient list presenter" );

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", p_id);

        PatientListByIdInterface patientListByIdInterface = APIClient.getPatientListAPI().create(PatientListByIdInterface.class);
        patientListByIdInterface.getPatientListById(jsonObject).enqueue(new Callback<PatientListByIdModel>() {
            @Override
            public void onResponse(Call<PatientListByIdModel> call, Response<PatientListByIdModel> response) {
                if(response.isSuccessful()){
                    if(response != null){
                        Log.e("onResponse: ", new GsonBuilder().create().toJson(response.body()));
                        getView().onPatientListByIdResponseSuccess(response.body());
                    } else {
                        Log.e("onResponse: ", "patient list by id presenter has null value");
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientListByIdModel> call, Throwable t) {
                getView().onPatientListByIdResponseFailure(t.getMessage());
            }
        });

    }
}
