#!/bin/bash


git co master
mvn clean install
if [ $? -ne 0 ] ; then
  exit $?
fi
git push origin master

mvn javadoc:javadoc
rm -rf /tmp/apidocs
mv target/apidocs/ /tmp/
gs
git co gh-pages
git pull
rm -rf javadoc/
mv /tmp/apidocs/ javadoc
gs
git add --all javadoc/
git commit -m "update javadoc"
git push origin gh-pages

