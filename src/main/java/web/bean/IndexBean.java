package web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named("index")
@SessionScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String language = "es";
	private List<SelectItem> languages = new ArrayList<SelectItem>();

	public IndexBean() {
		this.languages.add(new SelectItem("es", "Español"));
		this.languages.add(new SelectItem("en", "English"));
		this.languages.add(new SelectItem("eus", "Euskara"));
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public List<SelectItem> getLanguages() {
		return languages;
	}
	public void setLanguages(List<SelectItem> languages) {
		this.languages = languages;
	}

	public Locale getLocale() {
		return new Locale(language);
	}

	public void listener(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(getLocale());
	}

	public String getString(String key) {
		return ResourceBundle.getBundle("Etiquetas", getLocale()).getString(key).trim();
	}
}
