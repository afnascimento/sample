package com.unilever.julia;

import com.google.gson.Gson;
import com.unilever.julia.data.model.ClienteModel;
import com.unilever.julia.ui.clientes.ClientesInteractor;
import com.unilever.julia.ui.clientes.ClientesPresenter;
import com.unilever.julia.ui.clientes.ClientesPresenterImpl;
import com.unilever.julia.ui.clientes.ClientesView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ClientesPresenterTest {

    @Mock
    private ClientesView mView;

    @Mock
    private ClientesInteractor mInteractor;

    private ClientesPresenter<ClientesView, ClientesInteractor> mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ClientesPresenterImpl<>(mView, mInteractor);
    }

    private static final String JSON = "[\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 89.91,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10770916\",\n" +
            "      \"name\": \"SERVIMED COML LTDA                 \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 89.56,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30156800\",\n" +
            "      \"name\": \"SERVIMED COML LTDA                 \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 46.93,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10011952\",\n" +
            "      \"name\": \"GENESIO A MENDES E CIA LTDA        \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10606121\",\n" +
            "      \"name\": \"DROG H FARM LTDA                   \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 69.79,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30234263\",\n" +
            "      \"name\": \"GENESIO A MENDES E CIA LTDA        \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 73.34,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30528510\",\n" +
            "      \"name\": \"GAUCHAFARMA MEDICAMENTOS LTDA      \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 31.81,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10013692\",\n" +
            "      \"name\": \"MERC FATIMA DE NILOPOLIS LTDA EPP  \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 33.09,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10044595\",\n" +
            "      \"name\": \"SUPERM REAL DE EDEN LTDA           \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 26.7,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10066399\",\n" +
            "      \"name\": \"ACOUGUE MERCE N SRA A BRASILIA LTDA\",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 41.54,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10453839\",\n" +
            "      \"name\": \"TORRE E CIA SUPERM S A             \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 44.12,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10473949\",\n" +
            "      \"name\": \"M M DE FRAGOSO COMEST LTDA         \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 20.27,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10524786\",\n" +
            "      \"name\": \"TORRE E CIA SUPERM S A             \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10588724\",\n" +
            "      \"name\": \"SUPERM PADRAO DO FONSECA LTDA      \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 37.78,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10656371\",\n" +
            "      \"name\": \"RAMIGOS SUPERM LTDA                \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 50.11,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10752207\",\n" +
            "      \"name\": \"SUPERM BARRA OESTE LTDA            \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 28.1,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30393520\",\n" +
            "      \"name\": \"CRUZEIRO DO SUL MERC LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.54,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30402756\",\n" +
            "      \"name\": \"GMAP SUPERM LTDA                   \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 39.44,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30416227\",\n" +
            "      \"name\": \"SUPERM ALVORADA EIRELI             \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.03,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30558208\",\n" +
            "      \"name\": \"SUPERM PADRAO DO FONSECA EIRELI    \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 40.52,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10562194\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 16.21,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10562329\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 4.63,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10568274\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 34.92,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10570429\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 42.42,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10769889\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10778195\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 57.75,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10826503\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 48.97,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30479776\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 65.6,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30486905\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30490992\",\n" +
            "      \"name\": \"WMS SUPERM DO BRASIL LTDA          \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007277\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 14.87,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007280\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 29.48,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007282\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 45.57,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007285\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.64,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007286\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 57.36,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007288\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 20.18,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007289\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 37.61,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007293\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 19.04,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10007298\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.18,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10236942\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 61.26,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10247732\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.51,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10342487\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 35.32,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10458107\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 13.01,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10474792\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 14.46,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10497684\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 19.68,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10575635\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 18.22,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10591975\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 53.29,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10599829\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10627167\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 50.71,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10648190\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10660764\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 37.95,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10674922\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 27.99,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10699528\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 0,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10755982\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 50.27,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10776071\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 36.02,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10805221\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 6.38,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10836847\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 10.03,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10843039\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 33.58,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"10843402\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 21.11,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30133262\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 1.39,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30141279\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 34.1,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30238395\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 13.01,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30294741\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 61.44,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30352415\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"customer\": {\n" +
            "      \"value\": 60.46,\n" +
            "      \"color\": \"#78BE20\",\n" +
            "      \"code\": \"30445129\",\n" +
            "      \"name\": \"ATACADAO S A                       \",\n" +
            "      \"adress\": \"\"\n" +
            "    },\n" +
            "    \"code\": \"13_STATUS_PEDIDO_CARTEIRA_LISTA\"\n" +
            "  }\n" +
            "]";

    @Test
    public void sortItems_test() {
        ClienteModel[] array = new Gson().fromJson(JSON, ClienteModel[].class);
        List<ClienteModel> sortItems = mPresenter.sortItems(false, Arrays.asList(array));
        for (ClienteModel it: sortItems) {
            if (it.getCustomer() != null) {
                double value = it.getCustomer().getValue() == null ? 0.0 : it.getCustomer().getValue();
                System.out.println(value);
            }
        }
    }
}
