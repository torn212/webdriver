package com.example.webdriver.test;

import com.alibaba.fastjson.JSON;
import com.example.webdriver.vo.Ssq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * webDriver dom元素定位
 */
@Slf4j
public class Anjuke4 {
    public static Set<String> allKey = new HashSet<>();
    public static List<Ssq> allCity = new ArrayList<>();


    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void writeExcel(String targetFile) {
        String finalXlsxPath = targetFile;

        OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            System.out.println("删除行数：" + rowNumber);
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */

            for (int i = 0; i < allCity.size(); i++) {
                Ssq ssq = allCity.get(i);
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(ssq.getSheng());
                row.createCell(1).setCellValue(ssq.getShi());
                row.createCell(2).setCellValue(ssq.getQu());

                AtomicInteger atomicInteger = new AtomicInteger(2);
                for (int j = 2011; j <= 2020; j++) {
                    for (int k = 1; k <= 12; k++) {
                        String format = new DecimalFormat("00").format(k);
                        String s = ssq.getPrice().get(j + format);
                        if (StringUtils.isBlank(s)) {
                            s = "";
                        }
                        row.createCell(atomicInteger.incrementAndGet()).setCellValue(s);
                    }
                }
                row.createCell(atomicInteger.incrementAndGet()).setCellValue(ssq.getShiUrl());
                row.createCell(atomicInteger.incrementAndGet()).setCellValue(ssq.getQuUrl());
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功" + allCity.size() + "行。");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public static void init(String jsonFile) throws IOException {
        if (allKey.size() <= 0) {
            LineIterator lineIterator = FileUtils.lineIterator(new File(jsonFile));
            while (lineIterator.hasNext()) {
                String s = lineIterator.nextLine();
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                Ssq ssq = JSON.parseObject(s, Ssq.class);
                if (ssq == null) {
                    continue;
                }
                allKey.add(ssq.getSheng() + ssq.getShi() + ssq.getQu());
                allCity.add(ssq);
            }
            System.out.println("init city finish");
        }
    }


}
