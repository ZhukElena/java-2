package com.geekbrains;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        firstMethod();
        secondMethod();

    }

    public static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Время первого потока: " + (System.currentTimeMillis() - startTime) + " мс.");

    }

    public static void secondMethod() throws InterruptedException {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();

        long startDivideTime = System.currentTimeMillis();
        float[] leftHalf = new float[size / 2];
        float[] rightHalf = new float[size / 2];
        System.arraycopy(arr, 0, leftHalf, 0, size / 2);
        System.arraycopy(arr, size / 2, rightHalf, 0, size / 2);

        System.out.println("время разделения: " + (System.currentTimeMillis() - startDivideTime) + " ms.");
        Thread firstThread = new Thread(new Calculator(leftHalf));
        Thread secondThread = new Thread(new Calculator(rightHalf));
        firstThread.start();
        firstThread.join();
        secondThread.start();
        secondThread.join();

        long startMergeTime = System.currentTimeMillis();
        System.arraycopy(leftHalf, 0, arr, 0, size / 2);
        System.arraycopy(rightHalf, 0, arr, size / 2, size / 2);
        System.out.println("время склейки: " + (System.currentTimeMillis() - startMergeTime) + " ms.");
        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }


}

