package com.yyl.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/21 20:08
 * @Version: 1.0
 */
@Getter
@Setter
public class SysResource implements Serializable {
    private String id;
    private String pid;
    private String name;
    private String code;
    private String uri;
}
