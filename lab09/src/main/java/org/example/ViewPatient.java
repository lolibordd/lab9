package org.example;

import java.util.List;
import java.util.Map;

public class ViewPatient {

    public static void showList(List<Patient> patients) {
        for (int i = 0; i < patients.size(); i++) {
            System.out.println(patients.get(i));
        }
        System.out.println("--------------------");
    }

    public static void showListt(List<String> patients) {
        for (int i = 0; i < patients.size(); i++) {
            System.out.println(patients.get(i));
        }
        System.out.println("--------------------");
    }

    public static void showListtt (Map<String, Integer> patients){
        for (Map.Entry entry : patients.entrySet()) {
            System.out.println(entry.getKey()+" - "+ entry.getValue());
        }
    }

}