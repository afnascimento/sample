package com.unilever.julia.video;

import java.util.ArrayList;
import java.util.List;

public class SampleGroup {

  private final String title;
  private final List<Sample> samples = new ArrayList<>();

  public SampleGroup(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public List<Sample> getSamples() {
    return samples;
  }

  public void addSamples(List<Sample> samples) {
    this.samples.addAll(samples);
  }
}
