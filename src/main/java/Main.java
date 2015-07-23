import com.drew.imaging.ImageProcessingException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zirka on 21.05.2015.
 */
public class Main {

    public static void main(String[] args) throws ImageProcessingException, IOException {

        // String path = "d:\\test";

        String path = "D:\\Maksim\\My foto\\!!test01";

        RenameFiles rename = new RenameFiles();

        rename.renameFiles(path);


    }


}

