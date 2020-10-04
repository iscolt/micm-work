package icu.miners.micm.work.model.dto;

import lombok.Data;

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
@Data
public class UserInfo {
    private String token;
    private Short role; // 0 学生 1 学委

    public UserInfo(String token, Short role) {
        this.token = token;
        this.role = role;
    }
}
