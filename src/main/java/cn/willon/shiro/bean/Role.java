package cn.willon.shiro.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Role
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Data
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String permissions;
}
