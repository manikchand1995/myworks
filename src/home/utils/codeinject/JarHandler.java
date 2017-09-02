//$Id$
package home.utils.codeinject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import javassist.NotFoundException;
public class JarHandler{
	public void replaceJarFile(String jarPathAndName,byte[] fileByteCode,String fileName) throws IOException, NotFoundException {
		if(new File(jarPathAndName).exists()) 
		{     File jarFile = new File(jarPathAndName);
		File tempJarFile = new File(jarPathAndName + ".tmp");
		JarFile jar = new JarFile(jarFile);
		boolean jarWasUpdated = false;

		try {

			JarOutputStream tempJar =
					new JarOutputStream(new FileOutputStream(tempJarFile));

			// Allocate a buffer for reading entry data.

			byte[] buffer = new byte[1024];
			int bytesRead;

			try {
				// Open the given file.

				try {
					// Create a jar entry and add it to the temp jar.

					JarEntry entry = new JarEntry(fileName);
					tempJar.putNextEntry(entry);
					tempJar.write(fileByteCode);

				} catch (Exception ex) {
					System.out.println(ex);

					// Add a stub entry here, so that the jar will close without an
					// exception.

					tempJar.putNextEntry(new JarEntry("stub"));
				}


				// Loop through the jar entries and add them to the temp jar,
				// skipping the entry that was added to the temp jar already.
				InputStream entryStream = null;
				for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements(); ) {
					// Get the next entry.

					JarEntry entry = (JarEntry) entries.nextElement();

					// If the entry has not been added already, so add it.

					if (! entry.getName().equals(fileName)) {
						// Get an input stream for the entry.

						entryStream = jar.getInputStream(entry);
						tempJar.putNextEntry(entry);

						while ((bytesRead = entryStream.read(buffer)) != -1) {
							tempJar.write(buffer, 0, bytesRead);
						}
					}else
					{ System.out.println("Byte code diff injected");}
				}
				if(entryStream!=null)
					entryStream.close();
				jarWasUpdated = true;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				// IMportant so the jar will close without an
				// exception.

				tempJar.putNextEntry(new JarEntry("stub"));
			}
			finally {
				tempJar.close();
			}
		}
		finally {

			jar.close();

			if (! jarWasUpdated) {
				tempJarFile.delete();
			}
		}


		if (jarWasUpdated) {            
			if(jarFile.delete()){
				tempJarFile.renameTo(jarFile);
				System.out.println(jarPathAndName + " updated.");
			}else
				System.out.println("Could Not Delete JAR File");
		}}
		else
		{
			System.out.println("Given Input Jar : "+jarPathAndName + " doesn't exist");
			throw new NotFoundException("Given Input Jar : "+jarPathAndName + " doesn't exist");
		}
	}
	public void createJarFile(String jarPathAndName,String outputJarName, byte[] fileByteCode,String fileName) throws IOException, NotFoundException {
		if(new File(jarPathAndName).exists()) 
		{File jarFile = new File(jarPathAndName);
		File tempJarFile = new File(jarPathAndName + ".tmp");
		JarFile jar = new JarFile(jarFile);
		boolean jarWasUpdated = false;

		try {

			JarOutputStream tempJar =
					new JarOutputStream(new FileOutputStream(tempJarFile));

			// Allocate a buffer for reading entry data.

			byte[] buffer = new byte[1024];
			int bytesRead;

			try {
				// Open the given file.

				try {
					// Create a jar entry and add it to the temp jar.

					JarEntry entry = new JarEntry(fileName);
					tempJar.putNextEntry(entry);
					tempJar.write(fileByteCode);

				} catch (Exception ex) {
					System.out.println(ex);

					// Add a stub entry here, so that the jar will close without an
					// exception.

					tempJar.putNextEntry(new JarEntry("stub"));
				}


				// Loop through the jar entries and add them to the temp jar,
				// skipping the entry that was added to the temp jar already.
				InputStream entryStream = null;
				for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements(); ) {
					// Get the next entry.

					JarEntry entry = (JarEntry) entries.nextElement();

					// If the entry has not been added already, so add it.

					if (! entry.getName().equals(fileName)) {
						// Get an input stream for the entry.

						entryStream = jar.getInputStream(entry);
						tempJar.putNextEntry(entry);

						while ((bytesRead = entryStream.read(buffer)) != -1) {
							tempJar.write(buffer, 0, bytesRead);
						}
					}else
					{ System.out.println("Byte code diff injected");}
				}
				if(entryStream!=null)
					entryStream.close();
				jarWasUpdated = true;
			}
			catch (Exception ex) {
				System.out.println(ex);

				// IMportant so the jar will close without an
				// exception.

				tempJar.putNextEntry(new JarEntry("stub"));
			}
			finally {
				tempJar.close();
			}
		}
		finally {

			jar.close();

			if (! jarWasUpdated) {
				tempJarFile.delete();
			}
		}


		if (jarWasUpdated) {            

			tempJarFile.renameTo(new File(outputJarName));
			System.out.println(outputJarName + " created.");

		}
		}

		else
		{
			System.out.println("Given Input Jar : "+jarPathAndName + " doesn't exist");
			throw new NotFoundException("Given Input Jar : "+jarPathAndName + " doesn't exist");
		}
	}
}