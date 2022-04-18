package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class ImagePacket {

    private String content;
    private String fileName;

    public ImagePacket(File content, String fileName) throws IOException {
        this.fileName = fileName;
        FileInputStream fileInputStream = new FileInputStream(content);
        byte[] byteArr = new byte[(int) content.length()];
        fileInputStream.read(byteArr);
        this.content = new String(Base64.getEncoder().encode(byteArr), StandardCharsets.UTF_8);
    }

    public ImagePacket() {}

    /**
     * This method will return the byte array equivalent of the string containing the serialized image
     * @return the byte array
     */
    public byte[] getByteArr() {
        return Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String extension) {
        this.fileName = extension;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagePacket that = (ImagePacket) o;
        return Objects.equals(content, that.content) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, fileName);
    }
}
