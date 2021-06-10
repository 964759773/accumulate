package com.accumulate.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String password;

    private Integer status;
}
