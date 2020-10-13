package icu.miners.micm.work.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import icu.miners.micm.work.model.entity.StudentHomeWork;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/4
 * @see: icu.miners.micm.work.model.dto
 * @version: v1.0.0
 */
@Component
public class HomeWorkDTOFactory {

    @Data
    public static class HomeWorkDTO {
        private Integer id;

        private String name;

        private String subject;

        private String description; // 描述

        @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
        private Date begin;

        @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
        private Date end;

        private short subMethod; // 提交方式 0 打包邮箱发送(线上) 1 其他（线下）

        private String subEmail; // 提交邮箱

        private String resource; // 文件url

        private short status; // 0 未开始 1 进行中 2 已结束

        private String subResource; // 提交的附件

        private short subStatus; // 0 未交， 1 提交

        private int subTimes; // 提交次数

        @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
        private Date subDate; // 提交时间(最后一次)
    }

    public static HomeWorkDTO buildDTO(StudentHomeWork studentHomeWork) {
        HomeWorkDTO homeWorkDTO = new HomeWorkDTO();
        homeWorkDTO.setId(studentHomeWork.getHomeWork().getId());
        homeWorkDTO.setName(studentHomeWork.getHomeWork().getName());
        homeWorkDTO.setDescription(studentHomeWork.getHomeWork().getDescription());
        homeWorkDTO.setBegin(studentHomeWork.getHomeWork().getBegin());
        homeWorkDTO.setEnd(studentHomeWork.getHomeWork().getEnd());
        homeWorkDTO.setSubMethod(studentHomeWork.getHomeWork().getSubMethod());
        homeWorkDTO.setSubEmail(studentHomeWork.getHomeWork().getSubEmail());
        homeWorkDTO.setResource(studentHomeWork.getHomeWork().getResource());
        homeWorkDTO.setStatus(studentHomeWork.getHomeWork().getStatus());
        homeWorkDTO.setSubResource(studentHomeWork.getResource());
        homeWorkDTO.setSubStatus(studentHomeWork.getStatus());
        homeWorkDTO.setSubTimes(studentHomeWork.getSubTimes());
        homeWorkDTO.setSubDate(studentHomeWork.getSubDate());
        homeWorkDTO.setSubject(studentHomeWork.getHomeWork().getSubject());
        return homeWorkDTO;
    }
}
