package repository;

import org.springframework.stereotype.Repository;

import domain.GitHubRepositoryBean;

@Repository("inMemoryGitHubRestRepository")
public class InMemoryGitHubRestRepository {
	
	public GitHubRepositoryBean getEmptyBean() {
		GitHubRepositoryBean gitHubRepositoryBean = new GitHubRepositoryBean();
		
		gitHubRepositoryBean.setFull_name(GitHubRepositoryBean.EMPTY_STRING_MODEL_ENTRY);
		gitHubRepositoryBean.setDescription(GitHubRepositoryBean.EMPTY_STRING_MODEL_ENTRY);
		gitHubRepositoryBean.setCreated_at(GitHubRepositoryBean.EMPTY_STRING_MODEL_ENTRY);
		gitHubRepositoryBean.setClone_url(GitHubRepositoryBean.EMPTY_STRING_MODEL_ENTRY);
		gitHubRepositoryBean.setStargazers_count(0);
		
		return gitHubRepositoryBean;
	}

}
