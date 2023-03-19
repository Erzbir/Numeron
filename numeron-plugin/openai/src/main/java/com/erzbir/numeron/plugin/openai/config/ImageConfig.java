package com.erzbir.numeron.plugin.openai.config;

import com.erzbir.numeron.core.utils.ConfigReadException;
import com.erzbir.numeron.core.utils.ConfigWriteException;
import com.erzbir.numeron.core.utils.JsonUtil;
import com.theokanning.openai.image.CreateImageRequest;

import java.io.File;
import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:53
 */
public class ImageConfig implements Serializable {
    private static final Object key = new Object();
    private static volatile ImageConfig INSTANCE;
    private static final String configFile = "erzbirnumeron/plugin-configs/chatgpt/image.json";
    private int n = 1;
    private String size = ImageSize.LARGE;
    private String format = "b64_json";
    private String folder = "";
    private boolean save = false;

    private ImageConfig() {

    }

    public static ImageConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, ImageConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            INSTANCE = new ImageConfig();
            try {
                JsonUtil.dump(configFile, INSTANCE, ImageConfig.class);
            } catch (ConfigWriteException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
        //return new ImageConfig();
    }

    public CreateImageRequest load() {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return CreateImageRequest.builder().n(n)
                .size(String.valueOf(size))
                .responseFormat(format)
                .prompt("")
                .build();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}
