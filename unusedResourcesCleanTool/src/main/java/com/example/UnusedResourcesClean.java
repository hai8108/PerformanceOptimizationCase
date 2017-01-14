package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnusedResourcesClean {


    /**
     * key: resourceType
     * value: resource file，or resource key name
     */
    static HashMap<String, Set<String>> resMap = new HashMap<>();

    static String Type_drawable = "drawable";
    static String Type_layout = "layout";
    static String Type_string = "string";
    static String Type_color = "color";
    static String Type_dimen = "dimen";
    static String Type_menu = "menu";
    static String Type_anim = "anim";

    private static boolean cleanDrawable = true;
    private static boolean cleanLayout = true;
    private static boolean cleanAnim = true;
    private static boolean cleanMenu = true;
    private static boolean cleanColor = true;
    private static boolean cleanString = true;
    private static boolean cleanDimen = true;


    static String sourceProjectResDir = "/Users/alvin/Documents/workproject/PerformanceOptimizationCase/reduceSizeCase/src/main/res";
    static String lintResultFilePath = "unusedResourcesCleanTool/unusedLint";


    public static void main(String[] args) {

        try {
            resMap = initUnUsedMap(lintResultFilePath);
            if (resMap == null || resMap.isEmpty()) {
                return;
            }
            printUnUsedMap();
            clean(sourceProjectResDir);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * split  The resource 'R.drawable.btn_bg_top_normal' appears to be unused
     * lint result --> map
     *
     * @param resultFilePath
     */
    private static HashMap<String, Set<String>> initUnUsedMap(String resultFilePath) throws Exception {

        HashMap<String, Set<String>> resMap = new HashMap<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(resultFilePath));
            String line = "";
            while ((line = bf.readLine()) != null) { //
                if (line.contains("appears to be unused")) {
                    String resKey = line.split("'")[1];//R.drawable.btn_bg_top_normal
                    System.out.println(resKey);
                    String resType = resKey.split("\\.")[1];
                    String resName = resKey.split("\\.")[2];

                    System.out.println(resType + " , " + resName);
                    if (resMap.get(resType) == null) {
                        resMap.put(resType, new HashSet<String>());
                    }
                    Set<String> ids = resMap.get(resType);
                    ids.add(resName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resMap;


    }

    private static void printUnUsedMap() {

        for (Map.Entry<String, Set<String>> entry : resMap.entrySet()) {
            System.out.println("------------------" + entry.getKey() + "------------------");
            for (String s : entry.getValue()) {
                System.out.println(s);
            }
            System.out.println(" ");
        }

    }

    private static void clean(String sourceProjectResDir) {

        Map<String, Boolean> deletionFileMap = new HashMap<>();
        deletionFileMap.put(Type_drawable, cleanDrawable);
        deletionFileMap.put(Type_layout, cleanLayout);
        deletionFileMap.put(Type_anim, cleanAnim);
        deletionFileMap.put(Type_menu, cleanMenu);
        deletionFileMap.put(Type_color, cleanColor);

        Map<String, Boolean> deletionLineMap = new HashMap<>();
        deletionLineMap.put(Type_string, cleanString);
        deletionLineMap.put(Type_dimen, cleanDimen);
        deletionLineMap.put(Type_color, cleanColor);

        Map<String, String> lineKeyMap = new HashMap<>();
        lineKeyMap.put(Type_string, "<string name");
        lineKeyMap.put(Type_dimen, "<dimen name=");
        lineKeyMap.put(Type_color, "<color name=");

        File dir = new File(sourceProjectResDir);
        for (File file : dir.listFiles()) {
            if (!file.isDirectory()) {
                continue;
            }


            if (file.getName().contains("value")) {
                for (File file1 : file.listFiles()) {
                    for (Map.Entry<String, Boolean> entry : deletionLineMap.entrySet()) {
                        String type = entry.getKey();
                        boolean clean = entry.getValue();
                        if (file1.getName().contains(type) && clean) {
                            resetXml(file1, resMap.get(type), lineKeyMap.get(type));
                        }
                    }
                }
            } else {
                for (Map.Entry<String, Boolean> entry : deletionFileMap.entrySet()) {

                    if (file.getName().contains(entry.getKey()) && entry.getValue()) {
                        deleteResFile(entry.getKey(), file);
                    }
                }
            }
        }
    }

    /**
     * 从指定资源目录中,删除指定类型的文件
     *
     * @param resourceType
     * @param resDir       drawable layout ...
     */
    private static void deleteResFile(String resourceType, File resDir) {

        try {
            Set<String> resSet = resMap.get(resourceType);

            for (File file1 : resDir.listFiles()) {
                if (resSet.contains(file1.getName().split("\\.")[0])) {
                    file1.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void resetXml(File sourceFile, Set<String> unusedSourceKeys, String xmlTypeKey) {
        BufferedWriter bw = null;
        try {
            if (unusedSourceKeys==null&&unusedSourceKeys.isEmpty()){
                return;
            }
            String outFilename = sourceFile.getAbsolutePath() + "_temp";
            File outFile = new File(outFilename);
            BufferedReader bf = new BufferedReader(new FileReader(sourceFile));
            bw = new BufferedWriter(new FileWriter(outFilename));

            String line = "";// <string name="app_name">App Backup &amp; Restore</string>

            while ((line = bf.readLine()) != null) {

                if (line.contains(xmlTypeKey) && unusedSourceKeys.contains(line.split("\"")[1])) {
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
            sourceFile.delete();
            outFile.renameTo(new File(sourceFile.getAbsolutePath()));

        } catch (Exception e) {

        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
