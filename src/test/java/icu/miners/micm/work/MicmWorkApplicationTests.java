package icu.miners.micm.work;
import java.util.Date;
import java.sql.Timestamp;

import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.utils.EmailUtil;
import icu.miners.micm.work.utils.FileUtil;
import icu.miners.micm.work.utils.ZipUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class MicmWorkApplicationTests {

    @Resource
    JavaMailSenderImpl mailSender;

    @Resource
    HomeWorkService homeWorkService;

    @Resource
    StudentService studentService;

//    @Test
//    public void contextLoads() {
//        SimpleMailMessage message = new SimpleMailMessage();//简单邮件
//        //邮件设置
//        message.setSubject("通知>>今晚开房");
//        message.setText("今晚11点,享受papapa>>part!");
//
//        message.setTo("1250074822@qq.com");
//        message.setFrom("1329208516@qq.com");
//        mailSender.send(message);
//        System.out.println("成功");
//    }
//
//    @Test
//    public void test02() throws Exception{
//        //创建一个复杂的消息邮件
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        //邮件设置
//        helper.setSubject("通知>>今晚开房");
//        helper.setText("<b style='color:red'>今晚11点,享受papapa>>part!</b>",true);//开启html格式
//
//        helper.setTo("1329208516@qq.com");
//        helper.setFrom("1329208516@qq.com");
//
//        //上传文件
//        helper.addAttachment("1.jpg",new File("C:\\Users\\isColt\\Pictures\\IMG_20200907_172839.jpg"));
//        mailSender.send(mimeMessage);
//        System.out.println("成功");
//    }
//
//    @Test
//    public void test03() {
//        String path = System.getProperty("user.home") + File.separator + "Documents";
//        System.out.println(path); // C:\Users\isColt\Documents
//        path += File.separator + "Folder Name"; // separator分割
//        File customDir = new File(path);
//
//        if (customDir.exists()) {
//            System.out.println(customDir + " already exists");
//        } else if (customDir.mkdirs()) {
//            System.out.println(customDir + " was created");
//        } else {
//            System.out.println(customDir + " was not created");
//        }
//    }
//
//    @Test
//    public void test04() throws FileNotFoundException, MessagingException {
//        /** 测试压缩方法1  */
//        FileOutputStream fos1 = new FileOutputStream(new File("C:\\Users\\isColt\\Documents\\micm-work\\homework1.zip"));
//        ZipUtil.toZip("C:\\Users\\isColt\\Documents\\micm-work\\homework1", fos1,true);
//        //创建一个复杂的消息邮件
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        //邮件设置
//        helper.setSubject("通知>>今晚开房");
//        helper.setText("<b style='color:red'>今晚11点,享受papapa>>part!</b>",true);//开启html格式
//
//        helper.setTo("1329208516@qq.com");
//        helper.setFrom("1329208516@qq.com");
//
//        //上传文件
//        helper.addAttachment("1.jpg",new File("C:\\Users\\isColt\\Documents\\micm-work\\homework1.zip"));
//        mailSender.send(mimeMessage);
//        System.out.println("成功");
//    }

//    @Test
//    public void test05() {
//        EmailTask emailTask = new EmailTask();
//        emailTask.setFromAddr("1329208516@qq.com");
//        emailTask.setToAddr("1329208516@qq.com");
//        emailTask.setTitle("123");
//        emailTask.setContent("23");
//        emailTask.setResource("12.zip");
//
//        File file = new File("C:\\Users\\isColt\\Documents\\micm-work\\homework1.zip");
//        EmailUtil.complexMail(emailTask, file, mailSender);
//    }

//    @Test
//    public void test06() {
//        Student student = new Student();
//        student.setId(1);
//        student.setNumber("00");
//        student.setPassword(DigestUtils.md5DigestAsHex("njpji00".getBytes()));
//        student.setEmail("1329208516@qq.com");
//        student.setRole((short)1);
//        student.setInit((short)1);
//        studentService.update(student);
//    }

}
