package fr.mirumiru.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

@XmlRootElement(name = "portfolio")
@XmlAccessorType(XmlAccessType.NONE)
public class Portfolio {

	@XmlElement(name = "item")
	private List<PortfolioItem> items;

	public List<PortfolioItem> getItems() {
		if (items == null) {
			items = Lists.newArrayList();
		}
		return items;
	}

	public void setItems(List<PortfolioItem> items) {
		this.items = items;
	}

	@XmlRootElement(name = "item")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class PortfolioItem {

		@XmlAttribute(name = "image")
		private String image;

		@XmlElement(name = "name")
		private List<PortfolioName> names;

		public String getName(String lang) {
			String result = null;
			for (PortfolioName name : getNames()) {
				if (StringUtils.equals(lang, name.getLang())) {
					result = name.getName();
					break;
				}
			}
			return result;
		}

		public String getDescription(String lang) {
			String result = null;
			for (PortfolioName name : getNames()) {
				if (StringUtils.equals(lang, name.getLang())) {
					result = name.getDescription();
					break;
				}
			}
			return result;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public List<PortfolioName> getNames() {
			if (names == null) {
				names = Lists.newArrayList();
			}
			return names;
		}

		public void setNames(List<PortfolioName> names) {
			this.names = names;
		}
	}

	@XmlRootElement(name = "name")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class PortfolioName {

		@XmlAttribute(name = "lang")
		private String lang;

		@XmlAttribute(name = "name")
		private String name;

		@XmlAttribute(name = "desc")
		private String description;

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

}
