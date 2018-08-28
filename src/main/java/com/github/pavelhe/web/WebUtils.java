package com.github.pavelhe.web;


import java.io.*;

import org.apache.commons.compress.utils.*;
import org.springframework.core.io.*;
import org.springframework.web.multipart.*;

public class WebUtils {

    public static String base64Photo(MultipartFile photo) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(getBytesFromPhotoFile(photo));
    }

    public static String base64Photo(byte[] photo) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(photo);
    }

    public static byte[] getBytesFromPhotoFile(MultipartFile photo) {
        try {
            if (!photoIsPresent(photo)) {
                return defaultPhoto();
            }
            return photo.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error getting bytes from MultipartFile");
        }
    }

    public static byte[] defaultPhoto() {
        ClassPathResource res = new ClassPathResource("/static/img/file-empty-icon.png");
        try {
            return IOUtils.toByteArray(res.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean photoIsPresent(MultipartFile photo) {
        try {
            return photo != null && photo.getBytes().length > 0;
        } catch (IOException e) {
            throw new RuntimeException("Error getting bytes from MultipartFile");
        }
    }

}
