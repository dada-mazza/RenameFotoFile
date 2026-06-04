package com.rename;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by zirka on 22.05.2015.
 */
public class RenameMediaFiles implements Renamer {

    Logger logger = Logger.getLogger(getClass());

    @Override
    public void renameFiles(String pathDirectory) {

        File directory = new File(pathDirectory);

        if (!directory.isDirectory()) {
            logger.info("directory does not exist");
            return;
        }

        RenameImages renameImages = new RenameImages();
        RenameVideos renameVideos = new RenameVideos();

        // Collect every media file together with its capture date.
        List<MediaFile> mediaFiles = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile() && !file.isHidden()) {
                Date date = null;
                if (isImage(file)) {
                    logger.info("image");
                    date = renameImages.readDate(file);
                } else if (isVideo(file)) {
                    logger.info("video");
                    date = renameVideos.readDate(file);
                } else {
                    logger.info("other: " + file.getName());
                    continue;
                }

                if (date != null) {
                    mediaFiles.add(new MediaFile(file, date));
                } else {
                    logger.info("no capture date, skipped: " + file.getName());
                }
            }
        }

        // Sort chronologically so the _NNNN counter follows the capture order.
        mediaFiles.sort(Comparator.comparing(MediaFile::getDate));

        for (MediaFile mediaFile : mediaFiles) {
            rename(mediaFile);
        }
    }

    private void rename(MediaFile mediaFile) {
        File newFile = new FileNamer().getFile(mediaFile.getDate(), mediaFile.getFile());
        if (mediaFile.getFile().renameTo(newFile)) {
            logger.info("renamed: " + mediaFile.getFile().getName() + " -> " + newFile.getName());
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

    /** A media file paired with the capture date used for sorting and naming. */
    private static class MediaFile {

        private final File file;
        private final Date date;

        MediaFile(File file, Date date) {
            this.file = file;
            this.date = date;
        }

        File getFile() {
            return file;
        }

        Date getDate() {
            return date;
        }
    }
}
