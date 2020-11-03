package com.unilever.julia.video;

import android.content.Context;
import android.content.Intent;

public class PlaylistSample extends Sample {

  private final UriSample[] children;

  public PlaylistSample(String name, DrmInfo drmInfo, UriSample... children) {
    super(name, drmInfo);
    this.children = children;
  }

  @Override
  public Intent buildIntent(Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
    String[] uris = new String[children.length];
    String[] extensions = new String[children.length];
    for (int i = 0; i < children.length; i++) {
      uris[i] = children[i].getUri().toString();
      extensions[i] = children[i].getExtension();
    }
    Intent intent = super.buildIntent(context, preferExtensionDecoders, abrAlgorithm);
    intent.putExtra(PlayerActivity.URI_LIST_EXTRA, uris);
    intent.putExtra(PlayerActivity.EXTENSION_LIST_EXTRA, extensions);
    intent.setAction(PlayerActivity.ACTION_VIEW_LIST);
    return intent;
  }
}