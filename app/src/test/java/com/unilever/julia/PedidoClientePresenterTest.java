package com.unilever.julia;

import com.unilever.julia.data.enums.FiltroOrdenacaoEnum;
import com.unilever.julia.data.model.FiltroModel;
import com.unilever.julia.data.model.PedidoClienteResponse;
import com.unilever.julia.ui.pedidos.PedidosClienteInteractor;
import com.unilever.julia.ui.pedidos.PedidosClientePresenter;
import com.unilever.julia.ui.pedidos.PedidosClientePresenterImpl;
import com.unilever.julia.ui.pedidos.PedidosClienteView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class PedidoClientePresenterTest {

    @Mock
    private PedidosClienteView mView;

    @Mock
    private PedidosClienteInteractor mInteractor;

    private PedidosClientePresenter<PedidosClienteView, PedidosClienteInteractor> mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new PedidosClientePresenterImpl<>(mView, mInteractor);
    }

    @Test
    public void filtrarDados_test() {
        List<PedidoClienteResponse> dataSet = new ArrayList<>();
        //dataSet.add(new PedidoClienteResponse(300.70));
        //dataSet.add(new PedidoClienteResponse(800.70));
        //dataSet.add(new PedidoClienteResponse(400.70));
        dataSet.add(new PedidoClienteResponse());
        dataSet.add(new PedidoClienteResponse("01/02/2019"));
        dataSet.add(new PedidoClienteResponse("23/01/2019"));
        dataSet.add(new PedidoClienteResponse("19/03/2019"));

        FiltroModel filtro = new FiltroModel();
        filtro.setOrdenacao(FiltroOrdenacaoEnum.DATA_MAIS_RECENTE);

        mPresenter.filtrarDados(filtro, dataSet);
        ///Conversations conversations = new Gson().fromJson(json, Conversations.class);
        //mPresenter.addConversationInView(conversations);

        //InnovationCardVerticalModel model = customerArgument1.getValue();
        //Assert.assertEquals(1, model.getItems().size());
    }
}
