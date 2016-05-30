package inputs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * I used the Singleton Designed pattern to create this Property class in order to have only one instance
 * in our program. It is able to read from properties files the properties, the room constraints, even objects.
 * @author Aris
 *
 */
// SINGLETON PATERN
public class SingletonProperties {
	private static final String defPropFileName = "default.properties";
	private static SingletonProperties singletonInstance = new SingletonProperties();

	// Private Constructor so that this class can not be instantiated
	private SingletonProperties() {
	}

	// Get the only object available
	public static SingletonProperties getInstance() {
		return singletonInstance;
	}

	/**
	 * Get properties from properties file
	 * 
	 * @param propertyName
	 * @return
	 * @throws IOException
	 */
	public String getProperty(String propertyName) throws IOException {
		InputStream inputStream = null;
		String result = "";
		try {
			java.util.Properties properties = new java.util.Properties();

			inputStream = SingletonProperties.class.getClassLoader().getResourceAsStream(defPropFileName);

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"property file '" + defPropFileName + "' not found in the missing from the classpath");
			}

			result = properties.getProperty(propertyName);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			System.exit(1);
		} finally {
			inputStream.close();
		}
		return result;
	}

	/**
	 * Get room constraints from property file
	 * 
	 * @param room
	 * @return
	 */
	public String[] getRoomConstraints(String room) {
		String[] screens = null;
		try {
			screens = getProperty("rooms." + room).split("____");
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			System.exit(1);
		}
		return screens;
	}

	/**
	 * Get our World from property File
	 * 
	 * @return
	 */
	public Object getWorld() {
		Object result = null;
		try {
			Class<?> controller = Class.forName("world." + getProperty("world"));
			result = controller.newInstance();
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			System.exit(1);
		}
		return result;
	}

}