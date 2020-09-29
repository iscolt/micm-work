package icu.miners.micm.work.utils;

import java.io.File;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.utils
 * @version: v1.0.0
 */
public class FileUtil {

    /**
     * 用户目录下创建文件夹
     * @param name
     * @return
     */
    public static Boolean createFolderOnUserHome(String name) {
        String path = System.getProperty("user.home") + File.separator + "Documents";
        System.out.println(path); // C:\Users\isColt\Documents
        path += File.separator + name; // separator分割
        File customDir = new File(path);

        if (customDir.exists()) {
            return true;
        }

        return customDir.mkdirs();
    }

    /**
     * 获取用户目录
     * @return
     */
    public static String getUserHomeFolderPath() {
        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator; // separator分割
        return path;
    }
}
