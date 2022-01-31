package com.geekbrains;

import com.geekbrains.exception.MyArrayDataException;
import com.geekbrains.exception.MyArraySizeException;

public class Main {
    public static void main(String[] args) {
        String[][] arr = new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4sdsaf"},
                {"1", "2", "3", "4"}
        };

        try {
            System.out.printf("Сумма всех элементов массива: %d", sum(arr));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.printf("Упала ошибка: %s \nСообщение ошибки: %s", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    private static Integer sum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length != 4) {
            throw new MyArraySizeException("Неверное количество строк");
        }
        for (String[] strings : arr) {
            if (strings.length != 4) {
                throw new MyArraySizeException("Неверное количество столбцов");
            }
        }

        int result = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    result += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Кривая ячейка i: %d, j: %d", i, j), e);
                }
            }
        }

        return result;
    }
}

