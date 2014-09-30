selenium-tinafw
===============

A minimal and effective selenium framework, not a f***ing wrapper.

## Overview

Its intent is to serve as a basis for any test suite using [Selenium WebDriver](http://seleniumhq.org).

Wraps without wrapping.

It sits on top of Selenium WebDriver and provides a few useful abstractions
and stuff to make your life easier.

In particular:

- the WebDriverFactory hierarchy: elegant solution for solving the problem of
  creating instances of WebDriver (and a little more), modeled using the decorator
  design pattern
- the interface WebTask, which helps organizing the code, and especially
  solves elegantly the following problem:

  > Given two tasks that run sequentially, the first does something and stops at a certain page.
  > How can I pass the last visited page to the second task?
  
  (a naive solution being "every task restarts navigating the webapp from the home page").


## Requirements

Apart from the dependencies managed by maven (i.e. selenium-java, junit, and so on),
there is one that needs to be installed manually: graphviz.

Actually it is optional, as it is needed only for  `mvn site` or `mvn javadoc:javadoc`.
This is because we are using [UMLGraph](http://www.umlgraph.org) as a java doclet
to show pretty cool UML class diagrams in the javadocs.

## Usage

#. clone this repo
#. `cd` to its directory
#. run `mvn install`

Now you can start building your own test suite on top of selenium-tinafw
by adding this to your pom.xml:

	<dependency>
		<groupId>me.alb-i986.selenium</groupId>
		<artifactId>selenium-tinafw</artifactId>
		<version>0.0.5-SNAPSHOT</version>
	</dependency>

For a start, please see https://github.com/alb-i986/selenium-forbes-sample/
