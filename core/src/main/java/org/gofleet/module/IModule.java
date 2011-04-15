package org.gofleet.module;

import java.util.List;

public interface IModule {

	String getTitle();

	boolean isEnabled();

	List<String> getDependencies();
}
