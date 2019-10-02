package com.ruikun.sys.investment.poiWord;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordExportDemo {
    public static void main(String[] args) throws Exception {
        String filePath = "D:/temp/word/demo.docx";
        String outPath = "D:/temp/word/demo1.docx";
        WordReporter exporter = new WordReporter(filePath);
        exporter.init();
        Map params = Maps.newHashMap();
        params.put("title", "怡美假日旅行社");
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for (int i = 0; i < 30; i++) {
            Map map = Maps.newHashMap();
            map.put("time", "2018" + i);
            map.put("begin", "我在这" + i);
            map.put("end", "你好" + i);
            map.put("content", "加班啊" + i);
            map.put("money", "123");
            list.add(map);
        }
        exporter.export(params);
        exporter.export(list,0);
        exporter.generate(outPath);
    }
}