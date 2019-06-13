package com.graph.demo.app;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @ClassName User
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/6/5 9:04
 **/
@Data
public class User {
    private int id;
    private String name;
    private String comment;

}
