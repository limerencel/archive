package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Archive {
    private int id;
    private String archiveCode;
    private String title;
    private String category;
    private Integer status;
    private String uploader;

    private int userId;

    @JSONField(name = "date", format = "yyyy-MM-dd HH:mm:ss") // 前端要date并格式化时间
    private LocalDateTime createdDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    private String content;
}
