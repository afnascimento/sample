package com.unilever.julia.utils

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.components.model.InnovationCardHorizontalModel
import com.unilever.julia.components.model.InnovationCardVerticalModel
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.data.model.inovacaoDetalhe.Produto
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.agenda.AgendaActivity
import com.unilever.julia.ui.agenda.reuniao.AgendaReuniaoActivity
import com.unilever.julia.ui.boletim.area.AttendanceActivity
import com.unilever.julia.ui.boletim.main.BoletimMainActivity
import com.unilever.julia.ui.boletim.multiple.BoletimMultipleActivity
import com.unilever.julia.ui.chat.ChatMainActivity
import com.unilever.julia.ui.clientes.ClientesActivity
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import com.unilever.julia.ui.inovacao.detailV2.DetailActivity
import com.unilever.julia.ui.inovacao.detalhe.InovacaoDetalheActivity
import com.unilever.julia.ui.inovacao.gallery.GalleryActivity
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsActivity
import com.unilever.julia.ui.inovacao.projetos.InovacaoProjetosActivity
import com.unilever.julia.ui.inovacao.tradestoryEvaluation.TradeStoryEvaluationActivity
import com.unilever.julia.ui.ipv.IpvActivity
import com.unilever.julia.ui.ipv.categoriesPriorities.CategoriesPrioritiesActivity
import com.unilever.julia.ui.ipv.management.ManagementActivity
import com.unilever.julia.ui.ipv.offersDetail.OffersDetailActivity
import com.unilever.julia.ui.ipv.products.IpvProductsActivity
import com.unilever.julia.ui.ipv.unitaryGroup.GroupUnitaryActivity
import com.unilever.julia.ui.login.LoginActivity
import com.unilever.julia.ui.menu.configuracao.ConfiguracaoActivity
import com.unilever.julia.ui.menu.notificacao.NotificacaoActivity
import com.unilever.julia.ui.menu.notificacao.detail.NotificacaoDetailActivity
import com.unilever.julia.ui.pedidos.PedidosClienteActivity
import com.unilever.julia.ui.splash.SplashActivity
import com.unilever.julia.ui.status.detalhe.StatusDetalheActivity
import com.unilever.julia.ui.status.detalheTransporte.DetalheTransporteActivity
import com.unilever.julia.ui.status.pendentes.StatusPendentesActivity
import com.unilever.julia.ui.tutorial.TutorialActivity

class RedirectIntents {

