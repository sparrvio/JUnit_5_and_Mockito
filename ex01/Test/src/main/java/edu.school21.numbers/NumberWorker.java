package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Illegal argument");
        }
        for (int i = 2; i < number / 2 + 1; ++i) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public int digitsSum(Integer number) {
        if (number < 0) {
            return 0;
        }
        int result = 0;
        if (number / 10 > 0) {
            result += number % 10;
            return digitsSum(number / 10) + result;
        }
        return result + number;
    }
}
