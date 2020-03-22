package com.luniva.lunivacaredemo.apiservices;

import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.models.PatientListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PatientListInterface {

    @POST("api/GetPatientListBySearch")
    Call<PatientListModel> getPatientList(@Body JsonObject jsonObject);
}

