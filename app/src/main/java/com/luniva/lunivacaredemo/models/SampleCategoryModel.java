package com.luniva.lunivacaredemo.models;

public class SampleCategoryModel {
    int id;
    String sample_status;
    int sample_count;

    public SampleCategoryModel() {
    }

    public SampleCategoryModel(int id, String sample_status, int sample_count) {
        this.id = id;
        this.sample_status = sample_status;
        this.sample_count = sample_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSample_status() {
        return sample_status;
    }

    public void setSample_status(String sample_status) {
        this.sample_status = sample_status;
    }

    public int getSample_count() {
        return sample_count;
    }

    public void setSample_count(int sample_count) {
        this.sample_count = sample_count;
    }
}
