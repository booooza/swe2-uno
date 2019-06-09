package ch.swe2.uno.utils;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.toByteArray;

public class CustomClassLoader
		extends ClassLoader {
	CustomClassLoaderListener listener;

	public CustomClassLoader(ClassLoader parent, CustomClassLoaderListener listener) {
		super(parent);
		this.listener = listener;
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		// respect the java.* packages.
		if (name.startsWith("java.")) {
			return super.loadClass(name, resolve);
		} else {
			// see if we have already loaded the class.
			Class<?> c = findLoadedClass(name);
			if (c != null) return c;

			// the class is not loaded yet.  Since the parent class loader has all of the
			// definitions that we need, we can use it as our source for classes.
			InputStream in = null;
			try {
				// get the input stream, throwing ClassNotFound if there is no resource.
				in = getParent().getResourceAsStream(name.replaceAll("\\.", "/") + ".class");
				if (in == null) throw new ClassNotFoundException("Could not find " + name);

				// read all of the bytes and define the class.
				byte[] cBytes = toByteArray(in);
				c = defineClass(name, cBytes, 0, cBytes.length);
				if (resolve) resolveClass(c);
				if (listener != null) listener.classLoaded(c);
				return c;
			} catch (IOException e) {
				throw new ClassNotFoundException("Could not load " + name, e);
			} finally {
				closeQuietly(in);
			}
		}
	}
}