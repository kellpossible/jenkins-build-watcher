import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.Job;
import git4idea.repo.GitRepositoryManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

/**
 *
 */
public class TestJenkins
{
	private static final String SERVER_URL = "127.0.0.1:8080";
	private static final String USER_NAME = "test";
	private static final String PASSWORD = "test";
	private static final String FOLDER_JOB_NAME = "test";
	private static final String FOLDER_JOB_URL = "job/test";
	private static final String JOB_NAME = "test/job/name";

	@Test
	public void test()
	{
		BasicConfigurator.configure();
		Log4JLogger logger = new Log4JLogger();
		try
		{
			JenkinsServer jenkins = new JenkinsServer(new URI(SERVER_URL), USER_NAME, PASSWORD);
//			System.out.println(jenkins.getViews());

			Map<String, Job> jobs = jenkins.getJobs(new FolderJob(FOLDER_JOB_NAME, SERVER_URL + FOLDER_JOB_URL));

			System.out.println(jobs);
			System.out.println(jobs.get(JOB_NAME.replace("/", "%2F")));

			Project project = ProjectManager.getInstance().getOpenProjects()[0];
			GitRepositoryManager.getInstance(project).getRepositories();
		}
		catch (URISyntaxException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
