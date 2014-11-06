#!/bin/bash


# stash pop only if we did run a `git stash` previously
stash_back() {
  if [ "$STASHED" -eq "1" ] ; then
    git stash pop
  fi
}




git co master

# first, stash 'em all
if [ "$( git status | grep -F 'nothing to commit' )" = "" ] ; then
  git stash -u --keep-index
  if [ $? -eq 0 ] ; then
    STASHED=1
  else
    STASHED=0
    exit $?
  fi
fi

# assert that the build is good
mvn clean install
if [ $? -ne 0 ] ; then
  stash_back
  exit $?
fi

read -p " Are you sure you want to proceed with the release process? "


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

# finally checkout master and deploy to maven central
git co master
git branch -d gh-pages
mvn deploy

# finally, stash 'em all back, if appropriate
stash_back
