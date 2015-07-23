import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Created by zirka on 22.05.2015.
 */
public class RenameFiles {

    Logger logger = Logger.getLogger(RenameFiles.class);

    final private int DIGIT_COUNTER = 4;

    private Metadata metadata;
    private ExifSubIFDDirectory directory;
    private Date date;
    // private Map<String, Integer> mapDayFile;
    private String stringDateFile;
    private int counterDateFile;

    RenameFiles() {
        // mapDayFile = new HashMap<String, Integer>();
    }

    public void renameFiles(String pathDirectory) throws IOException {

        File imageFiles = new File(pathDirectory);

        if (!imageFiles.isDirectory()) {
            logger.info("directory does not exist");
            return;
        }

        for (File file : imageFiles.listFiles()) {
            if (file.isFile() && !file.isHidden()) {

                // logger.info(file.getName());
                logger.info(file.getAbsolutePath());

                try {
                    metadata = ImageMetadataReader.readMetadata(file);
                } catch (ImageProcessingException e) {
                    //   e.printStackTrace();
                    logger.error("File format is not supported");
                    continue;
                }

                /*
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        System.out.println(tag);
                    }
                }
                */

                // obtain the Exif directory
                directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

                if (directory != null) {

                    // query the tag's value
                    date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);

                    if (date != null) {
                        stringDateFile = new StringBuilder()
                                .append(String.format("%04d", date.getYear() + 1900))
                                .append(".")
                                .append(String.format("%02d", date.getMonth() + 1))
                                .append(".")
                                .append(String.format("%02d", date.getDate()))
                                .toString();


                      /*  if (mapDayFile.containsKey(stringDateFile)) {
                            counterDateFile = mapDayFile.get(stringDateFile) + 1;
                        } else {
                            counterDateFile = 1;
                        }*/

                        counterDateFile = 1;
                        StringBuilder stringCounterDateFile = new StringBuilder()
                                .append("%0")
                                .append(DIGIT_COUNTER)
                                .append("d");



                        while (true) {
                            String newFileName = new StringBuilder(file.getParent())
                                    .append("\\")
                                    .append(stringDateFile)
                                    .append("_")
                                    .append(String.format(stringCounterDateFile.toString(), counterDateFile))
                                    .append(file.getName().substring(file.getName().length() - 4).toLowerCase())
                                    .toString();

                            //logger.info(newFileName);

                            if (file.renameTo(new File(newFileName))) {
                                logger.info(newFileName);
                                logger.info(true);
                                break;
                            }

                            counterDateFile++;
                        }

                       /* mapDayFile.put(stringDateFile, counterDateFile);*/
                    }
                }
            }
        }
    }
}

