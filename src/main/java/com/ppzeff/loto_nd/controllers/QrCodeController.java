package com.ppzeff.loto_nd.controllers;

import com.google.zxing.WriterException;
import com.ppzeff.loto_nd.service.MyQr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class QrCodeController {

    @GetMapping("/genqr")
    public String genqr(Model model) {

        try {
            MyQr.mainQR();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        model.addAttribute("qrimg", "demo.png");
        return "genqr";
    }

    @ResponseBody
    @GetMapping("/genqr/{qr}")
    public String genqrshow( @PathVariable(value = "qr") String qr, Model model) {
        System.out.println(qr);
        model.addAttribute("qr", qr);
        return "QR: "+ qr;
    }
}
