package me.alb_i986.selenium.tinafw.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Composite of {@link PropertyLoader}s.
 */
public class PropertyLoaderComposite extends PropertyLoader {

	private List<PropertyLoader> loaders = new ArrayList<>();

	public PropertyLoaderComposite(PropertyLoader... loaders) {
		this.loaders = Arrays.asList(loaders);
	}

	/**
	 * Loop over the components and
	 * return the first not null value found.
	 */
	@Override
	public String getProperty(String key) {
		for (PropertyLoader loader : loaders) {
			String value = loader.getProperty(key);
			if(value != null)
				return value;
		}
		return null;
	}
	
}
