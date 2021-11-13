package com.ppzeff.loto_nd.service;

import com.ppzeff.loto_nd.models.LOTONdModel;
import com.ppzeff.loto_nd.property.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class sendHtmlEmailService {
    @Autowired
    public JavaMailSender emailSender;

    public void sendHtmlEmail(LOTONdModel lotoNdModel, String fotoName) throws MessagingException, IOException {
        Date date = lotoNdModel.getSomeDate();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd-MM-yyyy");

        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("DEHSSISFS<" + MyConstants.MY_EMAIL + ">");
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(MyConstants.EMAIL_1));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(MyConstants.EMAIL_2));
//        message.setRecipient(Message.RecipientType.CC, new InternetAddress(MyConstants.EMAIL_3));
        Address[] addresses = new Address[2];
        addresses[0] = new InternetAddress(MyConstants.EMAIL_1);
        addresses[1] = new InternetAddress(MyConstants.EMAIL_2);

//        addresses[0] = new InternetAddress(MyConstants.EMAIL_3);
//        addresses[1] = new InternetAddress(MyConstants.EMAIL_3);

        message.addRecipients(Message.RecipientType.TO, addresses);
        message.setSubject("Зарегистрировано LOTO разрешение №" + lotoNdModel.getId() + ", " + formatter.format(date));
//        message.setText(lotoNd.toString());

        //Певый кусочек - html
        MimeBodyPart part1 = new MimeBodyPart();
        part1.addHeader("Content-Type", "text/plain; charset=UTF-8");
        String htmlText = "<html><body>" + "\n" +
//                "<p>"+ "  "+ lotoNd.getId()+"</p>"+

                "<p>Зарегистрировано LOTO разрешение № <span style=\"color: #ff0000;\" data-darkreader-inline-color=\"\"><strong>" +
                lotoNdModel.getId() + ", в " + formatter.format(date) + "</strong></span></p>\n" +
                "<p>ФИО LOTO специалиста: <strong>" + lotoNdModel.getSpecialistFIO() + "</strong></p>\n" +
                "<p>Дата начала работ: <strong>" + new SimpleDateFormat("dd-MM-yyyy").format(lotoNdModel.getDataStart()) + "</strong></p>\n" +
                "<p>Комплексная блокировка?: <strong>" + lotoNdModel.isComplexBlocking() + "</strong></p>\n" +
                "<p>Участок проведения работ: <strong>" + lotoNdModel.getWorkSite() + "</strong></p>\n" +
                "<p>Фото титульного листа LOTO разрешения: </p>" +
                "<br><img src=\"cid:" + lotoNdModel.getFotoName() +
                "\"></body></html>";


//                    "<br><img src=\"cid:" + fotoID + ".jpg\"></body></html>";
        part1.setDataHandler(new DataHandler(htmlText, "text/html; charset=\"utf-8\""));

        //Второй кусочек - файл
/*
        MimeBodyPart part2 = new MimeBodyPart();
        part2.setFileName(MimeUtility.encodeWord(file.getName()));
        part2.setDataHandler(new DataHandler(new FileDataSource(file)));
        multipart.addBodyPart(part2);
 */
        // Третий кусочек - вложение фото
        MimeBodyPart imagePart = new MimeBodyPart();
        System.out.println(fotoName);
        imagePart.attachFile(fotoName);
        String cid = lotoNdModel.getFotoName();
        System.out.println("cid: " + cid);
        imagePart.setContentID("<" + cid + ">");
        imagePart.setDisposition(MimeBodyPart.INLINE);


        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(imagePart);
        mimeMultipart.addBodyPart(part1);

        message.setContent(mimeMultipart);

        this.emailSender.send(message);
    }

}
