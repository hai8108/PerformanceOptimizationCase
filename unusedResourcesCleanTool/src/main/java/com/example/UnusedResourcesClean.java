package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnusedResourcesClean {


    /**
     * key: resourceType
     * value: resource file，or resource key name
     */
    static HashMap<String,Set<String>> resMap=new HashMap<>();

    static String Type_drawable ="drawable" ;
    static String Type_layout ="layout" ;
    static String Type_string ="string" ;
    static String Type_color ="color" ;
    static String Type_dimen ="dimen" ;
    static String Type_menu ="menu" ;
    static String Type_anim ="anim" ;

    private  static boolean cleanDrawable=true;
    private  static boolean cleanLayout =true;
    private  static boolean cleanAnim =true;
    private  static boolean cleanMenu =true;
    private  static boolean cleanColor =true;
    private  static boolean cleanString=true;
    private  static boolean cleanDimen=true;


    static String sourceProjectResDir ="/Users/alvin/Documents/workproject/PerformanceOptimizationCase/reduceSizeCase/src/main/res";
    static String lintResultFilePath = "./unusedLint";


    public static void main(String[] args) {

        initUnUsedMap(lintResultFilePath);
        printUnUsedMap();
        clean(sourceProjectResDir);
    }


    /**
     * split  The resource 'R.drawable.btn_bg_top_normal' appears to be unused
     * lint result --> map
     * @param resultFilePath
     */
    private static void initUnUsedMap(String resultFilePath) {

        HashMap<String,Set<String>> resMap=new HashMap<>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(resultFilePath));
            String line = "";
            while ((line = bf.readLine()) != null) { //
                if (line.contains("appears to be unused")) {
                    String resKey= line.split("'")[1];//R.drawable.btn_bg_top_normal
                    System.out.println(resKey);
                    String resType=resKey.split("\\.")[1];
                    String resName=resKey.split("\\.")[2];

                    System.out.println(resType+" , "+resName);
                    if (resMap.get(resType)==null){
                        resMap.put(resType,new HashSet<String>());
                    }
                    Set<String> ids=resMap.get(resType);
                    ids.add(resName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printUnUsedMap() {

        for (Map.Entry<String, Set<String>> entry : resMap.entrySet()) {
            System.out.println("------------------"+entry.getKey()+"------------------");
            for (String s : entry.getValue()) {
                System.out.println(s);
            }
            System.out.println(" ");
        }

    }

    private static void clean(String sourceProjectResDir) {

        File dir=new File(sourceProjectResDir);
        for (File file : dir.listFiles()) {


            // drawable 文件
            if (cleanDrawable) {
                if (file.isDirectory() && file.getName().contains(Type_drawable)) {
                    deleteResFile(Type_drawable, file);
                }
            }

            if (cleanLayout) {
                // layout 文件
                if (file.isDirectory() && file.getName().contains(Type_layout)) {
                    deleteResFile(Type_layout, file);
                }
            }

            if (cleanAnim) {
//            // anim 文件
                if (file.isDirectory() && file.getName().contains(Type_anim)) {
                    deleteResFile(Type_anim, file);
                }
            }

            if (cleanMenu) {
                // menu 文件
                if (file.isDirectory() && file.getName().contains(Type_menu)) {
                    deleteResFile(Type_menu, file);
                }
            }

            if (cleanColor) {
                // color/.xm;文件
                if (file.isDirectory() && file.getName().contains(Type_color)) {
                    deleteResFile(Type_color, file);
                }
            }

            if (cleanString) {
//             strings 文件
                if (file.isDirectory() && file.getName().contains("value")) {

                    for (File file1 : file.listFiles()) {
                        if (file1.getName().contains("strings.xml")) {

                            Utils.resetStringXml(file1, resMap.get(Type_string));

                        }
                    }
                }
            }
            if (cleanDimen){
                //             dimen 文件
                if (file.isDirectory() && file.getName().contains("value")) {

                    for (File file1 : file.listFiles()) {
                        if (file1.getName().contains("dimens.xml")) {

                            Utils.resetDimentXml(file1, resMap.get(Type_dimen));

                        }
                    }

                }
            }
        }

        if (cleanColor) {
            // colors 文件
            File colorXmlFile = new File(sourceProjectResDir + "/values/colors.xml");
            Utils.resetColorXml(colorXmlFile, resMap.get(Type_color));
        }
    }

    /**
     * 从指定资源目录中,删除指定类型的文件
     * @param resourceType
     * @param resDir drawable layout ...
     */
    private static void deleteResFile(String resourceType, File resDir) {

        try{
            Set<String> resSet=resMap.get(resourceType);

            for (File file1 : resDir.listFiles()) {
                if (resSet.contains(file1.getName().split("\\.")[0])){
                    file1.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }





}
