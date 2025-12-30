package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String number;
    private Integer gender;       // tinyint

    // 指定格式，Fastjson 序列化和反序列化时会自动处理
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDate; // 默认为空即用户还没登陆过

    private Integer status;       // tinyint
    private String bio;
    private String pic;
    private String role;
}
