package com.projects.assignments.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ServicePassByValue {

    private int changePrimitive(int x) {
        x = 999;
        return x;
    }

    private int[] modifyArray(int[] arr) {
        arr[0] = 99;
        return arr;
    }

    private StringBuilder reassignReference(StringBuilder sb) {
        sb = new StringBuilder("Goodbye");
        return sb;
    }

    public String demoPrimitive(int value) {
        int original = value;
        int methodResult = changePrimitive(value);
        return "Caller still has: " + original + " | Method changed its copy to: " + methodResult;
    }

    public String demoObjectMutation(int[] arr) {
        String before = Arrays.toString(arr);
        int[] methodResult = modifyArray(arr);
        return "Original was: " + before + " | After method call: " + Arrays.toString(methodResult);
    }

    public String demoReferenceReassignment(String text) {
        StringBuilder sb = new StringBuilder(text);
        StringBuilder methodResult = reassignReference(sb);
        return "Caller still has: " + sb + " | Method reassigned its copy to: " + methodResult;
    }
}
