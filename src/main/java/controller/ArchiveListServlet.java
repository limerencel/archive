package controller;

import com.alibaba.fastjson.JSON;
import entity.Archive;
import service.ArchiveService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/archiveList")
public class ArchiveListServlet extends HttpServlet {
    private ArchiveService archiveService = new ArchiveService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置返回的类型以及编码
        resp.setContentType("application/json;charset=utf-8");

        // 拿到filter参数
        String keyword = req.getParameter("keyword");
        String category = req.getParameter("category");
        String status = req.getParameter("status");
        String uploader = req.getParameter("uploader");

        // 获取所有档案
        List<Archive> archiveList = archiveService.findAllArchives();

        // 使用Stream获取filtered archive
        List<Archive> filteredList = archiveList.stream()
                .filter(archive -> {
                    // Keyword筛选 （title, id）
                    if (keyword != null && !keyword.isEmpty()) {
                        if (!String.valueOf(archive.getId()).contains(keyword) && !archive.getTitle().contains(keyword)) {
                            return false;
                        }
                    }
                    // Category筛选
                    if (category != null && !category.isEmpty()) {
                        if (!archive.getCategory().contains(category)) {
                            return false;
                        }
                    }
                    // Status筛选
                    if (status != null && !status.isEmpty()) {
                        if (!String.valueOf(archive.getStatus()).contains(status)) {
                            return false;
                        }
                    }
                    // uploader筛选
                    if (uploader != null && !uploader.isEmpty()) {
                        if (!archive.getUploader().contains(uploader)) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

        // 过滤后的结果转化成JSON字符串返回给前端
        String archiveListJson = JSON.toJSONString(filteredList);
        resp.getWriter().write(archiveListJson);
    }
}
