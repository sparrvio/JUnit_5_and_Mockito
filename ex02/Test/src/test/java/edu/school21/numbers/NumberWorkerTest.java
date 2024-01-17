package edu.school21.numbers;

import edu.school21.numbres.IllegalNumberException;
import edu.school21.numbres.NumberWorker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 11, 13, 17, 19})
    void isPrimeForPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 28, 46, 54, 88, 100})
    void isPrimeForNotPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1, -10, -101})
    void isPrimeForIncorrectNumbers(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void isDigitsSum(int input, int expected) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(numberWorker.digitsSum(input), expected);
    }

}