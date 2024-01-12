package com.engine.demo.demo01.web;
import weaver.file.ImageFileManager;
import com.alibaba.fastjson.JSONObject;
import com.engine.common.util.ParamUtil;
import com.engine.common.util.ServiceUtil;
import com.engine.demo.demo01.service.DemoService01;
import com.engine.demo.demo01.service.Impl.DemoServiceImpl01;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * @Author      :wyl
 * @Date        :2019/4/19  11:46
 * @Version 1.0 :
 * @Description :获取纯表单
 **/
public class DemoAction01 {

    private DemoService01 getService(User user) {
        return (DemoServiceImpl01) ServiceUtil.getService(DemoServiceImpl01.class, user);
    }

    /**
     * 获取表单的demo
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/getFormDemo")
    @Produces({MediaType.TEXT_PLAIN})
    public String getFormDemo(@Context HttpServletRequest request, @Context HttpServletResponse response){
        Map<String, Object> apidatas = new HashMap<String, Object>();
        try {
            // test
            Map var1 = request.getParameterMap();
            HashMap var2 = new HashMap();
            Iterator var3 = var1.entrySet().iterator();
            String var5 = "";

            for(Object var6 = null; var3.hasNext(); var2.put(var5, var6)) {
                Map.Entry var4 = (Map.Entry)var3.next();
                var5 = (String)var4.getKey();
                Object var7 = var4.getValue();
                if (null == var7) {
                    var6 = null;
                } else if (var7 instanceof String[]) {
                    String[] var8 = (String[])((String[])var7);
                    if (var8.length == 1) {
                        var6 = var8[0];
                    } else {
                        var6 = var8;
                    }
                } else {
                    var6 = var7.toString();
                }
            }

            //获取当前用户
            User user = HrmUserVarify.getUser(request, response);
            apidatas.putAll(getService(user).getFormDemo(ParamUtil.request2Map(request)));
            apidatas.put("api_status", true);
        } catch (Exception e) {
            e.printStackTrace();
            apidatas.put("api_status", false);
            apidatas.put("api_errormsg", "catch exception : " + e.getMessage());
        }
        return JSONObject.toJSONString(apidatas);
    }

    /**
     * 保存表单到后台，这里只是保存一个值，做了一个示范
     * @param request
     * @param response
     * @return
     */
    @POST
    @Path("/saveFormDemo")
    @Produces({MediaType.TEXT_PLAIN})
    public String saveFormDemo(@Context HttpServletRequest request, @Context HttpServletResponse response){
        Map<String, Object> apidatas = new HashMap<String, Object>();
        try {
//            Map tmpMap = ParamUtil.request2Map(request);
//            Collection<Part> parts = request.getParts();

            int fileId = 3;
            InputStream is = ImageFileManager.getInputStreamById(fileId);
            Reader reader = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(reader);

//          int data; while ((data = inputStream.read()) != -1) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();

            //获取当前用户
            User user = HrmUserVarify.getUser(request, response);
            apidatas.putAll(getService(user).saveFormDemo(ParamUtil.request2Map(request)));
            apidatas.put("api_status", true);
        } catch (Exception e) {
            e.printStackTrace();
            apidatas.put("api_status", false);
            apidatas.put("api_errormsg", "catch exception : " + e.getMessage());
        }
        return JSONObject.toJSONString(apidatas);
    }
}
