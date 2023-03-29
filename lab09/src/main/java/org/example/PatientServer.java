
package org.example;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class PatientServer {
    private Connection connection;

    public List<Patient> findList() {
        ArrayList<Patient> patient = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from patient")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                int cardNumber = rs.getInt("cardNumber");
                String diagnosis = rs.getString("diagnosis");
                patient.add(new Patient(id, fullName, address, phoneNumber, cardNumber, diagnosis));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    // a) список пацієнтів, які мають вказаний діагноз в порядку зростання номерів медичної картки;
    public List<Patient> findPatientsByDiagnosis(String diagnosis) {
        ArrayList<Patient> patients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from patient where diagnosis = ? order by cardNumber asc")) {
            ps.setString(1, diagnosis);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                int cardNumber = rs.getInt("cardNumber");
                patients.add(new Patient(id, fullName, address, phoneNumber, cardNumber, diagnosis));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    //b) Список пацієнтів, номер медичної карти у яких знаходиться в заданому інтервалі
    public List<Patient> findPatientsByCardNumberRange(int start, int end) {
        ArrayList<Patient> patients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from patient where cardNumber between ? and ?")) {
            ps.setInt(1, start);
            ps.setInt(2, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                int cardNumber = rs.getInt("cardNumber");
                String diagnosis = rs.getString("diagnosis");
                patients.add(new Patient(id, fullName, address, phoneNumber, cardNumber, diagnosis));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    // c) Кількість та список пацієнтів, номер телефона яких починається з вказаної цифри:
    public List<Patient> findPatientsByPhoneNumberPrefix(String prefix) {
        ArrayList<Patient> patients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from patient where phoneNumber like ?")) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                int cardNumber = rs.getInt("cardNumber");
                String diagnosis = rs.getString("diagnosis");
                patients.add(new Patient(id, fullName, address, phoneNumber, cardNumber, diagnosis));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    public int countPatientsByPhoneNumberPrefix(String prefix) {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement("select count(*) from patient where phoneNumber like ?")) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    // d) Список діагнозів пацієнтів (без повторів) із вказанням кількості пацієнтів, що мають цей діагноз у порядку спадання цієї кількості
    public Map<String, Integer> getDiagnosisCount() {
        Map<String, Integer> diagnosisCount = new LinkedHashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT diagnosis, COUNT(*) as count FROM patient GROUP BY diagnosis ORDER BY count DESC")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String diagnosis = rs.getString("diagnosis");
                int count = rs.getInt("count");
                diagnosisCount.put(diagnosis, count);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return diagnosisCount;
    }

    // e) Список діагнозів пацієнтів, зареєстрованих у системі без повторів
    public List<String> getUniqueDiagnoses() {
        List<String> uniqueDiagnoses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT diagnosis FROM patient")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String diagnosis = rs.getString("diagnosis");
                uniqueDiagnoses.add(diagnosis);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return uniqueDiagnoses;
    }

    // f) Для кожного діагнозу визначити кількість пацієнтів, яким він поставлений
    public Map<String, Integer> getPatientCountByDiagnosis() {
        Map<String, Integer> patientCountByDiagnosis = new LinkedHashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT diagnosis, COUNT(*) as count FROM patient GROUP BY diagnosis")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String diagnosis = rs.getString("diagnosis");
                int count = rs.getInt("count");
                patientCountByDiagnosis.put(diagnosis, count);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientCountByDiagnosis;
    }




}