package com.luniva.lunivacaredemo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.activities.PatientDetailsActivity;
import com.luniva.lunivacaredemo.databinding.FragmentPatientListBinding;
import com.luniva.lunivacaredemo.helpers.AppRecyclerViewAdapter;
import com.luniva.lunivacaredemo.helpers.DefineClassType;
import com.luniva.lunivacaredemo.helpers.ShowToast;
import com.luniva.lunivacaredemo.models.PatientListModel;
import com.luniva.lunivacaredemo.presenters.PatientListPresenter;
import com.robertlevonyan.views.expandable.Expandable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientListFragment extends Fragment implements PatientListPresenter.View {

//    private List<PatientListModel> patientListModels;

    private PatientListAdapter patientListAdapter;

    private FragmentPatientListBinding binding;
    private PatientListPresenter patientListPresenter;

    private String p_id;


    public PatientListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        showProgressBar();
        patientListPresenter = new PatientListPresenter(this);
        patientListPresenter.patientList("");
        //        getPatientList();

        binding.searchPatientSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                ShowToast.withLongMessage(newText);
                patientListPresenter.patientList(newText);
                patientListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        prepareSampleCategoryItems();

        return view;
    }

    private void showProgressBar() {
        binding.contenetPB.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.contenetPB.setVisibility(View.GONE);
    }


    private void prepareSampleCategoryItems() {
        patientListAdapter = new PatientListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.patientListRv.setLayoutManager(linearLayoutManager);
        binding.patientListRv.setAdapter(patientListAdapter);
    }

    @Override
    public void onPatientListResponseSuccess(PatientListModel body) {
        hideProgressBar();
        patientListAdapter.add(body);
        patientListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPatientListResponseFailure(String something_went_wrong) {
        ShowToast.withLongMessage(something_went_wrong);
    }

    private class PatientListAdapter extends AppRecyclerViewAdapter {

        PatientListModel patientListModel;

        @Override
        public void add(Object object) {
            patientListModel = DefineClassType.getType(object, PatientListModel.class);
            Log.e("add: ", "added to RV adapter: " + new GsonBuilder().create().toJson(patientListModel));
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

            patientListVHItem.patient_name.setText(patientList.getFirstName() + " " + patientList.getMiddleName() + " " + patientList.getLastName());

            patientListVHItem.patient_gender.setText(patientList.getSex());
            patientListVHItem.patient_contact.setText(String.valueOf(patientList.getContactNo()));
            patientListVHItem.patient_age.setText(String.valueOf(patientList.getAge()));


        }

        @Override
        public int getItemCount() {
            if (patientListModel != null) {
                return patientListModel.getPatientList().size();
            }
            return 0;
        }

        private class PatientListVHItem extends RecyclerView.ViewHolder {

            private TextView patient_name;
            private TextView patient_contact;
            private TextView patient_gender;
            private TextView patient_age;
            private TextView patient_testname;
            private TextView patient_status;
            private TextView patient_location;
            private TextView patient_details_view;

            public PatientListVHItem(@NonNull View itemView) {
                super(itemView);

                Expandable expandable;
                expandable = itemView.findViewById(R.id.expandable);
                expandable.setIcon(getResources().getDrawable(R.drawable.ic_user));
                expandable.setAnimateExpand(true);

                patient_name = itemView.findViewById(R.id.patient_name_df);
                patient_details_view = itemView.findViewById(R.id.view_test_details_df);
                patient_gender = itemView.findViewById(R.id.gender_df);
                patient_age = itemView.findViewById(R.id.age_df);
                patient_status = itemView.findViewById(R.id.test_status_df);
                patient_location = itemView.findViewById(R.id.location_df);
                patient_contact = itemView.findViewById(R.id.contact_df);

                patient_details_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PatientDetailsActivity.class);
                        Log.e("onClick: Id ", String.valueOf(patientListModel.getPatientList().get(getAdapterPosition()).getId()));
                        intent.putExtra("patient_id", String.valueOf(patientListModel.getPatientList().get(getAdapterPosition()).getId()));
                        startActivity(intent);
                    }
                });

            }
        }
    }


}
