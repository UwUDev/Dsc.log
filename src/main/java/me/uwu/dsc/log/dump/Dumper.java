package me.uwu.dsc.log.dump;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Dumper {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void dumpUrl(String url) {
        new Thread(() ->{
            try {
                String extension = url.substring(url.lastIndexOf(".") + 1);

                URL website = new URL(url);
                File outputFolder = new File("dump/" + extension + "/");
                if (!outputFolder.exists()) outputFolder.mkdirs();

                String[] splits = url.split("/");

                File outputFile = new File("dump/" + extension + "/" + splits[splits.length-2] + "." + extension);
                new File(outputFile.getParent()).mkdirs();
                outputFile.createNewFile();

                URLConnection c = website.openConnection();
                c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4705.0 Safari/537.36");
                ReadableByteChannel rbc = Channels.newChannel(c.getInputStream());
                FileOutputStream fos = new FileOutputStream(outputFile);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
