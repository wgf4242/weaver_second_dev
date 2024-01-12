package com.engine.demo.demo01.cmd;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import weaver.conn.RecordSet;
import weaver.file.ExcelFile;
import weaver.file.ExcelParse;
import weaver.fna.invoice.utils.HttpUtil;
import weaver.general.Util;
import weaver.hrm.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * @Author      :wyl
 * @Date        :2019/3/22  15:02
 * @Version 1.0 :
 * @Description :保存表单数据
 **/
public class SaveFormDemoCmd extends AbstractCommonCommand<Map<String,Object>> {

    public SaveFormDemoCmd(User user, Map<String, Object> params) {
        this.user = user;
        this.params = params;
    }

    @Override
    public BizLogContext getLogContext() {
        return null;
    }

    @Override
    public Map<String, Object> execute(CommandContext commandContext) {

        Map<String,Object> apidatas  = new HashMap<>();
//        ExcelParse
//        HttpUtil.post()
        ExcelFile excelFile = new ExcelFile();
        String file = excelFile.createFile();
        excelFile.newExcelSheet("St1");
        System.out.println(file);


        if (null == user){
            apidatas.put("hasRight", false);
            return apidatas;
        }
        apidatas.put("hasRight", true);

        /**
         * 获取表单页面参数
         * 这里只取了表单的有一个参数
         */
        String input =  Util.null2String(params.get("input"));

        /**
         * 数据库操作的工具类
         */
        RecordSet recordSet = new RecordSet();

        String sql = "insert into ECOLOGY_PC_DEMO (demo_input) values (?)";
        recordSet.executeUpdate(sql,UUID.randomUUID().toString(),input);

        RecordSet recordSet1 = new RecordSet();
        recordSet1.execute("select * from ECOLOGY_PC_DEMO");
        while (recordSet1.next()) {
            String demo_input = recordSet1.getString("demo_input");
            System.out.println(demo_input);
        }
        recordSet1.execute("insert into ECOLOGY_PC_DEMO values ('aaa')");



        return apidatas;
    }
}
