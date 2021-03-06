package me.alb_i986.selenium.tinafw.sample.ui;
import java.util.List;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.ui.BaseWebPage;
import me.alb_i986.selenium.tinafw.ui.LoadablePage;
import me.alb_i986.selenium.tinafw.ui.WebPage;
import me.alb_i986.selenium.tinafw.ui.PageHelper;

/**
 * Models any profile page on about.me, e.g.
 * <a href="http://about.me/alb_i986">http://about.me/alb_i986</a>.
 */
public class MyAboutMePage extends BaseWebPage implements LoadablePage {

	private static final String DOTALL_EMBEDDED_FLAG = "(?s)";

	@FindBy(css = "h1.name")
	private WebElement nameTitle;
	
	@FindBy(css = "div.bio")
	private WebElement biography;

	@FindBy(css = "#app-icons li.app-icon")
	private List<WebElement> socialIcons;

	@FindBy(id = "terms")
	private WebElement searchTextField;

	
	public MyAboutMePage(WebDriver driver, WebPage previous) {
		super(driver, previous);
	}

	public static String getRelativeUrl() {
		return "/";
	}

	public SearchResultsPage doSearch(String searchQuery) {
		searchTextField.sendKeys(searchQuery);
		PageHelper.hitKeys(driver, Keys.ENTER);
		return new SearchResultsPage(driver, this);
	}
	
	public MyAboutMePage assertBioMatches(String expectedBioRegex) {
		String bio = biography.getText();
		assertTrue(
			"bio does not match. expected regex: '" + expectedBioRegex +
			"'; actual text: '" + bio + "'",
			// http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#DOTALL
			bio.matches(DOTALL_EMBEDDED_FLAG + expectedBioRegex)
		);
		logger.info("assertBioMatches " + expectedBioRegex + " passed");
		return this;
	}

	public MyAboutMePage assertSocialButtonIsDisplayed(String social) {
		WebElement socialIcon = getSocialIcon(social);
		assertTrue(
			"social icon for " + social + " not displayed", 
			socialIcon.isDisplayed()
		);
		logger.info("assertSocialButtonIsDisplayed " + social + " passed");
		return this;
	}

	public MyAboutMePage assertSocialButtonIsLink(String social) {
		WebElement socialIcon = getSocialIcon(social);
		WebElement socialLink = socialIcon.findElement(By.cssSelector("a"));
		String socialUrl = socialLink.getAttribute("href");
		String expectedUrl = "http.*" + social + "\\..*";
		assertTrue(
			"the social icon has a wrong link" + social + ". " +
					"Expected: " + expectedUrl + "; " +
					"actual: " + socialUrl,
			socialUrl.matches(expectedUrl)
		);
		logger.info("assertSocialButtonIsLink " + social + " passed");
		return this;
	}
	
	/*
	public SamplePage assertSocialButtons(SocialButtonsAssert socialButtonsAssert) {
	*/
	
	protected WebElement getSocialIcon(String social) {
		logger.debug("getSocialIcon " + social);
		By linkLocator = By.cssSelector("a." + social);

		WebElement foundSocialIcon = null;
		for (WebElement socialIcon : socialIcons) {
			try {
				socialIcon.findElement(linkLocator);
				foundSocialIcon = socialIcon;
			} catch(NoSuchElementException e) {
				continue;
			}
		}
		assertNotNull("social button for " + social + " not found.", foundSocialIcon);
		return foundSocialIcon;
	}

	@Override
	protected void waitUntilIsLoaded() {
		PageHelper.waitUntil(
			ExpectedConditions.visibilityOf(nameTitle),
			driver
		);
	}
	
}
