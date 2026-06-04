/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rename;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.Logger;


/**
 *
 * @author dada.mazza
 */
public class RenameVideos {

    Logger logger = Logger.getLogger(getClass());
    final private int DIGIT_COUNTER = 4;

    public boolean rename(File file) {

        logger.info(file.getAbsolutePath());

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            // obtain the Exif directory
            ExifSubIFDDirectory esifdd = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (esifdd != null) {
                // query the tag's value
                Date date = esifdd.getDate(ExifSubIFDDirectory.TAG_DATETIME, TimeZone.getDefault());
                if (date != null) {
                    File newFile = new FileNamer().getFile(date, file);
                    if (file.renameTo(newFile)) {
                        logger.info(newFile.getAbsolutePath());
                        logger.info("renamed");
                    };
                }
            }
        } catch (ImageProcessingException | IOException e) {
            //   e.printStackTrace();
            logger.error("File format is not supported: " + e.getMessage());
        }

        return false;
    }
}
