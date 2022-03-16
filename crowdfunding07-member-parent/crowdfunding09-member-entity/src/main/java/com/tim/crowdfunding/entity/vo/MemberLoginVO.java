package com.tim.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {
    private static final long serialVersionUID = -8107754612713611008L;
    private Integer id;
    private String username;
    private String email;
}
