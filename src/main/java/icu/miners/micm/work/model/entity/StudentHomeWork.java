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
@Table(name = "student_homework")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class StudentHomeWork extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "student")
    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    @JoinColumn(name = "homework")
    @ManyToOne(fetch = FetchType.EAGER)
    private HomeWork homeWork;

    @Column(name = "status")
    private short status; // 0 未交， 1 提交

    @Column(name = "resource")
    private String resource; // 附件

    @Column(name = "sub_times")
    private int subTimes; // 提交次数

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "sub_date")
    private Date subDate; // 提交时间(最后一次)
}
