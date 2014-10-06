selenium-tinafw
===============

Introducing Selenium Tina Framework.

A minimal and effective Selenium framework, not a f*ing wrapper.

For a quick insight, please see the sample provided in the [package _sample_](https://github.com/alb-i986/selenium-tinafw/tree/master/src/main/java/me/alb_i986/selenium/tinafw/sample/), which implements a small test suite showing all of the concepts introduced in this work.

## Overview

The intent is to serve as a basis for any test suite/framework using [Selenium WebDriver](http://seleniumhq.org).

It sits on top of Selenium WebDriver and provides a number of abstractions that encourage you to write better, more focused, more maintainable, more readable code, in a structured, organized way.

Wraps without wrapping.
Selenium WebDriver's API is not hidden in any way.
You could virtually access a WebDriver instance from a test class (although you are encouraged not to do so, unless you want to break cohesion).
_We believe that WebDriver is such a beautiful API that it's a shame to hide it_.


A list of some of the services we proudly provide:

- WebDriverFactory hierarchy: elegant solution solving the problem of
  creating instances of WebDriver, modeled using the decorator design pattern.
  E.g. see [how it allows us to solve the problem of handling the certificate error on IE](http://git.io/YQUJfw)
  
- WebTask hierarchy, the orchestrators of page objects:
  - helps keeping your code organized: each concrete WebTask is supposed to be a [Given/When/Then step](http://martinfowler.com/bliki/GivenWhenThen.html))
  - and highly readable ([fluent interface](http://www.martinfowler.com/bliki/FluentInterface.html)): see e.g. [SampleWebTest](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/sample/SampleWebTest.java)
  - solves the following problem in an elegant way:

  > Given two tasks that run sequentially, the first does something and ends at a certain page.
  > How can the second task know which was the last page the first task was visiting?
  
  (a naive solution being "every task restarts navigating the webapp from the home page").

- a multilayered architecture with:
  - the _tests_ layer, for keeping your test cases along with their data
  - the _domain_ layer, containing concepts like User and Browser, as well as the concepts from your own domain, e.g. BlogPost, BlogComment 
  - the _tasks_ layer, which orchestrate the page objects by building method chains of page objects
  - the _pages_ layer, with [page objects](https://code.google.com/p/selenium/wiki/PageObjects)


## Design goals

- small, clean, Object-based API ([as Simon Stewart said about WebDriver](http://google-opensource.blogspot.ie/2009/05/introducing-webdriver.html))

- not a wrapper

- high cohesion.
  E.g.: WebTest directly depends on User only;
  User directly depends on Browser.
  
- flexibility
  
- poetry (to be, as well as to allow for)


## Requirements

- Java 8 (see e.g. [LoadablePage](https://github.com/alb-i986/selenium-tinafw/blob/master/src/main/java/me/alb_i986/selenium/tinafw/pages/LoadablePage.java), which makes use of [static methods in interfaces](http://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html))
- Maven
- Graphviz

Graphviz is actually optional: it is needed for generating javadocs with pretty cool UML class diagrams (thanks [UMLGraph](http://www.umlgraph.org)!). So you're not gonna need it unless you want to run `mvn site` or `mvn javadoc:javadoc`. 


## Dependencies

As you can see in the POM file, they are:

- selenium-java
- junit
- log4j
- umlgraph

This is just a FYI, as maven handles that.


## Usage

1. clone this repo
2. `cd` to its directory
3. run `mvn install`

Now you can start building your own test suite/framework on top of `selenium-tinafw`
by adding this to your POM file:

	<dependency>
		<groupId>me.alb-i986.selenium</groupId>
		<artifactId>selenium-tinafw</artifactId>
		<version>0.1-SNAPSHOT</version>
	</dependency>

For a start, please see:
 - the sample provided in the [package _sample_](https://github.com/alb-i986/selenium-tinafw/tree/master/src/main/java/me/alb_i986/selenium/tinafw/sample/), containing a small but full working example of use
 - https://github.com/alb-i986/selenium-forbes-sample/ (sometimes may be out of date, bear with me)
 
### Configuration
Under `src/main/resources/` you can find the config file `selenium-tinafw.default.properties`.

Copy it to your project, under a 'resources' directory, customize it as per your needs, and add it to your VCS. Besides, you can have another config file, which you should _not_ version, in which you can override any property defined in the defaults file.


## TODO

- add WebTest hierarchy (soon to be available)
- add PageComponent hierarchy
- make it a data-driven framework: shouldn't be too difficult thanks to WebTask
