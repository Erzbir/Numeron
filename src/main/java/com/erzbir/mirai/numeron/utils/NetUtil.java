package com.erzbir.mirai.numeron.utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/12/1 14:43
 * <p>网络相关工具类</p>
 */
public class NetUtil {
    private static final OkHttpClient client = new OkHttpClient();

    private NetUtil() {

    }

    private static void downloadTo2(String url, File file) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response resp = client.newCall(request).execute();
        new Thread(() -> {
            InputStream inputStream;
            OutputStream fos = null;
            try {

                inputStream = Objects.requireNonNull(resp.body()).byteStream();
                fos = new FileOutputStream(file);
                fos.write(inputStream.readAllBytes());
                fos.flush();
            } catch (IOException e) {
                throw new RuntimeException();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void downloadTo(String url, File file) {
        new Thread(() -> {
            try {
                downloadTo2(url, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static InputStream open(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.newCall(request).execute();
        response.close();
        return Objects.requireNonNull(response.body()).byteStream();
    }

    public static Image getImage(Group group, String url) throws IOException {
        Image image;
        try (InputStream inputStream = open(url)) {
            image = Image.newBuilder(Contact.uploadImage(group, inputStream).getImageId()).build();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return image;
    }
}
