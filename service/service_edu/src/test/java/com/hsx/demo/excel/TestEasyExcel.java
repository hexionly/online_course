package com.hsx.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel写操作
        //设置写入文件夹和名称
        String fileName = "F:/writer.xlsx";

        //调用easy excel方法实现写操作
//        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());

        //写操作
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy" + i);
            list.add(demoData);
        }
        return list;
    }
}
