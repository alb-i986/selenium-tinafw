package me.alb_i986.selenium.tinafw.tests.rules;

import me.alb_i986.selenium.tinafw.domain.Browser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static me.alb_i986.selenium.tinafw.tests.rules.BrowserManager.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BrowserManagerTest {

    @Mock private Browser browserMock;

    @Test
    public void after_doNotCloseBrowsers() {
        BrowserManager manager = new BrowserManager(Mode.DO_NOT_CLOSE_BROWSERS);
        manager.registerBrowser(browserMock);
        assertThat(manager.registeredBrowsers, contains(browserMock));

        // when
        manager.after();

        verifyNoMoreInteractions(browserMock);
    }

    @Test
    public void after_doCloseBrowsers() {
        BrowserManager manager = new BrowserManager(Mode.DEFAULT);
        manager.registerBrowser(browserMock);
        assertThat(manager.registeredBrowsers, contains(browserMock));

        // when
        manager.after();

        verify(browserMock).close();
    }
}