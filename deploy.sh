#!/bin/bash


# the branch to deploy from
BRANCH=master



# stash pop only if we did run a `git stash` previously
stash_back() {
  if [ "$STASHED" -eq "1" ] ; then
    echo "Finally, Stash back"
    git stash pop
    STASHED=0
  fi
}

# deploy javadoc to Github Pages
deploy_javadoc() {
  TMP_DIR=/tmp/apidocs
  rm -rf $TMP_DIR
  mv target/apidocs/ /tmp/
  git co gh-pages
  git pull
  rm -rf javadoc/
  mv $TMP_DIR javadoc
  git status
  git add --all javadoc/
  git commit -m "update javadoc"
  git push origin gh-pages
  # finally delete the *local* branch gh-pages
  git co $BRANCH
  git branch -d gh-pages
}



git co $BRANCH

# first, stash 'em all, unless there is nothing to stash
if [ "$( git status | grep -F 'nothing to commit' )" = "" ] ; then
  git stash -u --keep-index
  if [ $? -eq 0 ] ; then
    STASHED=1
  else
    STASHED=0
    exit $?
  fi
fi


# stash 'em back on CTRL+C or any exit (whether clean or not)
trap stash_back SIGINT SIGTERM EXIT

# assert that the build is good
mvn clean install
if [ $? -ne 0 ] ; then
  exit $?
fi

read -p " -- Are you sure you want to proceed with the release process? " DEPLOY
if [[ ! "$DEPLOY" =~ ^[Yy] ]] ; then
  echo
  echo "deploy aborted by user"
  echo
  exit 0
fi


# deploy sources to github
git push origin $BRANCH

read -p "deploy javadoc? " DEPLOY_JAVADOC
if [[ "$DEPLOY_JAVADOC" =~ ^[Yy] ]] ; then
  deploy_javadoc
fi

# deploy to maven central
read -p " -- deploy to maven central? " DEPLOY_MAVEN
if [[ "$DEPLOY_MAVEN" =~ ^[Yy] ]] ; then
  mvn deploy
fi

exit 0

