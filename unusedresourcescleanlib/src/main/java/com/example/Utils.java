package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by alvin on 17/1/14.
 */

public class Utils {

    public static void resetStringXml(File sourceFile, Set<String> unusedSourceKeys) {
        resetXml(sourceFile, unusedSourceKeys, "<string name");
    }

    public static void resetColorXml(File sourceFile, Set<String> unusedSourceKeys) {
        resetXml(sourceFile, unusedSourceKeys, "<color name=");

    }

    public static void resetDimentXml(File sourceFile, Set<String> unusedSourceKeys) {
        resetXml(sourceFile, unusedSourceKeys, "<dimen name=");

    }

    private static void resetXml(File sourceFile, Set<String> unusedSourceKeys, String xmlTypeKey) {
        BufferedWriter bw = null;
        try {
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
