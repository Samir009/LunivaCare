package com.luniva.lunivacaredemo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;
import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.activities.MapActivity;
import com.luniva.lunivacaredemo.activities.PatientDetailsActivity;
import com.luniva.lunivacaredemo.activities.SampleRequestedActivity;
import com.luniva.lunivacaredemo.activities.TotalSamplesCollectedActivity;
import com.luniva.lunivacaredemo.constants.AppConstants;
import com.luniva.lunivacaredemo.databinding.FragmentDashboardBinding;
import com.luniva.lunivacaredemo.helpers.AppRecyclerViewAdapter;
import com.luniva.lunivacaredemo.helpers.DefineClassType;
import com.luniva.lunivacaredemo.helpers.ShowToast;
import com.luniva.lunivacaredemo.models.PatientListModel;
import com.luniva.lunivacaredemo.presenters.PatientListPresenter;
import com.robertlevonyan.views.expandable.Expandable;

import org.json.JSONArray;

import fcm.androidtoandroid.FirebasePush;
import fcm.androidtoandroid.model.Notification;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements PatientListPresenter.View, View.OnClickListener {
    private FragmentDashboardBinding binding;

    private PatientListAdapter patientListAdapter;

    PatientListPresenter patientListPresenter;

    private String p_id;

    private boolean isAccepted = false;
    private boolean isRejected = false;

    PatientDetailInterface patientDetialInterface;

    public DashboardFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        patientListModel = new PatientListModel();

        showProgressBar();
        patientListPresenter = new PatientListPresenter(this);
        patientListPresenter.patientList("");

        preparePatientList();
        initializeListeners();

        return view;
    }

    private void initializeListeners() {
        binding.samplesRequested.setOnClickListener(this);
        binding.totalSamplesCollected.setOnClickListener(this);
        binding.totalSamplesRejected.setOnClickListener(this);
        binding.samplesCollectedHistory.setOnClickListener(this);
        binding.viewAllPatientList.setOnClickListener(this);
    }

    private void sendNotification() {
        new FirebasePush("AIzaSyAT_ioa1aBUfr3-_jsWAjo-d1ZfHE7Ke-Y")
                .setNotification(new Notification("FCM-AndroidToOtherDevice", "This is a body"))
                .sendToTopic(AppConstants.FCM_TOPIC);
    }

    private void sendNotificationToGroup() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(1);

        new FirebasePush("AIzaSyAT_ioa1aBUfr3-_jsWAjo-d1ZfHE7Ke-Y")
                .setNotification(new Notification("FCM-AndroidToOtherDevice", "This is a body"))
                .sendToGroup(jsonArray);
    }

    private void showProgressBar() {

        binding.contenetPB.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar() {
        binding.contenetPB.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_all_patient_list:
                NavigationView navigationView = getActivity().findViewById(R.id.navigation_view);
                navigationView.setCheckedItem(R.id.id_patient_list);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentContainer, new PatientListFragment());
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.commit();
                break;

            case R.id.samples_requested:
                startActivity(new Intent(getActivity(), SampleRequestedActivity.class));
                break;

            case R.id.total_samples_collected:
                startActivity(new Intent(getActivity(), TotalSamplesCollectedActivity.class));
                break;

            case R.id.total_samples_rejected:
                startActivity(new Intent(getActivity(), SampleRequestedActivity.class));
                break;

            case R.id.samples_collected_history:
                startActivity(new Intent(getActivity(), SampleRequestedActivity.class));
        }
    }

    private void preparePatientList() {
        patientListAdapter = new PatientListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.dPatientListRv.setLayoutManager(linearLayoutManager);
        binding.dPatientListRv.setAdapter(patientListAdapter);
    }

    @Override
    public void onPatientListResponseSuccess(PatientListModel body) {

        hideProgressBar();
        patientListAdapter.add(body);
        patientListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPatientListResponseFailure(String something_went_wrong) {

    }

    public class PatientListAdapter extends AppRecyclerViewAdapter {

        PatientListModel patientListModel;

        @Override
        public void add(Object object) {
            patientListModel = DefineClassType.getType(object, PatientListModel.class);
            Log.e("add: ", "added Dashboard Fragement to RV adapter: " + new GsonBuilder().create().toJson(patientListModel));
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);
            return new PatientListVHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            PatientListVHItem patientListVHItem = (PatientListVHItem) holder;

            PatientListModel.PatientList patientList = patientListModel.getPatientList().get(position);
            p_id = String.valueOf(patientList.getId());

            Log.e("onBindViewHolder: ", "selected Paitent Id is " + p_id);

            patientListVHItem.patient_name_df.setText(patientList.getFirstName() + " " + patientList.getMiddleName() + " " + patientList.getLastName());
            patientListVHItem.patient_gender_df.setText(patientList.getSex());
            patientListVHItem.patient_contact_df.setText(patientList.getContactNo());
            patientListVHItem.patient_age_df.setText(patientList.getAge());
//            patientListVHItem.patient_test_details_df.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e( "Position sent: ", String.valueOf(position));
//                        Intent intent = new Intent(getActivity(), PatientDetailsActivity.class);
////                        intent.putExtra("request_accept", isAccepted);
//                        intent.putExtra("position", position);
//                        patientListModel.getPatientList().get(position).setAccepted(true);
//                        intent.putExtra("patient_id", String.valueOf(patientList.getId()));
//                        startActivity(intent);
//
//                }
//            });
//
//            patientListVHItem.patient_location_df.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent map = new Intent(getActivity(), MapActivity.class);
//                    startActivity(map);
//                }
//            });
//
//            patientListVHItem.accept_patient_request.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ShowToast.withLongMessage("Request accepted");
//                    patientListModel.getPatientList().get(position).setAccepted(true);
//                    Log.e( "onClick: ", String.valueOf(patientListModel.getPatientList().get(position).getAccepted()));
//                    patientListVHItem.accept_patient_request.setClickable(false);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            int rv_limit = 3;
            if (patientListModel != null) {
                return Math.min(patientListModel.getPatientList().size(), rv_limit);
            }
            return 0;
        }

        private class PatientListVHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
            Expandable expandable;
            private TextView patient_name_df;
            private TextView patient_contact_df;
            private TextView patient_age_df;
            private TextView patient_gender_df;
            private TextView patient_location_df;
            private Button patient_test_details_df;
            private Button accept_patient_request;
            private Button reject_patient_request;

            //        private LinearLayout linearLayout;
            public PatientListVHItem(@NonNull View itemView) {
                super(itemView);

                expandable = itemView.findViewById(R.id.expandable);
                expandable.setIcon(getResources().getDrawable(R.drawable.ic_user));
                expandable.setAnimateExpand(true);

                patient_name_df = itemView.findViewById(R.id.patient_name_df);
                patient_contact_df = itemView.findViewById(R.id.contact_df);
                patient_age_df = itemView.findViewById(R.id.age_df);
                patient_gender_df = itemView.findViewById(R.id.gender_df);
                patient_location_df = itemView.findViewById(R.id.location_df);
                patient_test_details_df = itemView.findViewById(R.id.view_test_details_df);
                accept_patient_request = itemView.findViewById(R.id.accept_patient_request_df);
                reject_patient_request = itemView.findViewById(R.id.reject_patient_request_df);
                patient_test_details_df.setOnClickListener(this);
                patient_location_df.setOnClickListener(this);
                accept_patient_request.setOnClickListener(this);
                reject_patient_request.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.view_test_details_df:
                        Intent intent = new Intent(getActivity(), PatientDetailsActivity.class);
//                        intent.putExtra("request_accept",  patientListModel.getPatientList().get(getAdapterPosition()).setAccepted(true));
                        intent.putExtra("request_reject", isRejected);
                        intent.putExtra("patient_id", String.valueOf(patientListModel.getPatientList()
                                .get(getAdapterPosition()).getId()));

                        Log.e("send position: ", String.valueOf(getAdapterPosition()));

                        intent.putExtra("patient_position", String.valueOf((getAdapterPosition())));
                        startActivity(intent);
                        break;

                    case R.id.location_df:
                        Intent map = new Intent(getActivity(), MapActivity.class);
                        startActivity(map);
                        break;

                    case R.id.accept_patient_request_df:

//                        patientDetialInterface.getPatientDetails("hello");
//                        ShowToast.withLongMessage("Request accepted");
//                        patientListModel.getPatientList().get(getAdapterPosition()).setAccepted(true);
//
//                        Log.e("onClick: ", String.valueOf(patientListModel.getPatientList().get(getAdapterPosition()).getAccepted()));
////
//                        accept_patient_request.setClickable(false);
                        break;

                    case R.id.reject_patient_request_df:
                        ShowToast.withLongMessage("Request Rejected");
                        isRejected = true;
                        isAccepted = false;
                        reject_patient_request.setClickable(false);
                        break;
                }

            }
        }
    }

    public interface PatientDetailInterface {
        void getPatientDetails(String arg);
    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        try {
//            patientDetialInterface = (PatientDetailInterface) context;
//        }
//        catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
//        }
//    }
}
