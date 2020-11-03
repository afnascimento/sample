package com.unilever.julia.ui.inovacao.gallery

import com.unilever.julia.ui.base.*

import javax.inject.Inject

class GalleryPresenterImpl<V : GalleryView, I : GalleryInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), GalleryPresenter<V, I> {


}