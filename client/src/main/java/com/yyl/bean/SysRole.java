package com.yyl.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/21 20:06
 * @Version: 1.0
 */
@Getter
@Setter
public class SysRole implements Serializable {
    private String id;
    private String roleName;
    private String roleCode;
    private List<SysResource> resources;
}
