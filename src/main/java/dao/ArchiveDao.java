package dao;

import entity.Archive;
import utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDao {
    public static List<Archive> findAllArchives() {
        String sql = "SELECT a.*, u.name AS uploader_name FROM archive a LEFT JOIN user u ON a.user_id = u.id;";

        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            List<Archive> archiveList = new ArrayList<>();
            while (rs.next()) {
                Archive archive = new Archive();
                archive.setId(rs.getInt("id"));
                archive.setArchiveCode(rs.getString("archive_code"));
                archive.setTitle(rs.getString("title"));
                archive.setCategory(rs.getString("category"));
                archive.setStatus(rs.getInt("status"));
                archive.setUploader(rs.getString("uploader_name"));
                archive.setCreatedDate(rs.getObject("created_date", LocalDateTime.class));
                archive.setUpdatedDate(rs.getObject("updated_date", LocalDateTime.class));
                archive.setContent(rs.getString("content"));

                archiveList.add(archive);
            }
            return archiveList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Archive findArchiveById(Integer id) {
        String sql = "SELECT a.*, u.name AS uploader_name FROM archive a LEFT JOIN user u ON a.user_id = u.id WHERE a.id = ?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String archiveCode = rs.getString("archive_code");
                String title = rs.getString("title");
                String category = rs.getString("category");
                Integer status = rs.getInt("status");
                String uploader = rs.getString("uploader_name");
                LocalDateTime createdDate = rs.getObject("created_date", LocalDateTime.class);
                LocalDateTime updatedDate = rs.getObject("updated_date", LocalDateTime.class);
                String content = rs.getString("content");

                return new Archive(id, archiveCode, title, category, status, uploader, createdDate, updatedDate, content);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
