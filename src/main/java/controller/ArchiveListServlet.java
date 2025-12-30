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

@WebServlet("/archiveList")
public class ArchiveListServlet extends HttpServlet {
    private ArchiveService archiveService = new ArchiveService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置返回的类型以及编码
        resp.setContentType("application/json;charset=utf-8");

        // 向数据库查询有哪些Archive
        List<Archive> archiveList = archiveService.findAllArchives();

        // 转化成JSON字符串返回给前端
        String archiveListJson = JSON.toJSONString(archiveList);
        resp.getWriter().write(archiveListJson);

        System.out.println(archiveListJson);
    }
}
