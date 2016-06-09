package com.plls.os.dedublicated;

import com.plls.os.dedublicated.server.Server;
import com.plls.os.dedublicated.server.data.Chunk;
import com.plls.os.dedublicated.server.data.OSDChunkedObject;
import com.plls.os.dedublicated.server.chunking.OSDObjectChunker;
import com.plls.os.dedublicated.server.meta.SingleDatabaseMetaServer;
import com.plls.os.dedublicated.server.storage.StorageOverLocalDiskBinned;
import com.plls.os.dedublicated.server.storage.StorageOverLocalDiskSimplest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {


    // export PATH=/Users/alexeykhatskevich/tools/apache-maven-3.3.9/bin/:$PATH
    // export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_77.jdk/Contents/Home/

    // mvn clean package shade:shade
    // java -jar target/plls_os_ded_over_s3-1.0-SNAPSHOT.jar save ~/Downloads/ojdbc7.jar2 obj2

    public static void main(String[] args) throws IOException {
        if ( args.length != 3){
            System.out.println("store path name");
            System.out.println("restore name path");
            return;
        }
        if (args[0].equals("store")){
            System.out.println("storeing file...");
            String filename = args[1];
            String name = args[2];
            Path path = Paths.get(filename);
            byte[] data =null;
            if (path != null)
                data = Files.readAllBytes(path);
            System.out.println("Size = " + data.length);
            OSDChunkedObject osdObject = null;
            try {
                osdObject = new OSDChunkedObject(filename, data, new OSDObjectChunker());
                osdObject.name = name;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Server server = null;
            try{
                server = new Server(new SingleDatabaseMetaServer(), new StorageOverLocalDiskBinned());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            try{
                server.saveFile(osdObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (args[0].equals("restore")){
            System.out.println("restoring file...");
            String filename = args[2];
            String name = args[1];
            Server server = null;
            try{
                server = new Server(new SingleDatabaseMetaServer(), new StorageOverLocalDiskBinned());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            OSDChunkedObject obj;
            try{
                obj = server.getFileByName(name);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            try{
                Path path = Paths.get(filename);
                FileChannel file = FileChannel.open( path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                file.position(0);
                ByteBuffer bb = obj.toByteBuffer();
                file.write(bb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
