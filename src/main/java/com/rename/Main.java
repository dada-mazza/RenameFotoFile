package com.rename;

/**
 * Created by zirka on 21.05.2015.
 */
public class Main {

    public static void main(String[] args) {

        String path = "e:\\test\\img";

        //String path = "h:\\!!!\\iCloud Photos\\";
        Renamer rename = new RenameMediaFiles();

        rename.renameFiles(path);
    }
}
