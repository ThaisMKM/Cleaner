/**
 * Filter file names by its prefix
 */
package com.thaisExample.folderCleaner;

import java.io.File;
import java.io.FilenameFilter;

class NameFilter implements FilenameFilter {

	private String str;

	public NameFilter(String str) {
		this.str = str;
	}

	@Override
	public boolean accept(File dir, String name) {
		return (name.startsWith(str));
	}
}
