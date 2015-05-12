#!/bin/bash


# the branch to deploy from
BRANCH=master
PROJECT_NAME=selenium-tinafw


# stash pop only if we did run a `git stash` previously
stash_back() {
  if [ "$STASHED" = "1" ] ; then
    echo "Finally, stash-pop back"
    git stash pop
    STASHED=0
  fi
}

# deploy javadoc to Github Pages
deploy_javadoc() {
  TMP_DIR=/tmp/$(mktemp -d /tmp/${PROJECT_NAME}.XXXXX)
  rm -rf $TMP_DIR
  mkdir -p $TMP_DIR &&
  mv target/apidocs/ $TMP_DIR &&
  git co gh-pages &&
  git pull &&
  rm -rf javadoc/ &&
  mv $TMP_DIR/apidocs javadoc &&
  git status &&
  git add --all javadoc/ &&
  git commit -m "update javadoc" &&
  git push origin gh-pages &&
  # finally delete the *local* branch gh-pages
  git co $BRANCH &&
  git branch -D gh-pages &&
  rm -rf $TMP_DIR
}



git co $BRANCH

# first, stash 'em all, unless there is nothing to stash
if [ "$( git status | grep -F 'nothing to commit' )" = "" ] ; then
  git stash -u
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
mvn clean install || {
  echo "The build is not good. Exiting"
  exit $?
}

# deploy sources
echo
read -p " -- deploy sources to github? " DEPLOY_GITHUB
echo
if [[ "$DEPLOY_GITHUB" =~ ^[Yy] ]] ; then
  git push origin $BRANCH
else
  echo -e "\n deploy sources SKIPPED"
fi

# deploy javadoc
echo
read -p " -- deploy javadoc? " DEPLOY_JAVADOC
echo
if [[ "$DEPLOY_JAVADOC" =~ ^[Yy] ]] ; then
  deploy_javadoc
else
  echo -e "\n deploy javadoc SKIPPED"
fi

# deploy to maven central
echo
read -p " -- deploy to maven central? " DEPLOY_MAVEN
echo
if [[ "$DEPLOY_MAVEN" =~ ^[Yy] ]] ; then
  mvn deploy
else
  echo -e "\n deploy package to maven SKIPPED"
fi

exit 0

