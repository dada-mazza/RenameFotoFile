package com.rename;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Created by zirka on 22.05.2015.
 */
public class RenameMediaFiles implements Renamer {

    Logger logger = Logger.getLogger(getClass());

    @Override
    public void renameFiles(String pathDirectory) {

        File imageFiles = new File(pathDirectory);
        RenameImages renameImages = new RenameImages();
        RenameVideos renameVideos = new RenameVideos();

        if (!imageFiles.isDirectory()) {
            logger.info("directory does not exist");
            return;
        }

        for (File file : imageFiles.listFiles()) {
            if (file.isFile() && !file.isHidden()) {
                if (isImage(file)) {
                    logger.info("image");
                    renameImages.rename(file);

                } else if (isVideo(file)) {
                    logger.info("video");
                    renameVideos.rename(file);
                } else {
                    logger.info("other");
                }
            }
        }
    }

    private boolean isImage(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".nef")
                || fileName.endsWith(".tiff");
    }

    private boolean isVideo(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp4")
                || fileName.endsWith(".mov")
                || fileName.endsWith(".avi");
    }
}
