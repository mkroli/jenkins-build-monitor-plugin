package com.smartcodeltd.jenkinsci.plugins.buildmonitor.order;

import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;

import java.util.Comparator;

public class ByStatus implements Comparator<Job<?, ?>> {
    @Override
    public int compare(Job<?, ?> a, Job<?, ?> b) {
        return compareChain(
                compareBuilding(a, b),
                bothProjectsHaveBuildHistory(a, b)
                        ? compareRecentlyCompletedBuilds(a, b)
                        : compareProjects(a, b)
        );
    }

    // --

    private int compareChain(int... comparisons) {
        for (int i = 0; i < comparisons.length; i++)
            if (comparisons[i] != 0)
                return comparisons[i];
        return 0;
    }

    private int compareBuilding(Job<?, ?> a, Job<?, ?> b) {
        if (a.isBuilding() ^ b.isBuilding()) {
            return a.isBuilding() ? -1 : 1;
        } else {
            return 0;
        }
    }

    private boolean bothProjectsHaveBuildHistory(Job<?, ?> a, Job<?, ?> b) {
        return a.getLastCompletedBuild() != null && b.getLastCompletedBuild() != null;
    }

    private int compareProjects(Job<?, ?> a, Job<?, ?> b) {
        Run<?, ?> recentBuildOfA = a.getLastCompletedBuild();
        Run<?, ?> recentBuildOfB = b.getLastCompletedBuild();

        if (recentBuildOfA == null && recentBuildOfB != null) {
            return -1;
        } else if (recentBuildOfA != null && recentBuildOfB == null) {
            return 1;
        } else {
            return 0;
        }
    }

    private int compareRecentlyCompletedBuilds(Job<?, ?> a, Job<?, ?> b) {
        Result lastResultOfA = a.getLastCompletedBuild().getResult();
        Result lastResultOfB = b.getLastCompletedBuild().getResult();

        if (lastResultOfA.isWorseThan(lastResultOfB)) {
            return -1;
        } else if (lastResultOfA.isBetterThan(lastResultOfB)) {
            return 1;
        } else {
            return 0;
        }
    }
}