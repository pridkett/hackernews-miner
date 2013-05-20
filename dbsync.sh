#!/bin/bash

HOST=9.12.201.169

rsync -avz root@$HOST:github-language-miner/github.db .
