package com.snow.lib;


import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by SNOW on 2018.02.08.
 */
public class FileUtil {
    /**
     * 返回格式如 2017/12/20/1120157
     *
     * @return
     */
    public static String getDateAsRelativePath(String originName) {
        String fileExtension = FilenameUtils.getExtension(originName);
        return (DateUtil.formatDate(new Date(),
                "yyyy" + File.separator +
                        "MM" + File.separator +
                        "dd" + File.separator +
                        "HH" + File.separator +
                        "mm" + File.separator +
                        "ss") + File.separator +
                UUID.randomUUID() + "." + fileExtension).toLowerCase();
    }
}
