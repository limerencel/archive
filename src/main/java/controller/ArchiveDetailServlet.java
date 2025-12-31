package controller;

import com.alibaba.fastjson.JSON;
import entity.Archive;
import service.ArchiveService;
import utils.GetUserId;

import javax.jws.WebResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/archiveDetail")
public class ArchiveDetailServlet extends HttpServlet {
    private ArchiveService archiveService = new ArchiveService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 返回JSON
        resp.setContentType("application/json;charset=utf-8");

        // 拿到ID
        Integer archiveId = Integer.parseInt(req.getParameter("id"));

        // 通过ID查找archive
        Archive archive = archiveService.findArchiveById(archiveId);

        // 返回结果
        String archiveStr = JSON.toJSONString(archive);
        resp.getWriter().write(archiveStr);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        // 从隐藏域input拿到id，如果为空那就新增档案，如果有值那就是修改模式
        String idStr = req.getParameter("id");

        // 获取用户名并据此查询ID
        String name = req.getParameter("uploader");
        Integer id = GetUserId.findUserId(name); //TODO 用户不存在

        // 封装对象（通用参数）
        Archive archive = new Archive();
        archive.setArchiveCode(req.getParameter("code"));
        archive.setTitle(req.getParameter("title"));
        archive.setCategory(req.getParameter("category"));
        archive.setStatus(Integer.parseInt(req.getParameter("status")));
        archive.setUploader(req.getParameter("uploader"));
        archive.setContent(req.getParameter("description"));
        archive.setUploader(name);
        archive.setUserId(id);

        System.out.println("uploaded url: " + req.getParameter("uploadedUrls"));

        // get current date
        LocalDateTime now = LocalDateTime.now();

        boolean success;
        // 修改模式
        if (idStr != null && !idStr.trim().isEmpty()) {
            archive.setId(Integer.parseInt(idStr));
            archive.setUpdatedDate(now); // 直接set时间进去，dao里get拿出来再写入数据库
            success = archiveService.updateArchive(archive);
        } else {
            // 调用Add方法，不需要setId，数据库会auto increment
            archive.setCreatedDate(now);
            success = archiveService.addArchive(archive);
        }

        // 返回结果
        String msg = success ? "操作成功" : "操作失败";
        resp.getWriter().write("{\"success\": " + success + ", \"message\": \"" + msg + "\"}");
    }
}
