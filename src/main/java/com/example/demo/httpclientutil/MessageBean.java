package com.example.demo.httpclientutil;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用于xxx服务的消息对象
 *
 * @author xyh
 * @date 2021-01-02 11:01 上午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBean {

    @NotNull
    private String name;
    @NotNull
    private Integer age;

}
