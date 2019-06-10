package ch.swe2.uno.utils;

import java.io.IOException;
import java.io.InputStream;

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
		if (name.startsWith("java.")) {
			return super.loadClass(name, resolve);
		} else {
			Class<?> c = findLoadedClass(name);
			if (c != null) return c;
			try (InputStream in = getParent().getResourceAsStream(name.replaceAll("\\.", "/") + ".class")) {
				if (in == null) throw new ClassNotFoundException("Could not find " + name);
				byte[] cBytes = toByteArray(in);
				c = defineClass(name, cBytes, 0, cBytes.length);
				if (resolve) resolveClass(c);
				if (listener != null) listener.classLoaded(c);
				return c;
			} catch (IOException e) {
				throw new ClassNotFoundException("Could not load " + name, e);
			}
		}
	}
}