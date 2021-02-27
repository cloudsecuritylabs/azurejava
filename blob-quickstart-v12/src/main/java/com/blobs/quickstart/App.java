package com.blobs.quickstart;

/**
 * Azure blob storage v12 SDK quickstart
 */
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        System.out.println( "Hello World!" );
        String connectStr = "DefaultEndpointsProtocol=https;AccountName=javacodeupload;AccountKey=Eykb9aWWt3r78m/2xz796djwgqMHuDGgMo5cBgzgrutAgVHl4UD9owFut860+kVLTtrCYKYvFRO76xVBCDMQUw==;EndpointSuffix=core.windows.net";

        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();

        //Create a unique name for the container

        String containerName = "quickstartblobs2"; //

        // Create the container and return a container client object
        BlobContainerClient containerClient = blobServiceClient.createBlobContainer(containerName);

        // Create a local file in the ./data/ directory for uploading and downloading
        String localPath = "./data/";
        String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
        File localFile = new File(localPath + fileName);

        // Write text to the file
        FileWriter writer = new FileWriter(localPath + fileName, true);
        writer.write("Hello, World!");
        writer.close();

        // Get a reference to a blob
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the blob
        blobClient.uploadFromFile(localPath + fileName);

        System.out.println("\nListing blobs...");

        // List the blob(s) in the container.
        for (BlobItem blobItem : containerClient.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
        }


        // Download the blob to a local file
        // Append the string "DOWNLOAD" before the .txt extension so that you can see both files.
        String downloadFileName = fileName.replace(".txt", "DOWNLOAD.txt");
        File downloadedFile = new File(localPath + downloadFileName);

        System.out.println("\nDownloading blob to\n\t " + localPath + downloadFileName);

        blobClient.downloadToFile(localPath + downloadFileName);

        // Clean up
        System.out.println("\nPress the Enter key to begin clean up");
        System.console().readLine();

        System.out.println("Deleting blob container...");
        containerClient.delete();

        System.out.println("Deleting the local source and downloaded files...");
        localFile.delete();
        downloadedFile.delete();

        System.out.println("Done");


    }
}
