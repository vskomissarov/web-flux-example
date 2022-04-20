package ru.filit.backend.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Методы работы с криптографией
 */
public class CryptUtils {

    /**
     * Перевод пароля в MD5
     *
     * @param password пароль
     * @return закриптованный пароль
     */
    public static String cryptWithMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (byte aDigested : digested) {
                /**
                 * Integer.toHexString убирает лидирующие нули, что приводит к генерации некорректного хэша
                 * Правильный вариант:
                 * sb.append(String.format("%02x", (0xff & aDigested)));
                 * Но, поскольку в БД уже есть сгенерированные данные,
                 * используем текущий вариант для совместимости
                 */
                sb.append(Integer.toHexString(0xff & aDigested));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Не найден алгоритм криптографии", ex);
        }
    }
}
