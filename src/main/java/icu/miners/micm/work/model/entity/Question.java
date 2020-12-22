package icu.miners.micm.work.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * 题目
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2020/12/22
 * @see: icu.miners.micm.work.model.entity
 * @version: v1.0.0
 */
@Data
@Entity
@Table(name = "question")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Question extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type; // 题型

    @Column(name = "stem", columnDefinition = "text not null")
    private String stem; // 题干

    @Column(name = "answer", columnDefinition = "text not null")
    private String answer; // 正确答案

    @Column(name = "resolve", columnDefinition = "text not null")
    private String resolve; // 解析

    @Column(name = "score")
    private double score; // 分数(难度系数)

    @Column(name = "options", columnDefinition = "text not null")
    private String options; // 选项

    @Column(name = "subject")
    private String subject; // 科目

    @JoinColumn(name = "organization")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization; // 组织：班级、学院、学校

}
