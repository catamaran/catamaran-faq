#!/bin/bash

MBIN=/bh/ws/catamaran-faq

$MBIN/stopTomcat.sh
$MBIN/buildLocal.sh
$MBIN/startTomcat.sh
