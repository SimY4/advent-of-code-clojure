#!/bin/sh
cd "`dirname $0`/.."

clj -M:nrepl -m nrepl.cmdline
