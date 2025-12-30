package DTO;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String emailCode;
    private String password;

    @JSONField(name = "realName") // 给Name起个别名，接受realname反序列化name，反之发送到前端序列化从name到realname
    private String name;

    @JSONField(name = "phone") // 同理
    private String phoneNumber;
}
