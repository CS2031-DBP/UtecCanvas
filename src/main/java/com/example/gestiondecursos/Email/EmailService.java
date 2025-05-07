package com.example.gestiondecursos.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void assignmentCreated(String to, String name, String lastname, String title, Integer maxScore, String dueDate) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("lastname", lastname);
        context.setVariable("title", title);
        context.setVariable("maxScore", maxScore);
        context.setVariable("dueDate", dueDate);

        String process = templateEngine.process("CORREO.HTML", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setTo(to);
        helper.setText(process, true);
        helper.setSubject("Assignment Created");
        javaMailSender.send(message);
    }

    @Async
    public void userRegister(String to, String name, String lastname, String email, String password) throws MessagingException{
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("lastname", lastname);
        context.setVariable("email", email);
        context.setVariable("password", password);

        String process = templateEngine.process("SignInEmail.html", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setTo(to);
        helper.setText(process, true);
        helper.setSubject("Bienvenido a UTEC++");
        javaMailSender.send(message);
    }

}
