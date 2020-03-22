package com.luniva.lunivacaredemo.apiservices;

import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.models.TestListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestListInterface {

    @POST("api/GetPatientBillDetailsByPatId")
    Call<TestListModel> getTestList(@Body JsonObject jsonObject);
}
