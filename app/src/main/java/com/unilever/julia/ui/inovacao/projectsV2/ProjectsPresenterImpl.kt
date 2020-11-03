package com.unilever.julia.ui.inovacao.projectsV2

import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class ProjectsPresenterImpl<V : ProjectsView, I : ProjectsInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), ProjectsPresenter<V, I> {

    private var mData : MutableList<InnovationProjectsModel.Project> = arrayListOf()

    override fun getProjectsInnovations(sessionCode: String,
                                        code: String,
                                        marcaId: String,
                                        categoriaId: String,
                                        commodityId: String) {
        getView().addDisposable(
            getInteractor().getProjectsInnovations(sessionCode, code, marcaId, categoriaId, commodityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<MutableList<InnovationProjectsModel.Project>>() {
                    override fun onNext(value: MutableList<InnovationProjectsModel.Project>) {
                        mData = sortedProjectsByDate(value)
                        getView().initTabs(mData)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            getProjectsInnovations(sessionCode, code, marcaId, categoriaId, commodityId)
                        })
                    }
                })
        )
    }

    override fun getProducts(index: Int): MutableList<InnovationProjectsModel.Item> {
        return mData[index].items ?: arrayListOf()
    }

    override fun sortedProjectsByDate(items: MutableList<InnovationProjectsModel.Project>): MutableList<InnovationProjectsModel.Project> {

        val projects = arrayListOf<InnovationProjectsModel.Project>()

        for (project in items) {
            val copyProject = project.copy(items = null)

            val newItems = arrayListOf<InnovationProjectsModel.Item>()
            for (group in project.groupByMonthAndYearInDate()) {
                newItems.addAll(getItemsFirstItemChecked(group.value))
            }

            copyProject.items = newItems
            projects.add(copyProject)
        }

        return projects
    }

    private fun getItemsFirstItemChecked(items : List<InnovationProjectsModel.Item>): MutableList<InnovationProjectsModel.Item> {
        val list = arrayListOf<InnovationProjectsModel.Item>()
        for (item in items) {
            val indexOf = items.indexOf(item)
            if (indexOf == 0) {
                item.isTitle = true
            }
            list.add(item)
        }
        return list
    }
}