#!/bin/bash


git co master

# assert that the build is good
mvn clean install
if [ $? -ne 0 ] ; then
  exit $?
fi

# deploy sources to github
git push origin master

# deploy javadoc to github (gh-pages)
rm -rf /tmp/apidocs
mv target/apidocs/ /tmp/
git co gh-pages
git pull
rm -rf javadoc/
mv /tmp/apidocs/ javadoc
git status
git add --all javadoc/
git commit -m "update javadoc"
git push origin gh-pages
git branch -d gh-pages

# finally checkout master and deploy to maven central
git co master
mvn deploy

