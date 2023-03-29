package org.example;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    private PatientServer PatientServer;
    private Scanner sc;
    private ViewPatient ViewPatient;

    public Main() {
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();

    }

    @SneakyThrows
    private void run() {
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("config.properties"))){
            props.load(reader);
            Connection connection = DriverManager.getConnection(props.getProperty("url"), props);
            PatientServer = new PatientServer(connection);
            ViewPatient  = new ViewPatient();
        }

        sc = new Scanner(System.in);
        int m;

        while ((m = menu()) != 0) {
            switch (m) {
                case 1 -> {
                    showAll();
                }
                case 2 -> {
                    findPatientsByDiagnosis();
                }
                case 3 -> {
                    findPatientsByCardNumberRange();
                }
                case 4 -> {
                    findPatientsByPhoneNumberPrefix();
                }
                case 5 -> {
                    getDiagnosisCount();
                }
                case 6 -> {
                    getUniqueDiagnoses();
                }
                case 7 -> {
                    getPatientCountByDiagnosis();
                }
            }
        }
    }

    private int menu () {
        System.out.println("""
                        1. Показати всіх пацієнтів
                        2. a) список пацієнтів, які мають вказаний діагноз в порядку зростання номерів медичної картки
                        3. b) список пацієнтів, номер медичної карти у яких знаходиться в заданому інтервалі
                        4. c) кількість та список пацієнтів, номер телефона яких починається з вказаної цифри
                        5. d) список діагнозів пацієнтів (без повторів) із вказанням кількості пацієнтів, що мають цей діагноз у порядку спадання цієї кількості
                        6. e) список діагнозів пацієнтів, зареєстрованих у системі без повторів
                        7. f) для кожного діагнозу визначити кількість пацієнтів, яким він поставлений
                        0. Exit
                        """
        );
        return Integer.parseInt(sc.nextLine());
    }

    private void showAll () {
        List<Patient> patients = PatientServer.findList();
        ViewPatient.showList(patients);
    }

    private void findPatientsByDiagnosis() {
        System.out.println("Введіть діагноз: ");
        String diagnosis = sc.nextLine();
        List<Patient> patients = PatientServer.findPatientsByDiagnosis(diagnosis);
        ViewPatient.showList(patients);
    }

    private void findPatientsByCardNumberRange() {
        System.out.println("З номеру: ");
        int start = Integer.parseInt(sc.nextLine());
        System.out.println("До: ");
        int end = Integer.parseInt(sc.nextLine());
        List<Patient> patients = PatientServer.findPatientsByCardNumberRange(start, end);
        ViewPatient.showList(patients);
    }

    private void findPatientsByPhoneNumberPrefix() {
        System.out.println("Введіть першу цифру телефону: ");
        String prefix = sc.nextLine();
        List<Patient> patients = PatientServer.findPatientsByPhoneNumberPrefix(prefix);
        ViewPatient.showList(patients);
        int count = PatientServer.countPatientsByPhoneNumberPrefix(prefix);
        System.out.println("Кількість пацієнтів, номер телефона яких починається з цифри " + prefix + ": " + count);
    }

    public void getDiagnosisCount() {
        Map<String, Integer> patients = PatientServer.getDiagnosisCount();
        ViewPatient.showListtt(patients);
    }

    private void getUniqueDiagnoses(){
        List<String> patients = PatientServer.getUniqueDiagnoses();
        ViewPatient.showListt(patients);
    }

    private void getPatientCountByDiagnosis(){
        Map<String, Integer> patients = PatientServer.getPatientCountByDiagnosis();
        ViewPatient.showListtt(patients);

    }

}