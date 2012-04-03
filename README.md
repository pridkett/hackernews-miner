github-language-miner
=====================

This is primarily a research tool that can connect to github.com/languages
and save a list of the top projects per language.

usage
-----

./github-language-miner.sh -c [CONFIGURATION]

configuration settings
----------------------

NOTE: this is probably out of date...

* net.wagstrom.research.github.language.db: URL of the derby database
where results should be saved
* net.wagstrom.research.github.language.delay: amount of time in seconds
between fetching subsequent pages

database schema
---------------

NOTE: this is probably out of date...

I've tried to keep this as simple as possible while still creating a
robust system for data analysis.

table: language
field: id int
field: name varchar(128)

table: project
field: id int
field: username varchar(128)
field: projectname varchar(128)

table: update_type
field: id int
field: type varchar(128)

table: update
field: id int
field: date datetime

table: project_update
field: id int
field: project_id int (foreign key: project:id)
field: language_id int (foreign key: language:id)
field: update_id int (foreign key: update:id)
field: update_type_id int (foreign key: update_type:id)
field: rank int
