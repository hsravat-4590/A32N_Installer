package com.ravat.hanzalah.a32n_installer.downloader;

import com.ravat.hanzalah.a32n_installer.fs.DirectoryDetect;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Installs Zip versions of the Addon (Based on Github releases). UNTESTED
 */
public class ZipInstaller implements Installer {

    protected String zipPath, communityPath;

    public ZipInstaller(String zipPath){
        this.zipPath = zipPath;
        this.communityPath = DirectoryDetect.detectCommunityDirectory();
    }

    public ZipInstaller(String zipPath, String communityPath){
        this.zipPath = zipPath;
        this.communityPath = communityPath;
    }

    public boolean install() throws IOException {
         return install2();

        /**
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(new File(communityPath), zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        return true;
         */
    }

    public boolean install2(){
        try{
            ZipFile zipFile = new ZipFile(zipPath);
            Enumeration<?> enumeration = zipFile.entries();
            while(enumeration.hasMoreElements()){
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                String name = entry.getName();
                long size = entry.getSize();
                long compressedSize = entry.getCompressedSize();
                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n",
                        name, size, compressedSize);

                File file = new File(communityPath.concat(name));
                if(name.endsWith("/")){
                    file.mkdirs();
                    continue;
                }
                File parent = file.getParentFile();
                if(parent != null){
                    parent.mkdirs();
                }

                InputStream inputStream = zipFile.getInputStream(entry);
                System.out.println(file.getAbsolutePath());
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file,false);
                byte[] bytes = new byte[1024];
                int length;
                while((length = inputStream.read(bytes)) >= 0){
                    fileOutputStream.write(bytes,0,length);
                }
                inputStream.close();
                fileOutputStream.close();
            }
            zipFile.close();
            return true;
        } catch(IOException exception){
            exception.printStackTrace();
            return false;
        }

    }
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}



