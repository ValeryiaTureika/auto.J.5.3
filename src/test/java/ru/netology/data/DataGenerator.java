package ru.netology.data;

import lombok.Value;

public class DataGenerator {

    private DataGenerator() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCardInf() {
        return new CardInfo("5559_0000_0000_0001");
    }

    public static CardInfo getSecondCardInf() {
        return new CardInfo("5559_0000_0000_0002");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }
}

