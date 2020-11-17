package icu.miners.micm.work.utils;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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

    public static int getFileSize(File file){
        int size = 0;
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
//            if (fileS < 1024) {
//                size = df.format((double) fileS) + "BT";
//            } else if (fileS < 1048576) {
//                size = df.format((double) fileS / 1024) + "KB";
//            } else if (fileS < 1073741824) {
                size = (int) (fileS / 1048576);
//            } else {
//                size = df.format((double) fileS / 1073741824) +"GB";
//            }
        }else if(file.exists() && file.isDirectory()){
            size = 0;
        }else{
            size = 0;
        }
        return size;
    }

    /**
     * 用户目录下创建文件夹
     * @param path
     * @return
     */
    public static Boolean createFolderOnUserHome(String path) {
        File customDir = new File(path);

        if (customDir.exists()) {
            return true;
        }

        return customDir.mkdirs();
    }

    /**
     * 获取用户目录 /usr/Documents/
     * @return
     */
    public static String getUserHomeFolderPath() {
        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator; // separator分割
        return path;
    }

    /**
     * 根据规则生成文件名
     * @return
     */
    public static String buildFileName(Student student, HomeWork homeWork) {
        Map<String, Object> map = new HashMap<>();
        map.put("number", student.getNumber() == null ? "" : student.getNumber());
        map.put("email", student.getEmail() == null ? "" : student.getEmail());
        map.put("name", student.getName() == null ? "" : student.getName());
        map.put("class", student.getClassName() == null ? "20计科转本01班" : student.getOrganization().getName());
        map.put("subject", homeWork.getSubject() == null ? "其他" : homeWork.getSubject());
        map.put("homework", homeWork.getName());
        map.put("symbol", "_");
        return (String) JexlUtil.executeExpression(buildCode(homeWork.getResourceRule()), map);
    }

    /**
     * 生成文件的拼接代码
     * @param filename 学号_姓名
     * @return number + symbol + name
     */
    public static String buildCode(String filename) {
        String[] strs = filename.split("_");
        String code = "";
        for (int i = 0; i < strs.length; i++) {
            if (i != strs.length-1) {
                // 如果不是最后一个， 后面都要加上 _
                code += caseKey(strs[i]) + " + symbol + ";
            } else {
                code += caseKey(strs[i]);
            }
        }
        return code;
    }

    public static String caseKey(String key) {
        switch (key) {
            case "学号":
                return "number";
            case "邮箱":
                return "email";
            case "姓名":
                return "name";
            case "班级":
                return "class";
            case "科目":
                return "subject";
            case "作业":
                return "homework";
        }
        return null;
    }
}
