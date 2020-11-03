package com.unilever.julia.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.JsonReader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ParserJsonSample {

  private static final String CHARSET = "UTF-8";

  @NonNull
  public Intent buildIntent(@NonNull Context context, @NonNull String json) throws IOException {
    Sample sample = null;

    List<SampleGroup> sampleGroups = parseSampleGroup(json);
    if (sampleGroups != null && !sampleGroups.isEmpty()) {
      List<Sample> samples = sampleGroups.get(0).getSamples();
      if (samples != null && !samples.isEmpty()) {
        sample = samples.get(0);
      }
    }

    //Prefer extension decoders
    boolean preferExtensionDecoders = false;

    //Enable random ABR
    boolean randomAbrMenuItem = false;

    String abrAlgorithm;
    if (randomAbrMenuItem)
      abrAlgorithm = PlayerActivity.ABR_ALGORITHM_RANDOM;
    else
      abrAlgorithm = PlayerActivity.ABR_ALGORITHM_DEFAULT;

    return sample.buildIntent(context, preferExtensionDecoders, abrAlgorithm);
  }

  public List<SampleGroup> parseSampleGroup(String json) throws IOException {
    return parseSampleGroup(new ByteArrayInputStream(json.getBytes(Charset.forName(CHARSET))));
  }

  public List<SampleGroup> parseSampleGroup(InputStream inputStream) throws IOException {
    return parseSampleGroup(new JsonReader(new InputStreamReader(inputStream, CHARSET)));
  }

  public List<SampleGroup> parseSampleGroup(JsonReader reader) throws IOException {
    List<SampleGroup> groups = new ArrayList<>();
    readSampleGroups(reader, groups);
    return groups;
  }

  private void readSampleGroups(JsonReader reader, List<SampleGroup> groups) throws IOException {
    reader.beginArray();
    while (reader.hasNext()) {
      readSampleGroup(reader, groups);
    }
    reader.endArray();
  }

  private void readSampleGroup(JsonReader reader, List<SampleGroup> groups) throws IOException {
    String groupName = "";
    ArrayList<Sample> samples = new ArrayList<>();

    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "name":
          groupName = reader.nextString();
          break;
        case "samples":
          reader.beginArray();
          while (reader.hasNext()) {
            samples.add(readEntry(reader, false));
          }
          reader.endArray();
          break;
        case "_comment":
          reader.nextString(); // Ignore.
          break;
        default:
          throw new ParserException("Unsupported name: " + name);
      }
    }
    reader.endObject();

    SampleGroup group = getGroup(groupName, groups);
    group.addSamples(samples);
  }

  private Sample readEntry(JsonReader reader, boolean insidePlaylist) throws IOException {
    String sampleName = null;
    Uri uri = null;
    String extension = null;
    String drmScheme = null;
    String drmLicenseUrl = null;
    String[] drmKeyRequestProperties = null;
    boolean drmMultiSession = false;
    ArrayList<UriSample> playlistSamples = null;
    String adTagUri = null;
    String sphericalStereoMode = null;

    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "name":
          sampleName = reader.nextString();
          break;
        case "uri":
          uri = Uri.parse(reader.nextString());
          break;
        case "extension":
          extension = reader.nextString();
          break;
        case "drm_scheme":
          Assertions.checkState(!insidePlaylist, "Invalid attribute on nested item: drm_scheme");
          drmScheme = reader.nextString();
          break;
        case "drm_license_url":
          Assertions.checkState(!insidePlaylist,
              "Invalid attribute on nested item: drm_license_url");
          drmLicenseUrl = reader.nextString();
          break;
        case "drm_key_request_properties":
          Assertions.checkState(!insidePlaylist,
              "Invalid attribute on nested item: drm_key_request_properties");
          ArrayList<String> drmKeyRequestPropertiesList = new ArrayList<>();
          reader.beginObject();
          while (reader.hasNext()) {
            drmKeyRequestPropertiesList.add(reader.nextName());
            drmKeyRequestPropertiesList.add(reader.nextString());
          }
          reader.endObject();
          drmKeyRequestProperties = drmKeyRequestPropertiesList.toArray(new String[0]);
          break;
        case "drm_multi_session":
          drmMultiSession = reader.nextBoolean();
          break;
        case "playlist":
          Assertions.checkState(!insidePlaylist, "Invalid nesting of playlists");
          playlistSamples = new ArrayList<>();
          reader.beginArray();
          while (reader.hasNext()) {
            playlistSamples.add((UriSample) readEntry(reader, true));
          }
          reader.endArray();
          break;
        case "ad_tag_uri":
          adTagUri = reader.nextString();
          break;
        case "spherical_stereo_mode":
          Assertions.checkState(
              !insidePlaylist, "Invalid attribute on nested item: spherical_stereo_mode");
          sphericalStereoMode = reader.nextString();
          break;
        default:
          throw new ParserException("Unsupported attribute name: " + name);
      }
    }
    reader.endObject();
    DrmInfo drmInfo =
        drmScheme == null
            ? null
            : new DrmInfo(drmScheme, drmLicenseUrl, drmKeyRequestProperties, drmMultiSession);
    if (playlistSamples != null) {
      UriSample[] playlistSamplesArray = playlistSamples.toArray(new UriSample[0]);
      return new PlaylistSample(sampleName, drmInfo, playlistSamplesArray);
    } else {
      return new UriSample(
          sampleName,
          drmInfo,
          uri,
          extension,
          adTagUri,
          sphericalStereoMode);
    }
  }

  private SampleGroup getGroup(String groupName, List<SampleGroup> groups) {
    for (int i = 0; i < groups.size(); i++) {
      if (Util.areEqual(groupName, groups.get(i).getTitle())) {
        return groups.get(i);
      }
    }
    SampleGroup group = new SampleGroup(groupName);
    groups.add(group);
    return group;
  }

}
