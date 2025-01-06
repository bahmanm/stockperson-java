# Copyright 2025 Bahman Movaqar
#
# This file is part of StockPerson-Java.
#
# StockPerson-Java is free software: you can redistribute it and/or modify it
# under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# StockPerson-Java is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
# for more details.
#
# You should have received a copy of the GNU General Public License
# along with StockPerson-Java. If not, see <https://www.gnu.org/licenses/>.
####################################################################################################

SHELL := /usr/bin/env bash
.DEFAULT_GOAL := test

####################################################################################################

export ROOT := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
export root.build = $(ROOT)stockperson/build/libs/
export root.data = $(ROOT)data/

####################################################################################################

.PHONY : bmakelib/bmakelib.mk
include  bmakelib/bmakelib.mk

####################################################################################################

gradle.gradle ?= ./gradlew
gradle.options ?= --console plain
gradle.command = $(gradle.gradle) $(gradle.options)

####################################################################################################

.PHONY : gradle-options(%)

gradle.options(%) :
	$(eval gradle.options += $(*))

####################################################################################################

.PHONY : gradle(%)

gradle(%) :
	$(gradle.command) $(*)

####################################################################################################

.PHONY : test

test : gradle( check )

####################################################################################################

.PHONY : format

format : gradle( spotlessApply )

####################################################################################################

.PHONY : compile

compile : gradle( classes )

####################################################################################################

.PHONY : clean

clean : gradle( clean )
clean:
	-@rm -rf build stockperson/build

####################################################################################################

.PHONY : chapter-1.0

chapter-1.0 : gradle( shadowJar )
chapter-1.0 :
	@java -cp stockperson/build/libs/stockperson.jar stockperson.chapter1_0.Main $(root.data)chapter-1.0.csv

####################################################################################################

.PHONY : chapter-2.0

chapter-2.0 : gradle( shadowJar )
chapter-2.0 :
	@java -cp stockperson/build/libs/stockperson.jar stockperson.chapter2_0.Main $(root.data)chapter-2.0.csv
