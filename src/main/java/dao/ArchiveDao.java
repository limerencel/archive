package dao;

import entity.Archive;
import utils.DataSourceUtil;
import utils.GetUserId;

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

    public static Archive findArchiveById(int id) {
        String sql = "SELECT a.*, u.name AS uploader_name FROM archive a LEFT JOIN user u ON a.user_id = u.id WHERE a.id = ?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String archiveCode = rs.getString("archive_code");
                String title = rs.getString("title");
                String category = rs.getString("category");
                Integer status = rs.getInt("status");
                String uploader = rs.getString("uploader_name");
                LocalDateTime createdDate = rs.getObject("created_date", LocalDateTime.class);
                LocalDateTime updatedDate = rs.getObject("updated_date", LocalDateTime.class);
                String content = rs.getString("content");

                return new Archive(id, archiveCode, title, category, status, uploader,userId, createdDate, updatedDate, content);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean updateArchive(Archive archive) {
        String sql = "UPDATE archive SET archive_code=?, title=?, category=?, status=?, updated_date=?, content=? WHERE id=?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, archive.getArchiveCode());
            ps.setString(2, archive.getTitle());
            ps.setString(3, archive.getCategory());
            ps.setInt(4, archive.getStatus());
            ps.setObject(5, archive.getUpdatedDate());
            ps.setString(6, archive.getContent());
            ps.setInt(7, archive.getId());

            int rows = ps.executeUpdate(); //该方法如果返回1那就是找到了id对应的那一行并且更新成功，如果是0就没有找到这个id，如果报错是SQL或者数据库链接的问题

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addArchive(Archive archive) {
        String sql = "INSERT INTO archive (archive_code, title, category, status, user_id, created_date, updated_date, content) VALUES(?,?,?,?,?,?,null,?)";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, archive.getArchiveCode());
            ps.setString(2, archive.getTitle());
            ps.setString(3, archive.getCategory());
            ps.setInt(4, archive.getStatus());
            //TODO handle userId
            ps.setInt(5, archive.getUserId());
            ps.setObject(6, archive.getCreatedDate());
            ps.setObject(7, archive.getContent());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteById(Integer id) {
        String sql = "DELETE FROM archive WHERE id = ?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer findUserId(String username) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}