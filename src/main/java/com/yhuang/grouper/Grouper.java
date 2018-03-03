package com.yhuang.grouper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Grouper {
    public final static String SPACE = " ";
    private List<Patient> _patients;
    private Map<String, Integer> _signColumnsValueKVP;

    private String _fileName;

    public Grouper(String fileName) {
        _fileName = fileName;
    }

    /* approach 1: This is a classical approach to sign every records,
        then sort the records by signed value,
        squash records with same signed value into one group
        read -> sign -> sort -> squash
    */
    public void process() throws GrouperException {
        CSVFileReader csvFileReader = new CSVFileReader(_fileName);

        // step 1:
        _patients = csvFileReader.readData();

        // step 2: sign all patients by patient lastName + firstName
        sign();

        // step 3: sort by signed value
        groupPatientsBySort();

        // step 4: output the group
        if (_patients.size() > 0) {
            squashSortedPatientsAsGroup();
        } else {
            System.out.println("no patient, nothing to do");
        }
    }


    /**
     * use the lastname and firstName to sign a patient
     */
    private void sign() {
        for (Patient patient : _patients) {
            patient.signNames();
        }
    }

    /**
     * use (lastName + "^" + firstName) to sort the patients
     */
    private void groupPatientsBySort() {
        Collections.sort(_patients);
    }

    private void squashSortedPatientsAsGroup() {
        int groupId = 0;
        int total = _patients.size();

        // we already ensure _patients size > 0 before calling this method
        String oldSig = _patients.get(0).getSignedValue();

        int i = 0;
        System.out.println(groupId + ":");
        while (i < total) {
            Patient patient = _patients.get(i);

            String currSig = patient.getSignedValue();
            if (!currSig.equalsIgnoreCase(oldSig)) {
                groupId++;
                System.out.println(groupId + ":");
                oldSig = currSig;
            }

            System.out.println(patient.toString());

            i++;
        }
    }


    /* approach 2: use hashmap to store [key: list of patients]
    after read and parse a record line,
    we use last name + first name as key to save a record into the list of them same key's value,
    that means, we group the patients when load the file.
     */
    public void processGroupAtLoading() throws GrouperException {
        CSVFileReader csvFileReader = new CSVFileReader(_fileName);

        // step 1:
        HashMap<String, List<Patient>> patients = csvFileReader.loadDataGroup();

        // step 2:
        if (patients.size() > 0) {
            outputPatientsAsGroup(patients);
        } else {
            System.out.println("no patient, nothing to do");
        }
    }

    private void outputPatientsAsGroup(HashMap<String, List<Patient>> patients) {
        int groupid = 0;

        for (Map.Entry<String, List<Patient>> entry : patients.entrySet()) {
            List<Patient> patientList = entry.getValue();
            System.out.println(groupid + ":");
            patientList.forEach(item->System.out.println(item.toString()));
            groupid++;
        }
    }
}
