package com.adrian.cinemakiosk.domain.servise;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Клас для відправлення електронних листів з кодом підтвердження користувачам.
 * Використовує SMTP-сервер для відправки повідомлень.
 */
public class EmailService {

    // Час створення коду підтвердження
    private static LocalDateTime codeCreationTime;

    /**
     * Відправляє електронний лист з кодом підтвердження на зазначену адресу.
     *
     * @param email Адреса електронної пошти отримувача.
     * @param verificationCode Код підтвердження, який буде надіслано.
     */
    public static void sendVerificationCodeEmail(String email, String verificationCode) {
        // Властивості для конфігурації підключення до поштового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Отримання сесії з автентифікацією
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("adriantegza23@gmail.com", "hzgkguskhhzcyxvx");
            }
        });

        try {
            // Створення об'єкта MimeMessage
            Message message = new MimeMessage(session);

            // Встановлення відправника
            message.setFrom(new InternetAddress("from@example.com")); // Замініть на власну адресу

            // Встановлення отримувача
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Встановлення теми
            message.setSubject("Код підтвердження");

            // Встановлення тексту повідомлення
            message.setText("Ваш код підтвердження: " + verificationCode);

            // Відправлення повідомлення
            Transport.send(message);

            System.out.println("Повідомлення успішно відправлено.");

        } catch (MessagingException e) {
            throw new RuntimeException(
                "Помилка при відправці електронного листа: " + e.getMessage());
        }
    }

    /**
     * Генерує та відправляє 6-значний код підтвердження на зазначену електронну пошту.
     *
     * @param email Адреса електронної пошти отримувача.
     * @return Сгенерований код підтвердження.
     */
    public static String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        // Відправка коду підтвердження
        sendVerificationCodeEmail(email, verificationCode);

        // Фіксація часу створення коду
        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }
}
