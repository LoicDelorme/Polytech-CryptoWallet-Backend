#!/bin/bash

if [ $# = 2 ]
then
    ssh $1@$2 'bash -s' < deployment.sh
else
    echo "Usage : auto_deployment @user @IP"
fi