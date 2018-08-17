package com.github.pavelhe.model.converters;


import java.io.*;
import java.util.*;
import javax.persistence.*;

import org.springframework.web.multipart.*;

@Converter(autoApply = true)
public class MultipartFileAttributeConverter implements AttributeConverter<MultipartFile, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(MultipartFile file) {
        return getBytesFromFile(file);
    }

    @Override
    public MultipartFile convertToEntityAttribute(byte[] bytes) {
        return getMultipartFileFromBytes(bytes);
    }

    private byte[] getBytesFromFile(MultipartFile file) {
        if (file != null) {
            try {
                return file.getBytes();
            } catch (IOException ex) {
                throw new RuntimeException("Error getting bytes from MultipartFile");
            }
        }
        return null;
    }

    private MultipartFile getMultipartFileFromBytes(byte[] bytes) {
        if (bytes == null)
            return null;
        String randomName = String.valueOf(new Random().nextInt(100000));
        return new SimpleMultipartFile(bytes, randomName, "multipart/*");
    }
}
