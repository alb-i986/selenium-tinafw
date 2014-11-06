# Tutorial: build your own Selenium test suite on top of selenium-tinafw


## Preliminaries


### Setup a maven project

For example, from eclipse, you may start from `File > New > Other > Maven Project`.

Then, update the POM  as follows:

```

	<dependencies>
		<dependency>
			<groupId>me.alb-i986.selenium</groupId>
			<artifactId>selenium-tinafw</artifactId>
			<version>0.2.2-SNAPSHOT</version>
		</dependency>
	</dependencies>
	
	<repositories>
		<repository>
			<id>snapshots-repo</id>
			<url>https://oss.sonatype.org/content/repositories/snapshots</url>
			<releases>
				<enabled>false</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
```

### Package layout
First of all we recommend to create the following directory structure aka package layout:

- tests
- domain
- pages
- utils


### Configuration
The framework can be configured in two non-exclusive ways:
- system property, e.g. `mvn test -Dtinafw.browsers=chrome`
- config files (`config.default.properties`, `config.custom.properties`)

A system property takes precedence over what is specified in the config files.
For more info, please see [PropertyLoader](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/utils/PropertyLoader.java).

Copy [config.default.properties](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/resources/config.default.properties) to your project, under `src/test/resources`, and configure it as per your needs. You should also add it to your VCS.
Besides, you can have another config file, which you must name `config.custom.properties`, supposed _not_ to be versioned,
in which you can override any property defined in the defaults file.



### Extend BasePage
You may want to define your own abstract base page, to be extended by any other
page object. It allows sharing code among all of your pages. 

Think of it as a hook: it may be empty at first, but one day or another
it may turn useful.

```
public abstract class MyBasePage extends BasePage {
	
	public TopNavBar topNavBar;
	
	public MyBasePage(WebDriver driver, Page previous) {
		super(driver, previous);
		topNavBar = new TopNavBar(driver, this);
	}
}

public class MyPage extends MyBasePage {
	
	public MyPage(WebDriver driver, Page previous) {
		super(driver, previous);
	}
	
	[..]
}
```

### Extend TinafwPropLoader
If you need extra config properties, you should also extend TinafwPropLoader,
and define a static method for each of your custom configurable properties.

```
public class MyPropLoader extends TinafwPropLoader {
	
	public static final String NAMESPACE = "my.namespace";

	public static String getMyProp1() {
		return getMyConfig("prop1");
	}
	
	public static String[] getMyProp2() {
		return PropertiesUtils.split(getMyConfig("prop2"));
	}

	private static String getMyConfig(String propName) {
		return propLoader.getProperty(NAMESPACE, propName);
	}
}
```

With this class in place, you can then specify in your config file
the following properties:

	my.namespace.prop1 = whatever
	my.namespace.prop2 = value1, value2, value3

Then, in your classes, whenever you need to load a property, you can call

	MyPropLoader.getMyPropN()

Please also see `TinafwPropLoader`'s javadoc for more details.



## Develop

Next, it's time to start writing the code specific to your own SUT: page objects, tasks, tests.

For a start, please see:
 - the sample provided in the [package _sample_](https://github.com/alb-i986/selenium-tinafw/tree/master/src/main/java/me/alb_i986/selenium/tinafw/sample/), containing a small but full working example of use

Javadocs can be found at [http://alb-i986.github.io/selenium-tinafw/javadoc/](http://alb-i986.github.io/selenium-tinafw/javadoc/)


### Page objects
When developing page objects, try to design them with a
[fluent interface](http://martinfowler.com/bliki/FluentInterface.html)
in mind, as much as possible.

For example, say you have a form with a submit button.
After you click submit, a dialog pops up asking the user to confirm.

```
submitBtn.click() // after submitting, a confirmation dialog will pop up
new ConfirmDialog(driver, this) // the constructor implicitly waits until the page object is displayed
	.confirm() // click the 'yes' button 
;
```

```
public class ConfirmDialog extends BasePage {
	@FindBy(id = "confirm")
	private WebElement confirmBtn;
	
	[..]
	
	public void confirm() {
		confirmBtn.click();
	}
}
```

Please note that `ConfirmDialog#confirm` returns void: in fact, after confirming, the dialog closes.


### Tasks

Each of your custom tasks should extend
[BaseWebTask](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tasks/BaseWebTask.java) or
[NavigationWebTask](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tasks/NavigationWebTask.java).



### Tests

`selenium-tinafw` fully supports and encourages to use JUnit as the testing framework.

TestNG is not recommended because it encourages bad practices like defining dependencies among tests,
and also for not being very clean, from a design point of view (in our opinion).
However, you are free to use whichever you prefer.

If you choose JUnit, you get for free a few nice features:
- HTML reports with embedded screenshots for each failing test, thanks to
  [HtmlReporter](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/HtmlReporter.java)
- the ability to run each test on many different browsers (or just one)
  by simply setting the property `tinafw.browsers = chrome, firefox`
- the ability to retry a failed test for a given number of times (configurable).
  See [TestRetrier](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/TestRetrier.java)
- an automatic mechanism for closing browsers as soon as a test finishes,
  as well as the option to disable such a mechanism by setting a property.
  See [BrowserManager](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/BrowserManager.java)

Each test class needs to extend
[JunitWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/JunitWebTest.java).

For each user involved in a test:

- set the type of the browser to open: `user.withBrowserType(browserType)`
- register his browser to BrowserManager: `browserManager.registerBrowsers`
- pass his browser to HtmlReporter: `htmlReporter.setBrowser(user.getBrowser())`

You may define all of these steps in a fixture, aka a `Before` method.

	@Override
	public void before() {
		user = new User().withBrowserType(browserType);
		browserManager.registerBrowsers(user.getBrowser());
		htmlReporter.setBrowser(user.getBrowser());
		user.openBrowser();
	}

See also [SampleWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tests/SampleWebTest.java).


### Final recommendation
We recommend developing
[outside-in](http://en.wikipedia.org/wiki/Outside%E2%80%93in_software_development).
That is, start from writing the test method, and let the IDE help you making
your way through tasks to pages.

