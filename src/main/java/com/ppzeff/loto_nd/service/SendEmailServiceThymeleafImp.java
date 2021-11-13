package com.ppzeff.loto_nd.service;

import com.ppzeff.loto_nd.models.LOTONdModel;
import com.ppzeff.loto_nd.property.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmailServiceThymeleafImp implements sendEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(LOTONdModel lotoNdModel, String fotoName)  {
        Date date = lotoNdModel.getSomeDate();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

//        String html = getHtmlContent(mail);
        Context context = new Context();
        Map<String, Object> map = new HashMap<>();
        map.put("lotoND", lotoNdModel);
        context.setVariables(map);
        System.out.println(lotoNdModel.getFotoName());

        String html = templateEngine.process("mailTemplate", context);
        helper.setTo(MyConstants.EMAIL_3);
        helper.setFrom("DEHSSISFS<" + MyConstants.MY_EMAIL + ">");
        helper.setSubject("Зарегистрирован LOTO наряд №" + lotoNdModel.getId() + ", " + formatter.format(date));
        helper.setText(html, true);
        System.out.println(lotoNdModel.getFotoName());

//        helper.addAttachment(lotoNd.getFotoName(), new ClassPathResource("upload/"+lotoNd.getFotoName()));
//        helper.addInline(lotoNd.getFotoName(), new ClassPathResource("upload/"+lotoNd.getFotoName()), "image/png");
        helper.addInline(lotoNdModel.getFotoName(), new FileSystemResource(
                new File(fotoName)));

            emailSender.send(message);
            System.out.println(new Date() + " email send");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    private String getHtmlContent(Mail mail) {
//        Context context = new Context();
//        context.setVariables(mail.getHtmlTemplate().getProps());
//        return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
//    }
}