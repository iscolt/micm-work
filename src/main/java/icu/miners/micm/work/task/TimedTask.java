package icu.miners.micm.work.task;

import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.repository.EmailTaskRepository;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.utils.EmailUtil;
import icu.miners.micm.work.utils.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
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
@Slf4j
public class TimedTask {

    @Resource
    private EmailTaskRepository emailTaskRepository;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private HomeWorkService homeWorkService;

    private Boolean sendLock = false; // false 没有任务

    @Scheduled(cron = "0/1 * * * * *") //每1秒启动一次
    public void checkEmailTask() {
        if (sendLock) { return; }
        // 查询未发送的邮箱任务
        List<EmailTask> emailTasks = emailTaskRepository.findAllByStatusAndDeletedAndSendDateLessThan((short) 0, false, new Date());
        if(emailTasks.size() == 0) { return; }
        // 批量发送
        sendLock = true;
        emailTasks.forEach(emailTask -> {
            boolean isSuccess = false;
            try {
                String pathname = emailTask.getResource() + emailTask.getTitle() + ".zip";
                if (emailTask.getCategory() == 0) { // 0 打包提交到老师邮箱, 1 作业提交提醒
                    FileOutputStream fos1 = null;

                        fos1 = new FileOutputStream(new File(pathname));

                    ZipUtil.toZip(emailTask.getResource() + "resources", fos1,false);
                    File file = new File(pathname);
                    isSuccess = EmailUtil.complexMail(emailTask, file, mailSender);
                }
                if (emailTask.getCategory() == 1) {
                    isSuccess = EmailUtil.simpleMail(emailTask, mailSender);
                }
                if (isSuccess) {
                    emailTask.setStatus((short)1);
                } else {
                    emailTask.setStatus((short)2);
                }
            } catch (Exception e) {
                sendLock = false;
                emailTask.setStatus((short)2);
                log.error(this.getClass().getName() + ": " + e.getMessage());
            }
        });
        emailTaskRepository.saveAll(emailTasks);
        sendLock = false;
    }

    @Scheduled(cron = "0/1 * * * * *") //每1秒启动一次
    public void checkHomework() {
        long now = new Date().getTime();
        List<HomeWork> homeWork = homeWorkService.listAll();
        if (homeWork.size() == 0) {
            return;
        }
        homeWork.forEach(item -> {
            if (now < item.getBegin().getTime() && item.getStatus() != 0) { // 设为未开始
                item.setStatus((short) 0);
            }

            if (now > item.getBegin().getTime() && item.getStatus() != 1) { // 设为开始
                item.setStatus((short) 1);
            }

            if (now > item.getEnd().getTime() && item.getStatus() != 2) { // 设为结束
                item.setStatus((short) 2);
            }
        });
        homeWorkService.updateInBatch(homeWork);
    }
}
