package com.github.aelmod.adadm.core;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class ImageWriteUtil {
    static void writeImageInFileSystem(Image downloadedImage) {
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("J:\\ponypic\\" + downloadedImage.getName()));
            outputStream.write(downloadedImage.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
