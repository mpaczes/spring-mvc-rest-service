package service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import domain.GitHubRepositoryBean;

public class GitHubRestServiceTest {
	
	private GitHubRestService gitHubRestService;
	private String owner;
	private String repositoryName;
	private String gitHubApiCallResults;
	
	@Before
	public void setUp() {
		gitHubRestService = new GitHubRestService();
		this.owner = "mpaczes";
		this.repositoryName = "hello-world";
		
		this.gitHubApiCallResults = gitHubRestService.callURL("https://api.github.com/search/repositories?q=" + this.repositoryName + "+user:" + this.owner);
	}
	
	@Test
	public void checkParseGithubRepsonse() {
		GitHubRepositoryBean gitHubRepositoryBean = gitHubRestService.parseGithubRepsonse(gitHubApiCallResults);
		
		assertThat("mpaczes/hello-world", is(gitHubRepositoryBean.getFull_name()));
		
		assertThat("https://github.com/mpaczes/hello-world.git", is(gitHubRepositoryBean.getClone_url()));
	}
	
	@Test
	public void checkFillClassFieldsFromJsonRepository() {
		// (1)
		JSONObject obj = new JSONObject(this.gitHubApiCallResults);
		
		JSONArray items = obj.getJSONArray("items");
		
		int numbersOfRepositories = items.length();
		
		assertThat(1, is(numbersOfRepositories));
		
		// (2)
		JSONObject repository = items.getJSONObject(0);
		
		GitHubRepositoryBean localGitHubRepositoryBean = this.gitHubRestService.fillClassFieldsFromJsonRepository(repository);
		
		assertThat("mpaczes/hello-world", anyOf(notNullValue(), is(localGitHubRepositoryBean.getFull_name())));
	}
	
	@Test
	public void checkBuildResponeToShow() {
		GitHubRepositoryBean gitHubRepositoryBean = new GitHubRepositoryBean();
		gitHubRepositoryBean.setFull_name("mpaczes/hello-world");
		
		String responeToShow = this.gitHubRestService.buildResponeToShow(gitHubRepositoryBean);
		
		JSONObject obj = new JSONObject(responeToShow);
		
		assertThat("mpaczes/hello-world", is(obj.getString("fullName")));
	}

}
