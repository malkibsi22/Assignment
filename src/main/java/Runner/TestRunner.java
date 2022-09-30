package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features={"src/test/java/Features/Ui.feature"},
		glue={"StepDefn"},
		plugin={"pretty","html:reports/report.html"}
		
		
		)


public class TestRunner {

}
