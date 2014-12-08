selenium-tinafw
===============

A minimal and effective Selenium framework, not a wrapper.

For a quick insight, please see the sample provided in the [package _sample_](https://github.com/alb-i986/selenium-tinafw/tree/master/src/main/java/me/alb_i986/selenium/tinafw/sample/), which implements a small test suite showing all of the concepts introduced in this work.
Start from [SampleWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tests/SampleWebTest.java), down to the WebTasks (i.e. [OnMyAboutMePage](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tasks/OnMyAboutMePage.java), [Search](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tasks/Search.java), [CanCompliment](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tasks/CanCompliment.java)), down to page objects ([SearchResultsPage](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/ui/SearchResultsPage.java), [SearchResult](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/ui/SearchResult.java), etc.)

## Overview

`selenium-tinafw` aspires to serve as a basis for _any_ test suite/framework using [Selenium WebDriver](http://seleniumhq.org) (Java bindings only).

It sits on top of Selenium WebDriver and provides a number of abstractions that encourage you to write better, more focused, more maintainable, more readable code, in a structured, organized way.

Wraps without wrapping.
Selenium WebDriver's API is not hidden in any way.
You could virtually access a WebDriver instance from a test class (although you are encouraged not to do so, unless you want to break cohesion).
_We believe that WebDriver is such a beautiful API that it's a shame to hide it_.


## Features

Some of the services/features we proudly provide/offer:

- `WebTask` hierarchy, the orchestrators of page objects:
  - helps keeping your code organized: each concrete WebTask is supposed to be a [Given/When/Then step](http://martinfowler.com/bliki/GivenWhenThen.html))
  - and highly readable ([fluent interface](http://www.martinfowler.com/bliki/FluentInterface.html)): see e.g. [SampleWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tests/SampleWebTest.java)
  - solves the following problem in an elegant way:

    > Given two tasks that run sequentially, the first does something and ends at a certain page.
    > How can the second task know which was the last page the first task was visiting?
  
    (a naive solution being "every task restarts navigating the SUT from the home page").

- `WebDriverFactory` hierarchy: elegant solution solving the problem of
  creating instances of WebDriver, modeled using the decorator design pattern.
  E.g. see [how it allows us to solve the problem of handling the certificate error on IE](http://git.io/YQUJfw)

- BDD-style tests are not only supported but also strongly encouraged.
  You can write BDD tests by wrapping your own WebTask's in a `given` `when` or `then` task (each of which is a `CompositeWebTask`).
  See also [WebTasks.BDD](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tasks/WebTasks.java)
  Please see [SampleWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/tests/SampleWebTest.java)
  for an example of usage.
  For an introduction to BDD, please see the article [Introducing BDD, by Dan North](http://dannorth.net/introducing-bdd/).

- HTML reports with embedded screenshots for each failing test, thanks to
  [HtmlReporter](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/HtmlReporter.java)
  
- the ability to run each test on many different browsers (or just one)
  by simply setting the property `tinafw.browsers = chrome, firefox`
  
- the ability to retry a failed test for a given number of times (configurable).
  See [TestRetrier](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/TestRetrier.java)
  
- an automatic mechanism for closing browsers as soon as a test finishes,
  as well as the option to disable such a mechanism by setting a property.
  See [BrowserManager](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/tests/rules/BrowserManager.java)

- a clean directory structure, aka multilayered architecture, with:
  - the _tests_ layer, for keeping your test cases along with their data
  - the _domain_ layer, containing concepts like User and Browser, and supposed to contain the concepts
    of your own domain as well, e.g. `BlogPost`, `BlogComment`
  - the _tasks_ layer, containing the orchestrators of page objects
  - the _ui_ layer, with [page objects](https://code.google.com/p/selenium/wiki/PageObjects)

- meaningful logs, e.g.:

		INFO  CompositeWebTask - BEGIN subtask Given
		INFO  CompositeWebTask - BEGIN subtask OnMyAboutMePage
		DEBUG PageHelper$Navigation - Page at URL http://alb-i986.me/ loaded
		INFO  BasePage - Loading page MyAboutMePage
		DEBUG PageHelper - BEGIN Explicit wait (timeout=15s). Waiting until visibility of [[ChromeDriver: chrome on MAC (e8eac3735de18ef7920ce08755d3f6bf)] -> css selector: #profile_box h1.name]
		DEBUG PageHelper - END Explicit wait: visibility of [[ChromeDriver: chrome on MAC (e8eac3735de18ef7920ce08755d3f6bf)] -> css selector: #profile_box h1.name]
		INFO  CompositeWebTask - END subtask OnMyAboutMePage
		INFO  CompositeWebTask - END subtask Given
		INFO  CompositeWebTask - BEGIN subtask When
		INFO  CompositeWebTask - BEGIN subtask Search
		INFO  BasePage - Loading page SearchResultsPage
		DEBUG PageHelper - BEGIN Explicit wait (timeout=15s). Waiting until visibility of [[ChromeDriver: chrome on MAC (e8eac3735de18ef7920ce08755d3f6bf)] -> css selector: #search-results div]
		DEBUG PageHelper - END Explicit wait: visibility of [[ChromeDriver: chrome on MAC (e8eac3735de18ef7920ce08755d3f6bf)] -> css selector: #search-results div]
		INFO  CompositeWebTask - END subtask Search
		INFO  CompositeWebTask - END subtask When
		INFO  CompositeWebTask - BEGIN subtask Then
		INFO  CompositeWebTask - BEGIN subtask CanCompliment
		INFO  CompositeWebTask - END subtask CanCompliment
		INFO  CompositeWebTask - END subtask Then


## Usage

Please see the [Getting started page](https://github.com/alb-i986/selenium-tinafw/wiki/Getting-started) on the wiki.

 
## Design goals

- small, clean, Object-based API ([here, quoting Simon Stewart](http://google-opensource.blogspot.ie/2009/05/introducing-webdriver.html))
- not a wrapper
- high cohesion.
- flexibility
- poetry (to be, as well as to allow for)


## Requirements

- Java 8 (see e.g. [LoadablePage](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/ui/LoadablePage.java), which makes use of [static methods in interfaces](http://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html))
- Maven
- Graphviz

Graphviz is actually optional: it is needed for generating javadocs with pretty cool UML class diagrams (thanks [UMLGraph](http://www.umlgraph.org)!). So you're not gonna need it unless you want to run `mvn site` or `mvn javadoc:javadoc`. 


## Dependencies

The main dependencies are:
- selenium-java
- junit
- log4j

Please see the [POM file](https://github.com/alb-i986/selenium-tinafw/blob/master/pom.xml) for more details.


## TODO

- add `PageComponent` hierarchy (soon to be available)
- make it a data-driven framework
