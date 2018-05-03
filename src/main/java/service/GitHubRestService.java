package service;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import domain.GitHubRepositoryBean;
import exception.RestGitHubException;
import repository.InMemoryGitHubRestRepository;


@JsonIgnoreProperties(ignoreUnknown = true)
@Service("gitHubRestService")
public class GitHubRestService {
	
	@Autowired
	@Qualifier("inMemoryGitHubRestRepository")
	private InMemoryGitHubRestRepository inMemoryGitHubRestRepository;
	
	public String callURL(String myURL) {
		// Using OkHttp for efficient network access :
		String stringResponse = "";
		try {
			OkHttpClient client = new OkHttpClient();
			
//			Proxy proxyTest = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("135.245.192.7", 8000));
//			client.setProxy(proxyTest);
			
	        Request request = new Request.Builder().url(myURL).build();
	        Response response = null;
			response = client.newCall(request).execute();
			stringResponse = response.body().string();
		} catch (Exception e) {
			RestGitHubException restGitHubException = new RestGitHubException("service.GitHubRestService", "callURL()");
			restGitHubException.setMessage("Original message from exception : " + e.getMessage() + ", and cause : " + e.getCause());
			throw restGitHubException;
		}
		
		return stringResponse;
	}
	
	public GitHubRepositoryBean parseGithubRepsonse(String gitHubApiCallResults) {
		GitHubRepositoryBean gitHubRepositoryBean = new GitHubRepositoryBean();
		try {
			JSONObject obj = new JSONObject(gitHubApiCallResults);
			
			JSONArray items = obj.getJSONArray("items");
			int numbersOfRepositories = items.length();
			
			// we expect only one repository to get
			if (numbersOfRepositories == 1) {
				for (int i = 0; i < numbersOfRepositories; ++i) {
					JSONObject repository = items.getJSONObject(i);
					
					gitHubRepositoryBean = fillClassFieldsFromJsonRepository(repository);
					return gitHubRepositoryBean;
				}
			}
		} catch(Exception e) {
			RestGitHubException restGitHubException = new RestGitHubException("service.GitHubRestService", "parseGithubRepsonse()");
			restGitHubException.setMessage("Original message from exception -- " + e.getMessage() + ", and cause : " + e.getCause());
			throw restGitHubException;
		}
		
		return gitHubRepositoryBean;
	}
	
	public GitHubRepositoryBean fillClassFieldsFromJsonRepository(JSONObject repository) {
		GitHubRepositoryBean gitHubRepositoryBean = new GitHubRepositoryBean();
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			gitHubRepositoryBean = mapper.readValue(repository.toString(), GitHubRepositoryBean.class);
			gitHubRepositoryBean.changeCreatedAtToLocale(repository);
		} catch (Exception e) {
			RestGitHubException restGitHubException = new RestGitHubException("service.GitHubRestService", "fillClassFieldsFromJsonRepository()");
			restGitHubException.setMessage("Original message from exception : " + e.getMessage() + ", and cause  : " + e.getCause());
			throw restGitHubException;
		}
		return gitHubRepositoryBean;
	}
	
	public String buildResponeToShow(GitHubRepositoryBean gitHubRepositoryBean) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		
		try {
			mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		
			jsonString = mapper.writeValueAsString(gitHubRepositoryBean);
			
			jsonString = replaceNamesOFBeanFileds(jsonString);
		} catch (Exception e) {
			RestGitHubException restGitHubException = new RestGitHubException("service.GitHubRestService", "buildResponeToShow()");
			restGitHubException.setMessage("Original message from exception : " + e.getMessage() + ", and cause : " + e.getCause());
			throw restGitHubException;
		}
		
		return jsonString;
	}
	
	private String replaceNamesOFBeanFileds(String jsonString) {
		jsonString = jsonString.replace("full_name", "fullName").replaceAll("clone_url", "cloneUrl")
				.replaceAll("stargazers_count", "stargazersCount").replaceAll("created_at", "createdAt");
		
		return jsonString;
	}

	public GitHubRepositoryBean getEmptyBean() {
		return inMemoryGitHubRestRepository.getEmptyBean();
	}

}
