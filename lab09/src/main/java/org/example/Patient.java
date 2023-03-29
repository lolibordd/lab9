package org.example;

import java.util.Objects;

public class Patient {
    protected int id;
    protected String FullName;
    protected String Address;
    protected String PhoneNumber;
    protected int cardNumber;
    protected String Diagnosis;

    public Patient(int id, String fullName, String address, String phoneNumber, int cardNumber, String diagnosis) {
        this.id = id;
        FullName = fullName;
        Address = address;
        PhoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        Diagnosis = diagnosis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                FullName.equals(patient.FullName) &&
                Address.equals(patient.Address) &&
                PhoneNumber == patient.PhoneNumber &&
                cardNumber == patient.cardNumber &&
                Diagnosis.equals(patient.Diagnosis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, FullName, Address, PhoneNumber, cardNumber, Diagnosis);
    }

    @Override
    public String toString() {
        return "\nPatient{" +
                "id=" + id +
                ", fullName=" + FullName +
                ", address=" + Address +
                ", phoneNumber=" + PhoneNumber +
                ", cardNumber=" + cardNumber +
                ", diagnosis='" + Diagnosis + '\'' +
                '}';
    }

}