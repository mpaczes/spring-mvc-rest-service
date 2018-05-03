package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.GitHubRepositoryBean;
import exception.RestGitHubException;
import service.GitHubRestService;


@Controller
@RequestMapping("/repositories")
public class GitHubRestController {
	
	@Autowired
	@Qualifier("gitHubRestService")
	private GitHubRestService gitHubRestService;
	
	/**
	 * Here is example how to invoke this method :
	 * <br />
	 * http://localhost:8080/github-repo/restservice/repositories/mpaczesn/test
	 */
	@RequestMapping(value = "/{owner}/{repositoryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String detailsOfGivenGithubRepository(@PathVariable(value = "owner") String owner, 
			@PathVariable(value = "repositoryName") String repositoryName) {
		
		String gitHubApiCallResults = gitHubRestService.callURL("https://api.github.com/search/repositories?q=" + repositoryName + "+user:" + owner);
		
		GitHubRepositoryBean gitHubRepositoryBean = gitHubRestService.parseGithubRepsonse(gitHubApiCallResults);
		
		String responeToShow = gitHubRestService.buildResponeToShow(gitHubRepositoryBean);
		
		return responeToShow;
	}
	
	@ExceptionHandler(RestGitHubException.class)
	public ModelAndView handleError(HttpServletRequest req, RestGitHubException exception) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
			
		mav.setViewName("restGitHubException");
		
		return mav;
	}

}
