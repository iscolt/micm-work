package icu.miners.micm.work.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import icu.miners.micm.work.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.model.entity
 * @version: v1.0.0
 */
@Data
@Entity
@Table(name = "email_task")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class EmailTask extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_addr")
    private String fromAddr;

    @Column(name = "to_addr")
    private String toAddr;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private short category; // 0 打包提交到老师邮箱, 1 作业提交提醒

    @Column(name = "resource")
    private String resource;

    @Column(name = "send_date")
    private Date sendDate;

    @JoinColumn(name = "organization")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization; // 组织：班级、学院、学校

    @Column(name = "status")
    private Short status; // 0 未发送 1 已发送 2 发送失败

    @JoinColumn(name = "homework")
    @ManyToOne(fetch = FetchType.LAZY)
    private HomeWork homeWork;
}
