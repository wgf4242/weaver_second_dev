// 直接运行 不需要启 Resin
package com.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import weaver.file.*;
import weaver.hrm.User;
import weaver.general.GCONST;
import weaver.general.Util;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestExcel {
    public static void main(String[] args) {
        GCONST.setRootPath("D:\\temp\\weaver_test\\"); // 需要在下面添加个 exceltmp
//        createFile();
//        writeFile();
        readExcel();
    }

    private static void readExcel() {
        ExcelParse file = new ExcelParse();
        file.init("D:\\temp\\weaver_test\\exceltmp\\1704940766268797\\report03.xls");

        // line1 , (sheet, row, column)
        String cellA1 = Util.null2String( file.getValue("1", ""+1 , "1" ) ).trim() ;
        System.out.println(cellA1);
        String cellA2 = Util.null2String( file.getValue("1", ""+2 , "1" ) ).trim() ;
        System.out.println(cellA2);
        String cellB1 = Util.null2String( file.getValue("1", ""+1 , "2" ) ).trim() ;
        System.out.println(cellB1);
    }

    private static void writeFile() {
        // 这个保存的是2007格式
        ExcelFile file = new ExcelFile();
        file.next(); // 修改 index 指向下一 sheet

        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("St1");
        sheet.initRowList(1);

        ExcelRow row = sheet.newExcelRow(0);
        row.addStringValue("test2");

        file.addSheet("St1", sheet);

        // set is newMode
        ConcurrentHashMap var3 = new ConcurrentHashMap();
        SXSSFWorkbook var4 = new SXSSFWorkbook(10);
        var3.put("count", 0);
        var3.put("SXSSFWorkbook", var4);
        file.threadLocal.set(var3);

        file.createFile();
    }

    private static void createFile() {
        ExcelFile excelFile = new ExcelFile();
        String file = excelFile.createFile();
        System.out.println(file);
    }
}
