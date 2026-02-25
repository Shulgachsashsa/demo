package org.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String mailUsername;

    private final JavaMailSender mailSender;

    public void send(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(mailUsername, "Popytka_by");
            helper.setTo(to);
            helper.setSubject("Подтверждение email");
            helper.setText("Ваш код подтверждения: " + code);
            mailSender.send(message);
            log.info("Sent code from email: {}", to);
        } catch (MessagingException | UnsupportedEncodingException | MailException e) {
            throw new RuntimeException(e);
        }
    }
}
