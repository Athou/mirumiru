package fr.mirumiru.pages;

import java.util.List;
import java.util.MissingResourceException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public class FAQPage extends ContentPage {

	public FAQPage() {

		IModel<List<QuestionAndAnswer>> model = new LoadableDetachableModel<List<QuestionAndAnswer>>() {
			@Override
			protected List<QuestionAndAnswer> load() {
				List<QuestionAndAnswer> list = Lists.newArrayList();

				String questionProp = "faq.question";
				String answerProp = "faq.answer";
				int i = 1;

				try {
					while (true) {
						QuestionAndAnswer qaa = new QuestionAndAnswer();
						qaa.question = getLocalizedString(questionProp + i);
						qaa.answer = getLocalizedString(answerProp + i);
						list.add(qaa);
						i++;
					}
				} catch (MissingResourceException e) {
					// do nothing
				}

				return list;
			}

		};

		ListView<QuestionAndAnswer> view = new ListView<QuestionAndAnswer>(
				"faqlist", model) {
			@Override
			protected void populateItem(ListItem<QuestionAndAnswer> item) {
				QuestionAndAnswer qaa = item.getModelObject();
				item.add(new Label("question", qaa.question));
				item.add(new Label("answer", qaa.answer));
			}
		};
		html.add(view);

	}

	@Override
	protected String getTitle() {
		return "faq";
	}

	private class QuestionAndAnswer {
		public String question;
		public String answer;
	}

}
