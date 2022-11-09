#!/bin/sh

# 测试配置所在目录
debug_config_dir="config"
# 正式配置所在目录
release_config_dir="release_config"

config_dir=$debug_config_dir
alias mapping=add_mapping
for arg in "$@"
do
  if [ "$arg" = "-release" ]; then
    echo "Please confirm whether the current environment is on the remote VPS? (yes/no)"
    echo "请确认当前环境是否在远程VPS上？ (yes/no)"
    read -r
    if [ "$REPLY" != "yes" ]; then
      exit
    fi
    config_dir=$release_config_dir
  elif [ "$arg" = "-clean" ]; then
    alias mapping=remove_mapping
  else
    echo "unknown arg: $arg"
    exit
  fi
done

add_mapping() {
  from="$config_dir/$1"
  to=$2
  if [ ! -e "$from" ]; then
      echo "can't find file: $from"
      exit 1
    fi
  abs_from="$(readlink -f "$from")"
  ln -fs "$abs_from" "$to"
  echo "add mapping $abs_from -> $to"
}

remove_mapping() {
  rm -rf "$2"
  echo "rm mapping $2"
}

# Add your custom file mapping
mapping AppConfig.kt buildSrc/src/main/kotlin/AppConfig.kt
mapping key.keystore app/key.keystore
mapping key.properties app/key.properties
mapping google-services.json app/google-services.json