package edu.uwm.cs361.factories;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

public class PersistenceFactory {
	public static PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
