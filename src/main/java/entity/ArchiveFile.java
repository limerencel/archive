package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveFile {
    private int id;
    private int archiveId;
    private String originName;
    private String filePath;
    private String fileSize;
    private String fileType;
    private LocalDateTime createTime;
}
