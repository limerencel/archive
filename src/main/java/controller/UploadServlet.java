package controller;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

@WebServlet("/uploadFile")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private String getSuffix(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        List<String> uploadedUrls = new ArrayList<>();

        try {
            // 获取请求里的所有part
            Collection<Part> parts = req.getParts();
            for (Part part: parts) {
                // 筛选出的确有内容的文件
                if ("files".equals(part.getName()) && part.getSize() > 0) {
                    // 那就保存
                    String fileName = UUID.randomUUID() + getSuffix(part.getSubmittedFileName());
                    part.write("D:/aki/crap/q/" + fileName);

                    uploadedUrls.add("/uploads/"+ fileName);
                }
            }

            // 封装返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("urls", uploadedUrls);
            result.put("message", "上传成功");

            // 转成JSON发送
            resp.getWriter().write(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"success\": false, \"message\": \"上传出错\"}");
        }

    }
}
