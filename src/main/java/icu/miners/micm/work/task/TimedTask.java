package icu.miners.micm.work.task;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.repository.EmailTaskRepository;
import icu.miners.micm.work.service.EmailTaskService;
import icu.miners.micm.work.utils.EmailUtil;
import icu.miners.micm.work.utils.ZipUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.task
 * @version: v1.0.0
 */
@Configuration
@EnableScheduling
public class TimedTask {

    @Resource
    private EmailTaskRepository emailTaskRepository;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Scheduled(cron = "0/1 * * * * *") //每1秒启动一次
    public void checkEmailTask() {
        // 查询未发送的邮箱任务
        List<EmailTask> emailTasks = emailTaskRepository.findAllByStatusAndSendDateLessThan((short) 0, new Date());
        if(emailTasks.size() == 0) {
            return;
        }
        // 批量发送
        emailTasks.forEach(emailTask -> {
            try {
                String pathname = emailTask.getResource() + File.separator + "20计科转本01班-" + emailTask.getTitle() + ".zip";
                if (emailTask.getCategory() == 0) { // 0 打包提交到老师邮箱, 1 作业提交提醒
                    FileOutputStream fos1 = null;

                        fos1 = new FileOutputStream(new File(pathname));

                    ZipUtil.toZip(emailTask.getResource(), fos1,true);
                    File file = new File(pathname);
                    EmailUtil.complexMail(emailTask, file, mailSender);
                }
                if (emailTask.getCategory() == 1) {
                    EmailUtil.simpleMail(emailTask, mailSender);
                }
                emailTask.setStatus((short)1);
            } catch (Exception e) {
                e.printStackTrace();
                emailTask.setStatus((short)2);
            }
        });
        emailTaskRepository.saveAll(emailTasks);
    }
}
