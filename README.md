github-language-miner
=====================

Copyright (c) 2012 IBM Corporation

This is primarily a research tool that can connect to [github.com/languages][githublanguages]
and save a list of the top projects per language. It is developed under the auspices of a
joint study agreement between IBM Research and the University of Nebraska-Lincoln.

The primary developer of this software is [Patrick Wagstrom][mywebpage].
License
-------

This tool is licensed under the terms of the [Apache Software License v2.0][license].

Compilation
-----------

This tool uses [maven][maven] to build and manage dependencies. Use the following command
to automatically download the dependencies and compile the software.

    mvn clean compile package

Usage
-----

The defaults for the script are pretty sane. You can just type:

    ./github.sh

If you would rather specify a configuration file use:

    ./github.sh -c [CONFIGURATION]

Configuration Settings
----------------------

See [`src/main/java/net/wagstrom/research/github/language/PropNames.java`][PropNames.java]
for the names of properties. The default values for those properties can be found in
[`src/main/java/net/wagstrom/research/github/language/Defaults.java`][Defaults.java].

Properties that are not set in the configuration file will use default values.

In general you can get by with a simple configuration file like this:

    net.wagstrom.research.github.language.jdbc_driver=org.apache.derby.jdbc.EmbeddedDriver
    net.wagstrom.research.github.language.jdbc_url=jdbc:derby:test.db;create=true

Everything else should magically work.

Simple Queries
--------------

By default the program uses the embedded [Derby][derby] database. The easiest
way to perform queries on it is to use the [`ij`][derbyij] tool that ships with
the binary distribution of Derby.

This query gets the JavaScript projects marked as 'Most Watched This Week'
obtained from the most recent update of the data:

    select username, reponame, rank
      from repo, proglang, repoupdate, topcategory
     where repo.id=repoupdate.repo_id
           and repoupdate.proglang_id=proglang.id
           and proglang.name='JavaScript'
           and repoupdate.category_id=topcategory.id
           and topcategory.name='Most Watched This Week'
           and repoupdate.update_id=(select max(id) from githubupdate);

Database Schema
---------------

The schema is currently found in the project source code. See the top of
[`src/main/java/net/wagstrom/research/github/language/DatabaseDriver.java`][DatabaseDriver.java]
for the declarations of all of the tables.

I'm Don't Want to Run this Scraper
----------------------------------

That's great to hear. You probably shouldn't. I've been running this scraper
for a much longer time and can just provide you the data I've been collecting
as a Derby database. Unfortunately, there are times when github changes how
it works and I missed updates for a few months at a time. But hey, it's better
than having to run the scraper again. Feel free to [email me][dataemail] and I
can get you a copy of this data.

Contributing
------------

If you have found a bug, please [file an issue on github][issue]. If you then
are able to patch the bug yourself, please [create a pull request on github][pullrequest].
If you have other contributions you think might be helpful feel free to [create a pull request on github][pullrequest],
although it might be helpful to [contact me][myemail] with your idea first.

[issue]: https://github.com/pridkett/github-language-miner/issues
[pullrequest]: https://github.com/pridkett/github-language-miner/pulls
[myemail]: mailto:patrick@wagstrom.net
[githublanguages]: https://github.com/languages
[mywebpage]: http://patrick.wagstrom.net/
[dataemail]: mailto:patrick@wagstrom.net?subject=I+would+like+a+copy+of+the+github-language-miner+data
[maven]: http://maven.apache.org/
[derby]: http://db.apache.org/derby/
[derbyij]: http://db.apache.org/derby/integrate/plugin_help/ij_toc.html
[DatabaseDriver.java]: https://github.com/pridkett/github-language-miner/blob/master/src/main/java/net/wagstrom/research/github/language/DatabaseDriver.java
[PropNames.java]: https://github.com/pridkett/github-language-miner/blob/master/src/main/java/net/wagstrom/research/github/language/PropNames.java
[Defaults.java]: https://github.com/pridkett/github-language-miner/blob/master/src/main/java/net/wagstrom/research/github/language/Defaults.java
[license]: http://www.apache.org/licenses/LICENSE-2.0.html
