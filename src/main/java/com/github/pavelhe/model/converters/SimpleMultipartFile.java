package com.github.pavelhe.model.converters;


import java.io.*;

import org.springframework.web.multipart.*;

public class SimpleMultipartFile implements MultipartFile {

    private final byte[] fileContent;

    private String fileName;

    private String contentType;

    private File file;

    private FileOutputStream fileOutputStream;

    SimpleMultipartFile(byte[] fileData, String name, String contentType) {
        this.fileContent = fileData;
        this.fileName = name;
        this.contentType = contentType;
        String destPath = System.getProperty("java.io.tmpdir");
        file = new File(destPath + fileName);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.write(fileContent);
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return !(fileContent != null && fileContent.length > 0);
    }

    @Override
    public long getSize() {
        return fileContent != null ? fileContent.length : 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    public void clearOutStreams() throws IOException {
        if (fileOutputStream != null) {
            fileOutputStream.flush();
            fileOutputStream.close();
            file.deleteOnExit();
        }
    }
}
