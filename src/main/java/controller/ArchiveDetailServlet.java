package controller;

import com.alibaba.fastjson.JSON;
import entity.Archive;
import service.ArchiveService;

import javax.jws.WebResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/archiveDetail")
public class ArchiveDetailServlet extends HttpServlet {
    private ArchiveService archiveService = new ArchiveService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 返回JSON
        resp.setContentType("application/json;charset=utf-8");

        // 拿到ID
        String archiveId = req.getParameter("id");

        // 通过ID查找archive
        Archive archive = archiveService.findArchiveById(archiveId);

        // 返回结果
        String archiveStr = JSON.toJSONString(archive);
        resp.getWriter().write(archiveStr);

    }
}
