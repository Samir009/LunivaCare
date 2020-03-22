package com.luniva.lunivacaredemo.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.apiservices.APIClient;
import com.luniva.lunivacaredemo.apiservices.PatientListInterface;
import com.luniva.lunivacaredemo.models.PatientListModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientListPresenter {

    private WeakReference<View> view;

    public PatientListPresenter(PatientListPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private PatientListPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onPatientListResponseSuccess(PatientListModel body);

        void onPatientListResponseFailure(String something_went_wrong);
    }

    public void patientList(String keyword){
        Log.e("patient: ","patient list presenter" );

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("searchParameter", keyword);

        PatientListInterface patientListInterface = APIClient.getPatientListAPI().create(PatientListInterface.class);
        patientListInterface.getPatientList(jsonObject).enqueue(new Callback<PatientListModel>() {
                @Override
            public void onResponse(Call<PatientListModel> call, Response<PatientListModel> response) {
                if(response.isSuccessful()){
                    if(response != null){
                        Log.e( "onResponse: ", new GsonBuilder().create().toJson(response.body()) );
                        getView().onPatientListResponseSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientListModel> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                getView().onPatientListResponseFailure(t.getMessage());
            }
        });
    }

}
