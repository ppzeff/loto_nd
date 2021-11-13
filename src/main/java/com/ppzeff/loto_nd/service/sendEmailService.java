package com.ppzeff.loto_nd.service;

import com.ppzeff.loto_nd.models.LOTONdModel;

import javax.mail.MessagingException;
import java.io.IOException;

public interface sendEmailService {
    void sendEmail(LOTONdModel lotoNdModel, String photoName) ;
}
