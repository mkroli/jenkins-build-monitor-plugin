package com.smartcodeltd.jenkinsci.plugins.buildmonitor;

import com.google.common.base.Objects;
import com.smartcodeltd.jenkinsci.plugins.buildmonitor.order.ByName;
import hudson.model.Job;

import java.util.Comparator;

public class Config {

    public static Config defaultConfig() {
        return new Config();
    }

    public Comparator<Job<?, ?>> getOrder() {
        return getOrElse(order, new ByName());
    }

    public void setOrder(Comparator<Job<?, ?>> order) {
        this.order = order;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public boolean isColourBlind() {
        return colourBlind;
    }

    public void setColourBlind(boolean colourBlind) {
        this.colourBlind = colourBlind;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("order", order.getClass().getSimpleName())
                .toString();
    }

    // --

    /**
     * Jenkins unmarshals objects from config.xml by setting their private fields directly and without invoking their constructors.
     * In order to retrieve a potentially already persisted field try to first get the field, if that didn't work - use defaults.
     *
     * This is defensive coding to avoid issues such as this one:
     *  https://github.com/jan-molak/jenkins-build-monitor-plugin/issues/43
     *
     * @param value         a potentially already persisted field to be returned
     * @param defaultValue  a default value to be used in case the requested valued was not available
     *
     * @return either value or defaultValue
     */
    private <T> T getOrElse(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    private Comparator<Job<?, ?>> order;
    private Double fontSize = 1.0;
    private Integer numberOfColumns = 2;
    private Boolean colourBlind = false;
    private boolean fullScreen = false;
}