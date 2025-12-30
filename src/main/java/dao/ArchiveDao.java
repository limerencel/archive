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
        String sql = "SELECT * FROM archive";
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
                archive.setUserId(rs.getInt("user_id"));
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
}
