#!/usr/bin/env python

# Type "python bild.py" to build all of the parsers needed by the plugin.

# bootstrap by downloading bilder.py if not found
import urllib
import os

if not os.path.exists("bilder.py"):
    print "bootstrapping; downloading bilder.py"
    urllib.urlretrieve(
        "https://raw.githubusercontent.com/parrt/bild/master/src/python/bilder.py",
        "bilder.py")

# assumes bilder.py is in current directory
from bilder import *

def latest_antlr4():
    mkdir("lib")
    # grab the lib that the plugin needs
    jarname = "antlr-4.5-complete.jar"
    download("http://www.antlr.org/download/" + jarname, "lib")

def latest_antlr4_sources():
    download("https://github.com/antlr/antlr4/archive/4.5.zip", "lib")
    mkdir("lib/src")
    unjar("lib/4.5.zip", "lib/src")
    rmfile("lib/4.5.zip")

def parsers():
    require(latest_antlr4)
    require(grammars)
    antlr4("src/grammars", "gen", version="4.5",
           package="edu.clemson.resolve.plugin.parser")

def clean():
    rmdir("gen")

def grammars():
    urllib.urlretrieve(
        "https://raw.githubusercontent.com/Welchd1/resolve-lite/master/compiler/src/edu/clemson/resolve/parser/Resolve.g4",
        "src/grammars/edu/clemson/resolve/plugin/Resolve.g4")
    urllib.urlretrieve(
        "https://raw.githubusercontent.com/Welchd1/resolve-lite/master/compiler/src/edu/clemson/resolve/parser/ResolveLexer.g4",
        "src/grammars/edu/clemson/resolve/plugin/ResolveLexer.g4")

def all():
    require(grammars)
    require(parsers)
    require(latest_antlr4_sources)

processargs(globals())