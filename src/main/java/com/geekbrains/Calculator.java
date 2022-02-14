package com.geekbrains;

public class Calculator implements Runnable{

    private float[] arr;

    public Calculator(float[] arr) {
        this.arr = arr;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("1 thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }
}
