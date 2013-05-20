/*
 * Copyright (c) 2013 IBM Corporation 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wagstrom.research.hackernews;

public class Defaults {
    public static final String BASE_URL = "https://news.ycombinator.com/";
    public static final String NEW_URL = "https://news.ycombinator.com/newest";
    public static final String HTTP_DELAY = "30000";
    
    public static final String PAGE_FETCH_DELAY = "604800";
    
    public static final String NUM_TOP_PAGES = "5";
    public static final String NUM_NEW_PAGES = "5";
    
    public static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:test.db;create=true";
}
