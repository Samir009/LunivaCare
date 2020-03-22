package com.luniva.lunivacaredemo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.databinding.ActivityPatientDetailsBinding;
import com.luniva.lunivacaredemo.fragments.DashboardFragment;
import com.luniva.lunivacaredemo.helpers.AppRecyclerViewAdapter;
import com.luniva.lunivacaredemo.helpers.DefineClassType;
import com.luniva.lunivacaredemo.models.PatientListByIdModel;
import com.luniva.lunivacaredemo.models.PatientListModel;
import com.luniva.lunivacaredemo.models.TestListModel;
import com.luniva.lunivacaredemo.presenters.PatientListByIdPresenter;
import com.luniva.lunivacaredemo.presenters.TestListPresenter;

public class PatientDetailsActivity extends AppCompatActivity implements PatientListByIdPresenter.View, TestListPresenter.View, DashboardFragment.PatientDetailInterface {

    private ActivityPatientDetailsBinding binding;
    private String p_id;
    private int p_position;
    private boolean requestAccepted;
    private boolean requestRejected;
    private String selected_payment_method;

    private PatientListModel patientListModel;
    private MainActivity mainActivity;

    private PatientListByIdPresenter patientListByIdPresenter;
    private TestListPresenter testListPresenter;

    TestInfoRVAdapter testInfoRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPatientDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        patientListModel = new PatientListModel();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Patient details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            p_id = extras.getString("patient_id");
            p_position = extras.getInt("position");
            requestAccepted = extras.getBoolean("request_accept");
            requestRejected = extras.getBoolean("request_reject");

            Log.e( "position: ", String.valueOf(p_position));
//            Log.e( "checkRequesNot: ", String.valueOf(patientListModel.getPatientList().get(p_position).getAccepted()));

        } else {
            Log.e("onCreate: ", "intent data is null");
        }

        Log.e("onCreate: ", "accept tested " + p_id + requestAccepted + requestRejected);

        testListPresenter = new TestListPresenter(this);
        testListPresenter.getTestList(p_id);
//
        patientListByIdPresenter = new PatientListByIdPresenter(this);
        patientListByIdPresenter.getPatientListById(p_id);

//        checkAcceptRejectRequest();

        prepareTestInfoRVAdapter();

        initializeListener();
    }

    private void initializeListener() {
        binding.selectPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Cash on delivery", "Debit/Credit card", "e-Sewa"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(PatientDetailsActivity.this);
                builder.setTitle("Select payment type");

                builder.setItems(items, (dialog, which) -> {
                    if (items[which].equals("Cash on delivery")) {
                        selected_payment_method = "Cash on delivery";
                    } else if (items[which].equals("Debit/Credit card")) {
                        selected_payment_method = "Debit/Credit card";
                    } else if (items[which].equals("e-Sewa")) {
                        selected_payment_method = "e-Sewa";
                    } else {
                        dialog.dismiss();
                    }
//                    set selected type to textview
                    binding.selectPaymentType.setText(selected_payment_method);
                });
                builder.show();
            }
        });


        checkRequestAcceptedOrNot();
    }

    @Override
    public void onPatientListByIdResponseSuccess(PatientListByIdModel body) {
        Log.e("addClass: ", new GsonBuilder().create().toJson(body));
        setData(body);
    }

    private void setData(PatientListByIdModel body) {
//        Log.e( "setData: ", body.getPatientInfo().get(0).getBillPaymentType());
        if (body.getPatientInfo().size() != 0) {
            binding.pName.setText(body.getPatientInfo().get(0).getFirstName());
            binding.pSex.setText(body.getPatientInfo().get(0).getSex());
            binding.pContact.setText(body.getPatientInfo().get(0).getContactNo());
            binding.pRequester.setText(body.getPatientInfo().get(0).getRequestor());
            binding.billRAmt.append(String.valueOf(body.getPatientInfo().get(0).getBillRemainingAmt()));
        } else {
            Log.e("setData: ", "data is empty to load in patient details.");
        }

    }

    @Override
    public void onPatientListByIdResponseFailure(String something_went_wrong) {

    }

    private void prepareTestInfoRVAdapter() {
        testInfoRVAdapter = new TestInfoRVAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.testInfoRv.setLayoutManager(linearLayoutManager);
        binding.testInfoRv.setAdapter(testInfoRVAdapter);
    }

    @Override
    public void onTestListResponseSuccess(TestListModel body) {
        testInfoRVAdapter.add(body);
        testInfoRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTestListResponseFailure(String something_went_wrong) {
        Log.e("onTestListResFailure: ", something_went_wrong);
    }

    @Override
    public void getPatientDetails(String arg) {
        Log.e("getPatientDetails: ","got from fragment " + arg );
    }


//    got data from interface

    //    shows test name
    private class TestInfoRVAdapter extends AppRecyclerViewAdapter {

        TestListModel testListModel;

        @Override
        public void add(Object object) {
            testListModel = DefineClassType.getType(object, TestListModel.class);
            Log.e("add: ", new GsonBuilder().create().toJson(testListModel));
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list, parent, false);
            return new TestListVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TestListVH testListVH = (TestListVH) holder;

            TestListModel.PatientBillDetail patientBillDetail = testListModel.getPatientBillDetails().get(position);
            Log.e("binding: ", patientBillDetail.getBillTestName());
            testListVH.test_name.setText(patientBillDetail.getBillTestName());
        }

        @Override
        public int getItemCount() {

            if (testListModel != null) {
                return testListModel.getPatientBillDetails().size();
            }
            return 0;
        }

        private class TestListVH extends RecyclerView.ViewHolder {

            private TextView test_name;

            public TestListVH(@NonNull View itemView) {
                super(itemView);
                test_name = itemView.findViewById(R.id.test_name);
            }
        }
    }

    private void checkRequestAcceptedOrNot(){


//        if(patientListModel.getPatientList().get(p_position).getAccepted()){
//            Log.e("checkedOrNot: ", "accepted");
//        } else {
//            Log.e("checkedOrNot: ", "not accepted");
//        }
//
//        if(requestAccepted){
//            binding.submitHistoryBtn.setVisibility(View.VISIBLE);
//        } else {
//            binding.submitHistoryBtn.setVisibility(View.INVISIBLE);
//        }
    }

}
