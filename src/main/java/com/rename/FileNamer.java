/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dada.mazza
 */
public class FileNamer {

    public File getFile(Date date, File oldFile) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String stringDateFile = sdf.format(date);

        String extension = oldFile.getName().substring(oldFile.getName().lastIndexOf(".")).toLowerCase();
        File parent = oldFile.getParentFile();

        // Names already present in the folder, without extension (e.g. "2015.05.21_0001"),
        // so the counter is shared across every file type of the same date.
        Set<String> takenBaseNames = new HashSet<>();
        File[] siblings = parent == null ? null : parent.listFiles();
        if (siblings != null) {
            for (File sibling : siblings) {
                String name = sibling.getName().toLowerCase();
                int dot = name.lastIndexOf(".");
                takenBaseNames.add(dot >= 0 ? name.substring(0, dot) : name);
            }
        }

        int counterDateFile = 1;
        String stringCounterDateFile = "%04d";

        while (true) {
            String baseName = stringDateFile + "_" + String.format(stringCounterDateFile, counterDateFile);
            if (!takenBaseNames.contains(baseName)) {
                return new File(parent, baseName + extension);
            }
            counterDateFile++;
        }

    }

}
