package com.unilever.julia.dagger

import com.unilever.julia.ui.agenda.AgendaActivity
import com.unilever.julia.ui.agenda.AgendaModule
import com.unilever.julia.ui.agenda.reuniao.AgendaReuniaoActivity
import com.unilever.julia.ui.agenda.reuniao.AgendaReuniaoModule
import com.unilever.julia.ui.boletim.area.AttendanceActivity
import com.unilever.julia.ui.boletim.area.AttendanceModule
import com.unilever.julia.ui.boletim.main.BoletimMainActivity
import com.unilever.julia.ui.boletim.main.BoletimMainModule
import com.unilever.julia.ui.boletim.multiple.BoletimMultipleActivity
import com.unilever.julia.ui.boletim.multiple.BoletimMultipleModule
import com.unilever.julia.ui.chat.ChatMainActivity
import com.unilever.julia.ui.chat.ChatMainModule
import com.unilever.julia.ui.clientes.ClientesActivity
import com.unilever.julia.ui.clientes.ClientesModule
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoModule
import com.unilever.julia.ui.inovacao.detailV2.DetailActivity
import com.unilever.julia.ui.inovacao.detailV2.DetailModule
import com.unilever.julia.ui.inovacao.detalhe.InovacaoDetalheActivity
import com.unilever.julia.ui.inovacao.detalhe.InovacaoDetalheModule
import com.unilever.julia.ui.inovacao.gallery.GalleryActivity
import com.unilever.julia.ui.inovacao.gallery.GalleryModule
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsActivity
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsModule
import com.unilever.julia.ui.inovacao.projetos.InovacaoProjetosActivity
import com.unilever.julia.ui.inovacao.projetos.InovacaoProjetosModule
import com.unilever.julia.ui.inovacao.tradestoryEvaluation.TradeStoryEvaluationActivity
import com.unilever.julia.ui.inovacao.tradestoryEvaluation.TradeStoryEvaluationModule
import com.unilever.julia.ui.ipv.IpvActivity
import com.unilever.julia.ui.ipv.IpvModule
import com.unilever.julia.ui.ipv.categoriesPriorities.CategoriesPrioritiesActivity
import com.unilever.julia.ui.ipv.categoriesPriorities.CategoriesPrioritiesModule
import com.unilever.julia.ui.ipv.management.ManagementActivity
import com.unilever.julia.ui.ipv.management.ManagementModule
import com.unilever.julia.ui.ipv.offersDetail.OffersDetailActivity
import com.unilever.julia.ui.ipv.offersDetail.OffersDetailModule
import com.unilever.julia.ui.ipv.products.IpvProductsActivity
import com.unilever.julia.ui.ipv.products.IpvProductsModule
import com.unilever.julia.ui.ipv.unitaryGroup.GroupUnitaryActivity
import com.unilever.julia.ui.ipv.unitaryGroup.GroupUnitaryModule
import com.unilever.julia.ui.login.LoginActivity
import com.unilever.julia.ui.login.LoginModule
import com.unilever.julia.ui.menu.configuracao.ConfiguracaoActivity
import com.unilever.julia.ui.menu.configuracao.ConfiguracaoModule
import com.unilever.julia.ui.menu.notificacao.NotificacaoActivity
import com.unilever.julia.ui.menu.notificacao.detail.NotificacaoDetailActivity
import com.unilever.julia.ui.menu.notificacao.detail.NotificacaoDetailModule
import com.unilever.julia.ui.menu.notificacao.NotificacaoModule
import com.unilever.julia.ui.pedidos.PedidosClienteActivity
import com.unilever.julia.ui.pedidos.PedidosClienteModule
import com.unilever.julia.ui.splash.SplashActivity
import com.unilever.julia.ui.splash.SplashModule
import com.unilever.julia.ui.status.detalhe.StatusDetalheActivity
import com.unilever.julia.ui.status.detalhe.StatusDetalheModule
import com.unilever.julia.ui.status.pendentes.StatusPendentesActivity
import com.unilever.julia.ui.status.pendentes.StatusPendentesModule
import com.unilever.julia.ui.tutorial.TutorialActivity
import com.unilever.julia.ui.tutorial.TutorialModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by andre.nascimento on 19/12/2018.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    internal abstract fun loginActivity(): LoginActivity
    
    @ActivityScoped
    @ContributesAndroidInjector(modules = [ChatMainModule::class])
    internal abstract fun chatMainActivity(): ChatMainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [InovacaoProjetosModule::class])
    internal abstract fun inovacaoProjetosActivity(): InovacaoProjetosActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [InovacaoDetalheModule::class])
    internal abstract fun inovacaoDetalheActivity(): InovacaoDetalheActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [StatusDetalheModule::class])
    internal abstract fun statusDetalheActivity(): StatusDetalheActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [StatusPendentesModule::class])
    internal abstract fun statusPendentesActivity(): StatusPendentesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AgendaModule::class])
    internal abstract fun agendaActivity(): AgendaActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PedidosClienteModule::class])
    internal abstract fun pedidosClienteActivity(): PedidosClienteActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FiltroPedidoModule::class])
    internal abstract fun filtroPedidoActivity(): FiltroPedidoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TutorialModule::class])
    internal abstract fun tutorialActivity(): TutorialActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AgendaReuniaoModule::class])
    internal abstract fun agendaReuniaoActivity(): AgendaReuniaoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ClientesModule::class])
    internal abstract fun clientesActivity(): ClientesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ProjectsModule::class])
    internal abstract fun projectsActivity(): ProjectsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DetailModule::class])
    internal abstract fun detailActivity(): DetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TradeStoryEvaluationModule::class])
    internal abstract fun tradeStoryEvaluationActivity(): TradeStoryEvaluationActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [GalleryModule::class])
    internal abstract fun galleryActivity(): GalleryActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ConfiguracaoModule::class])
    internal abstract fun configuracaoActivity(): ConfiguracaoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [NotificacaoModule::class])
    internal abstract fun notificacaoActivity(): NotificacaoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [NotificacaoDetailModule::class])
    internal abstract fun notificacaoDetailActivity(): NotificacaoDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [IpvModule::class])
    internal abstract fun ipvActivity(): IpvActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [CategoriesPrioritiesModule::class])
    internal abstract fun categoriesPrioritiesActivity(): CategoriesPrioritiesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [GroupUnitaryModule::class])
    internal abstract fun groupUnitaryActivity(): GroupUnitaryActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [IpvProductsModule::class])
    internal abstract fun ipvProductsActivity(): IpvProductsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [OffersDetailModule::class])
    internal abstract fun offersDetailActivity(): OffersDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ManagementModule::class])
    internal abstract fun managementActivity(): ManagementActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [BoletimMainModule::class])
    internal abstract fun boletimMainActivity(): BoletimMainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [BoletimMultipleModule::class])
    internal abstract fun boletimMultipleActivity(): BoletimMultipleActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AttendanceModule::class])
    internal abstract fun attendanceActivity(): AttendanceActivity
}