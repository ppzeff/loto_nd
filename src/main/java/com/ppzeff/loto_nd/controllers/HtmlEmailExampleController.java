package com.ppzeff.loto_nd.controllers;

import javax.mail.MessagingException;

import com.ppzeff.loto_nd.service.sendHtmlEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HtmlEmailExampleController {

    @Autowired
    private sendHtmlEmailService emailSender;

    @ResponseBody
    @RequestMapping("/sendHtmlEmail")
    public String sendHtmlEmail() throws MessagingException {
//        emailSender.sendHtmlEmail();

//        MimeMessage message = emailSender.createMimeMessage();
//
//        boolean multipart = true;
//
//        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
//
//        String htmlMsg = "<h3>Im testing send a HTML email</h3>"
//                +"<img src='http://www.apache.org/images/asf_logo_wide.gif'>";
//
//        message.setContent(htmlMsg, "text/html");
//
//        helper.setFrom("DEHSSISFS<"+MyConstants.MY_EMAIL+">");
//
//        helper.setTo(MyConstants.FRIEND_EMAIL);
//
//        helper.setSubject("Test send HTML email");
//
//
//        this.emailSender.send(message);

        return "Email Sent!";
    }

}