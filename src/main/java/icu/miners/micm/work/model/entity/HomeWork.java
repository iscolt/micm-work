package icu.miners.micm.work.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "homework")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class HomeWork extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject; // 科目

    @Column(name = "description")
    private String description; // 描述

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "begin")
    private Date begin;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "end")
    private Date end;

    @Column(name = "sub_method")
    private short subMethod; // 提交方式 0 打包邮箱发送(线上) 1 其他（线下）

    @Column(name = "sub_email")
    private String subEmail; // 提交邮箱

    @Column(name = "resource")
    private String resource; // 文件url

    /**
     * TODO 可以改成Json {rule: 学号_邮箱, splitKey: _}
     */
    @Column(name = "resource_rule")
    private String resourceRule; // 文件名规则 学号_邮箱 == number + symbol + email + symbol + subject

    @JoinColumn(name = "organization")
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization; // 组织：班级、学院、学校

    @Column(name = "status")
    private short status; // 0 未开始 1 进行中 2 已结束
}