    companion object {

        const val EXTRA_INNOVATION_DETAIL : String = "EXTRA_INNOVATION_DETAIL"

        const val EXTRA_LOGOUT : String = "EXTRA_LOGOUT"

        const val EXTRA_SESSION_CODE : String = "EXTRA_SESSION_CODE"

        const val EXTRA_CODE : String = "EXTRA_CODE"

        const val EXTRA_MARCA_ID : String = "EXTRA_MARCA_ID"

        const val EXTRA_CATEGORIA_ID : String = "EXTRA_CATEGORIA_ID"

        const val EXTRA_COMMODITY : String = "EXTRA_COMMODITY"

        const val EXTRA_INOVACAO_DETALHE : String = "EXTRA_INOVACAO_DETALHE"

        const val EXTRA_AGENDA : String = "EXTRA_AGENDA"

        const val EXTRA_STATUS : String = "EXTRA_STATUS"

        const val EXTRA_PEDIDO_CLIENTE : String = "EXTRA_PEDIDO_CLIENTE"

        const val EXTRA_FILTRO : String = "EXTRA_FILTRO"

        const val EXTRA_STATUS_PEDIDO_ITEM : String = "EXTRA_STATUS_PEDIDO_ITEM"

        const val EXTRA_STATUS_ENUM : String = "EXTRA_STATUS_ENUM"

        const val EXTRA_AGENDA_OUTLOOK_SUCESSO : String = "EXTRA_AGENDA_OUTLOOK_SUCESSO"

        const val EXTRA_AGENDA_OUTLOOK_EXCLUSAO : String = "EXTRA_AGENDA_OUTLOOK_EXCLUSAO"

        const val EXTRA_AGENDA_OUTLOOK_CONTEXT : String = "EXTRA_AGENDA_OUTLOOK_CONTEXT"

        const val EXTRA_NOTIFICATION : String = "EXTRA_NOTIFICATION"

        const val EXTRA_NOTIFICATION_PARAM : String = "EXTRA_NOTIFICATION_PARAM"

        const val EXTRA_INTENTION : String = "EXTRA_INTENTION"

        const val EXTRA_TERRITORY : String = "EXTRA_TERRITORY"

        const val EXTRA_GALLERY_INDEX : String = "EXTRA_GALLERY_INDEX"

        const val EXTRA_GALLERY_LIST : String = "EXTRA_GALLERY_LIST"

        const val EXTRA_ITEM_NOTIFICACAO : String = "EXTRA_ITEM_NOTIFICACAO"

        fun redirectClientesActivity(activity: Activity, requestCode: Int, sessionCode: String, intention: String, territory: String) {
            val intent = Intent(activity, ClientesActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            intent.putExtra(EXTRA_INTENTION, intention)
            intent.putExtra(EXTRA_TERRITORY, territory)
            activity.startActivityForResult(intent, requestCode)
        }

        fun redirectAgendaReuniaoActivity(activity: Activity, sessionCode: String, agenda: AgendaOutlookContext?, requestCode: Int) {
            val intent = Intent(activity, AgendaReuniaoActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            if (agenda != null) {
                intent.putExtra(EXTRA_AGENDA_OUTLOOK_CONTEXT, agenda)
            }
            activity.startActivityForResult(intent, requestCode)
        }

        fun redirectTutorialActivity(activity: Activity) {
            val intent = Intent(activity, TutorialActivity::class.java)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectLoginActivity(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectChatMainActivity(activity: Activity) {
            val intent = Intent(activity, ChatMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectChatMainActivity(activity: Activity, notification: FirebaseNotification) {
            val intent = Intent(activity, ChatMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(EXTRA_NOTIFICATION, notification)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectNotificationDetailActivity(activity: Activity, notification: FirebaseNotification) {
            val intent = Intent(activity, NotificacaoDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(EXTRA_NOTIFICATION, notification)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectNotificationDetailActivity(activity: Activity, requestCode: Int, model: Notification, sessionCode: String) {
            val intent = Intent(activity, NotificacaoDetailActivity::class.java)
            intent.putExtra(EXTRA_ITEM_NOTIFICACAO, model)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            activity.startActivityForResult(intent, requestCode)
        }

        fun backToChatMainActivity(activity: Activity, text: String) {
            val intent = Intent(activity, ChatMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(EXTRA_INTENTION, text)
            activity.startActivity(intent)
        }

        fun redirectNotificationsActivity(activity: Activity, sessionCode: String) {
            val intent = Intent(activity, NotificacaoActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            activity.startActivity(intent)
        }

        fun redirectConfigurationActivity(activity: Activity) {
            val intent = Intent(activity, ConfiguracaoActivity::class.java)
            activity.startActivity(intent)
        }

        fun redirectLogout(activity: Activity) {
            val intent = Intent(activity, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(EXTRA_LOGOUT, true)
            activity.startActivity(intent)
            ActivityCompat.finishAffinity(activity)
        }

        fun redirectInovacoesProjetosActivity(activity: Activity, inovacao : InnovationCardVerticalModel.Item) {
            val intent = Intent(activity, InovacaoProjetosActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, inovacao.reference.sessionCode)
            intent.putExtra(EXTRA_CODE, inovacao.reference.code)
            intent.putExtra(EXTRA_MARCA_ID, inovacao.reference.marcaId)
            intent.putExtra(EXTRA_CATEGORIA_ID, inovacao.reference.categoriaId)
            intent.putExtra(EXTRA_COMMODITY, inovacao.reference.commodityId)
            activity.startActivity(intent)
        }

        fun redirectInnovationsProjectsActivity(activity: Activity, model : InnovationCardHorizontalModel.Item) {
            val intent = Intent(activity, ProjectsActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, model.reference.sessionCode)
            intent.putExtra(EXTRA_CODE, model.reference.code)
            intent.putExtra(EXTRA_MARCA_ID, model.reference.marcaId)
            intent.putExtra(EXTRA_CATEGORIA_ID, model.reference.categoriaId)
            intent.putExtra(EXTRA_COMMODITY, model.reference.commodityId)
            activity.startActivity(intent)
        }

        fun redirectInnovationsDetailProjectActivity(activity: Activity, model: InnovationProjectsModel.Item) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_INNOVATION_DETAIL, model)
            activity.startActivity(intent)
        }

        fun redirectInovacaoDetalheActivity(activity: Activity, inovacaoDetalhe: InovacaoProjetoModel) {
            val intent = Intent(activity, InovacaoDetalheActivity::class.java)
            intent.putExtra(EXTRA_INOVACAO_DETALHE, inovacaoDetalhe)
            activity.startActivity(intent)
        }

        fun redirectAgendaActivity(activity: Activity, model: AgendaModel.Item, requestCode : Int, sessionCode: String) {
            val intent = Intent(activity, AgendaActivity::class.java)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            intent.putExtra(EXTRA_AGENDA, model)
            activity.startActivityForResult(intent, requestCode)
            //activity.startActivity(intent)
        }

        fun redirectStatusDetalheActivity(activity: Activity, model: StatusPedidoModel.Item, sessionCode: String) {
            val intent = Intent(activity, StatusDetalheActivity::class.java)
            intent.putExtra(EXTRA_STATUS, model)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            activity.startActivity(intent)
        }

        fun redirectStatusPendentesActivity(activity: Activity, model: StatusPedidoModel.Item, sessionCode: String) {
            val intent = Intent(activity, StatusPendentesActivity::class.java)
            intent.putExtra(EXTRA_STATUS, model)
            intent.putExtra(EXTRA_SESSION_CODE, sessionCode)
            activity.startActivity(intent)
        }

        fun redirectPedidosClienteActivity(activity: Activity, model: PedidosClienteModel, requestCode: Int) {
            val intent = Intent(activity, PedidosClienteActivity::class.java)
            intent.putExtra(EXTRA_PEDIDO_CLIENTE, model)
            activity.startActivityForResult(intent, requestCode)
        }

        fun redirectFiltroPedidoActivity(activity: Activity, model: FiltroModel, requestCode: Int) {
            val intent = Intent(activity, FiltroPedidoActivity::class.java)
            intent.putExtra(EXTRA_FILTRO, model)
            activity.startActivityForResult(intent, requestCode)
        }

        fun redirectDetalheTransporteActivity(activity: Activity, model: StatusPedidoItem, tipoStatus: StatusEnum) {
            val intent = Intent(activity, DetalheTransporteActivity::class.java)
            intent.putExtra(EXTRA_STATUS_PEDIDO_ITEM, model)
            intent.putExtra(EXTRA_STATUS_ENUM, tipoStatus)
            activity.startActivity(intent)
        }

        const val EXTRA_IDENTIFIER = "EXTRA_IDENTIFIER"

        fun redirectTradeStoreEvalution(activity: Activity, identifier: String) {
            val intent = Intent(activity, TradeStoryEvaluationActivity::class.java)
            intent.putExtra(EXTRA_IDENTIFIER, identifier)
            activity.startActivity(intent)
        }

        fun redirectGalleryInnovations(activity: Activity, index: Int, products: List<Produto>) {
            val intent = Intent(activity, GalleryActivity::class.java)
            intent.putExtra(EXTRA_GALLERY_INDEX, index)

            val array : ArrayList<Produto> = arrayListOf()
            array.addAll(products)

            intent.putParcelableArrayListExtra(EXTRA_GALLERY_LIST, array)
            activity.startActivity(intent)
        }

        const val EXTRA_IPV_ITEMS = "EXTRA_IPV_ITEMS"

        fun redirectIpvActivity(activity: Activity, ipvContext: IpvContext, items: List<IpvItem>) {
            val intent = Intent(activity, IpvActivity::class.java)
            val arrayList : ArrayList<IpvItem> = arrayListOf()
            arrayList.addAll(items)
            intent.putExtra(EXTRA_IPV_CONTEXT, ipvContext)
            intent.putParcelableArrayListExtra(EXTRA_IPV_ITEMS, arrayList)
            activity.startActivity(intent)
        }

        const val EXTRA_IPV_CLIENT = "EXTRA_IPV_CLIENT"

        fun redirectCategoriesPrioritiesActivity(activity: Activity, client: IpvClient) {
            val intent = Intent(activity, CategoriesPrioritiesActivity::class.java)
            intent.putExtra(EXTRA_IPV_CLIENT, client)
            activity.startActivity(intent)
        }

        const val EXTRA_IPV_CONTEXT = "EXTRA_IPV_CONTEXT"

        const val EXTRA_IPV_HEADER_DESC = "EXTRA_IPV_HEADER_DESC"

        fun redirectGroupUnitaryActivity(activity: Activity, client: IpvClient, ipvContext: IpvContext, headerDescription: String) {
            val intent = Intent(activity, GroupUnitaryActivity::class.java)
            intent.putExtra(EXTRA_IPV_CLIENT, client)
            intent.putExtra(EXTRA_IPV_CONTEXT, ipvContext)
            intent.putExtra(EXTRA_IPV_HEADER_DESC, headerDescription)
            activity.startActivity(intent)
        }

        fun redirectIpvProductsActivity(activity: Activity, client: IpvClient, ipvContext: IpvContext, headerDescription: String) {
            val intent = Intent(activity, IpvProductsActivity::class.java)
            intent.putExtra(EXTRA_IPV_CLIENT, client)
            intent.putExtra(EXTRA_IPV_CONTEXT, ipvContext)
            intent.putExtra(EXTRA_IPV_HEADER_DESC, headerDescription)
            activity.startActivity(intent)
        }

        const val EXTRA_IPV_OFFER = "EXTRA_IPV_OFFER"

        fun redirectIpvOffersDetailActivity(activity: Activity, ipvOffer: IpvOffer) {
            val intent = Intent(activity, OffersDetailActivity::class.java)
            intent.putExtra(EXTRA_IPV_OFFER, ipvOffer)
            activity.startActivity(intent)
        }

        fun redirectManagementActivity(activity: Activity, ipvContext: IpvContext) {
            val intent = Intent(activity, ManagementActivity::class.java)
            intent.putExtra(EXTRA_IPV_CONTEXT, ipvContext)
            activity.startActivityForResult(intent, 0)
        }

        const val EXTRA_BOLETIMCHATMODEL = "EXTRA_BOLETIMCHATMODEL"
        const val EXTRA_BOLETIM_FILTROS = "EXTRA_BOLETIM_FILTROS"

        fun redirectBoletimMainActivity(activity: Activity,
                                        territory: String,
                                        attendanceFilter: AttendanceFilter?,
                                        filters: BoletimFiltros?,
                                        requestCode: Int) {
            val intent = Intent(activity, BoletimMainActivity::class.java)
            intent.putExtra(EXTRA_TERRITORY, territory)
            intent.putExtra(EXTRA_ATTENDANCE_FILTER, attendanceFilter)
            intent.putExtra(EXTRA_BOLETIM_FILTROS, filters)
            activity.startActivityForResult(intent, requestCode)
        }

        const val EXTRA_ARRAY_STRING = "EXTRA_ARRAY_STRING"

        const val EXTRA_ATTENDANCE_FILTER = "EXTRA_ATTENDANCE_FILTER"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_HINT = "EXTRA_HINT"
        const val EXTRA_TYPE = "EXTRA_TYPE"
        const val EXTRA_ATTENDANCE = "EXTRA_ATTENDANCE"

        fun redirectBoletimMultipleActivity(activity: Activity,
                                            requestCode: Int,
                                            type : BulletinsMultipleFiltersResponse.Type,
                                            toolbarText : String,
                                            hintText: String,
                                            attendanceFilter: AttendanceFilter
        ) {
            val intent = Intent(activity, BoletimMultipleActivity::class.java)
            intent.putExtra(EXTRA_TITLE, toolbarText)
            intent.putExtra(EXTRA_HINT, hintText)
            intent.putExtra(EXTRA_TYPE, type.ordinal)
            intent.putExtra(EXTRA_ATTENDANCE_FILTER, attendanceFilter)
            activity.startActivityForResult(intent, requestCode)
        }

        fun redirectAttendanceActivity(activity: Activity, myTerritory: String, requestCode: Int) {
            val intent = Intent(activity, AttendanceActivity::class.java)
            intent.putExtra(EXTRA_TERRITORY, myTerritory)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}