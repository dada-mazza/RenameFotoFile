/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rename;

import java.io.File;

/**
 *
 * @author dada.mazza
 */
public class test {

    public static void main(String[] args) {

        String path = "h:\\!!!\\iCloud Photos\\";

        File imageFiles = new File(path);

        for (File file : imageFiles.listFiles()) {
            if (file.isFile() && !file.isHidden()) {
                if (file.getName().contains("jpeg")) {

                    String newFileName = new StringBuilder(file.getParent())
                            .append("\\")
                            .append(file.getName().replace("jpeg", ".jpeg"))
                            .toString().toLowerCase();

                    System.out.println("newFileName = " + newFileName);

                    file.renameTo(new File(newFileName));

                }
            }
        }
    }
}
