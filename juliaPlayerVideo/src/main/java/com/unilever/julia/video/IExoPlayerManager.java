package com.unilever.julia.video;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;

public interface IExoPlayerManager {

    @Nullable
    DownloadManager getDownloadManager();

    @NonNull
    DownloadTracker getDownloadTracker();

    @NonNull
    RenderersFactory buildRenderersFactory(boolean preferExtensionRenderer);

    @NonNull
    HttpDataSource.Factory buildHttpDataSourceFactory();

    @NonNull
    DataSource.Factory buildDataSourceFactory();
}
