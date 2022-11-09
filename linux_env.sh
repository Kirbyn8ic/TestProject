#!/usr/bin/env bash
set -e

cd "$HOME"

if command -v apt > /dev/null 2>&1; then
    APT=apt;
else
    APT=yum;
fi

sudo $APT install -y curl unzip git

curl -O https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
unzip commandlinetools-linux-8512546_latest.zip

# curl -O https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz # openjdk 11
curl -O https://download.java.net/openjdk/jdk15/ri/openjdk-15+36_src.zip # openjdk 15
tar -xvf openjdk-11+28_linux-x64_bin.tar.gz

cat >> ~/.bash_profile << "EOF"
export JAVA_HOME=$HOME/jdk-11
export ANDROID_SDK_ROOT=$HOME/Android/sdk
export ANDROID_HOME=$ANDROID_SDK_ROOT
export PATH=$JAVA_HOME/bin:$PATH
export PATH=$HOME/cmdline-tools/bin:$PATH
EOF

# shellcheck disable=SC1090
source ~/.bash_profile

yes | sdkmanager --sdk_root="$ANDROID_SDK_ROOT" --licenses