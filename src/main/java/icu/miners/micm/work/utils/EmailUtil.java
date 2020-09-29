package icu.miners.micm.work.utils;

import icu.miners.micm.work.model.entity.EmailTask;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
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
public class EmailUtil {

    public static boolean simpleMail(EmailTask emailTask, JavaMailSenderImpl mailSender) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();//简单邮件
            //邮件设置
            message.setSubject(emailTask.getTitle());
            message.setText(emailTask.getContent());

            message.setTo(emailTask.getToAddr());
            message.setFrom(emailTask.getFromAddr());
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * 复杂邮件
     * @param emailTask
     * @param file 附件
     * @return
     */
    public static boolean complexMail(EmailTask emailTask, File file, JavaMailSenderImpl mailSender) {
        try {
            //创建一个复杂的消息邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //邮件设置
            helper.setSubject(emailTask.getTitle());
            helper.setText(emailTask.getContent(),true);//开启html格式

            helper.setTo(emailTask.getToAddr());
            helper.setFrom(emailTask.getFromAddr());

            //上传文件
            helper.addAttachment(file.getName(), file);
            mailSender.send(mimeMessage);
            System.out.println("成功");
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
