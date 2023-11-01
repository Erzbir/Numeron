package com.erzbir.numeron.utils;

import java.io.*;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class SerializeUtil {
    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(byteOutStream)) {
            oos.writeObject(obj);
            oos.close();
            return byteOutStream.toByteArray();
        } catch (Exception e) {
            NumeronLogUtil.err(e.getMessage());
        }
        return null;
    }

    public static Object deserialization(byte[] bytes) {
        ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(byteInStream)) {
            return ois.readObject();
        } catch (Exception e) {
            NumeronLogUtil.err(e.getMessage());
        }
        return null;
    }

    public static Object deserialization(InputStream inputStream) {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return ois.readObject();
        } catch (Exception e) {
            NumeronLogUtil.err(e.getMessage());
        }
        return null;
    }
}
