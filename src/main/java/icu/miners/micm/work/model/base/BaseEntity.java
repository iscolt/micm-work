package icu.miners.micm.work.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/8
 * @see: icu.miners.carte_alimentaire.domain.base
 * @version: v1.0.0
 */
@Data
@ToString
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    /**
     * 更新时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated")
    private Timestamp updated;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Timestamp created;

    /**
     * 删除标志
     */
    @Column(name = "deleted")
    private boolean deleted;

    /**
     * 初始化执行
     */
    @PrePersist
    protected void prePersist() {
        this.deleted = false;
        if (created == null) {
            created = new Timestamp(new Date().getTime());
        }

        if (updated == null) {
            updated = new Timestamp(new Date().getTime());
        }
    }

    /**
     * 更新时执行
     */
    @PreUpdate
    protected void preUpdate() {
        updated = new Timestamp(new Date().getTime());
    }
}
