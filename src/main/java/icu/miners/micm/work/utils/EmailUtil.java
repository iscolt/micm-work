package icu.miners.micm.work.utils;

import icu.miners.micm.work.model.entity.EmailTask;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.error("simpleMail: " + e.getMessage());
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
            System.setProperty("mail.mime.splitlongparameters","false");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //邮件设置
            helper.setSubject(emailTask.getTitle());
            if (emailTask.getContent() == null) {
                helper.setText("作业上交【由<a href='http://work.iscolt.com' target='_blank'>系统</a>发送】",true);//开启html格式
            } else {
                helper.setText(emailTask.getContent(),true);//开启html格式
            }

            helper.setTo(emailTask.getToAddr());
            helper.setFrom(emailTask.getFromAddr());

            //上传文件
            helper.addAttachment(file.getName(), file);
            mailSender.send(mimeMessage);
            System.out.println("成功发送邮件，文件名：" + file.getName());
        } catch (Exception e) {
            log.error("complexMail: " + e.getMessage());
            return false;
        }
        return true;
    }
}
