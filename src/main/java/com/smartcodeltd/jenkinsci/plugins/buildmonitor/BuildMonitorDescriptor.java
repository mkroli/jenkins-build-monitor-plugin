package com.smartcodeltd.jenkinsci.plugins.buildmonitor;

import hudson.Util;
import hudson.model.ViewDescriptor;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class BuildMonitorDescriptor extends ViewDescriptor {

    public BuildMonitorDescriptor() {
        super(BuildMonitorView.class);
        load();
    }

    @Override
    public String getDisplayName() {
        return "Build Monitor View";
    }

    // Copy-n-paste from ListView$Descriptor as sadly we cannot inherit from that class
    public FormValidation doCheckIncludeRegex(@QueryParameter String value) {
        String v = Util.fixEmpty(value);
        if (v != null) {
            try {
                Pattern.compile(v);
            } catch (PatternSyntaxException pse) {
                return FormValidation.error(pse.getMessage());
            }
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckFontSize(@QueryParameter String fontSize) {
        try {
            Double.parseDouble(fontSize);
            return FormValidation.ok();
        } catch (NumberFormatException nfe) {
            return FormValidation.error("Not a number");
        }
    }

    public FormValidation doCheckNumberOfColumns(@QueryParameter String numberOfColumns) {
        try {
            Integer.parseInt(numberOfColumns);
            return FormValidation.ok();
        } catch (NumberFormatException nfe) {
            return FormValidation.error("Not a number");
        }
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        req.bindJSON(this, json.getJSONObject("build-monitor"));
        save();

        return true;
    }

    private boolean permissionToCollectAnonymousUsageStatistics = true;

    public boolean getPermissionToCollectAnonymousUsageStatistics() {
        return this.permissionToCollectAnonymousUsageStatistics;
    }

    @SuppressWarnings("unused") // used in global.jelly
    public void setPermissionToCollectAnonymousUsageStatistics(boolean collect) {
        this.permissionToCollectAnonymousUsageStatistics = collect;
    }
}