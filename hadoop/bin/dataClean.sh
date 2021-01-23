#!/bin/bash

#判断用户是否输入日期参数，如果没有输入则默认获取昨天的日期
if [ 'X$1' = 'X' ]
then
  yes_time=`date +%Y%m%d --date="1 days ago"`
else
  yes_time=$1
fi


dataClean_input=hdfs://yuanyuan:9000/data/videoinfo/${yes_time}
dataClean_output=hdfs://yuanyuan:9000/data/videoinfo_clean/${yes_time}

#删除目录，为了兼容脚本重跑
hdsf dfs -rm -r ${dataClean_output}

#jar路径
jar_path=/usr/software/hadoop-3.1.1

#执行清洗任务
hadoop jar ${jar_path}/hadoop-demo-1.0-SNAPSHOT-jar-with-dependencies.jar \
com.ruan.yuanyuan.dataClean.DataCleanJob \
${dataClean_input}/my.txt \
${dataClean_output}/out \


#判断任务是否执行成功,其实就是为了判断目录下是否有_SUCCESS这个文件，如果没有则返回1，否则返回0
hdfs dfs -ls ${dataClean_output}/_SUCCESS
if [ "$?" = "0" ]
then
  echo "dataClean execute job success...."
else
  echo "dataClean execute job faild...."
fi


