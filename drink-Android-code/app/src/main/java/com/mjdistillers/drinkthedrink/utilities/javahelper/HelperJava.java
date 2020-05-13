package com.mjdistillers.drinkthedrink.utilities.javahelper;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;


public class HelperJava {

    public String encodeToUTF16(String string){
        Charset charset = Charset.forName("UTF-16");
        try {
            ByteBuffer byteBuffer = charset.newEncoder().encode(CharBuffer.wrap(string));

            StringBuffer buffer = new StringBuffer();
            byte[] bytes = byteBuffer.array();

            for (int i = 0; i < bytes.length; i++){
                buffer.append(bytes[i]);
            }

             return buffer.toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        return "Some Problem Occured";
    }

}
