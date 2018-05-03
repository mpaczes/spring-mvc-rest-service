package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-DispatcherServlet-context.xml")
@WebAppConfiguration
public class GitHubRestControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private String jsonString;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		this.jsonString =
			"{" +
			"\"fullName\" : \"mpaczes/hello-world\"," +
			"\"description\" : \"Repozytroium utworzone na podstawie tutoriala ze strony https://guides.github.com/activities/hello-world/.\"," +
			"\"cloneUrl\" : \"https://github.com/mpaczes/hello-world.git\"," +
			"\"stargazersCount\" : 0," +
			"\"createdAt\" : \"sobota, 24 wrze?nia 2016\""+
			"}";	
	}
	
	@Test
	public void checkdetailsOfGivenGithubRepository() throws Exception {
		this.mockMvc.perform(get("/repositories/mpaczes/hello-world"))
			.andExpect(status().is(200));
		
		this.mockMvc.perform(get("/repositories/mpaczes/hello-world"))
			.andReturn().equals(this.jsonString);
	}
	
	@Test
	public void checkHandleError() throws Exception  {
		this.mockMvc.perform(get("/repositories/mpaczeserror/hello-world"))
			.andExpect(model().attributeExists("exception"))
			.andExpect(model().attributeExists("url"))
			.andExpect(status().is(200))
			.andExpect(view().name("restGitHubException"));
	}

}
