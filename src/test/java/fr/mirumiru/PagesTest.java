package fr.mirumiru;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.apache.wicket.util.tester.WicketTester;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.mirumiru.model.PageModel;

@RunWith(Arquillian.class)
public class PagesTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final String RESOURCES_SRC = "src/main/resources";
	private static final String TEST_RESOURCES_SRC = "src/test/resources";

	@Inject
	BeanManager beanManager;

	@Inject
	MiruPages miruPages;

	private WicketTester wicketTester;
	private MiruApplication application;

	@Before
	public void init() {
		application = new MiruTestApplication(beanManager);
		wicketTester = new WicketTester(application);
	}

	@Deployment
	public static WebArchive createDeployment() {

		WebArchive archive = ShrinkWrap.create(WebArchive.class);
		archive.merge(
				ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.importDirectory(TEST_RESOURCES_SRC)
						.importDirectory(RESOURCES_SRC)
						.as(GenericArchive.class), "/", Filters.includeAll());
		archive.addPackages(true, MiruApplication.class.getPackage());
		return archive;

	}

	@Test
	public void testHomePage() {

		Assert.assertTrue(miruPages.getMenuPages().size() > 0);

		for (PageModel pageModel : miruPages.getMenuPages()) {
			wicketTester.startPage(pageModel.getPageClass());
		}

	}
}
