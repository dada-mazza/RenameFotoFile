/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author dada.mazza
 */
public class FileNamer {

    Logger logger = Logger.getLogger(getClass());

    public File getFile(Date date, File oldFile) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String stringDateFile = sdf.format(date);

        int counterDateFile = 1;
        String stringCounterDateFile = "%04d";

        while (true) {
            String newFileName = new StringBuilder(oldFile.getParent())
                    .append("\\")
                    .append(stringDateFile)
                    .append("_")
                    .append(String.format(stringCounterDateFile, counterDateFile))
                    .append(oldFile.getName().substring(oldFile.getName().lastIndexOf(".")))
                    .toString().toLowerCase();

            //logger.info(newFileName);
            File newFile = new File(newFileName);
            if (!newFile.exists()) {                
                return newFile;
            }
            counterDateFile++;
        }

    }

}
