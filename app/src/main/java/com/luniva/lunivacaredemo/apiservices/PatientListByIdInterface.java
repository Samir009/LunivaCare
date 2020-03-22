package com.luniva.lunivacaredemo.apiservices;

import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.models.PatientListByIdModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PatientListByIdInterface {
        @POST("api/GetPatientInfoByPatientId")
        Call<PatientListByIdModel> getPatientListById(
                @Body JsonObject jsonObject
        );

}
