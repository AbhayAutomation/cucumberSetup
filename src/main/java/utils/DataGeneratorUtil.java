package utils;

import java.util.Random;

import com.github.javafaker.Faker;

public class DataGeneratorUtil {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getRandomAddress() {
        return faker.address().fullAddress();
    }

    public static String getRandomCity() {
        return faker.address().city();
    }

    public static String getRandomPhoneNumber(int min, int max) {
        return String.valueOf(faker.number().numberBetween(min, max));
    }

    public static String getRandomCompany() {
        return faker.company().name();
    }

    public static String getRandomPhoneNumber10Digit() {
        return String.valueOf(6000000000L + random.nextInt(400000000));
    }

    public static String getIndianMobileNumber() {
        int firstDigit = 6 + random.nextInt(4); //6,7,8,9
        long number = (long) (firstDigit + 1_000_000_000L) + random.nextInt(1_000_000_000);
        return String.valueOf(number);
    }

    public static String getFakerPhone() {
        return faker.phoneNumber().cellPhone();
    }
}


