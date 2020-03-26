#!/bin/bash

AZK_EXE_HOME=.
AZK_EXE_PORT=`cat ${AZK_EXE_HOME}/executor.port`

curl http://${HOSTNAME}:${AZK_EXE_PORT}/executor?action=activate
