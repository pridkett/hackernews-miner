/*
 * Copyright (c) 2012 IBM Corporation 
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

import java.util.Collection;

public class ProjectRecord {
    private int numProjects = -1;
    private int rank = -1;
    private Collection <String> mostWatchedToday;
    private Collection <String> mostForkedToday;
    private Collection <String> mostWatchedThisWeek;
    private Collection <String> mostForkedThisWeek;
    private Collection <String> mostWatchedThisMonth;
    private Collection <String> mostForkedThisMonth;
    private Collection <String> mostWatchedOverall;
    private Collection <String> mostForkedOverall;
    private Collection <String> mostWatchedProjects;
    private String language;
    
    public ProjectRecord(String language) {
        setLanguage(language);
    }
    
    public int getNumProjects() {
        return numProjects;
    }

    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }

    public Collection <String> getMostWatchedToday() {
        return mostWatchedToday;
    }

    public void setMostWatchedToday(Collection <String> mostWatchedToday) {
        this.mostWatchedToday = mostWatchedToday;
    }

    public Collection <String> getMostForkedToday() {
        return mostForkedToday;
    }

    public void setMostForkedToday(Collection <String> mostForkedToday) {
        this.mostForkedToday = mostForkedToday;
    }

    public Collection <String> getMostWatchedThisWeek() {
        return mostWatchedThisWeek;
    }

    public void setMostWatchedThisWeek(Collection <String> mostWatchedThisWeek) {
        this.mostWatchedThisWeek = mostWatchedThisWeek;
    }

    public Collection <String> getMostForkedThisWeek() {
        return mostForkedThisWeek;
    }

    public void setMostForkedThisWeek(Collection <String> mostForkedThisWeek) {
        this.mostForkedThisWeek = mostForkedThisWeek;
    }

    public Collection <String> getMostWatchedThisMonth() {
        return mostWatchedThisMonth;
    }

    public void setMostWatchedThisMonth(Collection <String> mostWatchedThisMonth) {
        this.mostWatchedThisMonth = mostWatchedThisMonth;
    }

    public Collection <String> getMostForkedThisMonth() {
        return mostForkedThisMonth;
    }

    public void setMostForkedThisMonth(Collection <String> mostForkedThisMonth) {
        this.mostForkedThisMonth = mostForkedThisMonth;
    }

    public Collection <String> getMostWatchedOverall() {
        return mostWatchedOverall;
    }

    public void setMostWatchedOverall(Collection <String> mostWatchedOverall) {
        this.mostWatchedOverall = mostWatchedOverall;
    }

    public Collection <String> getMostForkedOverall() {
        return mostForkedOverall;
    }

    public void setMostForkedOverall(Collection <String> mostForkedOverall) {
        this.mostForkedOverall = mostForkedOverall;
    }

    public Collection <String> getMostWatchedProjects() {
        return mostWatchedProjects;
    }

    public void setMostWatchedProjects(Collection <String> mostWatchedProjects) {
        this.mostWatchedProjects = mostWatchedProjects;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
