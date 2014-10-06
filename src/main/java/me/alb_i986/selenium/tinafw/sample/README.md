# Sample: testing my about.me page

This is a full sample that should give you an idea of how
a test suite/framework using selenium-tinafw would look like.

First of all, please notice the clean directory structure.
Each directory happens also to be a layer.
Each layer is focused on one thing and depends on the one below.
Well, apart from the domain layer, which is supposed to be cross
(i.e. you may have an enum BLOG_POST_TYPE in the domain, and use it
in a page object).

Start from SampleWebTest.
Enjoy the expressivity of the tests.
Their meaningfulness.
That's possible thanks to WebTasks' fluent interfaces.

Then go down to the tasks layer.
Finally down to the pages layer.

