package com.yhuang.grouper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVFileReader {
    private final static String COMMA_DELIMITOR = ",";
    private final static String CARAT_DELIMITOR = "\\^";

    private String _fileName;

    public CSVFileReader(String fileName) {
        _fileName = fileName;
    }

    public List<Patient> readData() throws GrouperException {
        List<Patient> rows = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new FileReader(_fileName))) {
            String readLine;
            while ((readLine = bufReader.readLine()) != null) {
                try {
                    Patient patient = parse(readLine);
                    rows.add(patient);
                } catch (GrouperException ex) {
                    System.out.println("has parsing error for a line, skip this line");
                }
            }
        } catch (IOException e) {
            throw new GrouperException("cannot read file");
        }
        return rows;
    }

    private Patient parse(String readLine) throws GrouperException {
        String[] splitStr = readLine.trim().split(COMMA_DELIMITOR);
        int size = splitStr.length;
        if (size != 4) {
            System.out.println("Wrong input fields, skip this line");
            throw new GrouperException("input fields are not full");
        }

        String pid = splitStr[0];
        String fullName = splitStr[1];
        String[] names = fullName.trim().split(CARAT_DELIMITOR);
        String lastName;
        String firstName;
        String middleName;
        if (names.length <= 1) {
            System.out.println("Wrong input names, skip this line");
            throw new GrouperException("input names need at least last name and first name");
        } else if (names.length == 2) {
            lastName = names[0];
            firstName = names[1];
            middleName = "";
        } else {
            lastName = names[0];
            firstName = names[1];
            middleName = names[2];
        }

        String sex = splitStr[2];
        String dateOfBirth = splitStr[3];

        return new Patient(pid, lastName, firstName, middleName, sex, dateOfBirth);
    }

    // for approach 2, group when loading records
    public HashMap<String, List<Patient>> loadDataGroup() throws GrouperException {
        HashMap<String, List<Patient>> rows = new HashMap<>();

        try (BufferedReader bufReader = new BufferedReader(new FileReader(_fileName))) {
            String readLine;
            while ((readLine = bufReader.readLine()) != null) {
                // parse the line
                try {
                    Patient patient = parse(readLine);

                    String key = patient.getLastName().toLowerCase() + patient.getFirstName().toLowerCase();
                    if (rows.containsKey(key)) {
                        rows.get(key).add(patient);
                    } else {
                        List<Patient> patientList = new ArrayList<>();
                        patientList.add(patient);
                        rows.put(key, patientList);
                    }
                } catch (GrouperException ex) {
                    System.out.println("has parsing error for a line, skip this line");
                }
            }
        } catch (IOException e) {
            throw new GrouperException("cannot read file");
        }
        return rows;
    }
}
