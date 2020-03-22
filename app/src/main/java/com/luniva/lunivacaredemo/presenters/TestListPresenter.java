package com.luniva.lunivacaredemo.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.luniva.lunivacaredemo.apiservices.APIClient;
import com.luniva.lunivacaredemo.apiservices.TestListInterface;
import com.luniva.lunivacaredemo.models.TestListModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestListPresenter {
    private WeakReference<View> view;

    public TestListPresenter(TestListPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private TestListPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onTestListResponseSuccess(TestListModel body);

        void onTestListResponseFailure(String something_went_wrong);
    }

    public void getTestList(String p_id){
        Log.e("patient: ","test list presenter : " + p_id );

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", p_id);

        TestListInterface testListInterface = APIClient.getPatientListAPI().create(TestListInterface.class);
        testListInterface.getTestList(jsonObject).enqueue(new Callback<TestListModel>() {
            @Override
            public void onResponse(Call<TestListModel> call, Response<TestListModel> response) {
                if(response.isSuccessful()){
                    if(response != null){
                        Log.e( "onResponse: ", new GsonBuilder().create().toJson(response.body()) );
                        getView().onTestListResponseSuccess(response.body());
                    }
                    else {
                        Log.e("onResponse: ","test list response null" );
                    }
                }
            }

            @Override
            public void onFailure(Call<TestListModel> call, Throwable t) {
                Log.e("onFailureTestList: ", t.getMessage());
                getView().onTestListResponseFailure(t.getMessage());
            }
        });

    }
}
