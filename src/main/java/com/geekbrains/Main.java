package com.geekbrains;

public class Main {
    public int[] testMethod(int[] arr) {
        int last4Index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                last4Index = i;
            }
        }
        if (last4Index < 0) {
            throw new RuntimeException("В массиве нет четверок");
        }

        int[] res = new int[arr.length - last4Index - 1];

        System.arraycopy(arr, last4Index + 1, res, 0, arr.length - last4Index - 1);

        return res;
    }

    public boolean testBoolMethod(int[] arr) {
        boolean has1 = false;
        boolean has4 = false;
        for (int i : arr) {
            if (i == 1) {
                has1 = true;
            } else if (i == 4) {
                has4 = true;
            } else {
                return false;
            }
        }
        return has1 && has4;
    }
}

