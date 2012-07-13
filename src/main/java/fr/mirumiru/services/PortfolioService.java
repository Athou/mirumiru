package fr.mirumiru.services;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import fr.mirumiru.model.Portfolio;

@Singleton
public class PortfolioService {

	@Inject
	Logger log;

	private Portfolio portfolio;

	@PostConstruct
	public void init() {
		InputStream is = null;
		try {
			JAXBContext context = JAXBContext.newInstance(Portfolio.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			is = getClass().getResourceAsStream("/portfolio.xml");
			portfolio = (Portfolio) unmarshaller.unmarshal(is);
		} catch (Exception e) {
			log.fatal("Could not load portfolio", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

}
