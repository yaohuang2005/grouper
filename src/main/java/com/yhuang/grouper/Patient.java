package com.yhuang.grouper;

public class Patient implements Comparable<Patient> {
    private String _pid;
    private String _lastName;
    private String _firstName;
    private String _middleName;
    private String _sex;
    private String _dateOfBirth;
    private String _signedValue;

    public Patient(String pid,  String lastName, String firstName, String middleName,
                   String sex, String dateOfBirth) {
        _pid = pid;
        _lastName = lastName;
        _firstName = firstName;
        _middleName = middleName;
        _sex = sex;
        _dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return _lastName;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getSignedValue() {
        return _signedValue;
    }

    /**
     * sign patient by lastname and firstname:
     */
    public void signNames() {
        _signedValue = _lastName.toLowerCase() + "^" + _firstName.toLowerCase();
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append(_pid).
                append(",").
                append(_lastName).
                append("^").
                append(_firstName);

        if (_middleName != null && !_middleName.trim().isEmpty())
            output.append("^").
                    append(_middleName);

        output.append(",").
                append(_sex).
                append(",").
                append(_dateOfBirth);

        return output.toString();
    }

    @Override
    public int compareTo(Patient anotherDateValue) {
        return this._signedValue.compareTo(anotherDateValue._signedValue);
    }

}
