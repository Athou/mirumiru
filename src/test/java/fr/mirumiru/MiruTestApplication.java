package fr.mirumiru;

import javax.enterprise.inject.spi.BeanManager;

public class MiruTestApplication extends MiruApplication {

	public MiruTestApplication(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

	@Override
	protected void setupBeanManager() {
		// do nothing
	}

}
