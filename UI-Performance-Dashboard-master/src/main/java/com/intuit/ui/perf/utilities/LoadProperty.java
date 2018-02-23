package com.intuit.ui.perf.utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;

import org.junit.Assert;

/**
 * The methods in this file could be static, but that would require constant
 * checking to determine if the properties have been loaded or each test
 * ensuring that the initialization happened before any of the calls.  Neither
 * seemed like a good solution, so instead they were just not made static so
 * that the constructor executes the check.
 */
public class LoadProperty {
	String isLoadedName = this.getClass().getName() + ".loaded";

	public LoadProperty() {
		// only load once, even if multiple tests are executed
		if (!getProperty(this, isLoadedName, "false").equals("true")) {
			String file = System.getProperty("qc.property.file", "");
			Assert.assertNotEquals("", file);
			loadPropteryFile(file);
		}
	}

	private void loadPropteryFile(final String fileName) {
		Properties properties = new Properties(System.getProperties());
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			properties.load(is);
			properties.setProperty(isLoadedName, "true");
			System.setProperties(properties);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}
	}

	public String getProperty(Object obj, final String name, final String defaultValue) {
		String className = obj.getClass().getName();
		String value = System.getProperty(className + "." + name);
		if (value == null || "".equals(value.trim())) {
			value = System.getProperty(name);
			if (value == null || "".equals(value.trim())) {
				return defaultValue;
			}
		}
		return value;
	}

	public String getPropertyNonEmpty(Object obj, final String name) {
		String value = getProperty(obj, name, "");
		if (value == null || "".equals(value.trim())) {
			Assert.fail("Property " + name + " must not be empty");
		}
		return value;
	}

	public File getFile(Object obj, final String name, final String defaultValue) {
		final String slash = File.separator;
		final String base = getProperty(obj, "base.filename", "");
		final String value = getProperty(obj, name, base + slash + defaultValue);
		final String className = obj.getClass().getName();
		String relativeFromClass = className.replaceAll("\\.", Matcher.quoteReplacement(slash));
		relativeFromClass = relativeFromClass.replaceAll("\\$.*", "");
		relativeFromClass = relativeFromClass.substring(0, relativeFromClass.lastIndexOf(slash));
		relativeFromClass = "src" + slash + "test" + slash + "java" + slash + relativeFromClass + slash + "files";
		final String relative = base + slash + relativeFromClass + slash + defaultValue;
		final String[] filenames = { relative, base + slash + value, value, defaultValue};
		for (String cur : filenames) {
			File rc = new File(cur);
			if (rc.exists() && !rc.isDirectory()) {
				return rc;
			}
		}
		Assert.fail("Property " + name + " did not map to a file for" + filenames.toString());
		return null;
	}
}
