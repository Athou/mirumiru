package fr.mirumiru.pages.admin;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.mirumiru.model.News;
import fr.mirumiru.services.NewsDAO;

@SuppressWarnings("serial")
public class AdminNewsEditPage extends AdminTemplatePage {

	public AdminNewsEditPage(PageParameters params) {
		long id = params.get("id").toLong();

		Form<News> form = new StatelessForm<News>("form",
				new CompoundPropertyModel<News>(new NewsModel(id)));
		add(form);

		TextField<News> title = new TextField<News>("title");
		form.add(title);
		TextArea<News> content = new TextArea<News>("content");
		form.add(content);

	}

	private class NewsModel extends LoadableDetachableModel<News> {

		private long id;

		public NewsModel(long id) {
			this.id = id;
		}

		@Override
		protected News load() {
			return getBean(NewsDAO.class).findById(id);
		}
	}

	@Override
	protected String getTitle() {
		return "Edit News";
	}

}
