github-language-miner
=====================

This is primarily a research tool that can connect to github.com/languages
and save a list of the top projects per language.

Usage
-----

The defaults for the script are pretty sane. You can just type:
    ./github.sh

If you would rather specify a configuration file use:
    ./github.sh -c [CONFIGURATION]

Configuration Settings
----------------------

See `src/main/java/net/wagstrom/research/github/language/PropNames.java`
for the names of properties. The default values for those properties can be found in
`src/main/java/net/wagstrom/research/github/language/Defaults.java`.

Simple Queries
--------------

Database Schema
---------------

The schema is currently found in the project source code. See the top of
`src/main/java/net/wagstrom/research/github/language/DatabaseDriver.java`
for the declarations of all of the tables.
