package fr.mirumiru;

import javax.enterprise.inject.spi.BeanManager;

import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;

public class MiruTestApplication extends MiruApplication {

	private BeanManager beanManager;

	public MiruTestApplication(BeanManager beanManager) {
		this.beanManager = beanManager;
	}

	protected void setupCDI() {
		new CdiConfiguration(beanManager).setPropagation(
				ConversationPropagation.NONE).configure(this);
	}

}
