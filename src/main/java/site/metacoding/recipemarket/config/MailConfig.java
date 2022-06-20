package site.metacoding.recipemarket.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("everygreentime@gmail.com");
        mailSender.setPassword("mpneanfebofqzpib");

        Properties javaMailProperties = new Properties();

        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    // public void send() {
    // Mail mail = new Mail();
    // mail.setMailFrom("from@gmail.com");
    // mail.setMailTo("to@gmail.com");
    // mail.setMailSubject("This is Email test.");
    // mail.setMailContent("Learn how to send email using Spring.");

    // AbstractApplicationContext context = new
    // AnnotationConfigApplicationContext(MailConfig.class);
    // MailService mailService = (MailService) context.getBean("mailService");
    // mailService.sendEmail(mail);
    // context.close();
    // }
}
