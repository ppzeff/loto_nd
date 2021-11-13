package com.ppzeff.loto_nd.service;

// Java code to generate QR code

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyQr {

    // Driver code
    public static void main(String[] args)
            throws WriterException, IOException,
            NotFoundException {
        mainQR();
    }

    public static void mainQR() throws IOException, WriterException {
        // The data that the QR code will contain
        String data = "www.geeksforgeeks.org";
        UUID uuid = UUID.randomUUID();
        data = "https://dehssisfs.herokuapp.com/genqr/".concat(uuid.toString());
        System.out.println(uuid);
        // The path where the image will get saved
        String path = "demo.png";

        // Encoding charset
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.H);

        // Create the QR code and save
        // in the specified folder
        // as a jpg file
        createQR(data, path, charset, hashMap, 500, 500);
        System.out.println("QR Code Generated!!! ");
    }

    // Function to create the QR code
    public static void createQR(String data, String path,
                                String charset, Map hashMap,
                                int height, int width)
            throws WriterException, IOException {
        path = "src/main/resources/upload/".concat(path);

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }


}
