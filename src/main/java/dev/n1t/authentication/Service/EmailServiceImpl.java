package dev.n1t.authentication.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class EmailServiceImpl {
    @Autowired private JavaMailSender emailSender;
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(
                    new InternetAddress(
                            "ninetenbankmail@gmail.com",
                            "NineTen Bank")
            );
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            emailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.printf("An email could not be sent to %s%n", subject);
        }
    }
    public void sendOTPEmail(String to, Integer OTP) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "One-Time Password (OTP)";

            // Generate the HTML content with the OTP
            String htmlContent = "<html><body>"
                    + "<p>Your One-Time Password (OTP) is: " + OTP + "</p>"
                    + "</body></html>";

            helper.setFrom(new InternetAddress("ninetenemailserver@gmail.com", "NineTen Bank"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Set HTML content to true

            emailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.printf("An email could not be sent to %s%n", to);
        }
    }
}