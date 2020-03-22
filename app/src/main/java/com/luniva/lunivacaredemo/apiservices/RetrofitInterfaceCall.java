package com.luniva.lunivacaredemo.apiservices;

public class RetrofitInterfaceCall {

    static PatientListInterface showPatientListInterface;

    public static PatientListInterface getShowPatientListInterface(){

        if(showPatientListInterface == null){
            showPatientListInterface = APIClient.getPatientListAPI().create(PatientListInterface.class);
        }
        return showPatientListInterface;
    }
}
