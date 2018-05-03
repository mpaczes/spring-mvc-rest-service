package domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryBean {
	
	public static final String EMPTY_STRING_MODEL_ENTRY = "empty model entry";
	
	private static final String EMPTY_STRING_GIT_HUB_ENTRY = "empty Git Hub entry";
	private static final Integer EMPTY_INTEGER_GIT_HUB_ENTRY = -1;
	
	public String full_name = EMPTY_STRING_GIT_HUB_ENTRY;
	public String description = EMPTY_STRING_GIT_HUB_ENTRY;
	public String clone_url = EMPTY_STRING_GIT_HUB_ENTRY;
	public Integer stargazers_count = EMPTY_INTEGER_GIT_HUB_ENTRY;
	public String created_at = EMPTY_STRING_GIT_HUB_ENTRY;
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClone_url() {
		return clone_url;
	}
	public void setClone_url(String clone_url) {
		this.clone_url = clone_url;
	}
	public Integer getStargazers_count() {
		return stargazers_count;
	}
	public void setStargazers_count(Integer stargazers_count) {
		this.stargazers_count = stargazers_count;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public void changeCreatedAtToLocale(JSONObject jsonObject) {
		String createdAtTemp = jsonObject.getString("created_at");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		try {
			date = simpleDateFormat.parse(createdAtTemp);
		} catch (ParseException e) {
			date = new Date();
		}
		
		Locale locale = new Locale("pl");
		DateFormat formatterLocale = DateFormat.getDateInstance(DateFormat.FULL, locale);
		String localeDateString = formatterLocale.format(date);
		
		this.created_at = localeDateString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.full_name == null) ? 0 : this.full_name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GitHubRepositoryBean other = (GitHubRepositoryBean) obj;
		if (this.full_name == null) {
			if (other.full_name != null)
				return false;
		} else if (!this.full_name.equals(other.full_name))
			return false;
		return true;
	}

}
