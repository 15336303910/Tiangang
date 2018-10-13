#!/bin/bash
#########################################
# content: 转移银行对接文件(20min执行)
# author: 王基伟
# date: 2018年9月21日17:22:19
# eg: 
#########################################
cd `dirname $0`/
SH_HOME=`pwd`
LOCAL_DIR=/bank/tgsftp
LOCAL_DIR_BACK=/data/bank/backup
if [ ! -d $LOCAL_DIR_BACK ];then
	echo "plase mkdir...."
	echo "exit..."
	exit 
fi
ALL_FILE=""
NOW_SECOND=`date +%s`
for f in ${LOCAL_DIR}/*
do
	temp_file=`basename $f`
	if [ -d $LOCAL_DIR/$temp_file ];then
		continue
	fi
	file_last_modify_time=`stat -c %Y $LOCAL_DIR/$temp_file`
	# 取15分钟前的文件
	if [ $[ $NOW_SECOND - $file_last_modify_time ] -gt 900 ];then
		ALL_FILE=${ALL_FILE}=$temp_file
	fi
done
# 待转移文件序列
WAIT_FILE=${ALL_FILE//'='/' '}
if [ "${WAIT_FILE}x" == "x" ];then
        exit
fi

FILE_DATE=`date +%F`
if [ ! -d $LOCAL_DIR_BACK/$FILE_DATE ];then
	mkdir $LOCAL_DIR_BACK/$FILE_DATE
fi

for f in $WAIT_FILE
do
	echo "The file being transferred is $f"
	$SH_HOME/cp_file.sh $LOCAL_DIR/$f
	mv $LOCAL_DIR/$f $LOCAL_DIR_BACK/$FILE_DATE/
done