package com.unilever.julia.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class UriSample extends Sample {

  private final Uri uri;
  private final String extension;
  private final String adTagUri;
  private final String sphericalStereoMode;

  public Uri getUri() {
    return uri;
  }

  public String getExtension() {
    return extension;
  }

  public String getAdTagUri() {
    return adTagUri;
  }

  public UriSample(
      String name,
      DrmInfo drmInfo,
      Uri uri,
      String extension,
      String adTagUri,
      String sphericalStereoMode) {
    super(name, drmInfo);
    this.uri = uri;
    this.extension = extension;
    this.adTagUri = adTagUri;
    this.sphericalStereoMode = sphericalStereoMode;
  }

  @Override
  public Intent buildIntent(Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
    Intent intent = super.buildIntent(context, preferExtensionDecoders, abrAlgorithm);
    intent.setData(uri);
    intent.putExtra(PlayerActivity.EXTENSION_EXTRA, extension);
    intent.putExtra(PlayerActivity.AD_TAG_URI_EXTRA, adTagUri);
    intent.putExtra(PlayerActivity.SPHERICAL_STEREO_MODE_EXTRA, sphericalStereoMode);
    intent.setAction(PlayerActivity.ACTION_VIEW);
    return intent;
  }
}
